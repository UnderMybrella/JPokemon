package org.abimon.jpokemon;

import org.abimon.omnis.io.Data;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

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

    /** Percentage for a male pokemon of this species to appear
     * ie: 1.0 is 100%, 0.5 is 50% male, 50% female, 0.25 is 25% male, 75% female
     */
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

    HashMap<Integer, Move[]> levelUp;
    Move[] eggMoves;

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
                    if(key.equalsIgnoreCase("BaseStats")){
                        String[] parts = val.split(",");
                        species.baseHP = Integer.parseInt(parts[0]);
                        species.baseAtk = Integer.parseInt(parts[1]);
                        species.baseDef = Integer.parseInt(parts[2]);
                        species.baseSpeed = Integer.parseInt(parts[3]);
                        species.baseSpAtk = Integer.parseInt(parts[4]);
                        species.baseSpDef = Integer.parseInt(parts[5]);
                    }
                    if(key.equalsIgnoreCase("GenderRate")){
                        switch (val){
                            case "AlwaysMale":
                                species.maleToFemale = 1.0;
                                break;
                            case "FemaleOneEighth":
                                species.maleToFemale = 0.875;
                                break;
                            case "Female25Percent":
                                species.maleToFemale = 0.75;
                                break;
                            case "Female50Percent":
                                species.maleToFemale = 0.5;
                                break;
                            case "Female75Percent":
                                species.maleToFemale = 0.25;
                                break;
                            case "FemaleSevenEighths":
                                species.maleToFemale = 0.125;
                                break;
                            case "AlwaysFemale":
                                species.maleToFemale = 0;
                                break;
                            case "Genderless":
                                species.maleToFemale = -1;
                                break;
                            default:
                                if(val.matches("\\d*"))
                                    species.maleToFemale = Double.parseDouble(val);
                                else if(val.matches("Female\\d+Percent"))
                                    species.maleToFemale = 1.0 - (Double.parseDouble(val.replaceAll("\\D", "")) / 100.0d);
                                else if(val.matches("Male\\d+Percent"))
                                    species.maleToFemale = (Double.parseDouble(val.replaceAll("\\D", "")) / 100.0d);
                                else
                                    species.maleToFemale = -1;
                        }
                    }
                    if(key.equalsIgnoreCase("GrowthRate"))
                        species.growthRate = GrowthRate.getFromString(val);
                    if(key.equalsIgnoreCase("BaseEXP"))
                        species.baseExp = Integer.parseInt(val);
                    if(key.equalsIgnoreCase("EffortPoints")){
                        String[] parts = val.split(",");
                        species.evHP = Integer.parseInt(parts[0]);
                        species.evAtk = Integer.parseInt(parts[1]);
                        species.evDef = Integer.parseInt(parts[2]);
                        species.evSpeed = Integer.parseInt(parts[3]);
                        species.evSpAtk = Integer.parseInt(parts[4]);
                        species.evSpDef = Integer.parseInt(parts[5]);
                    }
                    if(key.equalsIgnoreCase("Rareness"))
                        species.rarity = Integer.parseInt(val);
                    if(key.equalsIgnoreCase("Happiness"))
                        species.happiness = Integer.parseInt(val);
                    if(key.equalsIgnoreCase("Moves")){
                        species.levelUp = new HashMap<>();
                        String[] moveCombo = val.split(",");
                        for(int i = 0; i < moveCombo.length; i += 2){
                            int level = Integer.parseInt(moveCombo[i]);
                            Move move = Move.get(moveCombo[i+1]);

                            if(species.levelUp.containsKey(level)){
                                Move[] existing = species.levelUp.get(level);
                                Move[] moves = new Move[existing.length + 1];
                                for(int j = 0; j < existing.length; j++)
                                    moves[j] = existing[j];
                                moves[existing.length] = move;
                                species.levelUp.put(level, moves);
                            }
                            else
                                species.levelUp.put(level, new Move[]{move});
                        }
                    }
                    if(key.equalsIgnoreCase("Compatibility")){
                        String[] eggGroups = val.split(",");
                        species.eggGroup1 = EggGroup.valueOf(eggGroups[0].trim().toUpperCase());
                        if(eggGroups.length > 1)
                            species.eggGroup2 = EggGroup.valueOf(eggGroups[1].trim().toUpperCase());
                        else
                            species.eggGroup2 = null;
                    }
                    if(key.equalsIgnoreCase("StepsToHatch"))
                        species.stepsToHatch = Integer.parseInt(val);
                    if(key.equalsIgnoreCase("Height"))
                        species.height = Double.parseDouble(val);
                    if(key.equalsIgnoreCase("Weight"))
                        species.weight = Double.parseDouble(val);
                    if(key.equalsIgnoreCase("Color")){
                        switch(val){
                            case "Black":
                                species.colour = Color.BLACK;
                                break;
                            case "Blue":
                                species.colour = Color.BLUE;
                                break;
                            case "Brown":
                                species.colour = new Color(128, 64, 0);
                                break;
                            case "Gray":
                                species.colour = Color.GRAY;
                                break;
                            case "Green":
                                species.colour = Color.GREEN;
                                break;
                            case "Pink":
                                species.colour = Color.PINK;
                                break;
                            case "Purple":
                                species.colour = new Color(96, 0, 128);
                                break;
                            case "Red":
                                species.colour = Color.RED;
                                break;
                            case "White":
                                species.colour = Color.WHITE;
                                break;
                            case "Yellow":
                                species.colour = Color.YELLOW;
                                break;
                            default:
                                if(val.replaceAll("\\s+", "").matches("\\d{1,3},\\d{1,3},\\d{1,3}"))
                                    species.colour = new Color(Integer.parseInt(val.split(",")[0]), Integer.parseInt(val.split(",")[1]), Integer.parseInt(val.split(",")[2]));
                                else if(val.matches("[-,\\d]\\d+"))
                                    species.colour = new Color(Integer.parseInt(val));
                        }
                    }
                    if(key.equalsIgnoreCase("Kind"))
                        species.kind = val;
                    if(key.equalsIgnoreCase("Pokedex"))
                        species.pokedex = val;
                    if(key.equalsIgnoreCase("Abilities")){
                        String[] abilities = val.split(",");
                        species.ability1 = Ability.get(abilities[0]);
                        if(abilities.length > 1)
                            species.ability2 = Ability.get(abilities[1]);
                        else
                            species.ability2 = null;
                    }
                    if(key.equalsIgnoreCase("HiddenAbility")){
                        String[] abilities = val.split(",");
                        species.hiddenAbility1 = Ability.get(abilities[0]);
                        species.hiddenAbility2 = null;
                        species.hiddenAbility3 = null;
                        species.hiddenAbility4 = null;
                        if(abilities.length > 1){
                            species.hiddenAbility2 = Ability.get(abilities[1]);
                            if(abilities.length > 2) {
                                species.hiddenAbility3 = Ability.get(abilities[2]);
                                if (abilities.length > 3)
                                    species.hiddenAbility4 = Ability.get(abilities[3]);
                            }
                        }
                    }
                    if(key.equalsIgnoreCase("WildItemCommon"))
                        species.wildItemCommon = Item.get(val);
                    if(key.equalsIgnoreCase("WildItemUncommon"))
                        species.wildItemUncommon = Item.get(val);
                    if(key.equalsIgnoreCase("WildItemRare"))
                        species.wildItemRare = Item.get(val);
                    if(key.equalsIgnoreCase("EggMoves")){
                        String[] moveNames = val.split(",");
                        Move[] moves = new Move[moveNames.length];
                        for(int i = 0; i < moveNames.length; i++)
                            moves[i] = Move.get(moveNames[i]);
                        species.eggMoves = moves;
                    }
                    if(key.equalsIgnoreCase("Evolutions")){
                        String[] evolutionStrings = val.split(",");

                        LinkedList<Evolution> evolutions = new LinkedList<>();

                        if(val.trim().isEmpty())
                            continue;

                        for(int i = 0; i < evolutionStrings.length; i += 3){
                            String evolutionSpecies = evolutionStrings[i];
                            String method = evolutionStrings[i+1];
                            String other = evolutionStrings.length == i+2 ? "" : evolutionStrings[i+2];

                            Evolution evolution = new Evolution();
                            evolution.evolveInto = evolutionSpecies;
                            evolution.method = Evolution.EvolutionMethod.valueOf(method.trim().toUpperCase());
                            evolution.otherValue = other;
                            evolutions.add(evolution);
                        }

                        species.evolutions = evolutions.toArray(new Evolution[0]);
                    }
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

        public int getRequiredEXPForLevel(int level){
            switch(this){
                case ERRATIC:
                    if(level <= 50)
                        return (int) ((Math.pow(level, 3) * (100 - level)) / 50);
                    else if(level <= 68)
                        return (int) ((Math.pow(level, 3) * (150 - level)) / 100);
                    else if(level <= 98)
                        return (int) ((Math.pow(level, 3) * ((1911 - (10 * level)) / 3)) / 500);
                    return (int) ((Math.pow(level, 3) * (160 - level)) / 100);
                case FAST:
                    return (int) (4 * Math.pow(level, 3)) / 5;
                case MEDIUM:
                    return (int) Math.pow(level, 3);
                case PARABOLIC: //Medium Slow
                    return (int) (((6.0/5.0) * Math.pow(level, 3)) - (15 * Math.pow(level, 2)) + 100 * level - 140);
                case FLUCTUATING:
                    if(level <= 15)
                        return (int) (Math.pow(level, 3) * (((level + 1) / 3.0) + 24) / 50);
                    if(level <= 36)
                        return (int) (Math.pow(level, 3) * (level + 14) / 50.0);
                    return (int) (Math.pow(level, 3) * ((level / 2.0) + 32) / 50);
                default:
                    return (int) ((Math.pow(level, 3) * 5) / 4.0);
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
