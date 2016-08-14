package org.abimon.jpokemon;

import java.util.Random;

public class Formulas {

    public static final int generation = 5;

    public static double damageFormula(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider){

        double levelFactor = ((2.0 * provider.getLevel(attacking)) + 10.0) / 250.0;
        double attackFactor = move.damageCategory.equalsIgnoreCase("PHYSICAL") ? ((double) provider.getAttack(attacking)) / ((double) provider.getDefence(target)) : ((double) provider.getSpecialAttack(attacking)) / ((double) provider.getSpecialDefence(target));

        System.out.println(((levelFactor * attackFactor * move.basePower) + 2.0) + ":" + damageModifier(attacking, target, move, provider));

        return ((levelFactor * attackFactor * move.basePower) + 2.0) * damageModifier(attacking, target, move, provider);
    }

    public static double damageFormulaMin(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider){

        double levelFactor = ((2.0 * provider.getLevel(attacking)) + 10.0) / 250.0;
        double attackFactor = move.damageCategory.equalsIgnoreCase("PHYSICAL") ? ((double) provider.getAttack(attacking)) / ((double) provider.getDefence(target)) : ((double) provider.getSpecialAttack(attacking)) / ((double) provider.getSpecialDefence(target));

        return ((levelFactor * attackFactor * move.basePower) + 2.0) * damageModifierMin(attacking, target, move, provider);
    }

    public static double damageFormulaMax(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider){

        double levelFactor = ((2.0 * provider.getLevel(attacking)) + 10.0) / 250.0;
        double attackFactor = move.damageCategory.equalsIgnoreCase("PHYSICAL") ? ((double) provider.getAttack(attacking)) / ((double) provider.getDefence(target)) : ((double) provider.getSpecialAttack(attacking)) / ((double) provider.getSpecialDefence(target));

        return ((levelFactor * attackFactor * move.basePower) + 2.0) * damageModifierMax(attacking, target, move, provider);
    }

    public static double damageFormulaWithoutModifier(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider){

        double levelFactor = ((2.0 * provider.getLevel(attacking)) + 10.0) / 250.0;
        double attackFactor = move.damageCategory.equalsIgnoreCase("PHYSICAL") ? ((double) provider.getAttack(attacking)) / ((double) provider.getDefence(target)) : ((double) provider.getSpecialAttack(attacking)) / ((double) provider.getSpecialDefence(target));

        return ((levelFactor * attackFactor * move.basePower) + 2.0);
    }

    public static double damageModifier(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider){
        double stab = provider.getSpecies(attacking).type1 != null && provider.getSpecies(attacking).type1.equals(move.type) ? 1.5 : (provider.getSpecies(attacking).type2 != null && provider.getSpecies(attacking).type2.equals(move.type)) ? 1.5 : 1;
        double typeModifier = 1.0 * (provider.getSpecies(target).type1 != null ? provider.getSpecies(target).type1.getModifier(move.type) : 1) * (provider.getSpecies(target).type2 != null ? provider.getSpecies(target).type2.getModifier(move.type) : 1);
        int critStage = provider.getCriticalHitStage(attacking);
        double critChance = 0;

        if(generation < 6){
            switch(critStage) {
                case 0:
                    critChance = 6.25;
                    break;
                case 1:
                    critChance = 12.5;
                    break;
                case 2:
                    critChance = 25;
                    break;
                case 3:
                    critChance = 33.3;
                    break;
                default:
                    critChance = 50;
                    break;
            }
        }

        double critical = (new Random().nextDouble() * 100) < critChance ? (generation < 6 ? 2 : 1.5) : 1;

        return stab * typeModifier * critical * ((100 - new Random().nextInt(15)) / 100.0);
    }

    public static double damageModifierMin(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider){
        double stab = provider.getSpecies(attacking).type1 != null && provider.getSpecies(attacking).type1.equals(move.type) ? 1.5 : (provider.getSpecies(attacking).type2 != null && provider.getSpecies(attacking).type2.equals(move.type)) ? 1.5 : 1;
        double typeModifier = 1.0 * (provider.getSpecies(target).type1 != null ? provider.getSpecies(target).type1.getModifier(move.type) : 1) * (provider.getSpecies(target).type2 != null ? provider.getSpecies(target).type2.getModifier(move.type) : 1);
        int critStage = provider.getCriticalHitStage(attacking);
        double critChance = 0;

        if(generation < 6){
            switch(critStage) {
                case 0:
                    critChance = 6.25;
                    break;
                case 1:
                    critChance = 12.5;
                    break;
                case 2:
                    critChance = 25;
                    break;
                case 3:
                    critChance = 33.3;
                    break;
                default:
                    critChance = 50;
                    break;
            }
        }

        double critical = (new Random().nextDouble() * 100) < critChance ? (generation < 6 ? 2 : 1.5) : 1;

        return stab * typeModifier * critical * 0.85;
    }

    public static double damageModifierMax(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider){
        double stab = provider.getSpecies(attacking).type1 != null && provider.getSpecies(attacking).type1.equals(move.type) ? 1.5 : (provider.getSpecies(attacking).type2 != null && provider.getSpecies(attacking).type2.equals(move.type)) ? 1.5 : 1;
        double typeModifier = 1.0 * (provider.getSpecies(target).type1 != null ? provider.getSpecies(target).type1.getModifier(move.type) : 1) * (provider.getSpecies(target).type2 != null ? provider.getSpecies(target).type2.getModifier(move.type) : 1);
        int critStage = provider.getCriticalHitStage(attacking);
        double critChance = 0;

        if(generation < 6){
            switch(critStage) {
                case 0:
                    critChance = 6.25;
                    break;
                case 1:
                    critChance = 12.5;
                    break;
                case 2:
                    critChance = 25;
                    break;
                case 3:
                    critChance = 33.3;
                    break;
                default:
                    critChance = 50;
                    break;
            }
        }

        double critical = (new Random().nextDouble() * 100) < critChance ? (generation < 6 ? 2 : 1.5) : 1;

        return stab * typeModifier * critical;
    }
}
