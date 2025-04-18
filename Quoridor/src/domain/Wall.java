package domain;

import java.util.*;
import java.io.Serializable;

public abstract class Wall implements Serializable{
	 
	protected int number;
	protected int[] location;
	private char orientation;
	private int length;
	private String player;
	protected String identification;
	
	
	/**
     * Creates a Wall.
     * 
     * @param number      The number identifier for the wall.
     * @param player      The player who owns the wall.
     * @param location    The location where the wall is placed.
     * @param orientation The orientation of the wall ('H' for horizontal, 'V' for vertical).
     */
	public Wall(int number,String player, int[] location, char orientation)	{
		
		this.player = player;
		this.location = location;
		this.orientation = orientation;
		this.number = number;
	} 
	
	/**
     * Puts a wall on the board.
     * 
     * @param location    The location where the wall is to be placed.
     * @param orientation The orientation of the wall ('H' for horizontal, 'V' for vertical).
     * @param walls       The current walls on the board.
     * @param board       The current game board.
     * @param type        The type of the wall.
     * @param name        The name associated with the wall.
     * @return The updated game board with the wall placed.
     * @throws QuoridorException If the wall cannot be placed at the specified location.
     */
	public abstract String[][] putWall(int[] location, char orientation, HashMap<String, Wall> walls, String[][] board, String type, String name) throws QuoridorException;
			    	
	
	
	/**
     * Deletes the wall from the board.
     * 
     * @param board The current game board.
     * @param walls The current walls on the board.
     * @return The updated game board with the wall removed.
     */
	public abstract String[][] delete(String[][] board,HashMap<String, Wall> walls);
	
	
	/**
     * Gets the identification of the wall.
     * 
     * @return The identification of the wall.
     */
	public String getIdentification()	{
		return identification;
	}
	
	
	
	
	/**
     * Gets the location of the wall.
     * 
     * @return The location of the wall.
     */
	public int[] getLocation()	{
		
		return location;
	}

	
	/**
     * Gets the orientation of the wall.
     * 
     * @return The orientation of the wall.
     */
	public char getOrientation() {
		return orientation;
	}

	
	
}
