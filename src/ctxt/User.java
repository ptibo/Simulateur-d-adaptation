package ctxt;

public class User {
	
	public enum Localisation { Bathroom, Living_room, Bedroom, Kitchen; };
	
	public boolean interruptible;
	public boolean sighted;
	public boolean hearing;
	public boolean mobiSup;
	public boolean mobiInf;
	public Localisation localisation;
	
	public User(Localisation localisation, boolean interruptible, boolean view, boolean hearing, boolean moveSup, boolean moveInf) {
		this.localisation = localisation;
		this.interruptible = interruptible;
		this.sighted = view;
		this.hearing = hearing;
		this.mobiSup = moveSup;
		this.mobiInf = moveInf;
	}

}
