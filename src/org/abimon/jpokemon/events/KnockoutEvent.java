package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;

public class KnockoutEvent extends PokemonEvent{

    public int startingHP;
    public int finalHP = 0;
    public String source = "";
    public IPokemon knockedOut;
    public IBattleProvider provider;

    public KnockoutEvent(IPokemon knockedOut, int startingHP, String source, IBattleProvider provider){
        this.knockedOut = knockedOut;
        this.startingHP = startingHP;
        this.source = source;
        this.provider = provider;
    }
}
