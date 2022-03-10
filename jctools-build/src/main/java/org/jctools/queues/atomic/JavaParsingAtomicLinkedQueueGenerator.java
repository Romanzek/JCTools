package org.jctools.queues.atomic;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Modifier.Keyword;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithType;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;

/**
 * This generator takes in an JCTools 'LinkedQueue' Java source file and patches {@link sun.misc.Unsafe} accesses into
 * atomic {@link java.util.concurrent.atomic.AtomicLongFieldUpdater}. It outputs a Java source file with these patches.
 * <p>
 * An 'LinkedQueue' is one that is backed by a linked list and use a <code>producerNode</code> and a
 * <code>consumerNode</code> field to track the positions of each.
 */
public final class JavaParsingAtomicLinkedQueueGenerator extends JavaParsingAtomicQueueGenerator {
    private static final String MPSC_LINKED_ATOMIC_QUEUE_NAME = "MpscLinkedAtomicQueue";

    public static void main(String[] args) throws Exception {
        main(JavaParsingAtomicLinkedQueueGenerator.class, args);
    }

    JavaParsingAtomicLinkedQueueGenerator(String sourceFileName) {
        super(sourceFileName);
    }

    @Override
    public void visit(ConstructorDeclaration n, Void arg) {
        super.visit(n, arg);
        // Update the ctor to match the class name
        n.setName(translateQueueName(n.getNameAsString()));
        if (MPSC_LINKED_ATOMIC_QUEUE_NAME.equals(n.getNameAsString())) {
            // Special case for MPSC because the Unsafe variant has a static factory method and a protected constructor.
            n.setModifier(Keyword.PROTECTED, false);
            n.setModifier(Keyword.PUBLIC, true);
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration node, Void arg) {
        super.visit(node, arg);

        replaceParentClassesForAtomics(node);

        node.setName(translateQueueName(node.getNameAsString()));
        if (MPSC_LINKED_ATOMIC_QUEUE_NAME.equals(node.getNameAsString())) {
            /*
             * Special case for MPSC
             */
            node.removeModifier(Keyword.ABSTRACT);
        }

        if (isCommentPresent(node, GEN_DIRECTIVE_CLASS_CONTAINS_ORDERED_FIELD_ACCESSORS)) {
            node.setComment(null);
            removeStaticFieldsAndInitialisers(node);
            patchAtomicFieldUpdaterAccessorMethods(node);
        }

        for (MethodDeclaration method : node.getMethods()) {
            if (isCommentPresent(method, GEN_DIRECTIVE_METHOD_IGNORE)) {
                method.remove();
            }
        }

        node.setJavadocComment(formatMultilineJavadoc(0,
                "NOTE: This class was automatically generated by "
                        + JavaParsingAtomicLinkedQueueGenerator.class.getName(),
                "which can found in the jctools-build module. The original source file is " + sourceFileName + ".")
                + node.getJavadocComment().orElse(new JavadocComment("")).getContent());
    }

    @Override
    public void visit(CastExpr n, Void arg) {
        super.visit(n, arg);

        if (isRefArray(n.getType(), "E")) {
            n.setType(atomicRefArrayType((ArrayType) n.getType()));
        }
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        super.visit(n, arg);
        // Replace the return type of a method with altered types
        processSpecialNodeTypes(n);
    }

    @Override
    public void visit(ObjectCreationExpr n, Void arg) {
        super.visit(n, arg);
        processSpecialNodeTypes(n);
    }

    String translateQueueName(String originalQueueName) {
        if (originalQueueName.length() < 5) {
            return originalQueueName;
        }

        if (originalQueueName.contains("LinkedQueue") || originalQueueName.contains("LinkedArrayQueue")) {
            return originalQueueName.replace("Linked", "LinkedAtomic");
        }

        if (originalQueueName.contains("ArrayQueue")) {
            return originalQueueName.replace("ArrayQueue", "AtomicArrayQueue");
        }

        return originalQueueName;
    }

    String fieldUpdaterFieldName(String fieldName) {
        switch (fieldName) {
        case "producerNode":
            return "P_NODE_UPDATER";
        case "consumerNode":
            return "C_NODE_UPDATER";
        case "consumerIndex":
            return "C_INDEX_UPDATER";
        case "producerIndex":
            return "P_INDEX_UPDATER";
        case "producerLimit":
            return "P_LIMIT_UPDATER";
        default:
            throw new IllegalArgumentException("Unhandled field: " + fieldName);
        }
    }

    void organiseImports(CompilationUnit cu) {
        List<ImportDeclaration> importDecls = new ArrayList<>();
        for (ImportDeclaration importDeclaration : cu.getImports()) {
            String name = importDeclaration.getNameAsString();
            if (name.startsWith("org.jctools.util.Unsafe")) {
                continue;
            }

            if (name.startsWith("org.jctools.queues.LinkedArrayQueueUtil")) {
                continue;
            }

            importDecls.add(importDeclaration);
        }
        cu.getImports().clear();
        for (ImportDeclaration importDecl : importDecls) {
            cu.addImport(importDecl);
        }
        cu.addImport(importDeclaration("java.util.concurrent.atomic.AtomicReferenceFieldUpdater"));
        cu.addImport(importDeclaration("java.util.concurrent.atomic.AtomicLongFieldUpdater"));
        cu.addImport(importDeclaration("java.util.concurrent.atomic.AtomicReferenceArray"));

        cu.addImport(importDeclaration("org.jctools.queues.MessagePassingQueue"));
        cu.addImport(importDeclaration("org.jctools.queues.MessagePassingQueue.Supplier"));
        cu.addImport(importDeclaration("org.jctools.queues.MessagePassingQueueUtil"));
        cu.addImport(importDeclaration("org.jctools.queues.QueueProgressIndicators"));
        cu.addImport(importDeclaration("org.jctools.queues.IndexedQueueSizeUtil"));
        cu.addImport(staticImportDeclaration("org.jctools.queues.atomic.AtomicQueueUtil"));
    }

    /**
     * Given a variable declaration of some sort, check it's name and type and
     * if it looks like any of the key type changes between unsafe and atomic
     * queues, perform the conversion to change it's type.
     */
    void processSpecialNodeTypes(NodeWithType<?, Type> node, String name) {
        Type type = node.getType();
        if (node instanceof MethodDeclaration && ("newBufferAndOffset".equals(name) || "nextArrayOffset".equals(name))) {
            node.setType(PrimitiveType.intType());
        } else if (PrimitiveType.longType().equals(type)) {
            switch(name) {
            case "offset":
            case "offsetInNew":
            case "offsetInOld":
            case "lookAheadElementOffset":
                node.setType(PrimitiveType.intType());
            }
        } else if (isRefType(type, "LinkedQueueNode")) {
            node.setType(simpleParametricType("LinkedQueueAtomicNode", "E"));
        } else if (isRefArray(type, "E")) {
            node.setType(atomicRefArrayType((ArrayType) type));
        }
    }

    /**
     * Searches all extended or implemented super classes or interfaces for
     * special classes that differ with the atomics version and replaces them
     * with the appropriate class.
     */
    private void replaceParentClassesForAtomics(ClassOrInterfaceDeclaration n) {
        replaceParentClassesForAtomics(n.getExtendedTypes());
        replaceParentClassesForAtomics(n.getImplementedTypes());
    }

    private void replaceParentClassesForAtomics(NodeList<ClassOrInterfaceType> types) {
        for (ClassOrInterfaceType parent : types) {
            if ("BaseLinkedQueue".equals(parent.getNameAsString())) {
                parent.setName("BaseLinkedAtomicQueue");
            } else {
                // Padded super classes are to be renamed and thus so does the
                // class we must extend.
                parent.setName(translateQueueName(parent.getNameAsString()));
            }
        }
    }

    /**
     * For each method accessor to a field, add in the calls necessary to
     * AtomicFieldUpdaters. Only methods start with so/cas/sv/lv/lp/sp/xchg
     * followed by the field name are processed. Clearly <code>lv<code>,
     * <code>lp<code> and <code>sv<code> are simple field accesses with only
     * <code>so and <code>cas <code> using the AtomicFieldUpdaters.
     *
     * @param n the AST node for the containing class
     */
    private void patchAtomicFieldUpdaterAccessorMethods(ClassOrInterfaceDeclaration n) {
        String className = n.getNameAsString();

        for (FieldDeclaration field : n.getFields()) {
            if (field.getModifiers().contains(Modifier.staticModifier())) {
                // Ignore statics
                continue;
            }

            boolean usesFieldUpdater = false;
            for (VariableDeclarator variable : field.getVariables()) {
                String variableName = variable.getNameAsString();

                String methodNameSuffix = capitalise(variableName);

                for (MethodDeclaration method : n.getMethods()) {
                    String methodName = method.getNameAsString();
                    if (!methodName.endsWith(methodNameSuffix)) {
                        // Leave it untouched
                        continue;
                    }

                    String newValueName = "newValue";
                    if (methodName.startsWith("so") || methodName.startsWith("sp")) {
                        /*
                         * In the case of 'sp' use lazySet as the weakest
                         * ordering allowed by field updaters
                         */
                        usesFieldUpdater = true;
                        String fieldUpdaterFieldName = fieldUpdaterFieldName(variableName);

                        method.setBody(fieldUpdaterLazySet(fieldUpdaterFieldName, newValueName));
                    } else if (methodName.startsWith("cas")) {
                        usesFieldUpdater = true;
                        String fieldUpdaterFieldName = fieldUpdaterFieldName(variableName);
                        String expectedValueName = "expect";
                        method.setBody(
                                fieldUpdaterCompareAndSet(fieldUpdaterFieldName, expectedValueName, newValueName));
                    } else if (methodName.startsWith("sv")) {
                        method.setBody(fieldAssignment(variableName, newValueName));
                    } else if (methodName.startsWith("lv") || methodName.startsWith("lp")) {
                        method.setBody(returnField(variableName));
                    } else {
                        throw new IllegalStateException("Unhandled method: " + methodName);
                    }
                }

                if ("producerNode".equals(variableName)) {
                    usesFieldUpdater = true;
                    String fieldUpdaterFieldName = fieldUpdaterFieldName(variableName);

                    MethodDeclaration method = n.addMethod("xchgProducerNode", Keyword.PROTECTED, Keyword.FINAL);
                    method.setType(simpleParametricType("LinkedQueueAtomicNode", "E"));
                    method.addParameter(simpleParametricType("LinkedQueueAtomicNode", "E"), "newValue");
                    method.setBody(fieldUpdaterGetAndSet(fieldUpdaterFieldName, "newValue"));
                }

                if (usesFieldUpdater) {
                    if (PrimitiveType.longType().equals(variable.getType())) {
                        n.getMembers().add(0, declareLongFieldUpdater(className, variableName));
                    } else {
                        n.getMembers().add(0, declareRefFieldUpdater(className, variableName));
                    }
                }
            }

            if (usesFieldUpdater) {
                field.addModifier(Keyword.VOLATILE);
            }
        }
    }

    /**
     * Generates something like
     * <code>return P_INDEX_UPDATER.getAndSet(this, newValue)</code>
     *
     * @param fieldUpdaterFieldName
     * @param newValueName
     * @return
     */
    private BlockStmt fieldUpdaterGetAndSet(String fieldUpdaterFieldName, String newValueName) {
        BlockStmt body = new BlockStmt();
        body.addStatement(new ReturnStmt(
                methodCallExpr(fieldUpdaterFieldName, "getAndSet", new ThisExpr(), new NameExpr(newValueName))));
        return body;
    }

    /**
     * Generates something like
     * <code>private static final AtomicReferenceFieldUpdater<MpmcAtomicArrayQueueProducerNodeField> P_NODE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(MpmcAtomicArrayQueueProducerNodeField.class, "producerNode");</code>
     *
     * @param className
     * @param variableName
     * @return
     */
    private FieldDeclaration declareRefFieldUpdater(String className, String variableName) {
        MethodCallExpr initializer = newAtomicRefFieldUpdater(className, variableName);

        ClassOrInterfaceType type = simpleParametricType("AtomicReferenceFieldUpdater", className,
                "LinkedQueueAtomicNode");
        FieldDeclaration newField = fieldDeclarationWithInitialiser(type, fieldUpdaterFieldName(variableName),
                initializer, Keyword.PRIVATE, Keyword.STATIC, Keyword.FINAL);
        return newField;
    }

    private MethodCallExpr newAtomicRefFieldUpdater(String className, String variableName) {
        return methodCallExpr("AtomicReferenceFieldUpdater", "newUpdater", new ClassExpr(classType(className)),
                new ClassExpr(classType("LinkedQueueAtomicNode")), new StringLiteralExpr(variableName));
    }

    private ClassOrInterfaceType atomicRefArrayType(ArrayType in) {
        ClassOrInterfaceType out = new ClassOrInterfaceType(null, "AtomicReferenceArray");
        out.setTypeArguments(in.getComponentType());
        return out;
    }

    private void processSpecialNodeTypes(MethodDeclaration node) {
        processSpecialNodeTypes(node, node.getNameAsString());
    }

    private void processSpecialNodeTypes(ObjectCreationExpr node) {
        Type type = node.getType();
        if (isRefType(type, "LinkedQueueNode")) {
            node.setType(simpleParametricType("LinkedQueueAtomicNode", "E"));
        } else if (isRefArray(type, "E")) {
            node.setType(atomicRefArrayType((ArrayType) type));
        }
    }

}
