package com.gmail.mooman219.old;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class StoreTag extends CStorage<Serializable> {
    public byte[] serialize() throws IOException {
        if(!isInitialized()) {
            return new byte[0];
        }
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(store);
        return b.toByteArray();
    }

    public void deserialize(byte[] bytes) throws IOException {
        if(bytes == null || bytes.length == 0) {
            return;
        }
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        try {
            store = (HashMap<String, Serializable>) o.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}