package infrastructure.persistence.grpc;

import com.kaitait.Bar;
import com.kaitait.DummyBar;
import com.zencontrol.sites.generated.ControllerIdRequestMessage;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

import static org.apache.commons.lang3.ClassUtils.isPrimitiveOrWrapper;

public class ProtoMapper
{
    private static HashMap hmap;
    private static HashMap<String, Object> fieldValues = new HashMap<String, Object>();
    
    public static void main(String[] args) throws Exception {
        System.out.println("_____createClass______");
        Bar bar = new Bar(98765.4321, "foobar");
        System.out.println("Original class: " + bar.getClass());
        DummyBar dummyBar = (DummyBar) createClass(DummyBar.class, bar);
        System.out.println("dummy proto");
        System.out.println("String: " + dummyBar.getInnerString());
        System.out.println("Double: " + dummyBar.getSomeDouble());
        System.out.println("Class: " + dummyBar.getClass());
        
        System.out.println("_____after createClass______");
    }
    
    
    //    private static HashMap<String, Object> getMemberFields(Object obj) throws IllegalAccessException,
    private static Object getMemberFields(Object obj) throws IllegalAccessException,
            NoSuchFieldException, InvocationTargetException, NoSuchMethodException
    {
//         HashMap<String, Object> fieldValues = new HashMap<String, Object>();
        Class<?> objClass = obj.getClass();
        
        Field[] fields = objClass.getDeclaredFields();
        for(Field field : fields)
        {
            field.setAccessible(true);
            fieldValues.put(field.getName(), field.get(obj));
            if (!isPrimitiveOrWrapper(field.getType()))
            {
                getMemberFields(field.get(obj));
            }
            else {
                return setValue(obj, field);
            }
        }
        //return fieldValues;
        return null;
    }
    
    public static Object setValue(Object object, Field field)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        
        Class clazz = object.getClass();
        Method builderMethod = clazz.getDeclaredMethod("newBuilder");
        Object builderInstance = builderMethod.invoke(null);
        
        String variable = field.getName();
        String setter = getSetterFor(variable);
        Method method = ProtoMapper.<Method>arraySearch(
                builderInstance.getClass().getDeclaredMethods(),
                new MethodNameMatchesPredicate(setter));
        
        return method.invoke(builderInstance, field.get(object));
    }
    
    public static String getSetterFor(String variableName){
        return "set" + capitalizeFirstLetter(variableName);
    }
    
    public static String capitalizeFirstLetter(String variableName){
        return variableName.substring(0, 1).toUpperCase() + variableName.substring(1);
    }
    
    public static Object createClass(Class clazz, Object objectToMapFrom) throws
            IllegalAccessException,
            InstantiationException,
            NoSuchMethodException,
            InvocationTargetException,
            NoSuchFieldException, ClassNotFoundException
    {
        hmap = getMemberFields(objectToMapFrom);
        Set<String> variables = hmap.keySet();
        
        List<Class> constructorArgs = new ArrayList<Class>();
        List<Object> constructorValues = new ArrayList<Object>();
        List<String> fields = new ArrayList<String>();
        
        for(String key: variables) {
            fields.add(key);
            constructorArgs.add(hmap.get(key).getClass());
            constructorValues.add(hmap.get(key));
        }
        Class[] classes = (constructorArgs.toArray(new Class[variables.size()]));
        Object[] values = constructorValues.toArray();
        
        
        Class noparams[] = {};
        Method method = clazz.getDeclaredMethod("newBuilder");
        Object builderInstance = method.invoke(null);
        
        
        
        Method method1 = null;
        Method method2 = null;
        Method method3 = null;
        
        Method m = ProtoMapper.<Method>arraySearch(
                builderInstance.getClass().getDeclaredMethods(),
                new MethodNameMatchesPredicate("setLabel")
        );
        
        
        method1.invoke(builderInstance, "test");
        ControllerIdRequestMessage idRequestMessage = ControllerIdRequestMessage
                .newBuilder()
                .setSerial
                        ("SDFGHJK")
                .setGtin(1234L)
                .build();
        method2.invoke(builderInstance, idRequestMessage);
        
        
        
        return method3.invoke(builderInstance, null);
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
    
    private static void setMemberFields(Object obj) throws IllegalAccessException
    {
        Class objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for(Field field : fields)
        {
            field.setAccessible(true);
            Object fieldValue = hmap.get(field.getName());
            
            field.set(obj, fieldValue);
        }
    }
}

class MethodNameMatchesPredicate implements Predicate<Method>
{
    private final String value;
    
    MethodNameMatchesPredicate(String value)
    {
        this.value = value;
    }
    
    @Override
    public boolean test(Method method)
    {
        return this.value.equals(method.getName());
    }
}