package org.abimon.jpokemon;

public class Evolution {

    public EvolutionMethod method;
    public String evolveInto;
    public Object otherValue;

    public enum EvolutionMethod{
        HAPPINESS,
        HAPPINESSDAY,
        HAPPINESSNIGHT,
        LEVEL,
        TRADE,
        TRADEITEM,
        ITEM,
        ATTACKGREATER,
        ATKDEFEQUAL,
        DEFENSEGREATER,
        SILCOON,
        CASCOON,
        NINJASK,
        SHEDINJA,
        BEAUTY,
        ITEMMALE,
        ITEMFEMALE,
        DAYHOLDITEM,
        NIGHTHOLDITEM,
        HASMOVE,
        HASINPARTY,
        LEVELMALE,
        LEVELFEMALE,
        LOCATION,
        TRADESPECIES,
        LEVELDAY,
        LEVELNIGHT,
        LEVELDARKINPARTY,
        LEVELRAIN,
        HAPPINESSMOVETYPE;
    }
}
