/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jctools.queues.atomic;

import org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue;
import org.jctools.util.PortableJvmInfo;
import org.jctools.util.Pow2;
import org.jctools.util.RangeUtil;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.jctools.queues.MessagePassingQueue;
import org.jctools.queues.MessagePassingQueue.Supplier;
import org.jctools.queues.MessagePassingQueueUtil;
import org.jctools.queues.QueueProgressIndicators;
import org.jctools.queues.IndexedQueueSizeUtil;
import static org.jctools.queues.atomic.AtomicQueueUtil.*;

/**
 * NOTE: This class was automatically generated by org.jctools.queues.atomic.JavaParsingAtomicLinkedQueueGenerator
 * which can found in the jctools-build module. The original source file is BaseMpscLinkedArrayQueue.java.
 */
abstract class BaseMpscLinkedAtomicArrayQueuePad1<E> extends AbstractQueue<E> implements IndexedQueue {

    // 8b
    byte b000, b001, b002, b003, b004, b005, b006, b007;

    // 16b
    byte b010, b011, b012, b013, b014, b015, b016, b017;

    // 24b
    byte b020, b021, b022, b023, b024, b025, b026, b027;

    // 32b
    byte b030, b031, b032, b033, b034, b035, b036, b037;

    // 40b
    byte b040, b041, b042, b043, b044, b045, b046, b047;

    // 48b
    byte b050, b051, b052, b053, b054, b055, b056, b057;

    // 56b
    byte b060, b061, b062, b063, b064, b065, b066, b067;

    // 64b
    byte b070, b071, b072, b073, b074, b075, b076, b077;

    // 72b
    byte b100, b101, b102, b103, b104, b105, b106, b107;

    // 80b
    byte b110, b111, b112, b113, b114, b115, b116, b117;

    // 88b
    byte b120, b121, b122, b123, b124, b125, b126, b127;

    // 96b
    byte b130, b131, b132, b133, b134, b135, b136, b137;

    // 104b
    byte b140, b141, b142, b143, b144, b145, b146, b147;

    // 112b
    byte b150, b151, b152, b153, b154, b155, b156, b157;

    // 120b
    byte b160, b161, b162, b163, b164, b165, b166, b167;

    // 128b
    byte b170, b171, b172, b173, b174, b175, b176, b177;
}

/**
 * NOTE: This class was automatically generated by org.jctools.queues.atomic.JavaParsingAtomicLinkedQueueGenerator
 * which can found in the jctools-build module. The original source file is BaseMpscLinkedArrayQueue.java.
 */
abstract class BaseMpscLinkedAtomicArrayQueueProducerFields<E> extends BaseMpscLinkedAtomicArrayQueuePad1<E> {

    private static final AtomicLongFieldUpdater<BaseMpscLinkedAtomicArrayQueueProducerFields> P_INDEX_UPDATER = AtomicLongFieldUpdater.newUpdater(BaseMpscLinkedAtomicArrayQueueProducerFields.class, "producerIndex");

    private volatile long producerIndex;

    @Override
    public final long lvProducerIndex() {
        return producerIndex;
    }

    final void soProducerIndex(long newValue) {
        P_INDEX_UPDATER.lazySet(this, newValue);
    }

    final boolean casProducerIndex(long expect, long newValue) {
        return P_INDEX_UPDATER.compareAndSet(this, expect, newValue);
    }
}

/**
 * NOTE: This class was automatically generated by org.jctools.queues.atomic.JavaParsingAtomicLinkedQueueGenerator
 * which can found in the jctools-build module. The original source file is BaseMpscLinkedArrayQueue.java.
 */
abstract class BaseMpscLinkedAtomicArrayQueuePad2<E> extends BaseMpscLinkedAtomicArrayQueueProducerFields<E> {

    // 8b
    byte b000, b001, b002, b003, b004, b005, b006, b007;

    // 16b
    byte b010, b011, b012, b013, b014, b015, b016, b017;

    // 24b
    byte b020, b021, b022, b023, b024, b025, b026, b027;

    // 32b
    byte b030, b031, b032, b033, b034, b035, b036, b037;

    // 40b
    byte b040, b041, b042, b043, b044, b045, b046, b047;

    // 48b
    byte b050, b051, b052, b053, b054, b055, b056, b057;

    // 56b
    byte b060, b061, b062, b063, b064, b065, b066, b067;

    // 64b
    byte b070, b071, b072, b073, b074, b075, b076, b077;

