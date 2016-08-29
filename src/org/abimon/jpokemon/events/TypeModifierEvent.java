package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;
import org.abimon.jpokemon.Move;

public class TypeModifierEvent extends PokemonEvent{

    public IPokemon attacking, target;
    public Move move;
    public IBattleProvider provider;
    public double typeModifier;

    public TypeModifierEvent(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider, double typeModifier) {
        this.attacking = attacking;
        this.target = target;
        this.move = move;
        this.provider = provider;
        this.typeModifier = typeModifier;
    }
}
