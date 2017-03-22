package com.kaitait.matchers;

import java.lang.reflect.Method;
import java.util.function.Predicate;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class MethodNameMatchesPredicate implements Predicate<Method>
{
    public final String value;
    
    public MethodNameMatchesPredicate(String value)
    {
        this.value = value;
    }
    
    @Override
    public boolean test(Method method)
    {
        return this.value.equals(method.getName());
    }
}
