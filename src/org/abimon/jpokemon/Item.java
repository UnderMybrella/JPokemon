package org.abimon.jpokemon;

import org.abimon.jpokemon.events.PokemonEvent;
import org.abimon.omnis.io.Data;

import java.io.File;
import java.util.HashMap;

public class Item {

    public static final HashMap<String, Item> ITEMS = new HashMap<>();

    String internalName;
    String displayName;
    String displayNamePlural;
    String description;

    int price;

    UsabilityOutside outsideOfBattle;
    UsabilityInside inBattle;

    public static void load(){
        ITEMS.clear();

        try{
            for(String s : new Data(new File("items.txt")).getAsStringArray()){
                
            }
        }
        catch(Throwable th){
            th.printStackTrace();
        }
    }

    public enum UsabilityOutside{
        NOT_OUTSIDE_BATTLE,
        CONSUMABLE,
        OUTSIDE_NOT_ON_POKEMON,
        TM,
        HM,
        NONCONSUMABLE_USE_ON_POKEMON;

        public UsabilityOutside get(int i){
            switch (i){
                case 0:
                    return NOT_OUTSIDE_BATTLE;
                case 2:
                    return OUTSIDE_NOT_ON_POKEMON;
                case 3:
                    return TM;
                case 4:
                    return HM;
                case 5:
                    return NONCONSUMABLE_USE_ON_POKEMON;
                default:
                    return CONSUMABLE;
            }
        }
    }

    public enum UsabilityInside{
        NOT_IN_BATTLE,
        CONSUMABLE_ONE_PARTY,
        POKEBALL_ACTIVE_OR_DIRECT,
        NONCONSUMABLE_USE_ON_POKEMON,
        DIRECT_USE_NONCONSUMABLE;

        public UsabilityInside get(int i){
            switch (i){
                case 0:
                    return NOT_IN_BATTLE;
                case 2:
                    return POKEBALL_ACTIVE_OR_DIRECT;
                case 3:
                    return NONCONSUMABLE_USE_ON_POKEMON;
                case 4:
                    return DIRECT_USE_NONCONSUMABLE;
                default:
                    return CONSUMABLE_ONE_PARTY;
            }
        }
    }

}
