package com.sanderdl.match.util;

import com.google.gson.Gson;

public class MessagingConverter {

    private MessagingConverter(){}

    public static <T>String classToString(T instance){
        Gson gson = new Gson();
        return gson.toJson(instance);
    }

    public static <T> T stringToClass(String message, Class<T> dataClass){
        Gson gson = new Gson();
        return gson.fromJson(message, dataClass );
    }
}
