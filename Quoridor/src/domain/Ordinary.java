package domain;

import java.util.ArrayList;

public class Ordinary extends Square{
	
	/**
	 * Creates a ordinary type square given a location.
	 * @param location It is a vector.
	 */
	public Ordinary (int[] location, String name)	{
		super(location, name);
		
	} 
	
	
	/**
	 * Defines a behavior for the pawn 
	 * 
	 * @param pawn
	 */
	public void act( )	{
		
		Quoridor quoridor = Quoridor.getQuoridor();
		quoridor.changeState("Ordinary");
	}
}
