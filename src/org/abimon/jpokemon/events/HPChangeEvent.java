package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IPokemon;

public class HPChangeEvent extends PokemonEvent{

    public IPokemon change;
    public final int oldHP;
    public int newHP;

    public String method;

    public HPChangeEvent(IPokemon pokemon, int oldHP, int newHP, String method){
        this.change = pokemon;
        this.oldHP = oldHP;
        this.newHP = newHP;

        this.method = method;
    }

}
