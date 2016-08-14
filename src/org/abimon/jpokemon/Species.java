package org.abimon.jpokemon;

import org.abimon.omnis.io.Data;

import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class Species {

    public static final HashMap<String, Species> POKEMON = new HashMap<String, Species>();
    public static final HashMap<Integer, Species> POKEMON_BY_ID = new HashMap<Integer, Species>();

    int id = 0;
    String name = "";
    String internalName = "";
    Type type1 = null;
    Type type2 = null;
    int baseHP;
    int baseAtk;
    int baseDef;
    int baseSpeed;
    int baseSpAtk;
    int baseSpDef;

    double maleToFemale = 1.0;
    GrowthRate growthRate = GrowthRate.SLOW;
    int baseExp;
    int evHP;
    int evAtk;
    int evDef;
    int evSpeed;
    int evSpAtk;
    int evSpDef;
    int rarity;
    int happiness;
    EggGroup eggGroup1;
    EggGroup eggGroup2;
    int stepsToHatch;
    double height;
    double weight;
    Color colour;
    String kind;
    String pokedex;

    Ability ability1;
    Ability ability2;

    Ability hiddenAbility1;
    Ability hiddenAbility2;
    Ability hiddenAbility3;
    Ability hiddenAbility4;

    Item wildItemCommon;
    Item wildItemUncommon;
    Item wildItemRare;

    Evolution[] evolutions;
    String[] formNames;

    Item incenseBaby;

    public static void load(){
        POKEMON.clear();
        POKEMON_BY_ID.clear();

        try{
            Data moveData = new Data(new File("pokemon.txt"));

            Species species = null;

            for(String s : moveData.getAsStringArray()) {
                if(s.matches("\\[\\d+\\]")){
                    if(species != null) {
                        System.out.println("Registering " + species);
                        POKEMON.put(species.internalName, species);
                        POKEMON_BY_ID.put(species.id, species);
                    }
                    species = new Species();
                    species.id = Integer.parseInt(s.replaceAll("\\D", ""));
                }
                else if(s.contains("=") && species != null){
                    String key = s.split("=", 2)[0];
                    String val = s.split("=", 2)[1];

                    if(key.equalsIgnoreCase("Name"))
                        species.name = val;
                    if(key.equalsIgnoreCase("InternalName"))
                        species.internalName = val;
                    if(key.equalsIgnoreCase("Type1"))
                        species.type1 = Type.get(val);
                    if(key.equalsIgnoreCase("Type2"))
                        species.type2 = Type.get(val);
                }
            }

            System.out.println("Registered " + POKEMON.size() + " Pokemon!");
        }
        catch(Throwable th){
            th.printStackTrace();
        }
    }

    public enum GrowthRate{
        FAST,
        MEDIUM,
        SLOW,
        PARABOLIC,
        ERRATIC,
        FLUCTUATING;

        public static GrowthRate getFromString(String s){
            switch(s.toUpperCase()){
                case "FAST":
                    return FAST;
                case "MEDIUM":
                    return MEDIUM;
                case "MEDIUMFAST":
                    return MEDIUM;
                case "PARABOLIC":
                    return PARABOLIC;
                case "MEDIUMSLOW":
                    return PARABOLIC;
                case "ERRATIC":
                    return ERRATIC;
                case "FLUCTUATING":
                    return FLUCTUATING;
                default:
                    return SLOW;
            }
        }
    }

    public enum EggGroup{
        MONSTER,
        WATER1,
        BUG,
        FLYING,
        FIELD,
        FAIRY,
        GRASS,
        HUMANLIKE,
        WATER3,
        MINERAL,
        AMORPHOUS,
        WATER2,
        DITTO,
        DRAGON,
        UNDISCOVERED;
    }

    public String toString(){
        return name;
    }

    public static Species get(String s){
        return POKEMON.get(s);
    }

    public static Species get(int i){
        return POKEMON_BY_ID.get(i);
    }
}
