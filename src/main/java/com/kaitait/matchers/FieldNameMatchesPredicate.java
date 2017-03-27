package com.kaitait.matchers;

import java.lang.reflect.Field;

import java.util.function.Predicate;
import static com.kaitait.StringHelpers.fromSnakeCase;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class FieldNameMatchesPredicate implements Predicate<Field> {
    public final String value;

    public FieldNameMatchesPredicate(String value) {
        this.value = value;
    }

    @Override
    public boolean test(Field field) {
        final String snakeCase = fromSnakeCase(value) + "_";
        //Check if the field name matches either as snake case or camel case
        return this.value.equals(field.getName()) || snakeCase.equals(field.getName());
    }
}
