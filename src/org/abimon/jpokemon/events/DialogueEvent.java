package org.abimon.jpokemon.events;

public class DialogueEvent extends PokemonEvent{

    /** May be a localised string (pokemon.flashfire)
     * More details may be added after a | (Such as pokemon.ability.flashfire|foe) */
    public final String dialogue;

    public DialogueEvent(String dialogue){
        this.dialogue = dialogue;
    }
}
