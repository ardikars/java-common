package com.ardikars.common.memory.internal;

import com.ardikars.common.util.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Direct ByteBuffer helper.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
public final class ByteBufferHelper {

    private static long BUFFER_ADDRESS_FIELD_OFFSET = -1;
    private static final Constructor<?> DIRECT_BUFFER_CONSTRUCTOR;

    /**
     * Returns management address of direct buffer.
     * @param buffer direct buffer.
     * @return returns management address.
     * @since 1.2.3
     */
    public static long directByteBufferAddress(ByteBuffer buffer) {
        return UnsafeHelper.getUnsafe().getLong(buffer, BUFFER_ADDRESS_FIELD_OFFSET);
    }

    /**
     * Wrap direct buffer from management address.
     * @param memoryAddress management address.
     * @param size buffer size.
     * @return returns {@link ByteBuffer} object.
     * @since 1.2.3
     */
    public static ByteBuffer wrapDirectByteBuffer(long memoryAddress, int size) {
        if (DIRECT_BUFFER_CONSTRUCTOR != null) {
            try {
                return (ByteBuffer) DIRECT_BUFFER_CONSTRUCTOR.newInstance(memoryAddress, size);
            } catch (Throwable cause) {
                throw new RuntimeException(cause);
            }
        }
        throw new UnsupportedOperationException(
                "sun.misc.Unsafe or java.nio.DirectByteBuffer.<init>(long, int) not available");
    }

    /**
     * Find buffer address field.
     * @param unsafe {@link sun.misc.Unsafe} object.
     * @return returns buffer address field, or returns exception on failure.
     * @since 1.2.3
     */
    private static Object findBufferAddressField(final ByteBuffer direct, final sun.misc.Unsafe unsafe) {
        final sun.misc.Unsafe finalUnsafe = unsafe;
        final Object maybeAddressField = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    final Field field = Buffer.class.getDeclaredField("address");
                    final long offset = finalUnsafe.objectFieldOffset(field);
                    final long address = finalUnsafe.getLong(direct, offset);
                    if (address == 0) {
                        // if it's buffer is direct buffer, address will no not 0.
                        return new IllegalStateException("Non direct buffer.");
                    }
                    return field;
                } catch (NoSuchFieldException e) {
                    return e;
                } catch (SecurityException e) {
                    return e;
                }
            }
        });
        return maybeAddressField;
    }

    private static Object findDirectBufferConstructor(final ByteBuffer direct) {
        final Object maybeDirectBufferConstructor = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    final Constructor<?> constructor =
                            direct.getClass().getDeclaredConstructor(long.class, int.class);
                    Throwable cause = Reflections.trySetAccessible(constructor, true);
                    if (cause != null) {
                        return cause;
                    }
                    return constructor;
                } catch (NoSuchMethodException e) {
                    return e;
                } catch (SecurityException e) {
                    return e;
                }
            }
        });
        return maybeDirectBufferConstructor;
    }

    static {
        Constructor<?> directBufferConstructor;
        if (UnsafeHelper.getUnsafe() != null) {
            ByteBuffer direct = ByteBuffer.allocateDirect(1);
            Object maybeBufferAddressField = findBufferAddressField(direct, UnsafeHelper.getUnsafe());
            if (maybeBufferAddressField instanceof Field) {
                BUFFER_ADDRESS_FIELD_OFFSET = UnsafeHelper.getUnsafe().objectFieldOffset((Field) maybeBufferAddressField);
            }
            long address = -1;
            try {
                Object maybeDirectBufferConstructor = findDirectBufferConstructor(direct);
                if (maybeDirectBufferConstructor instanceof Constructor<?>) {
                    address = UnsafeHelper.getUnsafe().allocateMemory(1);
                    try {
                        ((Constructor<?>) maybeDirectBufferConstructor).newInstance(address, 1);
                        directBufferConstructor = (Constructor<?>) maybeDirectBufferConstructor;
                    } catch (InstantiationException e) {
                        directBufferConstructor = null;
                    } catch (IllegalAccessException e) {
                        directBufferConstructor = null;
                    } catch (InvocationTargetException e) {
                        directBufferConstructor = null;
                    }
                } else {
                    directBufferConstructor = null;
                }
            } finally {
                if (address != -1) {
                    UnsafeHelper.getUnsafe().freeMemory(address);
                }
            }
        } else {
            directBufferConstructor = null;
        }
        DIRECT_BUFFER_CONSTRUCTOR = directBufferConstructor;
    }

}
