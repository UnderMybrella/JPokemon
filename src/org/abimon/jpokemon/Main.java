package org.abimon.jpokemon;

import org.abimon.omnis.util.General;

public class Main {

    public static void main(String[] args) {
        Ability.load();
        Type.load();
        Move.load();
        Species.load();
        Item.load();

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
            public void setSpecies(Species species) {}

            @Override
            public int getMaxHP() {
                return 201;
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
            public void setMaxHP(int hp) {

            }

            @Override
            public void setHP(int hp) {

            }

            @Override
            public void setAttack(int attack) {

            }

            @Override
            public void setDefence(int defence) {

            }

            @Override
            public void setSpecialAttack(int specialAttack) {

            }

            @Override
            public void setSpecialDefence(int specialDefence) {

            }

            @Override
            public void setSpeed(int speed) {

            }

            @Override
            public int getGender() {
                return 0;
            }

            @Override
            public void setGender(int gender) {

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
            public void setExperience(int experience) {

            }

            @Override
            public void setLevel(int level) {

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
            public void setEVForHP(int ev) {

            }

            @Override
            public void setEVForAttack(int ev) {

            }

            @Override
            public void setEVForDefence(int ev) {

            }

            @Override
            public void setEVForSpecialAttack(int ev) {

            }

            @Override
            public void setEVForSpecialDefence(int ev) {

            }

            @Override
            public void setEVForSpeed(int ev) {

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
            public void setIVForHP(int iv) {

            }

            @Override
            public void setIVForAttack(int iv) {

            }

            @Override
            public void setIVForDefence(int iv) {

            }

            @Override
            public void setIVForSpecialAttack(int iv) {

            }

            @Override
            public void setIVForSpecialDefence(int iv) {

            }

            @Override
            public void setIVForSpeed(int iv) {

            }

            @Override
            public Move[] getMoves() {
                return new Move[]{Move.get("ICEFANG")};
            }

            @Override
            public void setMoves(Move[] moves) {

            }

            @Override
            public int getHappiness() {
                return 0;
            }

            @Override
            public void setHappiness(int happiness) {

            }

            @Override
            public Ability getAbility() {
                return Ability.get("WONDERGUARD");
            }

            @Override
            public Item getItem() {
                return null;
            }

            @Override
            public void setAbility(Ability ability) {

            }

            @Override
            public void setItem(Item item) {

            }

            @Override
            public String[] getStatusConditions() {
                return new String[0];
            }

            @Override
            public void setStatusConditions(String[] statuses) {

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
            public void setSpecies(Species species) {

            }

            @Override
            public int getMaxHP() {
                return 270;
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
            public void setMaxHP(int hp) {

            }

            @Override
            public void setHP(int hp) {

            }

            @Override
            public void setAttack(int attack) {

            }

            @Override
            public void setDefence(int defence) {

            }

            @Override
            public void setSpecialAttack(int specialAttack) {

            }

            @Override
            public void setSpecialDefence(int specialDefence) {

            }

            @Override
            public void setSpeed(int speed) {

            }

            @Override
            public int getGender() {
                return 0;
            }

            @Override
            public void setGender(int gender) {

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
            public void setExperience(int experience) {

            }

            @Override
            public void setLevel(int level) {

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
            public void setEVForHP(int ev) {

            }

            @Override
            public void setEVForAttack(int ev) {

            }

            @Override
            public void setEVForDefence(int ev) {

            }

            @Override
            public void setEVForSpecialAttack(int ev) {

            }

            @Override
            public void setEVForSpecialDefence(int ev) {

            }

            @Override
            public void setEVForSpeed(int ev) {

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
            public void setIVForHP(int iv) {

            }

            @Override
            public void setIVForAttack(int iv) {

            }

            @Override
            public void setIVForDefence(int iv) {

            }

            @Override
            public void setIVForSpecialAttack(int iv) {

            }

            @Override
            public void setIVForSpecialDefence(int iv) {

            }

            @Override
            public void setIVForSpeed(int iv) {

            }

            @Override
            public Move[] getMoves() {
                return new Move[]{Move.get("EARTHQUAKE")};
            }

            @Override
            public void setMoves(Move[] moves) {

            }

            @Override
            public int getHappiness() {
                return 0;
            }

            @Override
            public void setHappiness(int happiness) {

            }

            @Override
            public Ability getAbility() {
                return Ability.get("MOLDBREAKER");
            }

            @Override
            public Item getItem() {
                return null;
            }

            @Override
            public void setAbility(Ability ability) {

            }

            @Override
            public void setItem(Item item) {

            }

            @Override
            public String[] getStatusConditions() {
                return new String[0];
            }

            @Override
            public void setStatusConditions(String[] statuses) {

            }
        };

        IBattleProvider provider = new BattleProvider(1, new IPokemon[]{glaceon}, new IPokemon[]{garchomp});

        System.out.println("Glaceon deals: " + Formulas.damageFormula(glaceon, garchomp, glaceon.getMoves()[0], provider));
        System.out.println("Garchomp deals: " + Formulas.damageFormula(garchomp, glaceon, garchomp.getMoves()[0], provider));

        IPokemon porygonZ = IPokemon.create(Species.get("PORYGONZ"), General.createHashmap(new String[]{"level", "move_1", "ability", "sp_atk"}, new Object[]{100, "HYPERBEAM", "ADAPTABILITY", 561}));
        IPokemon blissey = IPokemon.create(Species.get("BLISSEY"), General.createHashmap(new String[]{"level", "sp_def"}, new Object[]{100, 405}));

        IBattleProvider test = new BattleProvider(1, new IPokemon[]{porygonZ}, new IPokemon[]{blissey});

        test.setSpecialAttackStage(porygonZ, 6);

        System.out.println("Porygon-Z deals: " + Formulas.damageFormula(porygonZ, blissey, porygonZ.getMoves()[0], test));

        System.out.println(blissey.toString(null));

        System.out.println(Species.GrowthRate.MEDIUM.getRequiredEXPForLevel(100));
    }
}
