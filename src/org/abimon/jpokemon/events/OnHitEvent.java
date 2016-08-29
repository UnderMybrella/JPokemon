package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;
import org.abimon.jpokemon.Move;

/** Fired every time a move hits, including multiple times for multi hit events.
 * Use AfterHitEvent if you're only checking once, after the attack hits.
 */
public class OnHitEvent extends PokemonEvent{

    public IPokemon attacking;
    public IPokemon target;
    public Move move;
    public IBattleProvider provider;
    public double damageDealt;
    public boolean critical;

    public OnHitEvent(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider, double damageDealt, boolean critical){
        this.attacking = attacking;
        this.target = target;
        this.move = move;
        this.provider = provider;
        this.damageDealt = damageDealt;
        this.critical = critical;
    }
}
