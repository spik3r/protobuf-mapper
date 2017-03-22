package com.kaitait.testClasses.proto;

import com.kaitait.testClasses.domain.ControllerId;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class ControllerRequestMessage {
    private String label_ = "";
    private ControllerIdRequestMessage controllerId_;
    
    private ControllerRequestMessage(){}
    
    private ControllerRequestMessage(ControllerRequestMessage.Builder builder) {
        this.label_ = builder.label_;
        this.controllerId_ = builder.controllerId_;
    }
    
    public String getLabel() {
        return this.label_;
    }
    
    public ControllerIdRequestMessage getControllerId() {
        return this.controllerId_;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static final class Builder {
        private String label_;
        private ControllerIdRequestMessage controllerId_;
    
        public Builder() {}
        
        public Builder setLabel(String value) {
            label_ = value;
            return this;
        }
    
        public String getLabel() {
            return this.label_;
        }
        
        public Builder setControllerId(ControllerIdRequestMessage value) {
            controllerId_ = value;
            return this;
        }
        
        public ControllerIdRequestMessage getControllerId() {
            return this.controllerId_;
        }
        public ControllerRequestMessage build() {
            return new ControllerRequestMessage(this);
        }
    }
}
