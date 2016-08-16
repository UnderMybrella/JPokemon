package org.abimon.jpokemon;

import java.util.HashMap;

public class Weather {

    public static final HashMap<String, Weather> WEATHER_CONDITIONS = new HashMap<>();

    public static void load(){
        WEATHER_CONDITIONS.put("CLEAR", new Weather());
    }

    public static Weather get(String condition) {
        return WEATHER_CONDITIONS.get(condition);
    }
}
