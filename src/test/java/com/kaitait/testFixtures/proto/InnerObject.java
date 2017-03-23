package com.kaitait.testFixtures.proto;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class InnerObject
{
    private long objectId_;
    private String serial_;
    
    
    private InnerObject(){}

    
    private InnerObject(Builder builder) {
        this.objectId_ = builder.objectId_;
        this.serial_ = builder.serial_;
    }
    
    public long getObjectId() {
        return this.objectId_;
    }
    
    public String getSerial() {
        return this.serial_;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static final class Builder {
        private long objectId_;
        private String serial_;
        
        public Builder() {
            return;
        }
        
        public Builder setObjectId(long value) {
            objectId_ = value;
            return this;
        }
    
        public Builder setSerial(String value) {
            serial_ = value;
            return this;
        }
        
        public InnerObject build() {
            return new InnerObject(this);
        }
    }
}
