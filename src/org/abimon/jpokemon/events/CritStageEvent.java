package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;
import org.abimon.jpokemon.Move;

public class CritStageEvent extends PokemonEvent{

    public IPokemon attacking, target;
    public Move move;
    public IBattleProvider provider;
    public int critStage;

    public CritStageEvent(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider, int critStage) {
        this.attacking = attacking;
        this.target = target;
        this.move = move;
        this.provider = provider;
        this.critStage = critStage;
    }
}
