package org.abimon.jpokemon;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public interface IPokemon {

    /** The UID doesn't necessarily have to be a UUID, just any form of unique identification
     * Should *not* be randomly generated at the time of calling, and should *not* change. Preferably ever.
     */
    public String getUID();
    public Species getSpecies();

    public void setSpecies(Species species);

    //Grumble grumble something about natures

    public int getMaxHP();
    public int getHP();
    public int getAttack();
    public int getDefence();
    public int getSpecialAttack();
    public int getSpecialDefence();
    public int getSpeed();

    public void setMaxHP(int hp);
    public void setHP(int hp);
    public void setAttack(int attack);
    public void setDefence(int defence);
    public void setSpecialAttack(int specialAttack);
    public void setSpecialDefence(int specialDefence);
    public void setSpeed(int speed);

    /** 0 is male, 1 is female, -1 is genderless */
    public int getGender();

    public void setGender(int gender);

    public int getExperience();
    public int getLevel();

    public void setExperience(int experience);
    public void setLevel(int level);

    public int getEVForHP();
    public int getEVForAttack();
    public int getEVforDefence();
    public int getEVForSpecialAttack();
    public int getEVForSpecialDefence();
    public int getEVForSpeed();

    public void setEVForHP(int ev);
    public void setEVForAttack(int ev);
    public void setEVForDefence(int ev);
    public void setEVForSpecialAttack(int ev);
    public void setEVForSpecialDefence(int ev);
    public void setEVForSpeed(int ev);

    public int getIVForHP();
    public int getIVForAttack();
    public int getIVforDefence();
    public int getIVForSpecialAttack();
    public int getIVForSpecialDefence();
    public int getIVForSpeed();

    public void setIVForHP(int iv);
    public void setIVForAttack(int iv);
    public void setIVForDefence(int iv);
    public void setIVForSpecialAttack(int iv);
    public void setIVForSpecialDefence(int iv);
    public void setIVForSpeed(int iv);

    public Move[] getMoves();

    public void setMoves(Move[] moves);

    public int getHappiness();

    public void setHappiness(int happiness);

    public Ability getAbility();
    public Item getItem();

    public void setAbility(Ability ability);
    public void setItem(Item item);

    /** Outside of IBattleProviders, this should only contain 'non-volatile' status conditions (ie: conditions that persist outside of battle) */
    public String[] getStatusConditions();
    public void setStatusConditions(String[] statuses);

    public static IPokemon create(Species templateSpecies, HashMap<String, Object> custom){
        IPokemon pkmn = new IPokemon() {
            String uid = UUID.randomUUID().toString();

            Species species = null;

            int maxHP = 0;
            int hp = 0;
            int atk = 0;
            int def = 0;
            int spAtk = 0;
            int spDef = 0;
            int speed = 0;

            int hpEV = 0;
            int atkEV = 0;
            int defEV = 0;
            int spAtkEV = 0;
            int spDefEV = 0;
            int speedEV = 0;

            int hpIV = 0;
            int atkIV = 0;
            int defIV = 0;
            int spAtkIV = 0;
            int spDefIV = 0;
            int speedIV = 0;

            int gender = 0;

            int exp = 0;
            int level = 0;

            Move[] moves;

            int happiness = 0;

            Ability ability;
            Item item;

            String[] statusConditions;

            @Override
            public String getUID() {
                return uid;
            }

            @Override
            public Species getSpecies() {
                return species;
            }

            @Override
            public void setSpecies(Species species) {
                this.species = species;
            }

            @Override
            public int getMaxHP() {
                return maxHP;
            }

            @Override
            public int getHP() {
                return hp;
            }

            @Override
            public int getAttack() {
                return atk;
            }

            @Override
            public int getDefence() {
                return def;
            }

            @Override
            public int getSpecialAttack() {
                return spAtk;
            }

            @Override
            public int getSpecialDefence() {
                return spDef;
            }

            @Override
            public int getSpeed() {
                return speed;
            }

            @Override
            public void setMaxHP(int hp) {
                this.maxHP = hp;
            }

            @Override
            public void setHP(int hp) {
                this.hp = hp;
            }

            @Override
            public void setAttack(int attack) {
                this.atk = attack;
            }

            @Override
            public void setDefence(int defence) {
                this.def = defence;
            }

            @Override
            public void setSpecialAttack(int specialAttack) {
                this.spAtk = specialAttack;
            }

            @Override
            public void setSpecialDefence(int specialDefence) {
                this.spDef = specialDefence;
            }

            @Override
            public void setSpeed(int speed) {
                this.speed = speed;
            }

            @Override
            public int getGender() {
                return gender;
            }

            @Override
            public void setGender(int gender) {
                this.gender = gender;
            }

            @Override
            public int getExperience() {
                return exp;
            }

            @Override
            public int getLevel() {
                return level;
            }

            @Override
            public void setExperience(int experience) {
                this.exp = experience;
            }

            @Override
            public void setLevel(int level) {
                this.level = level;
            }

            @Override
            public int getEVForHP() {
                return hpEV;
            }

            @Override
            public int getEVForAttack() {
                return atkEV;
            }

            @Override
            public int getEVforDefence() {
                return defEV;
            }

            @Override
            public int getEVForSpecialAttack() {
                return spAtkEV;
            }

            @Override
            public int getEVForSpecialDefence() {
                return spDefEV;
            }

            @Override
            public int getEVForSpeed() {
                return speedEV;
            }

            @Override
            public void setEVForHP(int ev) {
                this.hpEV = ev;
            }

            @Override
            public void setEVForAttack(int ev) {
                this.atkEV = ev;
            }

            @Override
            public void setEVForDefence(int ev) {
                this.defEV = ev;
            }

            @Override
            public void setEVForSpecialAttack(int ev) {
                this.spAtkEV = ev;
            }

            @Override
            public void setEVForSpecialDefence(int ev) {
                this.spDefEV = ev;
            }

            @Override
            public void setEVForSpeed(int ev) {
                this.speedEV = ev;
            }

            @Override
            public int getIVForHP() {
                return hpIV;
            }

            @Override
            public int getIVForAttack() {
                return atkIV;
            }

            @Override
            public int getIVforDefence() {
                return defIV;
            }

            @Override
            public int getIVForSpecialAttack() {
                return spAtkIV;
            }

            @Override
            public int getIVForSpecialDefence() {
                return spDefIV;
            }

            @Override
            public int getIVForSpeed() {
                return speedIV;
            }

            @Override
            public void setIVForHP(int iv) {
                this.hpIV = iv;
            }

            @Override
            public void setIVForAttack(int iv) {
                this.atkIV = iv;
            }

            @Override
            public void setIVForDefence(int iv) {
                this.defIV = iv;
            }

            @Override
            public void setIVForSpecialAttack(int iv) {
                this.spAtkIV = iv;
            }

            @Override
            public void setIVForSpecialDefence(int iv) {
                this.spDefIV = iv;
            }

            @Override
            public void setIVForSpeed(int iv) {
                this.speedIV = iv;
            }

            @Override
            public Move[] getMoves() {
                return moves;
            }

            @Override
            public void setMoves(Move[] moves) {
                this.moves = moves;
            }

            @Override
            public int getHappiness() {
                return happiness;
            }

            @Override
            public void setHappiness(int happiness) {
                this.happiness = happiness;
            }

            @Override
            public Ability getAbility() {
                return ability;
            }

            @Override
            public Item getItem() {
                return item;
            }

            @Override
            public void setAbility(Ability ability) {
                this.ability = ability;
            }

            @Override
            public void setItem(Item item) {
                this.item = item;
            }

            @Override
            public String[] getStatusConditions() {
                return statusConditions;
            }

            @Override
            public void setStatusConditions(String[] statuses) {
                this.statusConditions = statuses;
            }
        };

        try {
            int level = (int) custom.getOrDefault("level", 1);

            int evHP = (int) custom.getOrDefault("evHP", 0);
            int evAtk = (int) custom.getOrDefault("ev_atk", 0);
            int evDef = (int) custom.getOrDefault("ev_def", 0);
            int evSpAtk = (int) custom.getOrDefault("ev_sp_atk", 0);
            int evSpDef = (int) custom.getOrDefault("ev_sp_def", 0);
            int evSpeed = (int) custom.getOrDefault("ev_speed", 0);

            int ivHP = (int) custom.getOrDefault("iv_hp", 0);
            int ivAtk = (int) custom.getOrDefault("iv_atk", 0);
            int ivDef = (int) custom.getOrDefault("iv_def", 0);
            int ivSpAtk = (int) custom.getOrDefault("iv_sp_atk", 0);
            int ivSpDef = (int) custom.getOrDefault("iv_sp_def", 0);
            int ivSpeed = (int) custom.getOrDefault("iv_speed", 0);

            int hp = (int) custom.getOrDefault("hp", Formulas.getHPForLevel(templateSpecies.baseHP, ivHP, evHP, level));
            int atk = (int) custom.getOrDefault("atk", Formulas.getStatForLevel(templateSpecies.baseAtk, ivAtk, evAtk, level, 1.0));
            int def = (int) custom.getOrDefault("def", Formulas.getStatForLevel(templateSpecies.baseDef, ivDef, evDef, level, 1.0));
            int spAtk = (int) custom.getOrDefault("sp_atk", Formulas.getStatForLevel(templateSpecies.baseSpAtk, ivSpAtk, evSpAtk, level, 1.0));
            int spDef = (int) custom.getOrDefault("sp_def", Formulas.getStatForLevel(templateSpecies.baseSpDef, ivSpDef, evSpDef, level, 1.0));
            int speed = (int) custom.getOrDefault("speed", Formulas.getStatForLevel(templateSpecies.baseSpeed, ivSpeed, evSpeed, level, 1.0));

            pkmn.setSpecies(templateSpecies);

            pkmn.setLevel(level);
            pkmn.setExperience(templateSpecies.growthRate.getRequiredEXPForLevel(level));

            pkmn.setMaxHP(hp);
            pkmn.setHP(hp);
            pkmn.setAttack(atk);
            pkmn.setDefence(def);
            pkmn.setSpecialAttack(spAtk);
            pkmn.setSpecialDefence(spDef);
            pkmn.setSpeed(speed);

            Move[] moves = new Move[4];
            Move[][] levelUp = templateSpecies.levelUp.values().toArray(new Move[0][]);

            for (int i = 0; i < moves.length; i++)
                moves[i] = Move.get((String) custom.getOrDefault("move_" + (i + 1), levelUp[Math.min(levelUp.length, i)][0].name));

            pkmn.setMoves(moves);
            pkmn.setAbility(Ability.get((String) custom.getOrDefault("ability", templateSpecies.ability1.internalName)));
        }
        catch(Throwable th){
            th.printStackTrace();
        }

        return pkmn;
    }

    public default String toString(IPokemon pokemon){
        pokemon = this;
        String str = "Name: " + pokemon.getSpecies().name;
        str += "\nLevel: " + pokemon.getLevel();
        str += "\nExperience: " + pokemon.getExperience();
        str += "\nHP: " + pokemon.getHP() + "/" + pokemon.getMaxHP();
        str += "\nAttack: " + pokemon.getAttack();
        str += "\nDefence: " + pokemon.getDefence();
        str += "\nSpecial Attack: " + pokemon.getSpecialAttack();
        str += "\nSpecial Defence: " + pokemon.getSpecialDefence();
        str += "\nSpeed: " + pokemon.getSpeed();
        str += "\nAbility: " + pokemon.getAbility();
        str += "\nMoves: " + Arrays.toString(pokemon.getMoves());
        return str;
    }
}
