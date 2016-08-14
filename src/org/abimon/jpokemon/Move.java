package org.abimon.jpokemon;

import org.abimon.omnis.io.Data;

import java.io.File;
import java.util.HashMap;

public class Move {
	public static HashMap<String, Move> MOVES = new HashMap<String, Move>();

	public String name = null;
	public String display = null;
	public int function = 0;
	public int basePower = 0;
	public Type type = null;
	public String damageCategory = null;
	public int accuracy = 0;
	public int totalPP = 0;
	public int additionalEffectChance = 0;
	public MoveTarget target = MoveTarget.SINGLE_POKEMON_OTHER;
	public int priority = 0;
	public MoveFlag[] flags = null;
	public String description = null;
	
	public static void load(){
		MOVES.clear();
		try{

			Data moveData = new Data(new File("moves.txt"));

			for(String s : moveData.getAsStringArray()){
				String[] params = s.split(",", 14);

				Move move = new Move();
				move.name = params[1];
				move.display = params[2];
				move.function = Integer.parseInt(params[3], 16);
				move.basePower = Integer.parseInt(params[4]);
				move.type = Type.get(params[5]);
				move.damageCategory = params[6];
				move.accuracy = Integer.parseInt(params[7]);
				move.totalPP = Integer.parseInt(params[8]);
				move.additionalEffectChance = Integer.parseInt(params[9]);
				move.target = MoveTarget.getTarget(Integer.parseInt(params[10]));
				move.priority = Integer.parseInt(params[11]);
				move.flags = MoveFlag.getFlags(params[12]);
				move.description = params[13];

				MOVES.put(move.name, move);
				System.out.println("Registered " + move);
			}

			System.out.println("Registered " + MOVES.size() + " moves.");
		}
		catch(Throwable th){
			th.printStackTrace();
		}
	}

	public enum MoveTarget{
		SINGLE_POKEMON_OTHER,
		NO_TARGET,
		SINGLE_OPPOSING_RANDOM,
		ALL_OPPOSING,
		ALL_OTHER,
		USER,
		USER_SIDE,
		ALL_SIDES,
		OPPOSING_SIDES,
		USER_PARTNER,
		SINGLE_POKEMON_USER_SIDE,
		SINGLE_OPPOSING;

		public static MoveTarget getTarget(int num){
			switch(num){
				case 0:
					return SINGLE_POKEMON_OTHER;
				case 1:
					return NO_TARGET;
				case 2:
					return SINGLE_OPPOSING_RANDOM;
				case 4:
					return ALL_OPPOSING;
				case 8:
					return ALL_OTHER;
				case 10:
					return USER;
				case 20:
					return USER_SIDE;
				case 40:
					return ALL_SIDES;
				case 80:
					return OPPOSING_SIDES;
				case 100:
					return USER_PARTNER;
				case 200:
					return SINGLE_POKEMON_USER_SIDE;
				case 400:
					return SINGLE_OPPOSING;
				case 800:
					return SINGLE_OPPOSING;
				default:
					return SINGLE_POKEMON_OTHER;
			}
		}
	}

	public enum MoveFlag{
		PHYSICAL_CONTACT,
		CAN_PROTECT,
		CAN_REDIRECT,
		CAN_STEAL_SNATCH,
		CAN_COPY_MIRROR,
		CAN_FLINCH_ITEM,
		WILL_THAW_USER,
		HIGH_CRITICAL,
		BITING_MOVE,
		PUNCHING_MOVE,
		SOUND_MOVE,
		POWDER_MOVE,
		PULSE_MOVE,
		BOMB_MOVE;

		public static MoveFlag getFlag(char c){
			switch(c){
				case 'a':
					return PHYSICAL_CONTACT;
				case 'b':
					return CAN_PROTECT;
				case 'c':
					return CAN_REDIRECT;
				case 'd':
					return CAN_STEAL_SNATCH;
				case 'e':
					return CAN_COPY_MIRROR;
				case 'f':
					return CAN_FLINCH_ITEM;
				case 'g':
					return WILL_THAW_USER;
				case 'h':
					return HIGH_CRITICAL;
				case 'i':
					return BITING_MOVE;
				case 'j':
					return PUNCHING_MOVE;
				case 'k':
					return SOUND_MOVE;
				case 'l':
					return POWDER_MOVE;
				case 'm':
					return PULSE_MOVE;
				case 'n':
					return BOMB_MOVE;
				default:
					return PHYSICAL_CONTACT;
			}
		}

		public static MoveFlag[] getFlags(String s){
			MoveFlag[] flags = new MoveFlag[s.length()];
			for(int i = 0; i < flags.length; i++)
				flags[i] = getFlag(s.charAt(i));

			return flags;
		}

	}

	public String toString(){
		return display;
	}

	public static Move get(String s){
		return MOVES.get(s);
	}
}
