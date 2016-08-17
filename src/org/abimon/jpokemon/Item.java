package org.abimon.jpokemon;

import org.abimon.jpokemon.events.PokemonEvent;
import org.abimon.omnis.io.Data;
import org.abimon.omnis.util.General;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

public class Item {

    public static final HashMap<String, Item> ITEMS = new HashMap<>();

    String internalName;
    String displayName;
    String displayNamePlural;
    String description;

    int price;

    UsabilityOutside outsideOfBattle;
    UsabilityInside inBattle;
    SpecialCategory specialCategory;

    Move teaches;
    Species[] canBeTaught;

    public static void load(){
        ITEMS.clear();

        try{
            for(String s : new Data(new File("items.txt")).getAsStringArray()){
                String[] components = General.split(s.trim(), ",", -1);
                Item item = new Item();
                item.internalName = components[1];
                item.displayName = components[2];
                item.displayNamePlural = components[3];
                item.price = Integer.parseInt(components[5]);
                item.description = components[6];

                item.outsideOfBattle = UsabilityOutside.get(Integer.parseInt(components[7]));
                item.inBattle = UsabilityInside.get(Integer.parseInt(components[8]));
                item.specialCategory = SpecialCategory.get(Integer.parseInt(components[9]));

                if(item.outsideOfBattle == UsabilityOutside.TM || item.outsideOfBattle == UsabilityOutside.HM) {
                    item.teaches = Move.get(components[10]);
                    boolean us = false;
                    for(String tm : new Data(new File("tm.txt")).getAsStringArray()){
                        if(tm.matches(".*[" + item.teaches.name + "].*"))
                            us = true;
                        else if(us){
                            LinkedList<Species> canBeTaught = new LinkedList<>();
                            for(String pkmn : tm.split(","))
                                canBeTaught.add(Species.get(pkmn));
                            item.canBeTaught = canBeTaught.toArray(new Species[0]);
                            break;
                        }

                    }
                }
                ITEMS.put(item.internalName, item);
            }

            System.out.println("Registered " + ITEMS.size() + " items!");
        }
        catch(Throwable th){
            th.printStackTrace();
        }
    }

    public boolean canTeach(IPokemon pokemon){
        if(teaches == null || canBeTaught == null)
            return false;

        boolean matchingSpecies = false;
        for(Species species : canBeTaught)
            if(pokemon.getSpecies().internalName.equalsIgnoreCase(species.internalName)){
                matchingSpecies = true;
                break;
            }

        if(!matchingSpecies) return false;

        for(Move move : pokemon.getMoves())
            if(move != null && move.name.equalsIgnoreCase(teaches.name))
                return false;

        return true;
    }

    public static Item get(String name){
        return ITEMS.get(name);
    }

    public enum UsabilityOutside{
        NOT_OUTSIDE_BATTLE,
        CONSUMABLE,
        OUTSIDE_NOT_ON_POKEMON,
        TM,
        HM,
        NONCONSUMABLE_USE_ON_POKEMON;

        public static UsabilityOutside get(int i){
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

        public static UsabilityInside get(int i){
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

    public enum SpecialCategory{
        NOT_SPECIAL,
        MAIL_ITEM,
        MAIL_ITEM_HOLDER_AND_TWO_PARTY,
        SNAG_BALL,
        POKEBALL,
        PLANTABLE_BERRY,
        KEY_ITEM;

        public static SpecialCategory get(int i){
            switch (i){
                case 1:
                    return MAIL_ITEM;
                case 2:
                    return MAIL_ITEM_HOLDER_AND_TWO_PARTY;
                case 3:
                    return SNAG_BALL;
                case 4:
                    return POKEBALL;
                case 5:
                    return PLANTABLE_BERRY;
                case 6:
                    return KEY_ITEM;
                default:
                    return NOT_SPECIAL;
            }
        }
    }

}
