package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;
import org.abimon.jpokemon.Move;

public class MoveSelectEvent extends PokemonEvent{

    public IPokemon using;
    /** If move == null, then the player must reselect a move */
    public Move move;
    public IBattleProvider provider;

    public MoveSelectEvent(IPokemon using, Move move, IBattleProvider provider){
        this.using = using;
        this.move = move;
        this.provider = provider;
    }

}
