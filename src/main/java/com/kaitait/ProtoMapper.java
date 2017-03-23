package com.kaitait;

import com.kaitait.matchers.FieldNameMatchesPredicate;
import com.kaitait.matchers.MethodNameMatchesPredicate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

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
    public static Object createProtoMessageObject(Class clazz, Object domainObject)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Object builderInstance = createProtoMessageBuilder(clazz, domainObject);
        Method buildMethod = ProtoMapper.arraySearch(
                builderInstance.getClass().getDeclaredMethods(),
                new MethodNameMatchesPredicate("build"));
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

        ProtoMapper.arraySearch(
                builderInstance.getClass().getDeclaredMethods(),
                new MethodNameMatchesPredicate("build"));

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
            if (isPrimitiveOrWrapper(field.getType()) || field.getType().equals(String.class)) {
                setValue(domainObject, builderInstance, field);
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

        final Field innerField = ProtoMapper.arraySearch(
                clazz.getDeclaredFields(),
                new FieldNameMatchesPredicate(field.getName() + "_"));

        final Object nestedMessage = createProtoMessageBuilder(
                innerField.getType(),
                field.get(domainObject));
        final String variable = field.getName();
        final String setter = getSetterFor(variable);
        final Method method = ProtoMapper.arraySearch(
                builderInstance.getClass().getDeclaredMethods(),
                new MethodNameMatchesPredicate(setter));

        method.invoke(builderInstance, nestedMessage);
    }

    private static void setValue(Object domainObject, Object builderInstance, Field field)
            throws IllegalAccessException, InvocationTargetException {
        final String variable = field.getName();
        final String setter = getSetterFor(variable);
        final Method method = ProtoMapper.arraySearch(
                builderInstance.getClass().getDeclaredMethods(),
                new MethodNameMatchesPredicate(setter));
        method.invoke(builderInstance, field.get(domainObject));
    }

    /**
     * Helper method to search an array and return the value if it's found.
     *
     * @param array the array to search
     * @param predicate the matcher either method or field
     * @param <T> the type
     * @return the value if it's found in the array
     */
    private static <T> T arraySearch(final T[] array, final Predicate<T> predicate) {
        for (T value : array) {
            if (predicate.test(value)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Creates a camel case setter for the variable passed in.
     * eg. if the variable is objectId the setter returned will be setObjectId
     *
     * @param variableName
     * @return a String setter name in camel case
     */
    private static String getSetterFor(final String variableName) {
        return "set" + capitalizeFirstLetter(variableName);
    }

    /**
     * Returns the String passed in with the first letter capitalised.
     *
     * @param variableName
     * @return a String with the first letter in uppercase
     */
    private static String capitalizeFirstLetter(final String variableName) {
        return variableName.substring(0, 1).toUpperCase() + variableName.substring(1);
    }
}




