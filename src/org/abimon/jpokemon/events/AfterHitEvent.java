package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;
import org.abimon.jpokemon.Move;

public class AfterHitEvent extends PokemonEvent{

    public IPokemon attacking;
    public IPokemon target;
    public Move move;
    public IBattleProvider provider;
    public double damageDealt;
    public boolean critical;

    public AfterHitEvent(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider, double damageDealt, boolean critical){
        this.attacking = attacking;
        this.target = target;
        this.move = move;
        this.provider = provider;
        this.damageDealt = damageDealt;
        this.critical = critical;
    }
}
