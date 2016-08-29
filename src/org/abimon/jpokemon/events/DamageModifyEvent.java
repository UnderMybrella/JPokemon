package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;
import org.abimon.jpokemon.Move;

/** Your final chance to modify the damage of a move
 * stab, typeModifier, and other are all able to be modified */
public class DamageModifyEvent extends PokemonEvent{

    public IPokemon attacking, target;
    public Move move;
    public IBattleProvider provider;
    public double stab, typeModifier;
    public boolean isCritical;
    private double other;

    public DamageModifyEvent(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider, double stab, double typeModifier, boolean isCritical) {
        this.attacking = attacking;
        this.target = target;
        this.move = move;
        this.provider = provider;
        this.stab = stab;
        this.typeModifier = typeModifier;
        this.other = 1;
        this.isCritical = isCritical;
    }

    public void registerOtherDamage(double modifier){
        other *= modifier;
    }

    public double getOther(){
        return other;
    }
}
