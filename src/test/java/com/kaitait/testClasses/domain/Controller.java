package com.kaitait.testClasses.domain;

import com.kaitait.testClasses.proto.ControllerIdRequestMessage;

import java.lang.reflect.Constructor;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class Controller
{
    private ControllerId controllerId;
    private String label = "";
    
    public Controller(String label, ControllerId controllerId){
        this.label = label;
        this.controllerId = controllerId;
    }
    
    public ControllerId getControllerId()
    {
        return controllerId;
    }
    
    public void setControllerId(ControllerId controllerId)
    {
        this.controllerId = controllerId;
    }
    
    public String getLabel()
    {
        return label;
    }
    
    public void setLabel(String label)
    {
        this.label = label;
    }
}
