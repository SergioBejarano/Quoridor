package domain;

import java.util.ArrayList;

public class DoubleTurn extends Square{
	
	/**
	 * Creates a double shift type square given a location.
	 * 
	 * @param location The location of the square as a vector.
	 * @param name     The name associated with the square.
	 */
	public DoubleTurn(int[] location, String name)	{
		super(location, name);
		
	} 
	
	
	/**
	 * Defines a behavior for the pawn 
	 * 
	 * @param pawn
	 */
	public void act( )	{
		Quoridor quoridor = Quoridor.getQuoridor();
		quoridor.changeState("DoubleTurn");
		quoridor.stealingTurn();				
		
	}
}
