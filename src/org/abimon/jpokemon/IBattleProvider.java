package org.abimon.jpokemon;

/** Any object that provides information for the battle, such as temporary stat changes
 * For in-battle information, this should be used over direct calls
 */
public interface IBattleProvider {
    public Species getSpecies(IPokemon pokemon);

    public int getHP(IPokemon pokemon);
    public int getAttack(IPokemon pokemon);
    public int getDefence(IPokemon pokemon);
    public int getSpecialAttack(IPokemon pokemon);
    public int getSpecialDefence(IPokemon pokemon);
    public int getSpeed(IPokemon pokemon);

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

    public int getCriticalHitStage(IPokemon pokemon);
}
