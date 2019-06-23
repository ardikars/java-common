package com.ardikars.common.memory;

/**
 * Memory allocator.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
public interface MemoryAllocator {

    Memory allocate(int capacity);

    Memory allocate(int capacity, boolean checking);

    Memory allocate(int capacity, int maxCapacity);

    Memory allocate(int capacity, int maxCapacity, boolean checking);

    Memory allocate(int capacity, int maxCapacity, int readerIndex, int writerIndex);

    Memory allocate(int capacity, int maxCapacity, int readerIndex, int writerIndex, boolean checking);

    void close();

}
