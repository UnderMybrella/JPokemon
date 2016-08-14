package org.abimon.jpokemon;

public class Evolution {

    public EvolutionMethod method;
    public String evolveInto;
    public Object otherValue;

    public enum EvolutionMethod{
        HAPPINESS,
        HAPPINESS_DAY,
        HAPPINESS_NIGHT,
        LEVEL,
        TRADE,
        TRADE_ITEM,
        ITEM,
        ATTACK_GREATER,
        ATTACK_DEFENCE_EQUAL,
        DEFENCE_GREATER,
        SILCOON,
        CASCOON,
        NINJASK,
        SHEDNINJA,
        BEAUTY,
        ITEM_MALE,
        ITEM_FEMALE,
        DAY_HOLD_ITEM,
        NIGHT_HOLD_ITEM,
        HAS_MOVE,
        HAS_IN_PARTY,
        LEVEL_MALE,
        LEVEL_FEMALE,
        LOCATION,
        TRADE_SPECIES,
        LEVEL_DAY,
        LEVEL_NIGHT,
        LEVEL_DARK_IN_PARTY,
        LEVEL_RAIN,
        HAPPINESS_MOVE_TYPE;
    }
}
