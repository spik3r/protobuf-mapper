package com.kaitait;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Predicate;
import com.kaitait.matchers.*;



public class ProtoMapper
{
    public static Object createProtoMessageObject(Class clazz, Object domainObject)
            throws
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException,
            ClassNotFoundException,
            NoSuchFieldException,
            InstantiationException
    {
        Object builderInstance = createProtoMessageBuilder(clazz, domainObject);
        Method buildMethod = ProtoMapper.arraySearch(
                builderInstance.getClass().getDeclaredMethods(),
                new MethodNameMatchesPredicate("build"));
        return buildMethod.invoke(builderInstance,null);
    }
    
    public static Object createProtoMessageBuilder(Class clazz, Object domainObject) throws
            IllegalAccessException,
            InstantiationException,
            NoSuchMethodException,
            InvocationTargetException,
            NoSuchFieldException, ClassNotFoundException
    {
        //Make builder for main proto message
        Method builderMethod = clazz.getDeclaredMethod("newBuilder");
        Object builderInstance = builderMethod.invoke(null);
        
        Class<?> objClass = domainObject.getClass();
        Field[] domainObjectFields = objClass.getDeclaredFields();
        for(Field field : domainObjectFields)
        {
            field.setAccessible(true);
            //fieldValues.put(field.getName(), field.get(domainObject));
            if (!field.getType().getClass().getName().contains("java.lang"))
            {
//                iterateOverMemberFields(field.get(domainObject));
                
                
                Field innerField = ProtoMapper.arraySearch(
                        clazz.getDeclaredFields(),
                        new FieldNameMatchesPredicate(field.getName() + "_")
                );
                
                
                Object nestedMessage = createProtoMessageBuilder(
                        innerField.getType(),
                        field.get(domainObject)
                );
                String variable = field.getName();
                String setter = getSetterFor(variable);
                Method method = ProtoMapper.arraySearch(
                        builderInstance.getClass().getDeclaredMethods(),
                        new MethodNameMatchesPredicate(setter)
                );
                
                Class<?>[] a = method.getParameterTypes();
                Class b = nestedMessage.getClass();
                
                method.invoke(builderInstance, nestedMessage);
            }
            else {
                
                String variable = field.getName();
                String setter = getSetterFor(variable);
                Method method = ProtoMapper.arraySearch(
                        builderInstance.getClass().getDeclaredMethods(),
                        new MethodNameMatchesPredicate(setter));
                System.out.println("method: " + method.getName());
                System.out.println("param type: " + Arrays.toString(method.getParameterTypes()));
                System.out.println("params: " + Arrays.toString(method.getParameters()));
                System.out.println("field.get(domainObject): " + field.get(domainObject));
                System.out.println("field.get(domainObject).getClass(): " + field.get(domainObject).getClass());
                method.invoke(builderInstance, field.get(domainObject));
            }
        }
        Method buildMethod = ProtoMapper.arraySearch(
                builderInstance.getClass().getDeclaredMethods(),
                new MethodNameMatchesPredicate("build"));
        
        //return buildMethod.invoke(builderInstance,null);
        return builderInstance;
    }
    
    
    private static <T> T arraySearch(T[] array, Predicate<T> predicate)
    {
        for (T value : array)
        {
            if (predicate.test(value))
            {
                return value;
            }
        }
        
        return null;
    }
    
    public static String getSetterFor(String variableName){
        return "set" + capitalizeFirstLetter(variableName);
    }
    
    public static String capitalizeFirstLetter(String variableName){
        return variableName.substring(0, 1).toUpperCase() + variableName.substring(1);
    }
}




