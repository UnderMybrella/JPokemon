package org.abimon.jpokemon.events;

import org.abimon.jpokemon.BattleProvider;
import org.abimon.jpokemon.IPokemon;

public class SwitchInEvent extends PokemonEvent{
    public IPokemon newActive;
    public IPokemon previousActive;
    public BattleProvider battleProvider;

    public SwitchInEvent(IPokemon newActive, IPokemon previousActive, BattleProvider battleProvider) {
        this.newActive = newActive;
        this.previousActive = previousActive;
        this.battleProvider = battleProvider;
    }
}
