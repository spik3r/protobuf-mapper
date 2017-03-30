package com.kaitait;

import com.kaitait.matchers.alternativeMatchers.FieldNameMatcher;
import com.kaitait.matchers.alternativeMatchers.MethodNameMatcher;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.kaitait.StringHelpers.getSetterFor;
import static org.apache.commons.lang3.ClassUtils.isPrimitiveOrWrapper;

public class ProtoMapper {
    /**
     * Creates a protobuf object by using the builder created by: createProtoMessageBuilder
     *
     * @param clazz        the class of the object returned
     * @param domainObject the object to convert
     * @return an Object of type clazz
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object createProto(Class clazz, Object domainObject)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Object builderInstance = createProtoMessageBuilder(clazz, domainObject);

        Method buildMethod = ProtoMapper.methodSearch(
                builderInstance.getClass().getDeclaredMethods(),
                 "build");
        return buildMethod.invoke(builderInstance, null);
    }

    /**
     * Creates a protobuf object builder based on the class passed in with all the variables
     * of the domainObject. It should also work for non protobuf generated classes as long as they
     * have a builder and follow the same naming convention.
     * <p>
     * eg. if the domainObject has a variable: long objectId the proto class should have
     * long objectId_ as well as a builder with: newBuilder() and setObjectId(long value) methods.
     * See `package com.kaitait.testFixtures.proto` for an example.
     *
     * @param clazz        the class of the object builder returned
     * @param domainObject the object to convert
     * @return a builder instance of the type clazz
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static Object createProtoMessageBuilder(Class clazz, Object domainObject)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        final Method builderMethod = clazz.getDeclaredMethod("newBuilder");
        final Object builderInstance = builderMethod.invoke(null);
        final Class<?> objClass = domainObject.getClass();
        final Field[] domainObjectFields = objClass.getDeclaredFields();

        iterateOverFields(clazz, domainObject, builderInstance, domainObjectFields);

        return builderInstance;
    }

    private static void iterateOverFields(
            Class clazz,
            Object domainObject,
            Object builderInstance,
            Field[] domainObjectFields)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (Field field : domainObjectFields) {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            if (isPrimitiveOrWrapper(fieldType) || fieldType.equals(String.class) || fieldType.getName().contains("android")) {
                setValue(builderInstance, field.getName(), field.get(domainObject));
                continue;
            }

            iterateOverInnerFields(clazz, domainObject, builderInstance, field);
        }
    }

    private static void iterateOverInnerFields(
            Class clazz,
            Object domainObject,
            Object builderInstance, Field field)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        final Field innerField = ProtoMapper.fieldSearch(
                clazz.getDeclaredFields(),
                field.getName() + "_");

        final Object nestedMessage = createProto(
                innerField.getType(),
                field.get(domainObject));

        setValue(builderInstance, field.getName(), innerField.getType(), nestedMessage);
    }

    private static void setValue(Object builderInstance, String fieldName, Object value)
            throws IllegalAccessException, InvocationTargetException {
        final String setter = getSetterFor(fieldName);
        final Method method = findByName(builderInstance.getClass().getDeclaredMethods(), setter);
        try {
            method.invoke(builderInstance, value);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void setValue(Object builderInstance, String fieldName, Class<?> type, Object value)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        final String setter = getSetterFor(fieldName);
        final Method method = builderInstance.getClass().getDeclaredMethod(setter, type);

        try {
            method.invoke(builderInstance, value);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static Method findByName(Method[] methods, String methodName) {
        return ProtoMapper.methodSearch(
                methods,
                methodName);
    }

    /**
     * Helper method to search an array of Fields return the field if it's found.
     * @param fields an array of Fields
     * @param field the field to search for
     * @return the field if found
     */
    private static Field fieldSearch(final Field[] fields, final String field) {
        for (Field value : fields) {
            FieldNameMatcher matcher = new FieldNameMatcher(field);
            if (matcher.test(value)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Helper method to search an array of Methods return the method if it's found.
     * @param methods an array of Methods
     * @param method the method to search for
     * @return the method if found
     */
    private static Method methodSearch(final Method[] methods, final String method) {
        for (Method value : methods) {
            MethodNameMatcher matcher = new MethodNameMatcher(method);
            if (matcher.test(value)) {
                return value;
            }
        }
        return null;
    }
}




