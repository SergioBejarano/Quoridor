package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Teleporter extends Square{

	
	/**
	 * Creates a square teleporter given location.
	 * @param location It is a vector.
	 */
	public Teleporter(int[] location, String name)	{
		super(location, name);
		
	} 
	
	
	
	/**
	 * Defines a behavior for the pawn 
	 * 
	 * @param pawn
	 */
	public void act()	{
		Quoridor quoridor = Quoridor.getQuoridor();
		quoridor.changeState("Teleporter");
		
	}
	
}
