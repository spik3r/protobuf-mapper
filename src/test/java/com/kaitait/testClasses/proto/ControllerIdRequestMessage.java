package com.kaitait.testClasses.proto;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class ControllerIdRequestMessage {
    private long gtin_;
    private String serial_;
    
    
    private ControllerIdRequestMessage(){}

    
    private ControllerIdRequestMessage(Builder builder) {
        this.gtin_ = builder.gtin_;
        this.serial_ = builder.serial_;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static final class Builder {
        private long gtin_;
        private String serial_;
        
        public Builder() {
            return;
        }
        
        public Builder setGtin(long value) {
            gtin_ = value;
            return this;
        }
    
        public Builder setSerial(String value) {
            serial_ = value;
            return this;
        }
        
        public ControllerIdRequestMessage build() {
            return new ControllerIdRequestMessage(this);
        }
    }
}