    // 72b
    byte b100, b101, b102, b103, b104, b105, b106, b107;

    // 80b
    byte b110, b111, b112, b113, b114, b115, b116, b117;

    // 88b
    byte b120, b121, b122, b123, b124, b125, b126, b127;

    // 96b
    byte b130, b131, b132, b133, b134, b135, b136, b137;

    // 104b
    byte b140, b141, b142, b143, b144, b145, b146, b147;

    // 112b
    byte b150, b151, b152, b153, b154, b155, b156, b157;

    // 120b
    byte b160, b161, b162, b163, b164, b165, b166, b167;

    // 128b
    byte b170, b171, b172, b173, b174, b175, b176, b177;
}

/**
 * NOTE: This class was automatically generated by org.jctools.queues.atomic.JavaParsingAtomicLinkedQueueGenerator
 * which can found in the jctools-build module. The original source file is BaseMpscLinkedArrayQueue.java.
 */
abstract class BaseMpscLinkedAtomicArrayQueueConsumerFields<E> extends BaseMpscLinkedAtomicArrayQueuePad2<E> {

    private static final AtomicLongFieldUpdater<BaseMpscLinkedAtomicArrayQueueConsumerFields> C_INDEX_UPDATER = AtomicLongFieldUpdater.newUpdater(BaseMpscLinkedAtomicArrayQueueConsumerFields.class, "consumerIndex");

    private volatile long consumerIndex;

    protected long consumerMask;

    protected AtomicReferenceArray<E> consumerBuffer;

    @Override
    public final long lvConsumerIndex() {
        return consumerIndex;
    }

    final long lpConsumerIndex() {
        return consumerIndex;
    }

    final void soConsumerIndex(long newValue) {
        C_INDEX_UPDATER.lazySet(this, newValue);
    }
}

/**
 * NOTE: This class was automatically generated by org.jctools.queues.atomic.JavaParsingAtomicLinkedQueueGenerator
 * which can found in the jctools-build module. The original source file is BaseMpscLinkedArrayQueue.java.
 */
abstract class BaseMpscLinkedAtomicArrayQueuePad3<E> extends BaseMpscLinkedAtomicArrayQueueConsumerFields<E> {

    // 8b
    byte b000, b001, b002, b003, b004, b005, b006, b007;

    // 16b
    byte b010, b011, b012, b013, b014, b015, b016, b017;

    // 24b
    byte b020, b021, b022, b023, b024, b025, b026, b027;

    // 32b
    byte b030, b031, b032, b033, b034, b035, b036, b037;

    // 40b
    byte b040, b041, b042, b043, b044, b045, b046, b047;

    // 48b
    byte b050, b051, b052, b053, b054, b055, b056, b057;

    // 56b
    byte b060, b061, b062, b063, b064, b065, b066, b067;

    // 64b
    byte b070, b071, b072, b073, b074, b075, b076, b077;

    // 72b
    byte b100, b101, b102, b103, b104, b105, b106, b107;

    // 80b
    byte b110, b111, b112, b113, b114, b115, b116, b117;

    // 88b
    byte b120, b121, b122, b123, b124, b125, b126, b127;

    // 96b
    byte b130, b131, b132, b133, b134, b135, b136, b137;

    // 104b
    byte b140, b141, b142, b143, b144, b145, b146, b147;

    // 112b
    byte b150, b151, b152, b153, b154, b155, b156, b157;

    // 120b
    byte b160, b161, b162, b163, b164, b165, b166, b167;

    // 128b
    byte b170, b171, b172, b173, b174, b175, b176, b177;
}

/**
 * NOTE: This class was automatically generated by org.jctools.queues.atomic.JavaParsingAtomicLinkedQueueGenerator
 * which can found in the jctools-build module. The original source file is BaseMpscLinkedArrayQueue.java.
 */
abstract class BaseMpscLinkedAtomicArrayQueueColdProducerFields<E> extends BaseMpscLinkedAtomicArrayQueuePad3<E> {

    private static final AtomicLongFieldUpdater<BaseMpscLinkedAtomicArrayQueueColdProducerFields> P_LIMIT_UPDATER = AtomicLongFieldUpdater.newUpdater(BaseMpscLinkedAtomicArrayQueueColdProducerFields.class, "producerLimit");

    private volatile long producerLimit;

    protected long producerMask;

    protected AtomicReferenceArray<E> producerBuffer;

