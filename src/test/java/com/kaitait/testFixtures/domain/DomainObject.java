package com.kaitait.testFixtures.domain;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class DomainObject {
    private InnerObject innerObject;
    private String label = "";

    public DomainObject(String label, InnerObject innerObject) {
        this.label = label;
        this.innerObject = innerObject;
    }

    public InnerObject getInnerObject() {
        return innerObject;
    }

    public void setInnerObject(InnerObject innerObject) {
        this.innerObject = innerObject;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
