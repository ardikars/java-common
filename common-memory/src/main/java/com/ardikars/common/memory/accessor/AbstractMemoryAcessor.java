package com.ardikars.common.memory.accessor;

import com.ardikars.common.memory.internal.ByteBufferHelper;
import com.ardikars.common.memory.internal.UnsafeHelper;
import sun.misc.Unsafe;

import java.nio.ByteBuffer;

abstract class AbstractMemoryAcessor implements MemoryAccessor {

    static final Unsafe UNSAFE = UnsafeHelper.getUnsafe();

    static final int BYTE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);

    @Override
    public long allocate(int size) {
        return UNSAFE.allocateMemory(size);
    }

    @Override
    public long reallocate(long addr, int size) {
        return UNSAFE.reallocateMemory(addr, size);
    }

    @Override
    public void deallocate(long addr) {
        UNSAFE.freeMemory(addr);
    }

    @Override
    public ByteBuffer nioBuffer(long addr, int size) {
        return ByteBufferHelper.wrapDirectByteBuffer(addr, size);
    }

    @Override
    public byte getByte(long addr) {
        return UNSAFE.getByte(addr);
    }

    @Override
    public void setByte(long addr, int val) {
        UNSAFE.putByte(addr, (byte) val);
    }

    @Override
    public void getBytes(long srcAddr, int index, long dstAddr, int dstIndex, int size) {
        UNSAFE.copyMemory(srcAddr + index, dstAddr + dstIndex, size);
    }

    @Override
    public void getBytes(long srcAddr, int index, byte[] dst, int dstIndex, int size) {
        UNSAFE.copyMemory(null, srcAddr + index, dst, (long) (BYTE_ARRAY_OFFSET + dstIndex), size);
    }

    @Override
    public void setBytes(long dstAddr, int index, long srcAddr, int srcIndex, int size) {
        UNSAFE.copyMemory(srcAddr + srcIndex, dstAddr + index, size);
    }

    @Override
    public void setBytes(long dstAddr, int index, byte[] src, int srcIndex, int size) {
        UNSAFE.copyMemory(src, (long) (BYTE_ARRAY_OFFSET + srcIndex), null, dstAddr + index, size);
    }

}
