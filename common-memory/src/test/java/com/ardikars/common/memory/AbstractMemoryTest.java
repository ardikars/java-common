package com.ardikars.common.memory;

import java.nio.ByteBuffer;

abstract class AbstractMemoryTest extends BaseTest {

    protected Memory memory;

    protected abstract MemoryAllocator memoryAllocator();

    public abstract void allocate();

    public abstract void deallocate();

    public abstract void capacityAndMaxCapacityTest();

    protected void doCapacityAndMaxCapacityTest() {
        assert memory.capacity() == DEFAULT_CAPACITY;
        assert memory.maxCapacity() == DEFAULT_MAX_CAPACITY;
    }

    public abstract void readerAndWriterIndexTest();

    protected void doReaderAndWriterIndexTest() {
        assert memory.writerIndex() == 0;
        assert memory.readerIndex() == 0;
        memory.writerIndex(BYTE_SIZE);
        assert memory.writerIndex() == BYTE_SIZE;
        memory.readerIndex(BYTE_SIZE);
        assert memory.readerIndex() == BYTE_SIZE;
        memory.setIndex(BYTE_SIZE / BIT_SIZE, BYTE_SIZE / BIT_SIZE);
        assert memory.writerIndex() == BYTE_SIZE / BIT_SIZE;
        assert memory.readerIndex() == BYTE_SIZE / BIT_SIZE;
    }

    public abstract void isReadableTest();

    protected void doIsReadableTest() {
        assert memory.isWritable();
        assert memory.isWritable(DEFAULT_CAPACITY);
        assert !memory.isWritable(DEFAULT_MAX_CAPACITY);
        assert !memory.isReadable();
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            memory.writeByte(i);
        }
        assert !memory.isWritable();
        assert memory.isReadable();
        assert memory.isReadable(DEFAULT_CAPACITY);
        assert !memory.isReadable(DEFAULT_MAX_CAPACITY);
    }

    public abstract void readableWriteableAndMaxWriableBytesTest();

    protected void doReadableWriteableAndMaxWriableBytesTest() {
        for (int i = 0; i < DEFAULT_CAPACITY / BIT_SIZE; i++) {
            memory.writeByte(i);
        }
        assert memory.writableBytes() == DEFAULT_CAPACITY / BIT_SIZE;
        assert memory.readableBytes() == DEFAULT_CAPACITY / BIT_SIZE;
        memory.writeInt(1);
        assert memory.writableBytes() == DEFAULT_CAPACITY - ((DEFAULT_CAPACITY / BIT_SIZE) + INT_SIZE);
        assert memory.maxWritableBytes() == DEFAULT_MAX_CAPACITY - ((DEFAULT_CAPACITY / BIT_SIZE) + INT_SIZE);
        memory.readInt();
        assert memory.readableBytes() == DEFAULT_CAPACITY / BIT_SIZE;
    }

    public abstract void readerIndexTest();

    protected void doReaderIndexTest() {
        for (byte val : DUMMY) {
            memory.writeByte(val);
        }
        assert memory.readByte() == DUMMY[0];
        memory.markReaderIndex();
        assert memory.readByte() == DUMMY[1];
        memory.resetReaderIndex();
        assert memory.readByte() == DUMMY[1];
    }

    public abstract void writerIndexTest();

    protected void doWriterIndexTest() {
        memory.writeByte(1);
        memory.writeByte(2);
        memory.markWriterIndex();
        memory.writeByte(3);
        assert memory.getByte(0) == 1;
        assert memory.getByte(1) == 2;
        assert memory.getByte(2) == 3;
        memory.resetWriterIndex();
        memory.writeByte(1);
        assert memory.getByte(2) == 1;
    }

    public abstract void skipBytesTest();

    protected void doSkipBytesTest() {
        for (byte val : DUMMY) {
            memory.writeByte(val);
        }
        for (int i = 0; i < DUMMY.length; i++) {
            if (i % 2 == 0) {
                assert memory.readByte() == i;
            } else {
                memory.skipBytes(1);
            }
        }
    }

    public abstract void copyTest();

    protected void doCopyTest() {
        for (byte val : DUMMY) {
            memory.writeByte(val);
        }
        memory.readerIndex(1);
        Memory cpy = memory.copy();
        assert cpy.readerIndex() == memory.readerIndex();
        assert cpy.writerIndex() == memory.writerIndex();
        for (int i = 1; i < DUMMY.length - 1; i++) {
            assert cpy.readByte() == memory.readByte();
        }
        Memory copy = memory.copy(2, 1);
        assert copy.readerIndex() == memory.readerIndex();
        assert copy.writerIndex() == memory.writerIndex();
        assert copy.getByte(0) == memory.getByte(2);
        cpy.release();
        copy.release();
    }

    public abstract void sliceTest();

    protected void doSliceTest() {
        for (byte val : DUMMY) {
            memory.writeByte(val);
        }
        memory.readShort();
        Memory sliced = memory.slice();
        if (memory instanceof NativeMemory) {
            assert sliced.memoryAddress() - 2 == memory.memoryAddress();
        }
        assert sliced.capacity() == DUMMY.length - 2;
        assert sliced.maxCapacity() == memory.maxCapacity();
        for (int i = 0; i < sliced.capacity(); i++) {
            assert sliced.getByte(i) == DUMMY[i + 2];
            assert sliced.readByte() == DUMMY[i + 2];
        }
    }

    public abstract void clearTest();

    protected void doClearTest() {
        memory.clear();
        assert memory.readerIndex() == 0 && memory.writerIndex() == 0;
    }

    public abstract void newCapacityTest();

    protected void doNewCapacityTest() {
        int capacity = memory.capacity();
        int maxCapacity = memory.maxCapacity();
        assert capacity < maxCapacity;
        Memory newMemory = memory.capacity(maxCapacity);
        assert newMemory.readerIndex() == memory.readerIndex();
        assert newMemory.writerIndex() == memory.writerIndex();
        assert newMemory.capacity() == maxCapacity;
        Memory newMemoryAlso = newMemory.capacity(capacity);
//        assert newMemoryAlso.capacity() < newMemory.capacity();
        assert newMemoryAlso.capacity() == capacity;
        assert newMemoryAlso.readerIndex() == newMemory.readerIndex();
        assert newMemoryAlso.writerIndex() == newMemory.writerIndex();
        newMemory.release();
        newMemoryAlso.release();
    }

    public abstract void duplicateTest();

    protected void doDuplicateTest() {
        for (byte val : DUMMY) {
            memory.writeByte(val);
        }
        Memory duplicated = memory.duplicate();
        if (duplicated instanceof NativeMemory) {
            assert duplicated.memoryAddress() == memory.memoryAddress();
        }
        assert duplicated.capacity() == memory.capacity();
        assert duplicated.maxCapacity() == memory.maxCapacity();
        for (int i = 0; i < DUMMY.length; i++) {
            assert duplicated.readByte() == DUMMY[i];
        }
        // test visibility (sharing buffer)
        memory.writeByte(9);
        duplicated.writerIndex(duplicated.writerIndex() + 1);
        assert duplicated.readByte() == 9;
    }

    public abstract void nioBufferTest();

    protected void doNioBufferTest() {
        for (byte val : DUMMY) {
            memory.writeByte(val);
        }
        ByteBuffer buffer = memory.nioBuffer();
        buffer.position(0);
        assert buffer.capacity() == memory.capacity();
        for (int i = 0; i < DUMMY.length; i++) {
            assert buffer.get(i) == DUMMY[i];
        }
    }

}
