import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Mapper {
    private static HashMap hmap;
    
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

    
    private static HashMap<String, Object> getMemberFields(Object obj) throws IllegalAccessException,
            NoSuchFieldException
    {
         HashMap<String, Object> fieldValues = new HashMap<String, Object>();
                Class<?> objClass = obj.getClass();
        
        Field[] fields = objClass.getDeclaredFields();
        for(Field field : fields)
        {
            field.setAccessible(true);
            fieldValues.put(field.getName(), field.get(obj));
            // Todo: use the commons class to check if it is primitive or an object
            if (!field.getType().isPrimitive() && !field.getType().getName().contains("java.lang"))
            {
                getMemberFields(field.get(obj));
            }
        }
        return fieldValues;
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
        
        Constructor constructor = clazz.getConstructor(classes);
        constructor.setAccessible(true);
        Object someClass = constructor.newInstance(values);
        setMemberFields(someClass);

        return someClass;
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
