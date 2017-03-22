package com.kaitait.testClasses.domain;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class ControllerId
{
    private long gtin;
    private String serial;
    
    public ControllerId(long gtin, String serial) {
        this.gtin = gtin;
        this.serial = serial;
    }
    
    public long getGtin()
    {
        return gtin;
    }
    
    public void setGtin(long gtin)
    {
        this.gtin = gtin;
    }
    
    public String getSerial()
    {
        return serial;
    }
    
    public void setSerial(String serial)
    {
        this.serial = serial;
    }
}
