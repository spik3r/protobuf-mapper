package com.kaitait.testFixtures.proto;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class ProtoObject
{
    private String label_ = "";
    private InnerObject innerObject_;
    
    private ProtoObject(){}
    
    private ProtoObject(ProtoObject.Builder builder) {
        this.label_ = builder.label_;
        this.innerObject_ = builder.innerObject_;
    }
    
    public String getLabel() {
        return this.label_;
    }
    
    public InnerObject getInnerObject() {
        return this.innerObject_;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static final class Builder {
        private String label_;
        private InnerObject innerObject_;
    
        public Builder() {}
        
        public Builder setLabel(String value) {
            label_ = value;
            return this;
        }
    
        public String getLabel() {
            return this.label_;
        }
        
        public Builder setInnerObject(InnerObject.Builder value) {
            innerObject_ = value.build();
            return this;
        }
        
        public InnerObject getInnerObject() {
            return this.innerObject_;
        }
        public ProtoObject build() {
            return new ProtoObject(this);
        }
    }
}
