package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;

public class StatChangeEvent extends PokemonEvent{

    public IPokemon changing;
    public String stat;
    public int changeFrom; //Note: May be stages
    public int changeTo; //Note: May be stages
    public String cause;
    public IBattleProvider provider;

    public StatChangeEvent(IPokemon changing, String stat, int changeFrom, int changeTo, String cause, IBattleProvider provider){
        this.changing = changing;
        this.stat = stat;
        this.changeFrom = changeFrom;
        this.changeTo = changeTo;
        this.cause = cause;
        this.provider = provider;
    }
}
