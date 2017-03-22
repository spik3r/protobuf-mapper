package com.kaitait.matchers;

import java.lang.reflect.Field;
import java.util.function.Predicate;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class FieldNameMatchesPredicate implements Predicate<Field>
{
    public final String value;
    
    public FieldNameMatchesPredicate(String value)
    {
        this.value = value;
    }
    
    @Override
    public boolean test(Field field)
    {
        return this.value.equals(field.getName());
    }
}
