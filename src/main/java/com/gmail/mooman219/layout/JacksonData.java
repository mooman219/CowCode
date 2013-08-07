package com.gmail.mooman219.layout;

import java.io.Serializable;

public interface JacksonData extends Serializable {
    /**
     * Must have a empty constructor
     * protected JacksonData() {}
     */

    /**
     * Returns the json string representing this class.
     */
    public String serialize();

    /**
     * Must have this static method
     * public static JacksonData deserialize(String data) {}
     */

    /**
     * Should implement its own .hashCode() and .equals()
     */
}
