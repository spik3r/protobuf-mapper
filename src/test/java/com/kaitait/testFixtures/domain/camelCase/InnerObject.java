package com.kaitait.testFixtures.domain.camelCase;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class InnerObject {
    private long objectId;
    private String serial;

    public InnerObject(long objectId, String serial) {
        this.objectId = objectId;
        this.serial = serial;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
