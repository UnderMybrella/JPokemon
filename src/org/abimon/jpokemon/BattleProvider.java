package org.abimon.jpokemon;

import org.abimon.jpokemon.events.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/** Just a regular Battle Provider */
public class BattleProvider implements IBattleProvider {

    HashMap<String, int[]> uidToPosition = new HashMap<>();
    HashMap<String, HashMap<String, Object>> extraData = new HashMap<>();

    private IPokemon[][] teams;
    private IPokemon[][] active;
    private boolean[][] analyticDidAct;

    private final int activePerSide;

    private String currentWeather;
    private int remainingTurns = -1;

    private int turn = 1;

    public BattleProvider(int activePerSide, IPokemon[]... original) {
        LinkedList<IPokemon[]> teamsCopy = new LinkedList<>();
        for (IPokemon[] team : original) {
            IPokemon[] copy = new IPokemon[team.length];

            for (int i = 0; i < team.length; i++)
                copy[i] = team[i].clonePokemon();

            teamsCopy.add(copy);
        }

        teams = teamsCopy.toArray(new IPokemon[0][]);

        this.activePerSide = activePerSide;

        active = new IPokemon[teams.length][activePerSide];
        int teamNumber = 0;
        int biggestTeam = 0;
        for (IPokemon[] team : teams) {
            active[teamNumber] = new IPokemon[activePerSide];
            if (team.length > biggestTeam)
                biggestTeam = team.length;
            for (int pos = 0; pos < team.length; pos++) {
                if (pos < activePerSide)
                    active[teamNumber][pos] = team[pos];
                if (team[pos] != null) {
                    extraData.put(team[pos].getUID(), new HashMap<>());
                    uidToPosition.put(team[pos].getUID(), new int[]{teamNumber, pos});
                }
            }
            teamNumber++;
        }

        analyticDidAct = new boolean[teams.length][biggestTeam];

        currentWeather = "CLEAR";
    }


    @Override
    public IPokemon[][] getPokemon() {
        return teams;
    }

    @Override
    public int getTeam(IPokemon pokemon) {
        return uidToPosition.get(pokemon.getUID())[0];
    }

    @Override
    public IPokemon[] getParty(int team) {
        return teams[team];
    }

    @Override
    public IPokemon[] getParty(IPokemon allied) {
        return teams[getTeam(allied)];
    }

    @Override
    public IPokemon[] getActive(int team) {
        return active[team];
    }

    @Override
    public IPokemon[] getActive(IPokemon allied) {
        return active[getTeam(allied)];
    }

    @Override
    public IPokemon setActive(int team, int spot, IPokemon pokemon) {
        IPokemon old = active[team][Math.max(0, Math.min(activePerSide, spot))];

        SwitchOutEvent evnt = new SwitchOutEvent(old, pokemon, this);
        dispatch(evnt);

        active[team][Math.max(0, Math.min(activePerSide, spot))] = evnt.replace;

        SwitchInEvent event = new SwitchInEvent(evnt.replace, evnt.out, this);
        dispatch(event);

        active[team][Math.max(0, Math.min(activePerSide, spot))] = event.newActive;

        return old;
    }

    @Override
    public IPokemon setActive(IPokemon allied, int spot, IPokemon pokemon) {
        return setActive(getTeam(allied), spot, pokemon);
    }

    @Override
    public int getPokemonActivePerSide() {
        return activePerSide;
    }

    @Override
    public Species getSpecies(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getSpecies();
    }

    @Override
    public Type getTypeOne(IPokemon pokemon) {
        return Type.get((String) extraData.get(pokemon.getUID()).getOrDefault("type_one", getSpecies(pokemon).type1.internalName));
    }

    @Override
    public Type getTypeTwo(IPokemon pokemon) {
        if ((extraData.get(pokemon.getUID()).containsKey("type_two") || getSpecies(pokemon).type2 != null) && !extraData.get(pokemon.getUID()).getOrDefault("type_two", "").equals("none"))
            return Type.get((String) extraData.get(pokemon.getUID()).getOrDefault("type_two", getSpecies(pokemon).type2.internalName));
        return null;
    }

    @Override
    public void setTypeOne(IPokemon pokemon, Type typeOne) {
        extraData.get(pokemon.getUID()).put("type_one", typeOne.internalName);
    }

    @Override
    public void setTypeTwo(IPokemon pokemon, Type typeTwo) {
        if (typeTwo == null)
            extraData.get(pokemon.getUID()).put("type_two", "none");
        else
            extraData.get(pokemon.getUID()).put("type_two", typeTwo.internalName);
    }

    @Override
    public int getMaxHP(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getMaxHP();
    }

    @Override
    public int getHP(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getHP();
    }

    @Override
    public int getAttack(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return (int) (teams[pos[0]][pos[1]].getAttack() * getStageModifier(getAttackStage(pokemon)));
    }

    @Override
    public int getDefence(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return (int) (teams[pos[0]][pos[1]].getDefence() * getStageModifier(getDefenceStage(pokemon)));
    }

    @Override
    public int getSpecialAttack(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return (int) (teams[pos[0]][pos[1]].getSpecialAttack() * getStageModifier(getSpecialAttackStage(pokemon)));
    }

    @Override
    public int getSpecialDefence(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return (int) (teams[pos[0]][pos[1]].getSpecialDefence() * getStageModifier(getSpecialDefenceStage(pokemon)));
    }

    @Override
    public int getSpeed(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return (int) (teams[pos[0]][pos[1]].getSpeed() * getStageModifier(getSpeedStage(pokemon)));
    }

