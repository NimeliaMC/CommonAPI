package fr.nimelia.api.common;

import java.lang.reflect.Field;

public class Reflection {

    public static <T> boolean setField(String name, Class<T> clazz, boolean isPrivate, T instance, Object value) {
        try {
            Field field = null;
            if (isPrivate) {
                field = clazz.getDeclaredField(name);
                field.setAccessible(true);
            } else {
                field = clazz.getField(name);
            }
            field.set(instance, value);
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }

    }

}
