package com.kaitait.matchers.alternativeMatchers;

import java.lang.reflect.Field;

import static com.kaitait.StringHelpers.fromSnakeCase;

/**
 * Alternative matcher not using predicates
 * Created by kai-tait on 29/03/2017.
 */
public class FieldNameMatcher {
    public final String value;

    public FieldNameMatcher(String value) {
        this.value = value;
    }

    public boolean test(Field field) {
        final String snakeCase = fromSnakeCase(value) + "_";
        //Check if the field name matches either as snake case or camel case
        return this.value.equals(field.getName()) || snakeCase.equals(field.getName());
    }
}
