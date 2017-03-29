package com.kaitait.matchers.alternativeMatchers;

import java.lang.reflect.Method;

/**
 * Alternative matcher not using predicates
 * Created by kai-tait on 29/03/2017.
 */
public class MethodNameMatcher {
    public final String value;

    public MethodNameMatcher(String value) {
        this.value = value;
    }

    public boolean test(Method method) {
        return this.value.equals(method.getName());
    }
}
