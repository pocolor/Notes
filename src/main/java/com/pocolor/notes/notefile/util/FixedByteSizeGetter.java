package com.pocolor.notes.notefile.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class FixedByteSizeGetter {
    private FixedByteSizeGetter() throws Exception { throw new Exception("no instances of this class"); }

    public static int getFixedByteSizeOf(Class<?> clazz) {
        return getFixedByteSizeOf(clazz, clazz.getName());
    }

    private static int getFixedByteSizeOf(Class<?> clazz, String stackTrace) {
        if (clazz.isPrimitive()) return calcPrimitive(clazz);
        if (!clazz.isAnnotationPresent(FixedByteSize.class)) {
            throw new IllegalArgumentException(stackTrace + " class has no FixedByteSize annotation.");
        }

        int size = 0;

        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) continue;
            if (field.isAnnotationPresent(SkipByteSize.class)) continue;

            if (field.isAnnotationPresent(ByteSize.class)) {
                size += field.getAnnotation(ByteSize.class).value();
            }

            Class<?> innerClazz = field.getType();
            stackTrace += " ." + field.getName() + ":" + innerClazz.getSimpleName();
            checkUnsupportedFieldTypes(innerClazz, stackTrace);
            size += getFixedByteSizeOf(innerClazz, stackTrace);
        }

        return size;
    }

    private static int calcPrimitive(Class<?> clazz) {
        assert clazz.isPrimitive();

        if (clazz == byte.class) return Byte.BYTES;
        if (clazz == short.class) return Short.BYTES;
        if (clazz == int.class) return Integer.BYTES;
        if (clazz == long.class) return Long.BYTES;
        if (clazz == float.class) return Float.BYTES;
        if (clazz == double.class) return Double.BYTES;
        if (clazz == char.class) return Character.BYTES;
        if (clazz == boolean.class) return 1;  // will be stored in 1 byte for simplicity

        throw new IllegalArgumentException("Not a primitive type: " + clazz);
    }

    private static void checkUnsupportedFieldTypes(Class<?> clazz, String stackTrace) {
        if (clazz == String.class || clazz.isArray() || clazz.isEnum()) {
            throw new IllegalArgumentException(
                    stackTrace + " has no @ByteSize annotation. No way to determinate byte size. Annotate with @SkipByteSize to skip this field."
            );
        }
    }
}
