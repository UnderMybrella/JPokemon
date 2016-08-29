package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;

public class WeatherChangeEvent extends PokemonEvent{

    public String newWeather;
    public int turns;
    public String method;
    public IBattleProvider provider;

    public WeatherChangeEvent(String newWeather, int turns, String method, IBattleProvider provider) {
        this.newWeather = newWeather;
        this.turns = turns;
        this.method = method;
        this.provider = provider;
    }
}
