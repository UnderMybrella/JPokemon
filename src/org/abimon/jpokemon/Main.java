package org.abimon.jpokemon;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Ability.load();
        Type.load();
        Move.load();
        Species.load();

        IPokemon glaceon = new IPokemon() {
            @Override
            public String getUID() {
                return "glaceon";
            }

            @Override
            public Species getSpecies() {
                return Species.get("GLACEON");
            }

            @Override
            public int getHP() {
                return 201;
            }

            @Override
            public int getAttack() {
                return 123;
            }

            @Override
            public int getDefence() {
                return 181;
            }

            @Override
            public int getSpecialAttack() {
                return 0;
            }

            @Override
            public int getSpecialDefence() {
                return 0;
            }

            @Override
            public int getSpeed() {
                return 0;
            }

            @Override
            public int getGender() {
                return 0;
            }

            @Override
            public int getExperience() {
                return 0;
            }

            @Override
            public int getLevel() {
                return 75;
            }

            @Override
            public int getEVForHP() {
                return 0;
            }

            @Override
            public int getEVForAttack() {
                return 0;
            }

            @Override
            public int getEVforDefence() {
                return 0;
            }

            @Override
            public int getEVForSpecialAttack() {
                return 0;
            }

            @Override
            public int getEVForSpecialDefence() {
                return 0;
            }

            @Override
            public int getEVForSpeed() {
                return 0;
            }

            @Override
            public int getIVForHP() {
                return 0;
            }

            @Override
            public int getIVForAttack() {
                return 0;
            }

            @Override
            public int getIVforDefence() {
                return 0;
            }

            @Override
            public int getIVForSpecialAttack() {
                return 0;
            }

            @Override
            public int getIVForSpecialDefence() {
                return 0;
            }

            @Override
            public int getIVForSpeed() {
                return 0;
            }

            @Override
            public Move[] getMoves() {
                return new Move[]{Move.get("ICEFANG")};
            }

            @Override
            public int getHappiness() {
                return 0;
            }

            @Override
            public Ability getAbility() {
                return null;
            }

            @Override
            public Item getItem() {
                return null;
            }
        };
        IPokemon garchomp = new IPokemon() {
            @Override
            public String getUID() {
                return "garchomp";
            }

            @Override
            public Species getSpecies() {
                return Species.get("GARCHOMP");
            }

            @Override
            public int getHP() {
                return 270;
            }

            @Override
            public int getAttack() {
                return 210;
            }

            @Override
            public int getDefence() {
                return 163;
            }

            @Override
            public int getSpecialAttack() {
                return 0;
            }

            @Override
            public int getSpecialDefence() {
                return 0;
            }

            @Override
            public int getSpeed() {
                return 0;
            }

            @Override
            public int getGender() {
                return 0;
            }

            @Override
            public int getExperience() {
                return 0;
            }

            @Override
            public int getLevel() {
                return 78;
            }

            @Override
            public int getEVForHP() {
                return 0;
            }

            @Override
            public int getEVForAttack() {
                return 0;
            }

            @Override
            public int getEVforDefence() {
                return 0;
            }

            @Override
            public int getEVForSpecialAttack() {
                return 0;
            }

            @Override
            public int getEVForSpecialDefence() {
                return 0;
            }

            @Override
            public int getEVForSpeed() {
                return 0;
            }

            @Override
            public int getIVForHP() {
                return 0;
            }

            @Override
            public int getIVForAttack() {
                return 0;
            }

            @Override
            public int getIVforDefence() {
                return 0;
            }

            @Override
            public int getIVForSpecialAttack() {
                return 0;
            }

            @Override
            public int getIVForSpecialDefence() {
                return 0;
            }

            @Override
            public int getIVForSpeed() {
                return 0;
            }

            @Override
            public Move[] getMoves() {
                return new Move[]{Move.get("EARTHQUAKE")};
            }

            @Override
            public int getHappiness() {
                return 0;
            }

            @Override
            public Ability getAbility() {
                return null;
            }

            @Override
            public Item getItem() {
                return null;
            }
        };

        IBattleProvider provider = new IBattleProvider() {
            @Override
            public Species getSpecies(IPokemon pokemon) {
                return pokemon.getSpecies();
            }

            @Override
            public int getHP(IPokemon pokemon) {
                return pokemon.getHP();
            }

            @Override
            public int getAttack(IPokemon pokemon) {
                return pokemon.getAttack();
            }

            @Override
            public int getDefence(IPokemon pokemon) {
                return pokemon.getDefence();
            }

            @Override
            public int getSpecialAttack(IPokemon pokemon) {
                return pokemon.getSpecialAttack();
            }

            @Override
            public int getSpecialDefence(IPokemon pokemon) {
                return pokemon.getSpecialDefence();
            }

            @Override
            public int getSpeed(IPokemon pokemon) {
                return pokemon.getSpeed();
            }

            @Override
            public int getGender(IPokemon pokemon) {
                return pokemon.getGender();
            }

            @Override
            public int getExperience(IPokemon pokemon) {
                return pokemon.getExperience();
            }

            @Override
            public int getLevel(IPokemon pokemon) {
                return pokemon.getLevel();
            }

            @Override
            public int getEVForHP(IPokemon pokemon) {
                return pokemon.getEVForHP();
            }

            @Override
            public int getEVForAttack(IPokemon pokemon) {
                return pokemon.getEVForAttack();
            }

            @Override
            public int getEVforDefence(IPokemon pokemon) {
                return pokemon.getEVforDefence();
            }

            @Override
            public int getEVForSpecialAttack(IPokemon pokemon) {
                return pokemon.getEVForSpecialAttack();
            }

            @Override
            public int getEVForSpecialDefence(IPokemon pokemon) {
                return pokemon.getEVForSpecialDefence();
            }

            @Override
            public int getEVForSpeed(IPokemon pokemon) {
                return pokemon.getEVForSpeed();
            }

            @Override
            public int getIVForHP(IPokemon pokemon) {
                return pokemon.getIVForHP();
            }

            @Override
            public int getIVForAttack(IPokemon pokemon) {
                return pokemon.getIVForAttack();
            }

            @Override
            public int getIVforDefence(IPokemon pokemon) {
                return pokemon.getIVforDefence();
            }

            @Override
            public int getIVForSpecialAttack(IPokemon pokemon) {
                return pokemon.getIVForSpecialAttack();
            }

            @Override
            public int getIVForSpecialDefence(IPokemon pokemon) {
                return pokemon.getIVForSpecialDefence();
            }

            @Override
            public int getIVForSpeed(IPokemon pokemon) {
                return pokemon.getIVForSpeed();
            }

            @Override
            public Move[] getMoves(IPokemon pokemon) {
                return pokemon.getMoves();
            }

            @Override
            public int getHappiness(IPokemon pokemon) {
                return pokemon.getHappiness();
            }

            @Override
            public Ability getAbility(IPokemon pokemon) {
                return pokemon.getAbility();
            }

            @Override
            public Item getItem(IPokemon pokemon) {
                return pokemon.getItem();
            }

            @Override
            public int getCriticalHitStage(IPokemon pokemon) {
                return pokemon.getUID().equalsIgnoreCase("garchomp") ? 4 : 0;
            }
        };

        System.out.println("Glaceon deals: " + Formulas.damageFormulaMin(glaceon, garchomp, glaceon.getMoves()[0], provider) +  " - " + Formulas.damageFormulaMax(glaceon, garchomp, glaceon.getMoves()[0], provider));
        System.out.println("Garchomp deals: " + Formulas.damageFormulaMin(garchomp, glaceon, garchomp.getMoves()[0], provider) +  " - " + Formulas.damageFormulaMax(garchomp, glaceon, garchomp.getMoves()[0], provider));
    }
}
