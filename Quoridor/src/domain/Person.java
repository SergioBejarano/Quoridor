package domain;

import java.awt.Color;
import java.util.HashMap;

public class Person extends Player{
	
	
	
	/**
	 * Creates a person.
	 * 
	 * @param name        The name of the person.
	 * @param color       The color associated with the person.
	 * @param limitWalls  A map containing the limit of walls for the person.
	 * @param location    The initial location of the person on the board.
	 */
	public Person(String name, String color, HashMap<String, Integer> limitWalls, int[] location)	{
		
		
		super(name, color, limitWalls, location);
		
	}
	
	
	
	
	
	/**
	 * Moves the peon on the board.
	 * 
	 * @param n        The new location coordinates for the peon.
	 * @param board    The current game board.
	 * @param squares  The current configuration of squares on the board.
	 * @param players  The current players in the game.
	 * @throws QuoridorException If the move is invalid.
	 */
	public void movePeon( int[] n, String[][] board, HashMap<String, Square> squares, HashMap<String, Player> players)	throws QuoridorException {
		
		peon.move(name, n, board, walls,squares, players);
	}
	
	
	
	
	
	

	
	
	
}
