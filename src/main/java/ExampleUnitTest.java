import java.lang.reflect.Field;

public class ExampleUnitTest
{
    public void addition_isCorrect() throws Exception
    {
        ParentClass a = new ParentClass();
        getMemberFields(a);
    }
    
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException
    {
        Foo foo = new Foo();
        getMemberFields(foo);
    }
    private static void getMemberFields(Object obj) throws IllegalAccessException, NoSuchFieldException
    {
        Class<?> objClass = obj.getClass();
        System.out.println("obj: " + obj.getClass());
        
        Field[] fields = objClass.getDeclaredFields();
        for(Field field : fields)
        {
            field.setAccessible(true);
            System.out.println("Field: " + field.getName() + " value: " + field.get(obj));
            
            // Instead you'd use the commons class to check if it is primitive or an object
            if (!field.getType().isPrimitive() && !field.getType().getName().contains("java.lang"))
            {
                getMemberFields(field.get(obj));
            }
        }
    }
}


class ParentClass
{
    private String message = "hello";
    private boolean answer = false;
    private SubClass subclass = new SubClass();
    
    public ParentClass() { }
}

class SubClass
{
    private int number = 123;
    private double other = 321.1;
}
