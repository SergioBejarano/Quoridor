package domain;

import java.util.*;

import java.awt.Color;
import java.io.Serializable;

public class Player implements Serializable{
	
	protected String name;

	private int movements;
	
	protected  HashMap<String, Integer> limitWalls;
	
	protected Peon peon;
	protected HashMap<String, Wall> walls;

	/**
	 * Creates a player with the specified name, color, limit of walls, and initial location.
	 * 
	 * @param name        The name of the player.
	 * @param color       The color associated with the player.
	 * @param limitWalls  A map containing the limit of walls for the player.
	 * @param location    The initial location of the player on the board.
	 */
	public Player(String name, String color, HashMap<String, Integer> limitWalls, int[] location)	{
		
		this.name = name;
		this.limitWalls = getLimitWallsName(limitWalls); 
	
		movements = 0;
		
		walls = new HashMap<>();
		peon = new Peon(color,location);
	} 
	
	/**
	 * Returns color.
	 * 
	 * @return color of pawn
	 */
	public String getColor()	{
		return peon.getColor();
	}
	
	
	/**
	 * Put wall on board.
	 * 
	 * @param location    The location where the wall is to be placed.
	 * @param orientation The orientation of the wall ('H' for horizontal, 'V' for vertical).
	 * @param type        The type of the wall.
	 * @param board       The current game board.
	 * @return The updated game board with the wall placed.
	 * @throws QuoridorException If the path is invalid, if there are no walls of the specified type remaining, 
	 *                           or if there is an error creating the wall.
	 */
	public String[][] putWall(int[] location, char orientation, String type, String[][] board) throws QuoridorException{
	    if (!validatePath(board)) throw new QuoridorException(QuoridorException.NOT_BLOCK);
	    if (!(getRemainingWallsByType(type)>0)) throw new QuoridorException(QuoridorException.NOT_WALL_AVAILABLE);	    
	    try {
	    	Class<?> wallClass = Class.forName("domain." + type);
	        int n = location[0]*board.length + location[1];
	        java.lang.reflect.Constructor<?> constructor = wallClass.getConstructor(int.class,String.class, int[].class, char.class);

	        Wall wall = (Wall) constructor.newInstance(n,getName(), location, orientation);
	        if (wall instanceof Temporary)	{
	        	((Temporary) wall).setBorn();
	        }
	        
	        return wall.putWall(location, orientation, walls, board, type, name); 
	        } catch (Exception e) {
	        	throw new QuoridorException(QuoridorException.NOT_CREATE_WALL);
	        } 
	} 
	
	
	
	/**
	 * Validate that the road is not fully obstructed.
	 * 
	 * @param board
	 * @return res
	 */
	private boolean validatePath(String[][] board)	{
		boolean res = true;		
		for (int i=0; i<board.length;i++)	{
			int count = 0;
			for (int j=0; j<board.length;j++)	{				
				if (board[i][j].contains(".") && board[i][j].contains("|N"))	{
					count++;
				}
			}
			if (count+2==board.length) res = false;
		}		
		return res;
		
	}
	
	
	/**
	 * Delete the temporary barrier.
	 * 
	 * @param board
	 * @return
	 */
	public String[][] deleteTemporary(String[][] board)	{
		for(Wall w : walls.values()){
			String key = w.getIdentification();
			if (key.contains("Temporary"))	{
				return w.delete(board, walls);
			}
		}
		return board;
	}
	
	/**
	 * Return limit walls for type.
	 * 
	 * @param limitWalls
	 * @return
	 */
	private HashMap<String, Integer> getLimitWallsName(HashMap<String, Integer> limit)	{
		HashMap<String, Integer> translatedLimitWalls = new HashMap<>();
	    for (Map.Entry<String, Integer> entry : limit.entrySet()) {
	        String originalKey = entry.getKey();
	        Integer value = entry.getValue();
	        String translatedKey = translateKey(originalKey);
	        translatedLimitWalls.put(translatedKey, value);
	    }
	    return translatedLimitWalls;		
	}

	/**
	 * Given name for barrier.
	 * 
	 * @param originalKey
	 * @return
	 */
	private String translateKey(String originalKey) {
	    switch (originalKey) {
	        case "Normal":
	            return "Normal";
	        case "Temporal":
	            return "Temporary";
	        case "Larga":
	            return "Long";
	        case "Aliada":
	            return "Ally";
	        default:
	            return "";
	    }
	}
	
	
	
	/**
	 * Return the number of movements.
	 * 
	 * @param
	 * @return movements
	 */
	public int getMoves()  {
		return movements;
	}
	
	/**
	 * Obtain the name of player.
	 * 
	 * @param
	 * @return name
	 */
	public String getName()  {
		return name; 
	}
 
	
	/**
	 * Obtain the location.
	 * 
	 * @param 
	 * @return location of peon
	 */
	public int[] getLocation()	{
		return peon.getLocation();
	}
	
	
	/**
	 * 
	 * @param 
	 * @return location of peon
	 */
	public String getStatePeon()	{
		return peon.getState();
	}
	
	
	/**
	 * Returns the remaining number of walls of a specific type for the player.
	 * 
	 * @param type The type of wall.
	 * @return The remaining number of walls of the specified type.
	 */
	public int getRemainingWallsByType(String type) {
		int n = 0;
		
	    for (String name : walls.keySet()) {
	    	if (name.contains(type)) {
	    		n++;
	    	}
	    }	
	    return limitWalls.get(type)-n;
	}

	/**
	 * @return the walls
	 */
	public HashMap<String, Wall> getWalls() {
		return walls;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public int getVisitedSquaresByType(String type) {
		
		return peon.getVisitedSquaresByType(type);
		
	}

	
	/**
	 * Change the state of current pawn.
	 * 
	 * @param state
	 */
	public void changeState(String state)	{
		peon.changeState(state);
	}
	
	
	
	
}
