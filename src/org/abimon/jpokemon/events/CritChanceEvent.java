package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;
import org.abimon.jpokemon.Move;

public class CritChanceEvent extends PokemonEvent{

    public IPokemon attacking, target;
    public Move move;
    public IBattleProvider provider;
    public double critChance;

    public CritChanceEvent(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider, double critChance) {
        this.attacking = attacking;
        this.target = target;
        this.move = move;
        this.provider = provider;
        this.critChance = critChance;
    }
}
