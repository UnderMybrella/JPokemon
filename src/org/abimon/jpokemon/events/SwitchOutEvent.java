package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;

public class SwitchOutEvent extends PokemonEvent{

    public IPokemon out;
    public IPokemon replace;
    public IBattleProvider provider;

    public SwitchOutEvent(IPokemon out, IPokemon replace, IBattleProvider provider){

        this.out = out;
        this.replace = replace;
        this.provider = provider;
    }
}
