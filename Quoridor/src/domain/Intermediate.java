package domain;

import java.awt.Color;

import java.util.*;

public class Intermediate extends Machine{

	
	/**
	 * Constructor for the Intermediate machine class.
	 *
	 * @param name The name of the player.
	 * @param color The color of the player.
	 * @param limitWalls A HashMap containing the limit of walls for the player.
	 * @param location An array representing the initial location of the player on the board.
	 */
	public Intermediate(String name,String color, HashMap<String, Integer> limitWalls, int[] location)	{
		
		super(name,color, limitWalls, location);
		
	}
	
	
	/**
	 * Determines the next action for the advanced player based on the current state of the board.
	 * The player will either move their piece or place a wall depending on the distances to the goal.
	 *
	 * @param board The current state of the game board.
	 */
	public void act(String[][] board)	{
		HashMap<String, Player> players = Quoridor.getQuoridor().getPLayers();
		String user = name;
		for (String key : players.keySet())	{
			if(key != name)	{
				user = key;
			}
		}
		int machineDistance = calculateDistanceToGoal('m', peon.getLocation(), board.length);
        int userDistance = calculateDistanceToGoal('p', players.get(user).getLocation(), board.length); 

        if (machineDistance < userDistance) {
            movePeon(board);
        } else {
            putWall(board);
        }
		
	}
	
	/**
	 * Move the peon.
	 * 
	 * @param board
	 * @return
	 */
	public void movePeon(String[][] board)	{
		Quoridor quoripoob = Quoridor.getQuoridor();		
		try	{
			int[] newLocation = generatePosition(peon.getLocation(), board);
			HashMap<String, Square> squares = quoripoob.getSquares();
			HashMap<String, Player> players = quoripoob.getPLayers();
			peon.move(name, newLocation, board, walls, squares, players);
			String[][] board2 = removeName(name,getLocation(), board);
			board2[newLocation[0]][newLocation[1]] += "|"+name;
			quoripoob.updateBoard(board2);
		} catch(QuoridorException e)	{
			movePeon2(board);
		}
	}
	
	
	
	
	/**
	 * Move the peon.
	 * 
	 * @param board
	 * @return
	 */
	public void movePeon2(String[][] board)	{
		Quoridor quoripoob = Quoridor.getQuoridor();		
		try	{
			int[] newLocation = generatePosition(peon.getLocation(),board.length);
			HashMap<String, Square> squares = quoripoob.getSquares();
			HashMap<String, Player> players = quoripoob.getPLayers();
			peon.move(name, newLocation, board, walls, squares, players);
			String[][] board2 = removeName(name,getLocation(), board);
			board2[newLocation[0]][newLocation[1]] += "|"+name;
			quoripoob.updateBoard(board);
		} catch(QuoridorException e)	{
			movePeon2(board);
		}
	}
	
	
	/**
	 * Generete the new position of the peon.
	 * 
	 * @param pos
	 * @param n
	 * @return new position
	 */
	private int[] generatePosition(int[] pos, int n) {
	    Random rand = new Random();
	    int[][] directions = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
	    int[] direction = directions[rand.nextInt(directions.length)];

	    int[] newPos = {pos[0] + direction[0], pos[1] + direction[1]};
	    
	    while (!isValidPosition(newPos, n)) {
	        direction = directions[rand.nextInt(directions.length)];
	        newPos = new int[]{pos[0] + direction[0], pos[1] + direction[1]};
	    }
	    
	    return newPos;
	}
	
	
	/**
	 * Randomly place a wall.
	 * 
	 * @param board
	 * @return
	 */
	public void putWall(String[][] board)	{
		Quoridor quoripoob = Quoridor.getQuoridor();
		int n = board.length;	
		Random rand = new Random();
        int n2 = rand.nextInt(n*(n-7) - 12) + 13;
		int[] newPosition = generatePosition(new int[]	{n2/n,n2%n}, board);
		String type = generateType();
		try	{
			char direction = generateDirection();
			board = putWall(newPosition,direction, type, board);
		} catch(QuoridorException e)	{
			movePeon(board);
		}
		quoripoob.updateBoard(board);
	}
	
	
	/**
	 * Generate the new position for barrier.
	 * 
	 * @param pos
	 * @param board
	 * @return
	 */
	private int[] generatePosition(int[] pos, String[][] board)	{

        int[] newPos = new int[]{pos[0]+1, pos[1]};
        
        String place = board[newPos[0]][newPos[1]];
        String[] list = place.split("\\|");
        for (String t : list) {
        	if (t.contains(".") && (t.contains("N") || t.contains("E") )){
        		newPos = new int[]{pos[0], pos[1]-1};
        	}
        }
        return newPos;
	}
	
	/**
	 * Generate direction for barrier.
	 * 
	 * @return
	 */
	private char generateDirection()	{
		char[] directions = new char[]{'N','E'};		
		Random rand = new Random();
		char type = directions[rand.nextInt(directions.length)];
        return type;
	}
	
	/**
	 * Check if the position is valid.
	 * 
	 * @param pos The position to check.
	 * @param n The size of the board.
	 * @return True if the position is valid, false otherwise.
	 */
	private boolean isValidPosition(int[] pos, int n) {
	    int row = pos[0];
	    int col = pos[1];
	    return row >= 0 && row < n && col >= 0 && col < n;
	}
	
	
	/**
	 * Deletes the specified name from the given location on the board.
	 * 
	 * @param name The name to remove.
	 * @param lo The location on the board.
	 * @return The string updated after removing the name.
	 */
	private String[][] removeName(String name, int[] lo, String[][] board) {
	    String[] list = board[lo[0]][lo[1]].split("\\|");
	    StringBuilder updatedBoard = new StringBuilder();
	    for (String a : list) {
	        if (!a.equals(name)) {
	            updatedBoard.append(a).append("|");
	        }
	    }
	    if (updatedBoard.length() > 0) {
	        updatedBoard.deleteCharAt(updatedBoard.length() - 1);
	    }
	    board[lo[0]][lo[1]] = updatedBoard.toString();
	    return board;
	}
	
	/**
	 * Calculate the distance to goal
	 * 
	 * @param t
	 * @param location
	 * @param n
	 * @return
	 */
	private int calculateDistanceToGoal(char t,int[] location, int n)	{
		int pos = location[0];
		if (t == 'm') return Math.abs(pos-n-1);	
		return pos;
	}
	
	
	/**
	 * Generate a type of wall.
	 * 
	 * @param
	 * @return
	 */
	private String generateType()	{
		String[] types = new String[] {"Normal", "Temporary", "Ally"};
		Random rand = new Random();
		
		String type;
		do {
            type = types[rand.nextInt(types.length)];
        } while (getRemainingWallsByType(type) <= 0); 

        return type;
	}
	
}