    final long lvProducerLimit() {
        return producerLimit;
    }

    final boolean casProducerLimit(long expect, long newValue) {
        return P_LIMIT_UPDATER.compareAndSet(this, expect, newValue);
    }

    final void soProducerLimit(long newValue) {
        P_LIMIT_UPDATER.lazySet(this, newValue);
    }
}

/**
 * NOTE: This class was automatically generated by org.jctools.queues.atomic.JavaParsingAtomicLinkedQueueGenerator
 * which can found in the jctools-build module. The original source file is BaseMpscLinkedArrayQueue.java.
 *
 * An MPSC array queue which starts at <i>initialCapacity</i> and grows to <i>maxCapacity</i> in linked chunks
 * of the initial size. The queue grows only when the current buffer is full and elements are not copied on
 * resize, instead a link to the new buffer is stored in the old buffer for the consumer to follow.
 */
abstract class BaseMpscLinkedAtomicArrayQueue<E> extends BaseMpscLinkedAtomicArrayQueueColdProducerFields<E> implements MessagePassingQueue<E>, QueueProgressIndicators {

    // No post padding here, subclasses must add
    private static final Object JUMP = new Object();

    private static final Object BUFFER_CONSUMED = new Object();

    private static final int CONTINUE_TO_P_INDEX_CAS = 0;

    private static final int RETRY = 1;

    private static final int QUEUE_FULL = 2;

    private static final int QUEUE_RESIZE = 3;

    /**
     * @param initialCapacity the queue initial capacity. If chunk size is fixed this will be the chunk size.
     *                        Must be 2 or more.
     */
    public BaseMpscLinkedAtomicArrayQueue(final int initialCapacity) {
        RangeUtil.checkGreaterThanOrEqual(initialCapacity, 2, "initialCapacity");
        int p2capacity = Pow2.roundToPowerOfTwo(initialCapacity);
        // leave lower bit of mask clear
        long mask = (p2capacity - 1) << 1;
        // need extra element to point at next array
        AtomicReferenceArray<E> buffer = allocateRefArray(p2capacity + 1);
        producerBuffer = buffer;
        producerMask = mask;
        consumerBuffer = buffer;
        consumerMask = mask;
        // we know it's all empty to start with
        soProducerLimit(mask);
    }

    @Override
    public int size() {
        return IndexedQueueSizeUtil.size(this, IndexedQueueSizeUtil.IGNORE_PARITY_DIVISOR);
    }

    @Override
    public boolean isEmpty() {
        // nothing we can do to make this an exact method.
        return ((lvConsumerIndex() - lvProducerIndex()) / 2 == 0);
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }

