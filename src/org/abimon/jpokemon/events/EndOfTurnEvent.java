package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;

public class EndOfTurnEvent extends PokemonEvent{

    public IBattleProvider provider;

    public EndOfTurnEvent(IBattleProvider provider){

        this.provider = provider;
    }

}
