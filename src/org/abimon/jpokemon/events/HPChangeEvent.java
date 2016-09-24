package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;

public class HPChangeEvent extends PokemonEvent{

    public IPokemon change;
    public final int oldHP;
    public int newHP;

    public String method;
    public IBattleProvider provider;

    public HPChangeEvent(IPokemon pokemon, int oldHP, int newHP, String method, IBattleProvider provider){
        this.change = pokemon;
        this.oldHP = oldHP;
        this.newHP = newHP;

        this.method = method;
        this.provider = provider;
    }

}
