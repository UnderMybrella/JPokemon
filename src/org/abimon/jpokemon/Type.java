package org.abimon.jpokemon;

import org.abimon.omnis.io.Data;

import java.io.File;
import java.util.HashMap;

public class Type {
	public static HashMap<String, Type> TYPES = new HashMap<String, Type>();

	public static void load(){
		TYPES.clear();
		try{
			Data typeData = new Data(new File("types.txt"));

			String name = null;
			String internalName = null;
			String[] weaknesses = null;
			String[] resistances = null;
			String[] immunities = null;

			for(String s : typeData.getAsStringArray()){
				//System.out.println("[" + s + "]");
				if(s.matches("\\[\\d+\\]")){
					Type type = new Type(name, internalName, weaknesses, resistances, immunities);
					System.out.println("Registered new type: " + type);
					name = null;
					internalName = null;
					weaknesses = null;
					resistances = null;
					immunities = null;
				}
				else if(s.contains("=")){
					String val = s.split("=", 2)[1];
					if(s.startsWith("Name"))
						name = val;
					else if(s.startsWith("InternalName"))
						internalName = val;
					else if(s.startsWith("Weaknesses"))
						weaknesses = val.split(",");
					else if(s.startsWith("Resistances"))
						resistances = val.split(",");
					else if(s.startsWith("Immunities"))
						immunities = val.split(",");
				}
			}

			Type type = new Type(name, internalName, weaknesses, resistances, immunities);
			System.out.println("Registered new type: " + type);

			System.out.println("Registered types: " + TYPES + "\nThere are " + TYPES.size() + " types.");
		}
		catch(Throwable th){
			th.printStackTrace();
		}
	}

	String name, internalName;
	String[] weaknesses, resistances, immunities;

	public Type(String name, String internalName, String[] weaknesses, String[] resistances, String[] immunities){
		this.name = name;
		this.internalName = internalName;
		this.weaknesses = weaknesses == null ? new String[0] : weaknesses;
		this.resistances = resistances == null ? new String[0] : resistances;
		this.immunities = immunities == null ? new String[0] : immunities;

		TYPES.put(internalName, this);
	}

	public static Type get(String s){
		return TYPES.get(s);
	}

	/** Get the modifier of the passed Type attacking this one
	 * Eg: Type water = Type.get("WATER");
	 * 		water.getModifier(Type.get("GRASS")) = 2*/
	public double getModifier(Type type){
		for(String s : weaknesses)
			if(s.equalsIgnoreCase(type.internalName))
				return 2;
		for(String s : resistances)
			if(s.equalsIgnoreCase(type.internalName))
				return 0.5;
		for(String s : immunities)
			if(s.equalsIgnoreCase(type.internalName))
				return 0;
		return 1;
	}

	public boolean equals(Object obj){
		if(obj == null)
			return false;
		if(obj instanceof Type)
			return this.internalName.equals(((Type) obj).internalName);
		return false;
	}
}
