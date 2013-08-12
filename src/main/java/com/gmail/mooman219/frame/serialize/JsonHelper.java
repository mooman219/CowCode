package com.gmail.mooman219.frame.serialize;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.UUID;

import org.bukkit.Material;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.gmail.mooman219.frame.serialize.json.BasicVectorDouble;
import com.gmail.mooman219.frame.serialize.json.BasicVectorInteger;
import com.gmail.mooman219.frame.serialize.json.BukkitAdapters;
import com.gmail.mooman219.module.region.api.RegionCombatType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonHelper {
    /**
     * google-gson
     */

    private static Gson gson;

    static {
        gson = getGsonBuilder().create();
    }

    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder()
        .registerTypeAdapter(BasicVectorDouble.class, BasicVectorDouble.getAdapter())
        .registerTypeAdapter(BasicVectorInteger.class, BasicVectorInteger.getAdapter())
        .registerTypeAdapter(RegionCombatType.class, RegionCombatType.getAdapter())
        .registerTypeAdapter(Material.class, BukkitAdapters.getMaterialAdapter())
        .registerTypeAdapter(UUID.class, BukkitAdapters.getUUIDAdapter());
    }

    public static Gson getGson() {
        return gson;
    }

    public static <T> Type getCollectionType(T type) {
        return new TypeToken<Collection<T>>(){}.getType();
    }

    public static String toJson(Object object) {
        return getGson().toJson(object);
    }

    /**
     * Jackson
     */

    // Read shit
    // mapper.readValue(input string, class being read)
    //
    // Indented fancy output
    // SerializationFeature.INDENT_OUTPUT
    //
    // Variable Visability
    // mapper.setVisibilityChecker(mapper.getVisibilityChecker().withFieldVisibility(Visibility.ANY));
    // Or use at top of class:
    // @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    //
    // To hide null values,
    // Add at top of class, @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)

    private static ObjectMapper mapper;
    private static ObjectMapper fancyMapper;

    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new AfterburnerModule());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        fancyMapper = new ObjectMapper();
        fancyMapper.registerModule(new AfterburnerModule());
        fancyMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        fancyMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        fancyMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getJackson() {
        return mapper;
    }

    public static ObjectMapper getFancyJackson() {
        return fancyMapper;
    }

    public static String toJackson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T fromJackson(Object data, Class<T> type) {
        if(data != null) {
            try {
                if(data instanceof String) {
                    String data_string = (String) data;
                    if(data_string.length() > 0) {
                        return mapper.readValue(data_string, type);
                    }
                } else if(data instanceof File) {
                    File data_file = (File) data;
                    return mapper.readValue(data_file, type);
                } else {
                    throw new IllegalArgumentException("Unsupported data type.");
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
