import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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

    
    private static HashMap<String, Object> getMemberFieldss(Object obj) throws IllegalAccessException,
            NoSuchFieldException
    {
         HashMap<String, Object> fieldValues = new HashMap<String, Object>();
                Class<?> objClass = obj.getClass();
        System.out.println("obj: " + obj.getClass());
        
        Field[] fields = objClass.getDeclaredFields();
        for(Field field : fields)
        {
            field.setAccessible(true);
            System.out.println("Field: " + field.getName() + " value: " + field.get(obj));
            fieldValues.put(field.getName(), field.get(obj));
            // Todo: use the commons class to check if it is primitive or an object
            if (!field.getType().isPrimitive() && !field.getType().getName().contains("java.lang"))
            {
                getMemberFieldss(field.get(obj));
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
        hmap = getMemberFieldss(objectToMapFrom);
        Set<String> variables = hmap.keySet();
        
        List<Class> constructorArgs = new ArrayList<Class>();
        List<Object> constructorValues = new ArrayList<Object>();
        List<String> fields = new ArrayList<String>();
        
        for(String key: variables) {
            System.out.println("Class: " + hmap.get(key).getClass().getClass());
            System.out.println("Value: " + hmap.get(key));
            System.out.println("Key: " + key);
            fields.add(key);
            constructorArgs.add(hmap.get(key).getClass());
            constructorValues.add(hmap.get(key));
        }
        Class[] classes = (constructorArgs.toArray(new Class[variables.size()]));
        Object[] values = constructorValues.toArray();
        String[] vars = fields.toArray(new String[fields.size()]);
        System.out.println("classes: " + Arrays.toString(classes));
        System.out.println("variables: " + Arrays.toString(vars));
        System.out.println("values: " + Arrays.toString(values));
        
        Constructor constructor = clazz.getConstructor(classes);
        constructor.setAccessible(true);
        Object someClass = constructor.newInstance(values);
        
        System.out.println("someCLass: " + someClass.getClass());
        setMemberFields(someClass);
        
        
        
        return someClass;
    }
    
    private static void setMemberFields(Object obj) throws IllegalAccessException
    {
        Class objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        System.out.println("...Fields: " + Arrays.toString(fields));
        for(Field field : fields)
        {
            field.setAccessible(true);
            Object fieldValue = hmap.get(field.getName());

            field.set(obj, fieldValue);
        }
    }
}
