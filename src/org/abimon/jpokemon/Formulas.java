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
        double stab = provider.getTypeOne(attacking) != null && provider.getTypeOne(attacking).equals(move.type) ? 1.5 : (provider.getTypeTwo(attacking) != null && provider.getTypeTwo(attacking).equals(move.type)) ? 1.5 : 1;
        double typeModifier = 1.0 * (provider.getTypeOne(target) != null ? provider.getTypeOne(target).getModifier(move.type) : 1) * (provider.getTypeTwo(target) != null ? provider.getTypeTwo(target).getModifier(move.type) : 1);
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
        double other = damageModifyEvent.getOther();

        return stab * typeModifier * critical * other * ((100 - new Random().nextInt(15)) / 100.0);
    }

    public static int getHPForLevel(double base, double iv, double ev, int level){
        double hp = (((2 * base) + iv + (ev / 4)) * level) / 100;
        hp += level + 10;

        return (int) hp;
    }

    public static int getStatForLevel(double base, double iv, double ev, int level, double nature){
        double otherStat = (((((2 * base) + iv + (ev / 4)) * level) / 100) + 5) * nature;
        return (int) otherStat;
    }
}
