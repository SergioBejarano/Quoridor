package domain;

import java.util.*;

public class Revert extends Square {
	
	
	/**
	 * Creates a revert type square given a location.
	 * @param location It is a vector.
	 */
	public Revert(int[] location, String name)	{
		super(location, name);
	}  
	
	/**
	 * Defines a behavior for the pawn 
	 * 
	 * @param pawn
	 */
	public void act()	{
		Quoridor quoridor = Quoridor.getQuoridor();
		quoridor.changeState("Revert");
		
	}
	

}
