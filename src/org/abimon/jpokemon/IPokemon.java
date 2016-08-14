package org.abimon.jpokemon;

public interface IPokemon {
    /** The UID doesn't necessarily have to be a UUID, just any form of unique identification
     * Should *not* be randomly generated at the time of calling
     */
    public String getUID();
    public Species getSpecies();

    public int getHP();
    public int getAttack();
    public int getDefence();
    public int getSpecialAttack();
    public int getSpecialDefence();
    public int getSpeed();

    /** 0 is male, 1 is female, -1 is genderless */
    public int getGender();

    public int getExperience();
    public int getLevel();

    public int getEVForHP();
    public int getEVForAttack();
    public int getEVforDefence();
    public int getEVForSpecialAttack();
    public int getEVForSpecialDefence();
    public int getEVForSpeed();

    public int getIVForHP();
    public int getIVForAttack();
    public int getIVforDefence();
    public int getIVForSpecialAttack();
    public int getIVForSpecialDefence();
    public int getIVForSpeed();

    public Move[] getMoves();

    public int getHappiness();

    public Ability getAbility();
    public Item getItem();
}
