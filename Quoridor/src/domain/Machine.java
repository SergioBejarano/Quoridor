package domain;

import java.awt.Color;

import java.util.*;

public abstract class Machine extends Player{


	/**
     * Creates a machine player.
     * 
     * @param name        The name of the machine player.
     * @param color       The color associated with the machine player.
     * @param limitWalls  A map containing the limit of walls for the machine player.
     * @param location    The initial location of the machine player on the board.
     */
	public Machine(String name, String color, HashMap<String, Integer> limitWalls, int[] location)	{
		
		super(name,color, limitWalls, location);
		
	} 
	
	
	/**
	 * The machine makes a decision on what action to perform.
	 * 
	 * @param board The current game board.
	 */
	public abstract void act(String[][] board);
	
	
	/**
	 * Puts a wall on the board.
	 * 
	 * @param board The current game board.
	 */
	public abstract void putWall(String[][] board);
	
	
	/**
	 * Moves the pawn.
	 * 
	 * @param board The current game board.
	 */
	public abstract void movePeon(String[][] board);
	
	
}