    @Override
    public boolean offer(final E e) {
        if (null == e) {
            throw new NullPointerException();
        }
        long mask;
        AtomicReferenceArray<E> buffer;
        long pIndex;
        while (true) {
            long producerLimit = lvProducerLimit();
            pIndex = lvProducerIndex();
            // lower bit is indicative of resize, if we see it we spin until it's cleared
            if ((pIndex & 1) == 1) {
                continue;
            }
            // pIndex is even (lower bit is 0) -> actual index is (pIndex >> 1)
            // mask/buffer may get changed by resizing -> only use for array access after successful CAS.
            mask = this.producerMask;
            buffer = this.producerBuffer;
            // assumption behind this optimization is that queue is almost always empty or near empty
            if (producerLimit <= pIndex) {
                int result = offerSlowPath(mask, pIndex, producerLimit);
                switch(result) {
                    case CONTINUE_TO_P_INDEX_CAS:
                        break;
                    case RETRY:
                        continue;
                    case QUEUE_FULL:
                        return false;
                    case QUEUE_RESIZE:
                        resize(mask, buffer, pIndex, e, null);
                        return true;
                }
            }
            if (casProducerIndex(pIndex, pIndex + 2)) {
                break;
            }
        }
        // INDEX visible before ELEMENT
        final int offset = modifiedCalcCircularRefElementOffset(pIndex, mask);
        // release element e
        soRefElement(buffer, offset, e);
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single consumer thread use only.
     */
    @SuppressWarnings("unchecked")
    @Override
    public E poll() {
        final AtomicReferenceArray<E> buffer = consumerBuffer;
        final long cIndex = lpConsumerIndex();
        final long mask = consumerMask;
        final int offset = modifiedCalcCircularRefElementOffset(cIndex, mask);
        Object e = lvRefElement(buffer, offset);
        if (e == null) {
            long pIndex = lvProducerIndex();
            // isEmpty?
            if ((cIndex - pIndex) / 2 == 0) {
                return null;
            }
            // spin until element is visible.
            do {
                e = lvRefElement(buffer, offset);
            } while (e == null);
        }
        if (e == JUMP) {
            final AtomicReferenceArray<E> nextBuffer = nextBuffer(buffer, mask);
            return newBufferPoll(nextBuffer, cIndex);
        }
        // release element null
        soRefElement(buffer, offset, null);
        // release cIndex
        soConsumerIndex(cIndex + 2);
        return (E) e;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is correct for single consumer thread use only.
     */
    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        final AtomicReferenceArray<E> buffer = consumerBuffer;
        final long cIndex = lpConsumerIndex();
        final long mask = consumerMask;
        final int offset = modifiedCalcCircularRefElementOffset(cIndex, mask);
        Object e = lvRefElement(buffer, offset);
        if (e == null) {
            long pIndex = lvProducerIndex();
            // isEmpty?
            if ((cIndex - pIndex) / 2 == 0) {
                return null;
            }
            // spin until element is visible.
            do {
                e = lvRefElement(buffer, offset);
            } while (e == null);
        }
        if (e == JUMP) {
            return newBufferPeek(nextBuffer(buffer, mask), cIndex);
        }
        return (E) e;
    }

    /**
     * We do not inline resize into this method because we do not resize on fill.
     */
    private int offerSlowPath(long mask, long pIndex, long producerLimit) {
        final long cIndex = lvConsumerIndex();
        long bufferCapacity = getCurrentBufferCapacity(mask);
        if (cIndex + bufferCapacity > pIndex) {
            if (!casProducerLimit(producerLimit, cIndex + bufferCapacity)) {
                // retry from top
                return RETRY;
            } else {
                // continue to pIndex CAS
                return CONTINUE_TO_P_INDEX_CAS;
            }
        } else // full and cannot grow
        if (availableInQueue(pIndex, cIndex) <= 0) {
            // offer should return false;
            return QUEUE_FULL;
        } else // grab index for resize -> set lower bit
        if (casProducerIndex(pIndex, pIndex + 1)) {
            // trigger a resize
            return QUEUE_RESIZE;
        } else {
            // failed resize attempt, retry from top
            return RETRY;
        }
    }

    /**
     * @return available elements in queue * 2
     */
    protected abstract long availableInQueue(long pIndex, long cIndex);

    @SuppressWarnings("unchecked")
    private AtomicReferenceArray<E> nextBuffer(final AtomicReferenceArray<E> buffer, final long mask) {
        final int offset = nextArrayOffset(mask);
        final AtomicReferenceArray<E> nextBuffer = (AtomicReferenceArray<E>) lvRefElement(buffer, offset);
        consumerBuffer = nextBuffer;
        consumerMask = (length(nextBuffer) - 2) << 1;
        soRefElement(buffer, offset, BUFFER_CONSUMED);
        return nextBuffer;
    }

    private static int nextArrayOffset(long mask) {
        return modifiedCalcCircularRefElementOffset(mask + 2, Long.MAX_VALUE);
    }

    private E newBufferPoll(AtomicReferenceArray<E> nextBuffer, long cIndex) {
        final int offset = modifiedCalcCircularRefElementOffset(cIndex, consumerMask);
        final E n = lvRefElement(nextBuffer, offset);
        if (n == null) {
            throw new IllegalStateException("new buffer must have at least one element");
        }
        soRefElement(nextBuffer, offset, null);
        soConsumerIndex(cIndex + 2);
        return n;
    }

    private E newBufferPeek(AtomicReferenceArray<E> nextBuffer, long cIndex) {
        final int offset = modifiedCalcCircularRefElementOffset(cIndex, consumerMask);
        final E n = lvRefElement(nextBuffer, offset);
        if (null == n) {
            throw new IllegalStateException("new buffer must have at least one element");
        }
        return n;
    }

    @Override
    public long currentProducerIndex() {
        return lvProducerIndex() / 2;
    }

    @Override
    public long currentConsumerIndex() {
        return lvConsumerIndex() / 2;
    }

    @Override
    public abstract int capacity();

    @Override
    public boolean relaxedOffer(E e) {
        return offer(e);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E relaxedPoll() {
        final AtomicReferenceArray<E> buffer = consumerBuffer;
        final long cIndex = lpConsumerIndex();
        final long mask = consumerMask;
        final int offset = modifiedCalcCircularRefElementOffset(cIndex, mask);
        Object e = lvRefElement(buffer, offset);
        if (e == null) {
            return null;
        }
        if (e == JUMP) {
            final AtomicReferenceArray<E> nextBuffer = nextBuffer(buffer, mask);
            return newBufferPoll(nextBuffer, cIndex);
        }
        soRefElement(buffer, offset, null);
        soConsumerIndex(cIndex + 2);
        return (E) e;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E relaxedPeek() {
        final AtomicReferenceArray<E> buffer = consumerBuffer;
        final long cIndex = lpConsumerIndex();
        final long mask = consumerMask;
        final int offset = modifiedCalcCircularRefElementOffset(cIndex, mask);
        Object e = lvRefElement(buffer, offset);
        if (e == JUMP) {
            return newBufferPeek(nextBuffer(buffer, mask), cIndex);
        }
        return (E) e;
    }

    @Override
    public int fill(Supplier<E> s) {
        // result is a long because we want to have a safepoint check at regular intervals
        long result = 0;
        final int capacity = capacity();
        do {
            final int filled = fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH);
            if (filled == 0) {
                return (int) result;
            }
            result += filled;
        } while (result <= capacity);
        return (int) result;
    }

    @Override
    public int fill(Supplier<E> s, int limit) {
        if (null == s)
            throw new IllegalArgumentException("supplier is null");
        if (limit < 0)
            throw new IllegalArgumentException("limit is negative:" + limit);
        if (limit == 0)
            return 0;
        long mask;
        AtomicReferenceArray<E> buffer;
        long pIndex;
        int claimedSlots;
        while (true) {
            long producerLimit = lvProducerLimit();
            pIndex = lvProducerIndex();
            // lower bit is indicative of resize, if we see it we spin until it's cleared
            if ((pIndex & 1) == 1) {
                continue;
            }
            // pIndex is even (lower bit is 0) -> actual index is (pIndex >> 1)
            // NOTE: mask/buffer may get changed by resizing -> only use for array access after successful CAS.
            // Only by virtue offloading them between the lvProducerIndex and a successful casProducerIndex are they
            // safe to use.
            mask = this.producerMask;
            buffer = this.producerBuffer;
            // a successful CAS ties the ordering, lv(pIndex) -> [mask/buffer] -> cas(pIndex)
            // we want 'limit' slots, but will settle for whatever is visible to 'producerLimit'
            // -> producerLimit >= batchIndex
            long batchIndex = Math.min(producerLimit, pIndex + 2l * limit);
            if (pIndex >= producerLimit) {
                int result = offerSlowPath(mask, pIndex, producerLimit);
                switch(result) {
                    case CONTINUE_TO_P_INDEX_CAS:
                    // offer slow path verifies only one slot ahead, we cannot rely on indication here
                    case RETRY:
                        continue;
                    case QUEUE_FULL:
                        return 0;
                    case QUEUE_RESIZE:
                        resize(mask, buffer, pIndex, null, s);
                        return 1;
                }
            }
            // claim limit slots at once
            if (casProducerIndex(pIndex, batchIndex)) {
                claimedSlots = (int) ((batchIndex - pIndex) / 2);
                break;
            }
        }
        for (int i = 0; i < claimedSlots; i++) {
            final int offset = modifiedCalcCircularRefElementOffset(pIndex + 2l * i, mask);
            soRefElement(buffer, offset, s.get());
        }
        return claimedSlots;
    }

    @Override
    public void fill(Supplier<E> s, WaitStrategy wait, ExitCondition exit) {
        MessagePassingQueueUtil.fill(this, s, wait, exit);
    }

    @Override
    public int drain(Consumer<E> c) {
        return drain(c, capacity());
    }

    @Override
    public int drain(Consumer<E> c, int limit) {
        return MessagePassingQueueUtil.drain(this, c, limit);
    }

    @Override
    public void drain(Consumer<E> c, WaitStrategy wait, ExitCondition exit) {
        MessagePassingQueueUtil.drain(this, c, wait, exit);
    }

    /**
     * Get an iterator for this queue. This method is thread safe.
     * <p>
     * The iterator provides a best-effort snapshot of the elements in the queue.
     * The returned iterator is not guaranteed to return elements in queue order,
     * and races with the consumer thread may cause gaps in the sequence of returned elements.
     * Like {link #relaxedPoll}, the iterator may not immediately return newly inserted elements.
     *
     * @return The iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return new WeakIterator(consumerBuffer, lvConsumerIndex(), lvProducerIndex());
    }

    /**
     * NOTE: This class was automatically generated by org.jctools.queues.atomic.JavaParsingAtomicLinkedQueueGenerator
     * which can found in the jctools-build module. The original source file is BaseMpscLinkedArrayQueue.java.
     */
    private static class WeakIterator<E> implements Iterator<E> {

        private final long pIndex;

        private long nextIndex;

        private E nextElement;

        private AtomicReferenceArray<E> currentBuffer;

        private int mask;

        WeakIterator(AtomicReferenceArray<E> currentBuffer, long cIndex, long pIndex) {
            this.pIndex = pIndex >> 1;
            this.nextIndex = cIndex >> 1;
            setBuffer(currentBuffer);
            nextElement = getNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public boolean hasNext() {
            return nextElement != null;
        }

        @Override
        public E next() {
            final E e = nextElement;
            if (e == null) {
                throw new NoSuchElementException();
            }
            nextElement = getNext();
            return e;
        }

        private void setBuffer(AtomicReferenceArray<E> buffer) {
            this.currentBuffer = buffer;
            this.mask = length(buffer) - 2;
        }

        private E getNext() {
            while (nextIndex < pIndex) {
                long index = nextIndex++;
                E e = lvRefElement(currentBuffer, calcCircularRefElementOffset(index, mask));
                // skip removed/not yet visible elements
                if (e == null) {
                    continue;
                }
                // not null && not JUMP -> found next element
                if (e != JUMP) {
                    return e;
                }
                // need to jump to the next buffer
                int nextBufferIndex = mask + 1;
                Object nextBuffer = lvRefElement(currentBuffer, calcRefElementOffset(nextBufferIndex));
                if (nextBuffer == BUFFER_CONSUMED || nextBuffer == null) {
                    // Consumer may have passed us, or the next buffer is not visible yet: drop out early
                    return null;
                }
                setBuffer((AtomicReferenceArray<E>) nextBuffer);
                // now with the new array retry the load, it can't be a JUMP, but we need to repeat same index
                e = lvRefElement(currentBuffer, calcCircularRefElementOffset(index, mask));
                // skip removed/not yet visible elements
                if (e == null) {
                    continue;
                } else {
                    return e;
                }
            }
            return null;
        }
    }

    private void resize(long oldMask, AtomicReferenceArray<E> oldBuffer, long pIndex, E e, Supplier<E> s) {
        assert (e != null && s == null) || (e == null || s != null);
        int newBufferLength = getNextBufferSize(oldBuffer);
        final AtomicReferenceArray<E> newBuffer;
        try {
            newBuffer = allocateRefArray(newBufferLength);
        } catch (OutOfMemoryError oom) {
            assert lvProducerIndex() == pIndex + 1;
            soProducerIndex(pIndex);
            throw oom;
        }
        producerBuffer = newBuffer;
        final int newMask = (newBufferLength - 2) << 1;
        producerMask = newMask;
        final int offsetInOld = modifiedCalcCircularRefElementOffset(pIndex, oldMask);
        final int offsetInNew = modifiedCalcCircularRefElementOffset(pIndex, newMask);
        // element in new array
        soRefElement(newBuffer, offsetInNew, e == null ? s.get() : e);
        // buffer linked
        soRefElement(oldBuffer, nextArrayOffset(oldMask), newBuffer);
        // ASSERT code
        final long cIndex = lvConsumerIndex();
        final long availableInQueue = availableInQueue(pIndex, cIndex);
        RangeUtil.checkPositive(availableInQueue, "availableInQueue");
        // Invalidate racing CASs
        // We never set the limit beyond the bounds of a buffer
        soProducerLimit(pIndex + Math.min(newMask, availableInQueue));
        // make resize visible to the other producers
        soProducerIndex(pIndex + 2);
        // INDEX visible before ELEMENT, consistent with consumer expectation
        // make resize visible to consumer
        soRefElement(oldBuffer, offsetInOld, JUMP);
    }

    /**
     * @return next buffer size(inclusive of next array pointer)
     */
    protected abstract int getNextBufferSize(AtomicReferenceArray<E> buffer);

    /**
     * @return current buffer capacity for elements (excluding next pointer and jump entry) * 2
     */
    protected abstract long getCurrentBufferCapacity(long mask);
}
