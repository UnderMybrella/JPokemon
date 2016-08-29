package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;

public class OtherDamageEvent extends PokemonEvent{
    public IPokemon affecting;
    public double damage;
    public String damageType;
    public IBattleProvider provider;

    public OtherDamageEvent(IPokemon affecting, double damage, String damageType, IBattleProvider provider){
        this.affecting = affecting;
        this.damage = damage;
        this.damageType = damageType;
        this.provider = provider;
    }
}