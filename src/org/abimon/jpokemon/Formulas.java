package org.abimon.jpokemon;

import org.abimon.jpokemon.events.*;

import java.util.Random;

public class Formulas {

    public static final int generation = 5;

    public static double damageFormula(IPokemon attacking, IPokemon target, Move move, IBattleProvider provider){

        double levelFactor = ((2.0 * provider.getLevel(attacking)) + 10.0) / 250.0;
        double attackFactor = move.damageCategory.equalsIgnoreCase("PHYSICAL") ? ((double) provider.getAttack(attacking)) / ((double) provider.getDefence(target)) : ((double) provider.getSpecialAttack(attacking)) / ((double) provider.getSpecialDefence(target));

        return ((levelFactor * attackFactor * move.basePower) + 2.0) * damageModifier(attacking, target, move, provider);
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

        STABEvent stabEvent = new STABEvent(attacking, target, move, provider, stab);
        TypeModifierEvent typeModifierEvent = new TypeModifierEvent(attacking, target, move, provider, typeModifier);
        CritStageEvent critStageEvent = new CritStageEvent(attacking, target, move, provider, critStage);

        provider.dispatch(stabEvent);
        provider.dispatch(typeModifierEvent);
        provider.dispatch(critStageEvent);

        stab = stabEvent.stab;
        typeModifier = typeModifierEvent.typeModifier;
        critStage = critStageEvent.critStage;

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

        CritChanceEvent critChanceEvent = new CritChanceEvent(attacking, target, move, provider, critChance);

        provider.dispatch(critChanceEvent);

        critChance = critChanceEvent.critChance;

        double critical = (new Random().nextDouble() * 100) < critChance ? (generation < 6 ? 2 : 1.5) : 1;

        DamageModifyEvent damageModifyEvent = new DamageModifyEvent(attacking, target, move, provider, stab, typeModifier, critical > 1);

        provider.dispatch(damageModifyEvent);

        stab = damageModifyEvent.stab;
        typeModifier = damageModifyEvent.typeModifier;
        double other = damageModifyEvent.other;

        return stab * typeModifier * critical * other * ((100 - new Random().nextInt(15)) / 100.0);
    }
}
