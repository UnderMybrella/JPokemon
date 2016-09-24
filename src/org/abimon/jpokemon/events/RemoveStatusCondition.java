package org.abimon.jpokemon.events;

import org.abimon.jpokemon.BattleProvider;
import org.abimon.jpokemon.IPokemon;

/**
 * Created by undermybrella on 5/09/2016.
 */
public class RemoveStatusCondition extends PokemonEvent{

    public final IPokemon pkmn;
    public String condition;
    public final String method;
    public final BattleProvider battleProvider;

    public RemoveStatusCondition(IPokemon pkmn, String condition, String s, BattleProvider battleProvider) {
        this.pkmn = pkmn;
        this.condition = condition;
        this.method = s;
        this.battleProvider = battleProvider;
    }
}
