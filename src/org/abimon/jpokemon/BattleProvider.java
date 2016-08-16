package org.abimon.jpokemon;

import org.abimon.jpokemon.events.*;

import java.util.HashMap;

/** Just a regular Battle Provider */
public class BattleProvider implements IBattleProvider {

    HashMap<String, int[]> uidToPosition = new HashMap<String, int[]>();
    HashMap<String, HashMap<String, Object>> extraData = new HashMap<String, HashMap<String, Object>>();

    private IPokemon[][] teams;

    private Weather currentWeather;

    public BattleProvider(IPokemon[]... teams){
        this.teams = teams;

        int teamNumber = 0;
        for(IPokemon[] team : teams){
            for(int pos = 0; pos < team.length; pos++) {
                if(team[pos] != null) {
                    extraData.put(team[pos].getUID(), new HashMap<>());
                    uidToPosition.put(team[pos].getUID(), new int[]{teamNumber, pos});
                }
            }
            teamNumber++;
        }

        currentWeather = Weather.get("CLEAR");
    }

    @Override
    public IPokemon[][] getPokemon() {
        return teams;
    }

    @Override
    public Species getSpecies(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getSpecies();
    }

    @Override
    public int getHP(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getHP();
    }

    @Override
    public int getAttack(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getAttack();
    }

    @Override
    public int getDefence(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getDefence();
    }

    @Override
    public int getSpecialAttack(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getSpecialAttack();
    }

    @Override
    public int getSpecialDefence(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getSpecialDefence();
    }

    @Override
    public int getSpeed(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getSpeed();
    }

    @Override
    public int getGender(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getGender();
    }

    @Override
    public int getExperience(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getExperience();
    }

    @Override
    public int getLevel(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getLevel();
    }

    @Override
    public int getEVForHP(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getEVForHP();
    }

    @Override
    public int getEVForAttack(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getEVForAttack();
    }

    @Override
    public int getEVforDefence(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getEVforDefence();
    }

    @Override
    public int getEVForSpecialAttack(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getEVForSpecialAttack();
    }

    @Override
    public int getEVForSpecialDefence(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getEVForSpecialDefence();
    }

    @Override
    public int getEVForSpeed(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getEVForSpeed();
    }

    @Override
    public int getIVForHP(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getIVForHP();
    }

    @Override
    public int getIVForAttack(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getIVForAttack();
    }

    @Override
    public int getIVforDefence(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getIVforDefence();
    }

    @Override
    public int getIVForSpecialAttack(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getIVForSpecialAttack();
    }

    @Override
    public int getIVForSpecialDefence(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getIVForSpecialDefence();
    }

    @Override
    public int getIVForSpeed(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getIVForSpeed();
    }

    @Override
    public Move[] getMoves(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getMoves();
    }

    @Override
    public int getHappiness(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getHappiness();
    }

    @Override
    public Ability getAbility(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getAbility();
    }

    @Override
    public Item getItem(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getItem();
    }

    @Override
    public int getCriticalHitStage(IPokemon pokemon) {
        return (int) extraData.get(pokemon.getUID()).getOrDefault("critical_stage", 0);
    }

    @Override
    public Weather getWeatherCondition() {
        return currentWeather;
    }

    @Override
    public void setWeatherCondition(Weather weather) {
        this.currentWeather = weather;
    }

    @Override
    public void dispatch(PokemonEvent event) {
        for(IPokemon[] team : teams)
            for(IPokemon pokemon : team) {
                if (pokemon instanceof IPokemonEventHandler)
                    ((IPokemonEventHandler) pokemon).handleEvent(event);
                if(pokemon.getSpecies() != null && pokemon.getSpecies() instanceof IPokemonEventHandler)
                    ((IPokemonEventHandler) pokemon.getSpecies()).handleEvent(event);
                if(pokemon.getAbility() != null && pokemon.getAbility() instanceof IPokemonEventHandler)
                    ((IPokemonEventHandler) pokemon.getAbility()).handleEvent(event);
                if(pokemon.getItem() != null && pokemon.getItem() instanceof IPokemonEventHandler)
                    ((IPokemonEventHandler) pokemon.getItem()).handleEvent(event);
            }

        if(event instanceof STABEvent){
            STABEvent evnt = (STABEvent) event;
            switch(getAbility(evnt.attacking).internalName){
                case "ADAPTABILITY":
                    evnt.stab = 2;
                    break;
                case "TINTEDLENS":
                    if(evnt.stab < 1)
                        evnt.stab *= 2;
                    break;
            }
            switch(getAbility(evnt.target).internalName){
                case "SOLIDROCK":
                    if(evnt.stab > 1)
                        evnt.stab -= evnt.stab / 4;
                    break;
                case "FILTER":
                    if(evnt.stab > 1)
                        evnt.stab -= evnt.stab / 4;
                    break;
                case "WONDERGUARD":
                    if(!(evnt.stab > 1))
                        evnt.stab = 0;
                    break;
            }
        }

        if(event instanceof CritStageEvent){
            CritStageEvent evnt = (CritStageEvent) event;
            switch(getAbility(evnt.attacking).internalName){
                case "SUPERLUCK":
                    evnt.critStage++;
                    break;
            }
        }

        if(event instanceof CritChanceEvent){
            CritChanceEvent critChanceEvent = (CritChanceEvent) event;
            switch(getAbility(critChanceEvent.target).internalName){
                case "BATTLEARMOR":
                    critChanceEvent.critChance = 0;
                    break;
                case "SHELLARMOR":
                    critChanceEvent.critChance = 0;
                    break;
            }
        }

        if(event instanceof DamageModifyEvent){
            DamageModifyEvent evnt = (DamageModifyEvent) event;

            switch(getAbility(evnt.attacking).internalName){
                case "IRONFIST":
                    if(evnt.move.hasFlag(Move.MoveFlag.PUNCHING_MOVE))
                        evnt.other += 0.2;
                    break;
                case "MEGALAUNCHER":
                    if(evnt.move.hasFlag(Move.MoveFlag.PULSE_MOVE))
                        evnt.other += 0.5;
                    break;
                case "SNIPER":
                    if(evnt.isCritical)
                        evnt.other += 0.5;
                    break;
                case "TECHNICIAN":
                    evnt.other += 0.5;
                    break;
            }
        }
    }
}
