package org.example.ordermanagement.util;

import java.lang.reflect.Field;

public class CustomBeanUtils {

    public static void copyNonNullProperties(Object src, Object target) {
        Field[] fields = src.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(src);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                // Handle the exception appropriately
            }
        }
    }

}