    @Override
    public void setMaxHP(IPokemon pokemon, int maxHP) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        teams[pos[0]][pos[1]].setMaxHP(maxHP);
    }

    @Override
    public void setHP(IPokemon pokemon, int hp) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        teams[pos[0]][pos[1]].setHP(hp);
    }

    @Override
    public void setAttack(IPokemon pokemon, int atk) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        teams[pos[0]][pos[1]].setAttack(atk);
    }

    @Override
    public void setDefence(IPokemon pokemon, int def) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        teams[pos[0]][pos[1]].setDefence(def);
    }

    @Override
    public void setSpecialAttack(IPokemon pokemon, int spAtk) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        teams[pos[0]][pos[1]].setSpecialAttack(spAtk);
    }

    @Override
    public void setSpecialDefence(IPokemon pokemon, int spDef) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        teams[pos[0]][pos[1]].setSpecialDefence(spDef);
    }

    @Override
    public void setSpeed(IPokemon pokemon, int speed) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        teams[pos[0]][pos[1]].setSpeed(speed);
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
    public void setAbility(IPokemon pokemon, Ability ability) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        teams[pos[0]][pos[1]].setAbility(ability);
    }

    @Override
    public void setItem(IPokemon pokemon, Item item) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        teams[pos[0]][pos[1]].setItem(item);
    }

    @Override
    public int getCriticalHitStage(IPokemon pokemon) {
        return (int) extraData.get(pokemon.getUID()).getOrDefault("critical_stage", 0);
    }

    public int getAttackStage(IPokemon pokemon) {
        return (int) extraData.get(pokemon.getUID()).getOrDefault("attack_stage", 0);
    }

    public int getDefenceStage(IPokemon pokemon) {
        return (int) extraData.get(pokemon.getUID()).getOrDefault("defence_stage", 0);
    }

    public int getSpecialAttackStage(IPokemon pokemon) {
        return (int) extraData.get(pokemon.getUID()).getOrDefault("special-attack_stage", 0);
    }

    public int getSpecialDefenceStage(IPokemon pokemon) {
        return (int) extraData.get(pokemon.getUID()).getOrDefault("special-defence_stage", 0);
    }

    public int getSpeedStage(IPokemon pokemon) {
        return (int) extraData.get(pokemon.getUID()).getOrDefault("speed_stage", 0);
    }

    public int setCriticalHitStage(IPokemon pokemon) {
        return (int) extraData.get(pokemon.getUID()).getOrDefault("critical_stage", 0);
    }

    public void setAttackStage(IPokemon pokemon, int stage) {
        extraData.get(pokemon.getUID()).put("attack_stage", Math.max(-6, Math.min(6, stage)));
    }

    public void setDefenceStage(IPokemon pokemon, int stage) {
        extraData.get(pokemon.getUID()).put("defence_stage", Math.max(-6, Math.min(6, stage)));
    }

    public void setSpecialAttackStage(IPokemon pokemon, int stage) {
        extraData.get(pokemon.getUID()).put("special-attack_stage", Math.max(-6, Math.min(6, stage)));
    }

    public void setSpecialDefenceStage(IPokemon pokemon, int stage) {
        extraData.get(pokemon.getUID()).put("special-defence_stage", Math.max(-6, Math.min(6, stage)));
    }

    public void setSpeedStage(IPokemon pokemon, int stage) {
        extraData.get(pokemon.getUID()).put("speed_stage", Math.max(-6, Math.min(6, stage)));
    }

    public double getStageModifier(int stage) {
        return Math.max(2.0 + stage, 2.0) / Math.max(2.0 - stage, 2.0);
    }

    @Override
    public String getWeatherCondition() {
        return currentWeather;
    }

    @Override
    public void setWeatherCondition(String weather) {
        this.currentWeather = weather;
    }

    @Override
    public String[] getStatusConditions(IPokemon pokemon) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        return teams[pos[0]][pos[1]].getStatusConditions();
    }

    @Override
    public void addStatusCondition(IPokemon pokemon, String statusCondition) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        LinkedList<String> statusConditions = new LinkedList<>();

        for (String s : getStatusConditions(pokemon))
            statusConditions.add(s);

        if (!statusConditions.contains(statusCondition))
            statusConditions.add(statusCondition);

        teams[pos[0]][pos[1]].setStatusConditions(statusConditions.toArray(new String[0]));
    }

    @Override
    public void removeStatusCondition(IPokemon pokemon, String statusCondition) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        LinkedList<String> statusConditions = new LinkedList<>();

        for (String s : getStatusConditions(pokemon))
            statusConditions.add(s);

        statusConditions.remove(statusCondition);
        teams[pos[0]][pos[1]].setStatusConditions(statusConditions.toArray(new String[0]));
    }

    @Override
    public void setStatusConditions(IPokemon pokemon, String[] statusConditions) {
        int[] pos = uidToPosition.get(pokemon.getUID());
        teams[pos[0]][pos[1]].setStatusConditions(statusConditions);
    }

    LinkedList<IPokemonEventHandler> customHandlers = new LinkedList<>();

    @Override
    public void register(IPokemonEventHandler eventHandler) {
        if (!customHandlers.contains(eventHandler))
            customHandlers.add(eventHandler);
    }

    @Override
    public void dispatch(PokemonEvent event) {
        Random rng = new Random();
        for (IPokemon[] team : teams)
            for (IPokemon pokemon : team) {
                if (pokemon instanceof IPokemonEventHandler)
                    ((IPokemonEventHandler) pokemon).handleEvent(event);
                if (pokemon.getSpecies() != null && pokemon.getSpecies() instanceof IPokemonEventHandler)
                    ((IPokemonEventHandler) pokemon.getSpecies()).handleEvent(event);
                if (pokemon.getAbility() != null && pokemon.getAbility() instanceof IPokemonEventHandler)
                    ((IPokemonEventHandler) pokemon.getAbility()).handleEvent(event);
                if (pokemon.getItem() != null && pokemon.getItem() instanceof IPokemonEventHandler)
                    ((IPokemonEventHandler) pokemon.getItem()).handleEvent(event);
            }

        boolean moldBreaker = false;

        for (Field field : event.getClass().getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase("attacking")) {
                try {
                    field.setAccessible(true);
                    Object obj = field.get(event);
                    if (obj instanceof IPokemon) {
                        if (getAbility((IPokemon) obj).internalName.equalsIgnoreCase("MOLDBREAKER") || getAbility((IPokemon) obj).internalName.equalsIgnoreCase("TERAVOLT") || getAbility((IPokemon) obj).internalName.equalsIgnoreCase("TURBOBLAZE"))
                            moldBreaker = true;
                    }
                } catch (Throwable th) {
                }
            }
        }

        if (event instanceof STABEvent) {
            STABEvent evnt = (STABEvent) event;
            switch (getAbility(evnt.attacking).internalName) {
                case "ADAPTABILITY":
                    if (evnt.stab > 1) {
                        evnt.stab = 2;
                        dispatch(new DialogueEvent("pokemon.ability.adaptability"));
                    }
                    break;
                case "SCRAPPY":
                    if (evnt.stab == 0 && (getTypeOne(evnt.target).equals("GHOST") || (getTypeTwo(evnt.target) != null && getTypeTwo(evnt.target).equals("GHOST")))) {
                        if (evnt.move.equals("NORMAL") || evnt.move.equals("FIGHTING"))
                            evnt.stab = 1;
                    }
                    break;
                case "TINTEDLENS":
                    if (evnt.stab < 1) {
                        evnt.stab *= 2;
                        dispatch(new DialogueEvent("pokemon.ability.tintedlens"));
                    }
                    break;
            }

            if (!moldBreaker) {
                switch (getAbility(evnt.target).internalName) {
                    case "SOLIDROCK":
                        if (evnt.stab > 1) {
                            evnt.stab -= evnt.stab / 4;
                            dispatch(new DialogueEvent("pokemon.ability.solidrock|foe"));
                        }
                        break;
                    case "FILTER":
                        if (evnt.stab > 1) {
                            evnt.stab -= evnt.stab / 4;
                            dispatch(new DialogueEvent("pokemon.ability.filter|foe"));
                        }
                        break;
                    case "WONDERGUARD":
                        if (!(evnt.stab > 1)) {
                            evnt.stab = 0;
                            dispatch(new DialogueEvent("pokemon.ability.wonderguard|foe"));
                        }
                        break;
                }
            }
        }

        if (event instanceof CritStageEvent) {
            CritStageEvent evnt = (CritStageEvent) event;
            switch (getAbility(evnt.attacking).internalName) {
                case "SUPERLUCK":
                    evnt.critStage++;
                    dispatch(new DialogueEvent("pokemon.ability.superluck"));
                    break;
            }
        }

        if (event instanceof CritChanceEvent) {
            CritChanceEvent critChanceEvent = (CritChanceEvent) event;
            if (!moldBreaker) {
                switch (getAbility(critChanceEvent.target).internalName) {
                    case "BATTLEARMOR":
                        critChanceEvent.critChance = 0;
                        dispatch(new DialogueEvent("pokemon.ability.battlearmor"));
                        break;
                    case "SHELLARMOR":
                        critChanceEvent.critChance = 0;
                        dispatch(new DialogueEvent("pokemon.ability.shellarmor"));
                        break;
                }
            }

            switch (critChanceEvent.move.name) {
                case "STORMTHROW":
                    if (critChanceEvent.critChance > 0) {
                        critChanceEvent.critChance = 100;
                        dispatch(new DialogueEvent("pokemon.move.stormthrow"));
                    }
                    break;
                case "FROSTBREATH":
                    if (critChanceEvent.critChance > 0) {
                        critChanceEvent.critChance = 100;
                        dispatch(new DialogueEvent("pokemon.move.frostbreath"));
                    }
                    break;
            }
        }

        if (event instanceof DamageModifyEvent) {
            DamageModifyEvent evnt = (DamageModifyEvent) event;

            for (int team = 0; team < teams.length; team++) {
                for (IPokemon pkmn : getActive(team)) {
                    switch (getAbility(pkmn).internalName) {
                        case "DAMP":
                            if (evnt.move.name.equalsIgnoreCase("EXPLOSION") || evnt.move.name.equalsIgnoreCase("SELF-DESTRUCT"))
                                evnt.registerOtherDamage(0);
                            break;
                    }
                }
            }

            for (String s : getStatusConditions(evnt.attacking)) {
                if (s.equalsIgnoreCase("BURN") && evnt.move.damageCategory.equalsIgnoreCase("PHYSICAL") && !getAbility(evnt.attacking).internalName.equalsIgnoreCase("GUTS")) {
                    evnt.registerOtherDamage(0.5);
                    dispatch(new DialogueEvent("pokemon.status.burn"));
                }
            }

            HashMap<String, Object> attacking = extraData.getOrDefault(evnt.attacking.getUID(), new HashMap<>());
            for (String s : attacking.keySet()) {
                switch (s.toUpperCase()) {
                    case "FLASHFIRE":
                        if (((boolean) attacking.get(s)) && evnt.move.type.equals(Type.get("FIRE"))) {
                            evnt.registerOtherDamage(1.5);
                            dispatch(new DialogueEvent("pokemon.ability.flashfire"));
                        }
                        break;
                }
            }

            for (IPokemon ally : getActive(evnt.target))
                if (!ally.getUID().equals(evnt.target.getUID())) {
                    if (getAbility(ally).internalName.equalsIgnoreCase("FRIENDGUARD")) {
                        evnt.registerOtherDamage(0.75);
                    }
                }

            switch (getAbility(evnt.attacking).internalName) {
                case "BLAZE":
                    if ((getHP(evnt.attacking) * 100.0 / getMaxHP(evnt.attacking)) < 33 && evnt.move.type.equals(Type.get("FIRE"))) {
                        evnt.registerOtherDamage(1.5);
                        dispatch(new DialogueEvent("pokemon.ability.blaze"));
                    }
                    break;
                case "OVERGROW":
                    if ((getHP(evnt.attacking) * 100.0 / getMaxHP(evnt.attacking)) < 33 && evnt.move.type.equals(Type.get("GRASS"))) {
                        evnt.registerOtherDamage(1.5);
                        dispatch(new DialogueEvent("pokemon.ability.overgrow"));
                    }
                    break;
                case "TORRENT":
                    if ((getHP(evnt.attacking) * 100.0 / getMaxHP(evnt.attacking)) < 33 && evnt.move.type.equals(Type.get("WATER"))) {
                        evnt.registerOtherDamage(1.5);
                        dispatch(new DialogueEvent("pokemon.ability.torrent"));
                    }
                    break;
                case "SWARM":
                    if ((getHP(evnt.attacking) * 100.0 / getMaxHP(evnt.attacking)) < 33 && evnt.move.type.equals(Type.get("BUG"))) {
                        evnt.registerOtherDamage(1.5);
                        dispatch(new DialogueEvent("pokemon.ability.swarm"));
                    }
                    break;
                case "GUTS":
                    String[] conditions = getStatusConditions(evnt.attacking);
                    if (contains(conditions, "poison", "paralysis", "burn")) {
                        evnt.registerOtherDamage(1.5);
                        dispatch(new DialogueEvent("pokemon.ability.guts"));
                    }
                    break;
                case "HUGEPOWER":
                    if (evnt.move.damageCategory.equalsIgnoreCase("PHYSICAL")) {
                        evnt.registerOtherDamage(2);
                        dispatch(new DialogueEvent("pokemon.ability.hugepower"));
                    }
                    break;
                case "HUSTLE":
                    if (evnt.move.damageCategory.equalsIgnoreCase("PHYSICAL")) {
                        evnt.registerOtherDamage(1.5);
                        dispatch(new DialogueEvent("pokemon.ability.hustle"));
                    }
                    break;
                case "IRONFIST":
                    if (evnt.move.hasFlag(Move.MoveFlag.PUNCHING_MOVE)) {
                        evnt.registerOtherDamage(1.2);
                        dispatch(new DialogueEvent("pokemon.ability.ironfist"));
                    }
                    break;
                case "MEGALAUNCHER":
                    if (evnt.move.hasFlag(Move.MoveFlag.PULSE_MOVE)) {
                        evnt.registerOtherDamage(1.5);
                        dispatch(new DialogueEvent("pokemon.ability.megalauncher"));
                    }
                    break;
                case "PUREPOWER":
                    if (evnt.move.damageCategory.equalsIgnoreCase("PHYSICAL")) {
                        evnt.registerOtherDamage(2);
                        dispatch(new DialogueEvent("pokemon.ability.purepower"));
                    }
                    break;
                case "SNIPER":
                    if (evnt.isCritical) {
                        evnt.registerOtherDamage(1.5);
                        dispatch(new DialogueEvent("pokemon.ability.sniper"));
                    }
                    break;
                case "TECHNICIAN":
                    if (evnt.move.basePower <= 60) {
                        evnt.registerOtherDamage(1.5);
                        dispatch(new DialogueEvent("pokemon.ability.technician"));
                    }
                    break;
                case "RIVALRY":
                    if (getGender(evnt.target) == -1 || getGender(evnt.attacking) == -1) ;
                    else if (getGender(evnt.target) == getGender(evnt.attacking)) {
                        evnt.registerOtherDamage(1.25);
                        dispatch(new DialogueEvent("pokemon.ability.rivalry|same"));
                    } else {
                        evnt.registerOtherDamage(0.75);
                        dispatch(new DialogueEvent("pokemon.ability.rivalry|other"));
                    }
                    break;
                case "RECKLESS":
                    if (evnt.move.recoilPercentage > 0 && !evnt.move.name.equalsIgnoreCase("STRUGGLE")) {
                        evnt.registerOtherDamage(1.2);
                        dispatch(new DialogueEvent("pokemon.ability.reckless"));
                    }
                    break;
                case "SHEERFORCE":
                    if (evnt.move.additionalEffectChance > 0) {
                        evnt.registerOtherDamage(1.3);
                        dispatch(new DialogueEvent("pokemon.ability.sheerforce"));
                    }
                    break;
                case "TOXICBOOST":
                    if (contains(getStatusConditions(evnt.attacking), "POISON") && evnt.move.damageCategory.equalsIgnoreCase("PHYSICAL")) {
                        evnt.registerOtherDamage(1.5);
                        dispatch(new DialogueEvent("pokemon.ability.toxicboost"));
                    }
                    break;
                case "FLAREBOOST":
                    if (contains(getStatusConditions(evnt.attacking), "BURN") && evnt.move.damageCategory.equalsIgnoreCase("SPECIAL")) {
                        evnt.registerOtherDamage(1.5);
                        dispatch(new DialogueEvent("pokemon.ability.flareboost"));
                    }
                    break;
                case "ANALYTIC":
                    int[] pos = uidToPosition.get(evnt.target);
                    if (analyticDidAct[pos[0]][pos[1]]) {
                        evnt.registerOtherDamage(1.3);
                        dispatch(new DialogueEvent("pokemon.ability.analytic"));
                    }
                    break;
                case "SANDFORCE":
                    if (getWeatherCondition().equals("SANDSTORM")) {
                        if (evnt.move.type.equals("ROCK") || evnt.move.type.equals("GROUND") || evnt.move.type.equals("STEEL")) {
                            evnt.registerOtherDamage(1.3);
                            dispatch(new DialogueEvent("pokemon.ability.sandforce"));
                        }
                    }
                    break;
                case "SOLARPOWER":
                    if(getWeatherCondition().equalsIgnoreCase("HARSHSUN") && evnt.move.damageCategory.equalsIgnoreCase("SPECIAL"))
                        evnt.registerOtherDamage(1.5);
                    break;
            }

            if (!moldBreaker) {
                for (int team = 0; team < teams.length; team++) {
                    for (IPokemon pkmn : getActive(team)) {
                        switch (getAbility(pkmn).internalName) {
                            case "DAMP":
                                if (evnt.move.name.equalsIgnoreCase("EXPLOSION") || evnt.move.name.equalsIgnoreCase("SELF-DESTRUCT")) {
                                    evnt.registerOtherDamage(0);
                                    dispatch(new DialogueEvent("pokemon.ability.damp"));
                                }
                                break;
                        }
                    }
                }
                switch (getAbility(evnt.target).internalName) {
                    case "FLASHFIRE":
                        if (evnt.move.type.equals(Type.get("FIRE"))) {
                            evnt.registerOtherDamage(0);
                            HashMap<String, Object> target = extraData.getOrDefault(evnt.target.getUID(), new HashMap<>());
                            target.put("flashfire", true);
                            extraData.put(evnt.target.getUID(), target);
                            dispatch(new DialogueEvent("pokemon.ability.flashfire|foe"));
                        }
                        break;
                    case "MOTORDRIVE":
                        if (evnt.move.type.equals(Type.get("ELECTRIC"))) {
                            evnt.registerOtherDamage(0);
                            setSpeedStage(evnt.target, getSpeedStage(evnt.target) + 1);
                            dispatch(new DialogueEvent("pokemon.ability.flashfire|foe"));
                        }
                        break;
                    case "THICKFAT":
                        if (evnt.move.type.equals(Type.get("FIRE")) || evnt.move.type.equals(Type.get("ICE"))) {
                            evnt.registerOtherDamage(0.5);
                            dispatch(new DialogueEvent("pokemon.ability.thickfat|foe"));
                        }
                        break;
                    case "HEATPROOF":
                        if (evnt.move.type.equals(Type.get("FIRE"))) {
                            evnt.registerOtherDamage(0.5);
                            dispatch(new DialogueEvent("pokemon.ability.heatproof|foe"));
                        }
                        break;
                    case "LEVITATE":
                        if (evnt.move.type.equals(Type.get("GROUND"))) {
                            evnt.registerOtherDamage(0);
                            dispatch(new DialogueEvent("pokemon.ability.levitate|foe"));
                        }
                        break;
                    case "LIGHTNINGROD":
                        if (evnt.move.type.equals("ELECTRIC"))
                            if (evnt.stab > 0) {
                                evnt.registerOtherDamage(0);
                                setSpecialAttackStage(evnt.target, getSpecialAttackStage(evnt.target) + 1);
                                dispatch(new DialogueEvent("pokemon.ability.lightningrod|foe"));
                            }
                        break;
                    case "STORMDRAIN":
                        if (evnt.move.type.equals("WATER"))
                            if (evnt.stab > 0) {
                                evnt.registerOtherDamage(0);
                                setSpecialAttackStage(evnt.target, getSpecialAttackStage(evnt.target) + 1);
                                dispatch(new DialogueEvent("pokemon.ability.stormdrain|foe"));
                            }
                        break;
                    case "SOUNDPROOF":
                        if (evnt.move.hasFlag(Move.MoveFlag.SOUND_MOVE)) {
                            evnt.registerOtherDamage(0);
                            dispatch(new DialogueEvent("pokemon.ability.soundproof|foe"));
                        }
                        break;
                    case "VOLTABSORB":
                        if (evnt.move.type.equals(Type.get("ELECTRIC"))) {
                            evnt.registerOtherDamage(0);

                            HPChangeEvent hpChange = new HPChangeEvent(evnt.target, getHP(evnt.target), Math.min(getMaxHP(evnt.target), getHP(evnt.target) + (getMaxHP(evnt.target) / 4)), "ABILITY|" + evnt.target.getUID() + "|VOLTABSORB", this);
                            dispatch(hpChange);

                            setHP(evnt.target, hpChange.newHP);

                            dispatch(new DialogueEvent("pokemon.ability.voltabsorb|foe"));
                            evnt.registerOtherDamage(0);
                        }
                        break;
                    case "WATERABSORB":
                        if (evnt.move.type.equals(Type.get("WATER"))) {
                            evnt.registerOtherDamage(0);

                            HPChangeEvent hpChange = new HPChangeEvent(evnt.target, getHP(evnt.target), Math.min(getMaxHP(evnt.target), getHP(evnt.target) + (getMaxHP(evnt.target) / 4)), "ABILITY|" + evnt.target.getUID() + "|WATERABSORB", this);
                            dispatch(hpChange);

                            setHP(evnt.target, hpChange.newHP);

                            dispatch(new DialogueEvent("pokemon.ability.waterabsorb|foe"));
                            evnt.registerOtherDamage(0);
                        }
                        break;
                    case "MARVELSCALE":
                        String[] conditions = getStatusConditions(evnt.attacking);
                        if (contains(conditions, "poison", "paralysis", "burn")) {
                            evnt.registerOtherDamage(0.5);
                            dispatch(new DialogueEvent("pokemon.ability.marvelscale|foe"));
                        }
                        break;
                    case "DRYSKIN":
                        if (evnt.move.type.equals(Type.get("WATER"))) {
                            evnt.registerOtherDamage(0);

                            HPChangeEvent hpChange = new HPChangeEvent(evnt.target, getHP(evnt.target), Math.min(getMaxHP(evnt.target), getHP(evnt.target) + (getMaxHP(evnt.target) / 4)), "ABILITY|" + evnt.target.getUID() + "|DRYSKIN", this);
                            dispatch(hpChange);

                            setHP(evnt.target, hpChange.newHP);

                            dispatch(new DialogueEvent("pokemon.ability.dryskin|foe,heal"));
                            evnt.registerOtherDamage(0);
                        }

                        if (evnt.move.type.equals(Type.get("FIRE"))) {
                            evnt.registerOtherDamage(1.25);

                            dispatch(new DialogueEvent("pokemon.ability.dryskin|foe,damage"));
                        }
                        break;
                    case "MULTISCALE":
                        if (getMaxHP(evnt.target) == getHP(evnt.target)) {
                            evnt.registerOtherDamage(0.5);
                            dispatch(new DialogueEvent("pokemon.ability.multiscale|foe"));
                        }
                        break;
                    case "SAPSIPPER":
                        if (evnt.move.type.equals("GRASS")) {
                            evnt.registerOtherDamage(0);
                            setAttackStage(evnt.target, getAttackStage(evnt.target) + 1);
                            dispatch(new DialogueEvent("pokemon.ability.sapsipper|foe"));
                        }
                        break;
                }
            }

            if (getTypeOne(evnt.target).equals("GRASS") || (getTypeTwo(evnt.target) != null && getTypeTwo(evnt.target).equals("GRASS")))
                if (evnt.move.hasFlag(Move.MoveFlag.POWDER_MOVE)) {
                    evnt.registerOtherDamage(0);
                    dispatch(new DialogueEvent("pokemon.grass_no_powder|foe"));
                }


            if (getTypeOne(evnt.target).equals("GROUND") || (getTypeTwo(evnt.target) != null && getTypeTwo(evnt.target).equals("GROUND")))
                if (evnt.move.name.equalsIgnoreCase("THUNDERWAVE")) {
                    evnt.registerOtherDamage(0);
                    dispatch(new DialogueEvent("pokemon.ground_no_wave|foe"));
                }
        }

        if(event instanceof HPChangeEvent){
            HPChangeEvent evnt = (HPChangeEvent) event;

            if(evnt.method.matches("ATTACK\\|.+\\|.+")){
                int[] pos = uidToPosition.get(evnt.method.split("\\|", 3)[1]);
                IPokemon attacked = teams[pos[0]][pos[1]];
                switch(getAbility(attacked).internalName){
                    case "LIQUIDOOZE":
                        int difference = evnt.newHP - evnt.oldHP;
                        if(difference > 0)
                            evnt.newHP = evnt.oldHP - difference;
                        break;
                }
            }
        }

        if (event instanceof OnHitEvent) {
            OnHitEvent evnt = (OnHitEvent) event;

            switch (getAbility(evnt.target).internalName) {
                case "STATIC":
                    if (evnt.move.hasFlag(Move.MoveFlag.PHYSICAL_CONTACT)) {
                        if (rng.nextInt(100) < 30) {
                            AddStatusEvent statusEvent = new AddStatusEvent(evnt.attacking, "PARALYSIS", "ABILITY|" + evnt.target.getUID() + "|STATIC", this);
                            dispatch(statusEvent);
                            if (statusEvent.status != null && !statusEvent.status.isEmpty()) {
                                addStatusCondition(evnt.target, statusEvent.status);
                                dispatch(new DialogueEvent("pokemon.ability.static|foe"));
                            }
                        }
                    }
                    break;
                case "POISONPOINT":
                    if (evnt.move.hasFlag(Move.MoveFlag.PHYSICAL_CONTACT)) {
                        if (rng.nextInt(100) < 30) {
                            AddStatusEvent statusEvent = new AddStatusEvent(evnt.attacking, "POISON", "ABILITY|" + evnt.target.getUID() + "|POISONPOINT", this);
                            dispatch(statusEvent);
                            if (statusEvent.status != null && !statusEvent.status.isEmpty()) {
                                addStatusCondition(evnt.target, statusEvent.status);
                                dispatch(new DialogueEvent("pokemon.ability.poisonpoint|foe"));
                            }
                        }
                    }
                    break;
                case "FLAMEBODY":
                    if (evnt.move.hasFlag(Move.MoveFlag.PHYSICAL_CONTACT)) {
                        if (rng.nextInt(100) < 30) {
                            AddStatusEvent statusEvent = new AddStatusEvent(evnt.attacking, "BURN", "ABILITY|" + evnt.target.getUID() + "|FLAMEBODY", this);
                            dispatch(statusEvent);
                            if (statusEvent.status != null && !statusEvent.status.isEmpty()) {
                                addStatusCondition(evnt.target, statusEvent.status);
                                dispatch(new DialogueEvent("pokemon.ability.flamebody|foe"));
                            }
                        }
                    }
                    break;
                case "CUTECHARM":
                    if (evnt.move.hasFlag(Move.MoveFlag.PHYSICAL_CONTACT)) {
                        if (getGender(evnt.target) == -1 || getGender(evnt.attacking) == -1) ;
                        else if (getGender(evnt.target) != getGender(evnt.attacking)) {
                            if (rng.nextInt(100) < 30) {
                                AddStatusEvent statusEvent = new AddStatusEvent(evnt.attacking, "INFATUATED", "ABILITY|" + evnt.target.getUID() + "|CUTECHARM", this);
                                dispatch(statusEvent);
                                if (statusEvent.status != null && !statusEvent.status.isEmpty()) {
                                    addStatusCondition(evnt.target, statusEvent.status);
                                    dispatch(new DialogueEvent("pokemon.ability.cutecharm|foe"));
                                }
                            }
                        }
                    }
                    break; // if (getGender(evnt.target) == -1 || getGender(evnt.attacking) == -1) ;
                case "COLORCHANGE":
                    setTypeOne(evnt.target, evnt.move.type);
                    setTypeTwo(evnt.target, null);
                    break;
                case "ROUGHSKIN":
                    if (evnt.move.hasFlag(Move.MoveFlag.PHYSICAL_CONTACT)) {
                        OtherDamageEvent otherDamage = new OtherDamageEvent(evnt.attacking, getMaxHP(evnt.attacking) / 8.0, "ABILITY|" + evnt.target.getUID() + "|ROUGHSKIN", this);
                        dispatch(otherDamage);
                        setHP(evnt.attacking, (int) (getHP(evnt.attacking) - otherDamage.damage));

                        if (getHP(evnt.attacking) <= 0) ;
                        //faint
                    }
                    break;
                case "EFFECTSPORE":
                    if (evnt.move.hasFlag(Move.MoveFlag.PHYSICAL_CONTACT)) {
                        //Sow the seeds of ~~progress~~ CHAOS
                        int percent = rng.nextInt(100);
                        if (percent < 9) {
                            AddStatusEvent statusEvent = new AddStatusEvent(evnt.attacking, "POISON", "ABILITY|" + evnt.target.getUID() + "|EFFECTSPORE", this);
                            dispatch(statusEvent);
                            if (statusEvent.status != null && !statusEvent.status.isEmpty())
                                addStatusCondition(evnt.target, statusEvent.status);
                        } else if (percent < 19) {
                            AddStatusEvent statusEvent = new AddStatusEvent(evnt.attacking, "PARALYSIS", "ABILITY|" + evnt.target.getUID() + "|EFFECTSPORE", this);
                            dispatch(statusEvent);
                            if (statusEvent.status != null && !statusEvent.status.isEmpty())
                                addStatusCondition(evnt.target, statusEvent.status);
                        } else if (percent < 30) {
                            AddStatusEvent statusEvent = new AddStatusEvent(evnt.attacking, "SLEEP", "ABILITY|" + evnt.target.getUID() + "|EFFECTSPORE", this);
                            dispatch(statusEvent);
                            if (statusEvent.status != null && !statusEvent.status.isEmpty())
                                addStatusCondition(evnt.target, statusEvent.status);
                        }
                    }
                    break;
            }
        }

        if (event instanceof AfterHitEvent) {
            AfterHitEvent evnt = (AfterHitEvent) event;

            switch (getAbility(evnt.attacking).internalName) {
                case "STENCH":
                    if (rng.nextInt(100) < 10) {
                        AddStatusEvent statusEvent = new AddStatusEvent(evnt.target, "FLINCH", "ABILITY|" + evnt.attacking.getUID() + "|STENCH", this);
                        dispatch(statusEvent);
                        if (statusEvent.status != null && !statusEvent.status.isEmpty())
                            addStatusCondition(evnt.target, statusEvent.status);
                    }
                    break;
            }

            switch (getAbility(evnt.target).internalName) {
                case "ANGERPOINT":
                    if (evnt.critical) {
                        StatChangeEvent statChangeEvent = new StatChangeEvent(evnt.target, "ATTACK", getAttackStage(evnt.target), 6, true, "ABILITY|" + evnt.target.getUID() + "|ANGERPOINT", this);
                        if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                            setAttackStage(evnt.target, statChangeEvent.changeTo);
                    }
                    break;
            }
        }

        if (event instanceof SwitchOutEvent) {
            SwitchOutEvent evnt = (SwitchOutEvent) event;

            switch (getAbility(evnt.out).internalName) {
                case "NATURALCURE":
                    for(String s : getStatusConditions(evnt.out)){
                        RemoveStatusCondition removeStatusCondition = new RemoveStatusCondition(evnt.out, s, "ABILITY|SELF|SHEDSKIN", this);
                        dispatch(removeStatusCondition);
                        if (removeStatusCondition.condition != null && removeStatusCondition.condition.equalsIgnoreCase("")) {
                            removeStatusCondition(evnt.out, removeStatusCondition.condition);
                            break;
                        }
                    }
                    break;

                case "SWIFTSWIM":
                    if ((boolean) extraData.get(evnt.out.getUID()).getOrDefault("swiftswim", false)) {
                        setSpeed(evnt.out, getSpeed(evnt.out) / 2);
                        extraData.get(evnt.out.getUID()).put("swiftswim", false);
                    }
                    break;

                case "CHLOROPHYLL":
                    if ((boolean) extraData.get(evnt.out.getUID()).getOrDefault("chlorophyll", false)) {
                        setSpeed(evnt.out, getSpeed(evnt.out) / 2);
                        extraData.get(evnt.out.getUID()).put("chlorophyll", false);
                    }
                    break;
            }

            setAttackStage(evnt.out, 0);
            setDefenceStage(evnt.out, 0);
            setSpecialAttackStage(evnt.out, 0);
            setSpecialDefenceStage(evnt.out, 0);
            setSpeedStage(evnt.out, 0);
        }

        if (event instanceof SwitchInEvent) {
            SwitchInEvent evnt = (SwitchInEvent) event;

            switch (getAbility(evnt.newActive).internalName) {
                case "DRIZZLE":
                    WeatherChangeEvent weatherEvent = new WeatherChangeEvent("RAIN", -1, "ABILITY|" + evnt.newActive.getUID() + "|DRIZZLE", this);
                    dispatch(weatherEvent);
                    setWeatherCondition(weatherEvent.newWeather);
                    break;
                case "SANDSTREAM":
                    weatherEvent = new WeatherChangeEvent("SANDSTORM", -1, "ABIITY|" + evnt.newActive.getUID() + "|SANDSTREAM", this);
                    dispatch(weatherEvent);
                    setWeatherCondition(weatherEvent.newWeather);
                    break;
                case "DROUGHT":
                    weatherEvent = new WeatherChangeEvent("HARSHSUN", -1, "ABILITY|" + evnt.newActive.getUID() + "|DROUGHT", this);
                    dispatch(weatherEvent);
                    setWeatherCondition(weatherEvent.newWeather);
                    break;
                case "SNOWWARNING":
                    weatherEvent = new WeatherChangeEvent("HAIL", -1, "ABILITY|" + evnt.newActive.getUID() + "|SNOWWARNING", this);
                    dispatch(weatherEvent);
                    setWeatherCondition(weatherEvent.newWeather);
                case "SLOWSTART":
                    StatChangeEvent statChangeEvent = new StatChangeEvent(evnt.newActive, "ATTACK", getAttack(evnt.newActive), getAttack(evnt.newActive) / 2, false, "ABILITY|" + evnt.newActive.getUID() + "|SLOWSTART", this);
                    dispatch(statChangeEvent);
                    if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                        setAttack(evnt.newActive, statChangeEvent.changeTo);
                    statChangeEvent = new StatChangeEvent(evnt.newActive, "SPEED", getSpeed(evnt.newActive), getSpeed(evnt.newActive) / 2, false, "ABILITY|" + evnt.newActive.getUID() + "|SLOWSTART", this);
                    dispatch(statChangeEvent);
                    if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                        setSpeed(evnt.newActive, statChangeEvent.changeTo);
                    extraData.get(evnt.newActive.getUID()).put("slowstart", turn);
                    break;
                case "INTIMIDATE":
                    for (int team = 0; team < teams.length; team++) {
                        if (team != getTeam(evnt.newActive))
                            for (IPokemon pkmn : getActive(team)) {
                                statChangeEvent = new StatChangeEvent(pkmn, "ATTACK", getAttackStage(pkmn), getAttackStage(pkmn) - 1, true, "ABILITY|" + evnt.newActive.getUID() + "|INTIMIDATE", this);
                                dispatch(statChangeEvent);
                                if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                                    setAttackStage(pkmn, statChangeEvent.changeTo);
                            }
                    }
                    break;
                case "TRACE": //Bad implementation, but I don't care
                    LinkedList<IPokemon> active = new LinkedList<>();

                    for (int team = 0; team < teams.length; team++) {
                        if (team != getTeam(evnt.newActive)) {
                            for (IPokemon pkmn : getActive(team)) {
                                active.add(pkmn);
                            }
                        }
                    }

                    IPokemon target = active.get(rng.nextInt(active.size()));
                    setAbility(evnt.newActive, target.getAbility());
                    dispatch(new DialogueEvent("pokemon.ability.trace|" + target.getAbility().internalName));
                    break;
                case "PLUS":
                case "MINUS":
                    for (IPokemon pkmn : getActive(getTeam(evnt.newActive))) {
                        if (!pkmn.getUID().equalsIgnoreCase(evnt.newActive.getUID())) {
                            if (getAbility(pkmn).internalName.equalsIgnoreCase("PLUS") || getAbility(pkmn).internalName.equalsIgnoreCase("MINUS")) {
                                statChangeEvent = new StatChangeEvent(pkmn, "SP_ATTACK", getSpecialAttackStage(pkmn), getSpecialAttackStage(pkmn) + 1, true, "ABILITY|" + evnt.newActive.getUID() + "|" + getAbility(evnt.newActive).internalName, this);
                                dispatch(statChangeEvent);
                                if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                                    setSpecialAttackStage(pkmn, statChangeEvent.changeTo);
                                break;
                            }
                        }
                    }
                    break;
                case "DOWNLOAD":
                    int defence = 0;
                    int spDefence = 0;

                    for (int team = 0; team < teams.length; team++) {
                        if (team != getTeam(evnt.newActive)) {
                            for (IPokemon pkmn : getActive(team)) {
                                defence += getDefence(pkmn);
                                spDefence = getSpecialDefence(pkmn);
                            }
                        }
                    }

                    if(defence < spDefence){
                        statChangeEvent = new StatChangeEvent(evnt.newActive, "ATTACK", getAttackStage(evnt.newActive), getAttackStage(evnt.newActive) + 1, true, "ABILITY|" + evnt.newActive.getUID() + "|DOWNLOAD", this);
                        dispatch(statChangeEvent);
                        if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                            setAttackStage(evnt.newActive, statChangeEvent.changeTo);
                    }
                    else{
                        statChangeEvent = new StatChangeEvent(evnt.newActive, "SP_ATTACK", getSpecialAttackStage(evnt.newActive), getSpecialAttackStage(evnt.newActive) + 1, true, "ABILITY|" + evnt.newActive.getUID() + "|DOWNLOAD", this);
                        dispatch(statChangeEvent);
                        if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                            setSpecialAttackStage(evnt.newActive, statChangeEvent.changeTo);
                    }

                    break;
            }
        }

        if (event instanceof EndOfTurnEvent) {
            EndOfTurnEvent evnt = (EndOfTurnEvent) event;

            for (int team = 0; team < teams.length; team++) {
                for (IPokemon pkmn : getActive(team)) {
                    switch (getAbility(pkmn).internalName) {
                        case "SPEEDBOOST":
                            setSpeedStage(pkmn, getSpeedStage(pkmn) + 1);
                            break;
                        case "NORMALIZE":
                            for (Move move : pkmn.getMoves())
                                if (move.type.equals("NORMAL") && !move.type.equals(Move.get(move.name).type))
                                    move.type = Move.get(move.name).type;
                            break;
                        case "SLOWSTART":
                            if(extraData.get(pkmn.getUID()).containsKey("slowstart")) {
                                int startTurn = (int) extraData.get(pkmn.getUID()).get("slowstart");
                                if (turn >= startTurn) {
                                    StatChangeEvent statChangeEvent = new StatChangeEvent(pkmn, "ATTACK", getAttack(pkmn), getAttack(pkmn) * 2, false, "ABILITY|" + pkmn.getUID() + "|SLOWSTART", this);
                                    dispatch(statChangeEvent);
                                    if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                                        setAttack(pkmn, statChangeEvent.changeTo);
                                    statChangeEvent = new StatChangeEvent(pkmn, "SPEED", getSpeed(pkmn), getSpeed(pkmn) * 2, false, "ABILITY|" + pkmn.getUID() + "|SLOWSTART", this);
                                    dispatch(statChangeEvent);
                                    if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                                        setSpeed(pkmn, statChangeEvent.changeTo);

                                }
                            }
                            break;
                        case "RAINDISH":
                            HPChangeEvent hpChangeEvent = new HPChangeEvent(pkmn, getHP(pkmn), Math.min(getMaxHP(pkmn), getHP(pkmn) + (getMaxHP(pkmn) / 16)), "ABILITY|" + pkmn.getUID() + "|RAINDISH", this);
                            dispatch(hpChangeEvent);
                            if(hpChangeEvent.newHP != -1)
                                setHP(pkmn, hpChangeEvent.newHP);
                            break;
                        case "SHEDSKIN": //Random is good
                            if(rng.nextInt(100) < 30) {
                                LinkedList<String> statusConditions = new LinkedList<String>();

                                for (String s : getStatusConditions(pkmn))
                                    statusConditions.add(s);

                                for (String vol : new String[]{"BAD_POISON", "POISON", "BURN", "FREEZE", "PARALYSIS", "SLEEP"}) {
                                    if (statusConditions.remove(vol)) {
                                        RemoveStatusCondition removeStatusCondition = new RemoveStatusCondition(pkmn, vol, "ABILITY|SELF|SHEDSKIN", this);
                                        dispatch(removeStatusCondition);
                                        if (removeStatusCondition.condition != null && removeStatusCondition.condition.equalsIgnoreCase("")) {
                                            removeStatusCondition(pkmn, removeStatusCondition.condition);
                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        case "HYDRATION": //Random is good
                            if(getWeatherCondition().equalsIgnoreCase("RAIN")){
                                LinkedList<String> statusConditions = new LinkedList<>();

                                for(String s : getStatusConditions(pkmn))
                                    statusConditions.add(s);

                                for(String vol : new String[]{"BAD_POISON", "POISON", "BURN", "FREEZE", "PARALYSIS", "SLEEP"}) {
                                    if (statusConditions.remove(vol)) {
                                        RemoveStatusCondition removeStatusCondition = new RemoveStatusCondition(pkmn, vol, "ABILITY|SELF|SHEDSKIN", this);
                                        dispatch(removeStatusCondition);
                                        if (removeStatusCondition.condition != null && removeStatusCondition.condition.equalsIgnoreCase("")) {
                                            removeStatusCondition(pkmn, removeStatusCondition.condition);
                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        case "POISONHEAL":
                            if(contains(getStatusConditions(pkmn), "POISON", "BAD_POISON")){
                                hpChangeEvent = new HPChangeEvent(pkmn, getHP(pkmn), Math.min(getMaxHP(pkmn), getHP(pkmn) + (getMaxHP(pkmn) / 8)), "ABILITY|" + pkmn.getUID() + "|POISONHEAL", this);
                                dispatch(hpChangeEvent);
                                if(hpChangeEvent.newHP != -1)
                                    pkmn.setHP(hpChangeEvent.newHP);
                            }
                            break;
                        case "SOLARPOWER":
                            if(getWeatherCondition().equalsIgnoreCase("HARSHSUN")){
                                OtherDamageEvent damageEvent = new OtherDamageEvent(pkmn, getMaxHP(pkmn) / 8, "ABILITY|SELF|SOLARPOWER", this);
                                dispatch(damageEvent);
                                if(damageEvent.damage != -1)
                                    setHP(pkmn, (int) (getHP(pkmn) - damageEvent.damage));
                            }
                            break;
                    }

                    String[] conditions = getStatusConditions(pkmn);

                    if(contains(conditions, "POISON")){
                        if(!getAbility(pkmn).internalName.equalsIgnoreCase("POISONHEAL")){
                            //Poison Damage
                        }

                    }
                }
            }
        }

        if (event instanceof KnockoutEvent) {
            KnockoutEvent evnt = (KnockoutEvent) event;

            switch (getAbility(evnt.knockedOut).internalName) {
                case "STURDY":
                    if (getMaxHP(evnt.knockedOut) == evnt.startingHP) {
                        evnt.finalHP = 1;
                    }
                    break;
            }
        }

        if (event instanceof AddStatusEvent) {
            AddStatusEvent evnt = (AddStatusEvent) event;

            String status = evnt.status == null ? "" : evnt.status;

            switch (getAbility(evnt.addingTo).internalName) {
                case "LIMBER":
                    if (status.equalsIgnoreCase("PARALYSIS"))
                        evnt.status = "";
                    break;
                case "OBLIVIOUS":
                    if (status.equalsIgnoreCase("INFATUATED"))
                        evnt.status = "";
                    break;
                case "INSOMNIA":
                    if (status.equalsIgnoreCase("SLEEP") || status.equalsIgnoreCase("YAWN"))
                        evnt.status = "";
                    break;
                case "IMMUNITY":
                    if (status.equalsIgnoreCase("TOXIC") || status.equalsIgnoreCase("POISON"))
                        evnt.status = "";
                    break;
                case "OWNTEMPO":
                    if (status.equalsIgnoreCase("CONFUSED"))
                        evnt.status = "";
                    break;
                case "INNERFOCUS":
                    if (status.equalsIgnoreCase("FLINCH"))
                        evnt.status = "";
                    break;
                case "MAGMAARMOR":
                    if (status.equalsIgnoreCase("FROZEN"))
                        evnt.status = "";
                    break;
                case "WATERVEIL":
                    if (status.equalsIgnoreCase("BURN"))
                        evnt.status = "";
                    break;
                case "VITALSPIRIT":
                    if (status.equalsIgnoreCase("SLEEP") || status.equalsIgnoreCase("YAWN"))
                        evnt.status = "";
                    break;
                case "LEAFGUARD":
                    if (getWeatherCondition().equalsIgnoreCase("HARSHSUN"))
                        evnt.status = "";
                    break;
                case "SYNCHRONIZE":
                    if (evnt.cause.matches("ABILITY\\|.+\\|.+") && !evnt.cause.matches("ABILITY\\|.+\\|SYNCHRONIZE")) {
                        String pkmnID = evnt.cause.split("\\|", 3)[1];
                        int[] pos = uidToPosition.get(pkmnID);
                        IPokemon pkmn = getPokemon()[pos[0]][pos[1]];

                        if (pkmn != null) {
                            AddStatusEvent statusEvent = new AddStatusEvent(pkmn, evnt.status, "ABILITY|" + evnt.addingTo.getUID() + "|SYNCHRONIZE", this);
                            dispatch(statusEvent);
                            if (statusEvent.status != null && !statusEvent.status.isEmpty())
                                addStatusCondition(pkmn, statusEvent.status);
                        }
                    }
                    break;
                case "STEADFAST":
                    if (evnt.status.equalsIgnoreCase("FLINCH")){
                        StatChangeEvent statChangeEvent = new StatChangeEvent(evnt.addingTo, "SPEED", getSpeedStage(evnt.addingTo), getSpeedStage(evnt.addingTo) + 1, true, "ABILITY|" + evnt.addingTo.getUID() + "|STEADFAST", this);
                        dispatch(statChangeEvent);
                        if(statChangeEvent.changeTo != Integer.MIN_VALUE)
                            setSpeedStage(evnt.addingTo, statChangeEvent.changeTo);
                    }
                    break;
            }

            if(evnt.status.equalsIgnoreCase("PARALYSIS")){
                if(!getAbility(evnt.addingTo).internalName.equalsIgnoreCase("QUICKFEET")) {
                    StatChangeEvent statChangeEvent = new StatChangeEvent(evnt.addingTo, "SPEED", getSpeed(evnt.addingTo), (int) (getSpeed(evnt.addingTo) * 0.75), false, "STATUS|PARALYSIS", this);
                    dispatch(statChangeEvent);
                    if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                        setSpeed(evnt.addingTo, statChangeEvent.changeTo);
                }
                else{
                    StatChangeEvent statChangeEvent = new StatChangeEvent(evnt.addingTo, "SPEED", getSpeed(evnt.addingTo), (int) (getSpeed(evnt.addingTo) * 1.5), false, "ABILITY|SELF|QUICKFEET", this);
                    dispatch(statChangeEvent);
                    if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                        setSpeed(evnt.addingTo, statChangeEvent.changeTo);
                }
            }
        }

        if (event instanceof RemoveStatusCondition){
            RemoveStatusCondition evnt = (RemoveStatusCondition) event;

            if(evnt.condition.equalsIgnoreCase("PARALYSIS")){
                if(!getAbility(evnt.pkmn).internalName.equalsIgnoreCase("QUICKFEET")) {
                    StatChangeEvent statChangeEvent = new StatChangeEvent(evnt.pkmn, "SPEED", getSpeed(evnt.pkmn), (int) (getSpeed(evnt.pkmn) / 0.75), false, "STATUS|PARALYSIS", this);
                    dispatch(statChangeEvent);
                    if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                        setSpeed(evnt.pkmn, statChangeEvent.changeTo);
                }
                else{
                    StatChangeEvent statChangeEvent = new StatChangeEvent(evnt.pkmn, "SPEED", getSpeed(evnt.pkmn), (int) (getSpeed(evnt.pkmn) / 1.5), false, "ABILITY|SELF|QUICKFEET", this);
                    dispatch(statChangeEvent);
                    if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                        setSpeed(evnt.pkmn, statChangeEvent.changeTo);
                }
            }
        }

        if (event instanceof StatChangeEvent) {
            StatChangeEvent evnt = (StatChangeEvent) event;

            switch (getAbility(evnt.changing).internalName) {
                case "CLEARBODY":
                    if (evnt.changeFrom > evnt.changeTo && evnt.cause.equalsIgnoreCase("OTHER_POKEMON"))
                        evnt.changeTo = Integer.MIN_VALUE; //Integer.MIN_VALUE is "Ignore this change"
                    break;
                case "KEENEYE":
                    if (evnt.changeFrom > evnt.changeTo && evnt.stat.equalsIgnoreCase("ACCURACY"))
                        evnt.changeTo = Integer.MIN_VALUE;
                    break;
                case "HYPERCUTTER":
                    if (evnt.changeFrom > evnt.changeTo && evnt.stat.equalsIgnoreCase("ATTACK"))
                        evnt.changeTo = Integer.MIN_VALUE;
                    break;
                case "WHITESMOKE":
                    if (evnt.cause.equalsIgnoreCase("OTHER_POKEMON"))
                        evnt.changeTo = Integer.MIN_VALUE;
                    break;
                case "SIMPLE":
                    if(evnt.stages)
                        evnt.changeTo *= 2;
                    break;
            }
        }

        if (event instanceof WeatherChangeEvent) {
            WeatherChangeEvent evnt = (WeatherChangeEvent) event;
            for (int team = 0; team < teams.length; team++) {
                for (IPokemon pkmn : getActive(team)) {
                    switch (getAbility(pkmn).internalName) {
                        case "SWIFTSWIM":
                            if (evnt.newWeather.equalsIgnoreCase("RAIN")) {
                                StatChangeEvent statChangeEvent = new StatChangeEvent(pkmn, "SPEED", getSpeed(pkmn), getSpeed(pkmn) * 2, false, "ABILITY|" + pkmn.getUID() + "|SWIFTSWIM", this);
                                dispatch(statChangeEvent);
                                if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                                    setSpeed(pkmn, statChangeEvent.changeTo);
                                extraData.get(pkmn.getUID()).put("swiftswim", true);
                            } else if ((boolean) extraData.get(pkmn.getUID()).getOrDefault("swiftswim", false)) {
                                StatChangeEvent statChangeEvent = new StatChangeEvent(pkmn, "SPEED", getSpeed(pkmn), getSpeed(pkmn) / 2, false, "ABILITY|" + pkmn.getUID() + "|SWIFTSWIM", this);
                                dispatch(statChangeEvent);
                                if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                                    setSpeed(pkmn, statChangeEvent.changeTo);
                                extraData.get(pkmn.getUID()).put("swiftswim", false);
                            }
                            break;

                        case "CHLOROPHYLL":
                            if (evnt.newWeather.equalsIgnoreCase("HARSHSUN")) {
                                StatChangeEvent statChangeEvent = new StatChangeEvent(pkmn, "SPEED", getSpeed(pkmn), getSpeed(pkmn) * 2, false, "ABILITY|" + pkmn.getUID() + "|SWIFTSWIM", this);
                                dispatch(statChangeEvent);
                                if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                                    setSpeed(pkmn, statChangeEvent.changeTo);
                                extraData.get(pkmn.getUID()).put("chlorophyll", true);
                            } else if ((boolean) extraData.get(pkmn.getUID()).getOrDefault("chlorophyll", false)) {
                                StatChangeEvent statChangeEvent = new StatChangeEvent(pkmn, "SPEED", getSpeed(pkmn), getSpeed(pkmn) / 2, false, "ABILITY|" + pkmn.getUID() + "|SWIFTSWIM", this);
                                dispatch(statChangeEvent);
                                if (statChangeEvent.changeTo != Integer.MIN_VALUE)
                                    setSpeed(pkmn, statChangeEvent.changeTo);
                                extraData.get(pkmn.getUID()).put("chlorophyll", false);
                            }
                            break;
                    }
                }
            }
        }

        if (event instanceof OtherDamageEvent) {
            OtherDamageEvent evnt = (OtherDamageEvent) event;

            switch (getAbility(evnt.affecting).internalName) {
                case "ROCKHEAD":
                    if (evnt.damageType.equalsIgnoreCase("RECOIL"))
                        evnt.damage = 0;
                    break;
                case "MAGICGUARD":
                    evnt.damage = 0;
                    break;
            }
        }

        if (event instanceof MoveSelectEvent) {
            MoveSelectEvent evnt = (MoveSelectEvent) event;

            switch (getAbility(evnt.using).internalName) {
                case "NORMALIZE":
                    evnt.move.type = Type.get("NORMAL");
                    break;
            }
        }
    }

    private boolean contains(String[] str, String... check) {
        for (String string : str)
            for (String s : check)
                if (string.equalsIgnoreCase(s))
                    return true;
        return false;
    }
}
