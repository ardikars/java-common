package com.ardikars.common.memory;

import com.ardikars.common.util.Properties;

abstract class AbstractMemory implements Memory {

    int capacity;
    int maxCapacity;

    private int readerIndex;
    private int writerIndex;

    private int markedReaderIndex;
    private int markedWriterIndex;

    protected boolean freed;

    static final boolean checkBounds = Properties.getBoolean("jmalloc.checkBounds", false);

    static final boolean checkAcessible = Properties.getBoolean("jmalloc.checkAccessible", false);

    AbstractMemory(int capacity, int maxCapacity) {
        this(capacity, maxCapacity, 0, 0);
    }

    AbstractMemory(int capacity, int maxCapacity, int readerIndex, int writerIndex) {
        this.capacity = capacity;
        this.maxCapacity = maxCapacity;
        this.readerIndex = readerIndex;
        this.writerIndex = writerIndex;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int maxCapacity() {
        return maxCapacity;
    }

    @Override
    public int readerIndex() {
        return readerIndex;
    }

    @Override
    public Memory readerIndex(int readerIndex) {
        if (readerIndex < 0 || readerIndex > writerIndex) {
            throw new IndexOutOfBoundsException(String.format(
                    "readerIndex: %d (expected: 0 <= readerIndex <= writerIndex(%d))", readerIndex, writerIndex));
        }
        this.readerIndex = readerIndex;
        return this;
    }

    @Override
    public int writerIndex() {
        return writerIndex;
    }

    @Override
    public Memory writerIndex(int writerIndex) {
        if (writerIndex < readerIndex || writerIndex > capacity()) {
            throw new IndexOutOfBoundsException(String.format(
                    "writerIndex: %d (expected: readerIndex(%d) <= writerIndex <= capacity(%d))",
                    writerIndex, readerIndex, capacity()));
        }
        this.writerIndex = writerIndex;
        return this;
    }

    @Override
    public Memory setIndex(int readerIndex, int writerIndex) {
        if (readerIndex < 0 || readerIndex > writerIndex || writerIndex > capacity()) {
            throw new IndexOutOfBoundsException(String.format(
                    "readerIndex: %d, writerIndex: %d (expected: 0 <= readerIndex <= writerIndex <= capacity(%d))",
                    readerIndex, writerIndex, capacity()));
        }
        this.readerIndex = readerIndex;
        this.writerIndex = writerIndex;
        return this;
    }

    @Override
    public int readableBytes() {
        return writerIndex - readerIndex;
    }

    @Override
    public int writableBytes() {
        return capacity() - writerIndex;
    }

    @Override
    public int maxWritableBytes() {
        return maxCapacity() - writerIndex;
    }

    @Override
    public boolean isReadable() {
        return writerIndex > readerIndex;
    }

    @Override
    public boolean isReadable(int numBytes) {
        return writerIndex - readerIndex >= numBytes;
    }

    @Override
    public boolean isWritable() {
        return capacity() > writerIndex;
    }

    @Override
    public boolean isWritable(int numBytes) {
        return capacity() - writerIndex >= numBytes;
    }

    @Override
    public Memory clear() {
        readerIndex = writerIndex = 0;
        return this;
    }

    @Override
    public Memory markReaderIndex() {
        markedReaderIndex = readerIndex;
        return this;
    }

    @Override
    public Memory resetReaderIndex() {
        readerIndex(markedReaderIndex);
        return this;
    }

    @Override
    public Memory markWriterIndex() {
        markedWriterIndex = writerIndex;
        return this;
    }

    @Override
    public Memory resetWriterIndex() {
        writerIndex(markedWriterIndex);
        return this;
    }

    @Override
    public Memory ensureWritable(int minWritableBytes) {
        if (minWritableBytes < 0) {
            throw new IllegalArgumentException(String.format(
                    "minWritableBytes: %d (expected: >= 0)", minWritableBytes));
        }
        checkWritableBytes(minWritableBytes);
        return this;
    }

    @Override
    public boolean getBoolean(int index) {
        return getByte(index) != 0;
    }

    @Override
    public short getUnsignedByte(int index) {
        return (short) (getByte(index) & 0xFF);
    }

    @Override
    public int getUnsignedShort(int index) {
        return getShort(index) & 0xFFFF;
    }

    @Override
    public int getUnsignedShortLE(int index) {
        return getShortLE(index) & 0xFFFF;
    }

    @Override
    public long getUnsignedInt(int index) {
        return getInt(index) & 0xFFFFFFFFL;
    }

    @Override
    public long getUnsignedIntLE(int index) {
        return getIntLE(index) & 0xFFFFFFFFL;
    }

    @Override
    public float getFloat(int index) {
        return Float.intBitsToFloat(getInt(index));
    }

    @Override
    public float getFloatLE(int index) {
        return Float.intBitsToFloat(this.getIntLE(index));
    }

    @Override
    public double getDouble(int index) {
        return Double.longBitsToDouble(getLong(index));
    }

    @Override
    public double getDoubleLE(int index) {
        return Double.longBitsToDouble(this.getLongLE(index));
    }

    @Override
    public Memory getBytes(int index, Memory dst) {
        getBytes(index, dst, dst.writableBytes());
        return this;
    }

    @Override
    public Memory getBytes(int index, Memory dst, int length) {
        getBytes(index, dst, dst.writerIndex(), length);
        dst.writerIndex(dst.writerIndex() + length);
        return this;
    }

    @Override
    public Memory getBytes(int index, byte[] dst) {
        getBytes(index, dst, 0, dst.length);
        return this;
    }

    @Override
    public Memory setBoolean(int index, boolean value) {
        setByte(index, value? 1 : 0);
        return this;
    }

    @Override
    public Memory setFloat(int index, float value) {
        setInt(index, Float.floatToRawIntBits(value));
        return this;
    }

    @Override
    public Memory setFloatLE(int index, float value) {
        return this.setIntLE(index, Float.floatToRawIntBits(value));
    }

    @Override
    public Memory setDouble(int index, double value) {
        setLong(index, Double.doubleToRawLongBits(value));
        return this;
    }

    @Override
    public Memory setDoubleLE(int index, double value) {
        return this.setLongLE(index, Double.doubleToRawLongBits(value));
    }

    @Override
    public Memory setBytes(int index, Memory src) {
        setBytes(index, src, src.readableBytes());
        return this;
    }

    @Override
    public Memory setBytes(int index, Memory src, int length) {
        checkIndex(index, length);
        if (src == null) {
            throw new NullPointerException("src");
        }
        if (length > src.readableBytes()) {
            throw new IndexOutOfBoundsException(String.format(
                    "length(%d) exceeds src.readableBytes(%d) where src is: %s", length, src.readableBytes(), src));
        }

        setBytes(index, src, src.readerIndex(), length);
        src.readerIndex(src.readerIndex() + length);
        return this;
    }

    @Override
    public Memory setBytes(int index, byte[] src) {
        setBytes(index, src, 0, src.length);
        return this;
    }

    @Override
    public boolean readBoolean() {
        return readByte() != 0;
    }

    @Override
    public byte readByte() {
        checkReadableBytes(1);
        int i = readerIndex;
        byte b = getByte(i);
        readerIndex = i + 1;
        return b;
    }

    @Override
    public short readUnsignedByte() {
        return (short) (readByte() & 0xFF);
    }

    @Override
    public short readShort() {
        checkReadableBytes(2);
        short v = getShort(readerIndex);
        readerIndex += 2;
        return v;
    }

    @Override
    public short readShortLE() {
        checkReadableBytes(2);
        short v = getShortLE(readerIndex);
        readerIndex += 2;
        return v;
    }

    @Override
    public int readUnsignedShort() {
        return readShort() & 0xFFFF;
    }

    @Override
    public int readUnsignedShortLE() {
        return readShortLE() & 0xFFFF;
    }

    @Override
    public int readInt() {
        checkReadableBytes(4);
        int v = getInt(readerIndex);
        readerIndex += 4;
        return v;
    }

    @Override
    public int readIntLE() {
        checkReadableBytes(4);
        int v = getIntLE(readerIndex);
        readerIndex += 4;
        return v;
    }

    @Override
    public long readUnsignedInt() {
        return readInt() & 0xFFFFFFFFL;
    }

    @Override
    public long readUnsignedIntLE() {
        return readIntLE() & 0xFFFFFFFFL;
    }

    @Override
    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    @Override
    public float readFloatLE() {
        return Float.intBitsToFloat(this.readIntLE());
    }

    @Override
    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    @Override
    public double readDoubleLE() {
        return Double.longBitsToDouble(this.readLongLE());
    }

    @Override
    public long readLong() {
        checkReadableBytes(8);
        long v = getLong(readerIndex);
        readerIndex += 8;
        return v;
    }

    @Override
    public long readLongLE() {
        checkReadableBytes(8);
        long v = getLongLE(readerIndex);
        readerIndex += 8;
        return v;
    }

    @Override
    public Memory readBytes(Memory dst) {
        readBytes(dst, dst.writableBytes());
        return this;
    }

    @Override
    public Memory readBytes(Memory dst, int length) {
        if (checkBounds) {
            if (length > dst.writableBytes()) {
                throw new IndexOutOfBoundsException(String.format(
                        "length(%d) exceeds dst.writableBytes(%d) where dst is: %s", length, dst.writableBytes(), dst));
            }
        }
        readBytes(dst, dst.writerIndex(), length);
        dst.writerIndex(dst.writerIndex() + length);
        return this;
    }

    @Override
    public Memory readBytes(Memory dst, int dstIndex, int length) {
        checkReadableBytes(length);
        getBytes(readerIndex, dst, dstIndex, length);
        readerIndex += length;
        return this;
    }

    @Override
    public Memory readBytes(byte[] dst) {
        readBytes(dst, 0, dst.length);
        return this;
    }

    @Override
    public Memory readBytes(byte[] dst, int dstIndex, int length) {
        checkReadableBytes(length);
        getBytes(readerIndex, dst, dstIndex, length);
        readerIndex += length;
        return this;
    }

    @Override
    public Memory skipBytes(int length) {
        checkReadableBytes(length);
        readerIndex += length;
        return this;
    }

    @Override
    public Memory writeBoolean(boolean value) {
        writeByte(value ? 1 : 0);
        return this;
    }

    @Override
    public Memory writeByte(int value) {
        checkWritableBytes(1);
        setByte(writerIndex++, value);
        return this;
    }

    @Override
    public Memory writeShort(int value) {
        checkWritableBytes(2);
        setShort(writerIndex, value);
        writerIndex += 2;
        return this;
    }

    @Override
    public Memory writeShortLE(int value) {
        checkWritableBytes(2);
        setShortLE(writerIndex, value);
        writerIndex += 2;
        return this;
    }

    @Override
    public Memory writeInt(int value) {
        checkWritableBytes(4);
        setInt(writerIndex, value);
        writerIndex += 4;
        return this;
    }

    @Override
    public Memory writeIntLE(int value) {
        checkWritableBytes(4);
        setIntLE(writerIndex, value);
        writerIndex += 4;
        return this;
    }

    @Override
    public Memory writeLong(long value) {
        checkWritableBytes(8);
        setLong(writerIndex, value);
        writerIndex += 8;
        return this;
    }

    @Override
    public Memory writeLongLE(long value) {
        checkWritableBytes(8);
        setLongLE(writerIndex, value);
        writerIndex += 8;
        return this;
    }

    @Override
    public Memory writeFloat(float value) {
        writeInt(Float.floatToRawIntBits(value));
        return this;
    }

    @Override
    public Memory writeFloatLE(float value) {
        return writeIntLE(Float.floatToRawIntBits(value));
    }

    @Override
    public Memory writeDoubleLE(double value) {
        return writeLongLE(Double.doubleToRawLongBits(value));
    }

    @Override
    public Memory writeDouble(double value) {
        writeLong(Double.doubleToRawLongBits(value));
        return this;
    }

    @Override
    public Memory writeBytes(Memory src) {
        writeBytes(src, src.readableBytes());
        return this;
    }

    @Override
    public Memory writeBytes(Memory src, int length) {
        if (checkBounds) {
            if (length > src.readableBytes()) {
                throw new IndexOutOfBoundsException(String.format(
                        "length(%d) exceeds src.readableBytes(%d) where src is: %s", length, src.readableBytes(), src));
            }
        }
        writeBytes(src, src.readerIndex(), length);
        src.readerIndex(src.readerIndex() + length);
        return this;
    }

    @Override
    public Memory writeBytes(Memory src, int srcIndex, int length) {
        ensureWritable(length);
        setBytes(writerIndex, src, srcIndex, length);
        writerIndex += length;
        return this;
    }

    @Override
    public Memory writeBytes(byte[] src) {
        writeBytes(src, 0, src.length);
        return this;
    }

    @Override
    public Memory writeBytes(byte[] src, int srcIndex, int length) {
        ensureWritable(length);
        setBytes(writerIndex, src, srcIndex, length);
        writerIndex += length;
        return this;
    }

    @Override
    public Memory copy() {
        return copy(0, capacity());
    }

    @Override
    public Memory slice() {
        return slice(readerIndex, readableBytes());
    }

    private int calculateNewCapacity(int minNewCapacity, int maxCapacity) {
        if (minNewCapacity < 0) {
            throw new IllegalArgumentException("minNewCapacity: " + minNewCapacity + " (expected: 0+)");
        }
        if (minNewCapacity > maxCapacity) {
            throw new IllegalArgumentException(String.format(
                    "minNewCapacity: %d (expected: not greater than maxCapacity(%d)",
                    minNewCapacity, maxCapacity));
        }
        final int threshold = 1048576 * 4; // 4 MiB page

        if (minNewCapacity == threshold) {
            return threshold;
        }

        // If over threshold, do not double but just increase by threshold.
        if (minNewCapacity > threshold) {
            int newCapacity = minNewCapacity / threshold * threshold;
            if (newCapacity > maxCapacity - threshold) {
                newCapacity = maxCapacity;
            } else {
                newCapacity += threshold;
            }
            return newCapacity;
        }

        // Not over threshold. Double up to 4 MiB, starting from 64.
        int newCapacity = 64;
        while (newCapacity < minNewCapacity) {
            newCapacity <<= 1;
        }

        return Math.min(newCapacity, maxCapacity);
    }

    private void checkWritableBytes(int minWritableBytes) {
        if (minWritableBytes <= writableBytes()) {
            return;
        }

        if (minWritableBytes > maxCapacity - writerIndex()) {
            throw new IndexOutOfBoundsException(String.format(
                    "writerIndex(%d) + minWritableBytes(%d) exceeds maxCapacity(%d): %s",
                    writerIndex(), minWritableBytes, maxCapacity, this));
        }

        // Normalize the current capacity to the power of 2.
        int newCapacity = calculateNewCapacity(writerIndex() + minWritableBytes, maxCapacity);

        // Adjust to the new capacity.
        capacity(newCapacity);
    }

    private void checkReadableBytes(int minimumReadableBytes) {
        if (minimumReadableBytes < 0) {
            throw new IllegalArgumentException("minimumReadableBytes: " + minimumReadableBytes + " (expected: >= 0)");
        }
        if (readerIndex() > writerIndex() - minimumReadableBytes) {
            throw new IndexOutOfBoundsException(String.format(
                    "readerIndex(%d) + length(%d) exceeds writerIndex(%d): %s",
                    readerIndex(), minimumReadableBytes, writerIndex(), this));
        }
    }

    void checkIndex(int index, int fieldLength) {
        if (isOutOfBounds(index, fieldLength, capacity())) {
            throw new IndexOutOfBoundsException(String.format(
                    "index: %d, length: %d (expected: range(0, %d))", index, fieldLength, capacity()));
        }
    }

    boolean isOutOfBounds(int index, int length, int capacity) {
        return (index | length | (index + length) | (capacity - (index + length))) < 0;
    }

    void checkNewCapacity(int newCapacity) {
        if (checkBounds) {
            if (newCapacity < 0 || newCapacity > maxCapacity()) {
                throw new IllegalArgumentException("newCapacity: " + newCapacity +
                        " (expected: 0-" + maxCapacity() + ')');
            }
        }
    }

    void ensureAccessible() {
        if (checkAcessible) {
            if (freed) {
                if (this instanceof NativeMemory) {
                    throw new IllegalStateException(String.format("%d is already freed.", memoryAddress()));
                } else {
                    throw new IllegalStateException("Memory is already cleaned/freed.");
                }
            }
        }
    }

}
