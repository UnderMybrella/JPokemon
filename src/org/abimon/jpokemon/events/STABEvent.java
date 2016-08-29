package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;
import org.abimon.jpokemon.Move;

/** Note: All parameters except for STAB are ignored */
public class STABEvent extends PokemonEvent {
    public IPokemon attacking, target;
    public Move move;
    public IBattleProvider provider;
    public double stab;

    public STABEvent(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider, double stab) {
        this.attacking = attacking;
        this.target = target;
        this.move = move;
        this.provider = provider;
        this.stab = stab;
    }
}
