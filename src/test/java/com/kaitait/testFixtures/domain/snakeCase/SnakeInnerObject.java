package com.kaitait.testFixtures.domain.snakeCase;

/**
 * Created by kai-tait on 22/03/2017.
 */
public class SnakeInnerObject {
    private long object_id;
    private String serial;

    public SnakeInnerObject(long object_id, String serial) {
        this.object_id = object_id;
        this.serial = serial;
    }

    public long GetObjectId() {
        return object_id;
    }

    public void SetObjectId(long object_id) {
        this.object_id = object_id;
    }

    public String GetSerial() {
        return serial;
    }

    public void SetSerial(String serial) {
        this.serial = serial;
    }
}
