package org.abimon.jpokemon.events;

import org.abimon.jpokemon.IBattleProvider;
import org.abimon.jpokemon.IPokemon;

public class AddStatusEvent extends PokemonEvent{
    public IPokemon addingTo;
    /** if status == "" or == null then the status should not be applied. Otherwise, apply this variable's status */
    public String status;
    public String cause;
    public IBattleProvider provider;

    public AddStatusEvent(IPokemon addingTo, String status, String cause, IBattleProvider provider){
        this.addingTo = addingTo;
        this.status = status;
        this.cause = cause;
        this.provider = provider;
    }
}
