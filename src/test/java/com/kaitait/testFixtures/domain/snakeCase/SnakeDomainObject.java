package com.kaitait.testFixtures.domain.snakeCase;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class SnakeDomainObject {
    private SnakeInnerObject inner_object;
    private String label = "";

    public SnakeDomainObject(String label, SnakeInnerObject inner_object) {
        this.label = label;
        this.inner_object = inner_object;
    }

    public SnakeInnerObject GetInnerObject() {
        return inner_object;
    }

    public void SetInnerObject(SnakeInnerObject inner_object) {
        this.inner_object = inner_object;
    }

    public String GetLabel() {
        return label;
    }

    public void SetLabel(String label) {
        this.label = label;
    }
}
