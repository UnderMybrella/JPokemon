package org.abimon.jpokemon;

import org.abimon.omnis.io.Data;

import java.io.File;
import java.util.HashMap;

public class Ability {
    public static final HashMap<String, Ability> ABILITIES = new HashMap<String, Ability>();

    String internalName = "";
    String displayName = "";
    String description = "";

    public static void load() {
        ABILITIES.clear();

        try {
            Data moveData = new Data(new File("abilities.txt"));

            for (String s : moveData.getAsStringArray()) {
                String[] params = s.split(",", 4);
                Ability ability = new Ability();
                ability.internalName = params[1];
                ability.displayName = params[2];
                ability.description = params[3];
                ABILITIES.put(ability.internalName, ability);
                System.out.println("Registered " + ability);
            }

            System.out.println("Registered " + ABILITIES.size() + " abilities");
        } catch (Throwable th) {
        }
    }

    public String toString() {
        return displayName;
    }
}
