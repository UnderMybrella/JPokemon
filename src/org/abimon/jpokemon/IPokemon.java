package org.abimon.jpokemon;

public interface IPokemon {
    /** The UID doesn't necessarily have to be a UUID, just any form of unique identification
     * Should *not* be randomly generated at the time of calling, and should *not* change. Preferably ever.
     */
    public String getUID();
    public Species getSpecies();

    public void setSpecies(Species species);

    public int getHP();
    public int getAttack();
    public int getDefence();
    public int getSpecialAttack();
    public int getSpecialDefence();
    public int getSpeed();

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
}
