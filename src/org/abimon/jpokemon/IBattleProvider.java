package org.abimon.jpokemon;

import org.abimon.jpokemon.events.IPokemonEventHandler;
import org.abimon.jpokemon.events.PokemonEvent;

/** Any object that provides information for the battle, such as temporary stat changes
 * For in-battle information, this should be used over direct calls
 */
public interface IBattleProvider {

    public IPokemon[][] getPokemon();

    public int getTeam(IPokemon pokemon);

    public IPokemon[] getParty(int team);
    public IPokemon[] getParty(IPokemon allied);

    public IPokemon[] getActive(int team);
    public IPokemon[] getActive(IPokemon allied);

    public IPokemon setActive(int team, int spot, IPokemon pokemon);
    public IPokemon setActive(IPokemon allied, int spot, IPokemon pokemon);

    public int getPokemonActivePerSide();

    public Species getSpecies(IPokemon pokemon);

    public Type getTypeOne(IPokemon pokemon);
    public Type getTypeTwo(IPokemon pokemon);

    public void setTypeOne(IPokemon pokemon, Type typeOne);
    public void setTypeTwo(IPokemon pokemon, Type typeTwo);

    public int getMaxHP(IPokemon pokemon);
    public int getHP(IPokemon pokemon);
    public int getAttack(IPokemon pokemon);
    public int getDefence(IPokemon pokemon);
    public int getSpecialAttack(IPokemon pokemon);
    public int getSpecialDefence(IPokemon pokemon);
    public int getSpeed(IPokemon pokemon);

    public void setMaxHP(IPokemon pokemon, int maxHP);
    public void setHP(IPokemon pokemon, int hp);
    public void setAttack(IPokemon pokemon, int atk);
    public void setDefence(IPokemon pokemon, int def);
    public void setSpecialAttack(IPokemon pokemon, int spAtk);
    public void setSpecialDefence(IPokemon pokemon, int spDef);
    public void setSpeed(IPokemon pokemon, int speed);

    /** 0 is male, 1 is female, -1 is genderless */
    public int getGender(IPokemon pokemon);

    public int getExperience(IPokemon pokemon);
    public int getLevel(IPokemon pokemon);

    public int getEVForHP(IPokemon pokemon);
    public int getEVForAttack(IPokemon pokemon);
    public int getEVforDefence(IPokemon pokemon);
    public int getEVForSpecialAttack(IPokemon pokemon);
    public int getEVForSpecialDefence(IPokemon pokemon);
    public int getEVForSpeed(IPokemon pokemon);

    public int getIVForHP(IPokemon pokemon);
    public int getIVForAttack(IPokemon pokemon);
    public int getIVforDefence(IPokemon pokemon);
    public int getIVForSpecialAttack(IPokemon pokemon);
    public int getIVForSpecialDefence(IPokemon pokemon);
    public int getIVForSpeed(IPokemon pokemon);

    public Move[] getMoves(IPokemon pokemon);

    public int getHappiness(IPokemon pokemon);

    public Ability getAbility(IPokemon pokemon);
    public Item getItem(IPokemon pokemon);

    public void setAbility(IPokemon pokemon, Ability ability);
    public void setItem(IPokemon pokemon, Item item);

    public int getCriticalHitStage(IPokemon pokemon);

    public int getAttackStage(IPokemon pokemon);
    public int getDefenceStage(IPokemon pokemon);
    public int getSpecialAttackStage(IPokemon pokemon);
    public int getSpecialDefenceStage(IPokemon pokemon);
    public int getSpeedStage(IPokemon pokemon);

    public void setAttackStage(IPokemon pokemon, int stage);
    public void setDefenceStage(IPokemon pokemon, int stage);
    public void setSpecialAttackStage(IPokemon pokemon, int stage);
    public void setSpecialDefenceStage(IPokemon pokemon, int stage);
    public void setSpeedStage(IPokemon pokemon, int stage);

    public String getWeatherCondition();
    public void setWeatherCondition(String weather);

    public String[] getStatusConditions(IPokemon pokemon);
    public void addStatusCondition(IPokemon pokemon, String statusCondition);
    public void removeStatusCondition(IPokemon pokemon, String statusCondition);
    public void setStatusConditions(IPokemon pokemon, String[] statusConditions);

    public void dispatch(PokemonEvent event);
    public void register(IPokemonEventHandler eventHandler);
}
