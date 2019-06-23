package com.ardikars.common.memory;

import com.ardikars.common.util.Validate;

import java.nio.ByteBuffer;

class PooledByteBuf extends ByteBuf {

    PooledByteBuf(int capacity, int maxCapacity) {
        super(capacity, maxCapacity);
    }

    PooledByteBuf(int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(capacity, maxCapacity, readerIndex, writerIndex);
    }

    PooledByteBuf(int baseIndex, ByteBuffer buffer, int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        super(baseIndex, buffer, capacity, maxCapacity, readerIndex, writerIndex);
    }

    @Override
    public Memory capacity(int newCapacity) {
        Validate.notIllegalArgument(newCapacity <= maxCapacity,
                new IllegalArgumentException(String.format("newCapacity < maxCapacity: %s <= %s", newCapacity, maxCapacity)));
        this.capacity = newCapacity;
        return this;
    }

    @Override
    public Memory copy(int index, int length) {
        byte[] b = new byte[length];
        int currentIndex = baseIndex + index;
        getBytes(currentIndex, b, 0, length);
        ByteBuffer copy = ByteBuffer.allocateDirect(length);
        copy.put(b);
        return new PooledByteBuf(baseIndex, copy, capacity(), maxCapacity(), readerIndex(), writerIndex());
    }

    @Override
    public Memory slice(int index, int length) {
        ByteBuf duplicated = new PooledSlicedByteBuf(baseIndex + index, buffer.duplicate(), length, maxCapacity(), readerIndex() - index, writerIndex() - index);
        return duplicated;
    }

    @Override
    public Memory duplicate() {
        ByteBuf duplicated = new PooledByteBuf(baseIndex, buffer.duplicate(), capacity(), maxCapacity(), readerIndex(), writerIndex());
        return duplicated;
    }

    @Override
    public void release() {
        Memories.offer(this);
    }

}
