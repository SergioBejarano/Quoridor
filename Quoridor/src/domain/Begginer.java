package domain;

import java.awt.Color;

import java.util.*;

public class Begginer extends Machine{

	/**
	 * Constructs a beginner-level machine player.
	 * 
	 * @param name        The name of the beginner-level machine player.
	 * @param color       The color associated with the beginner-level machine player.
	 * @param limitWalls  A map containing the limit of walls for the beginner-level machine player.
	 * @param location    The initial location of the beginner-level machine player on the board.
	 */
	public Begginer(String name,String color, HashMap<String, Integer> limitWalls, int[] location)	{
		
		super(name,color, limitWalls, location);
		
	}
	
	/**
	 * Choose the action: put wall or move pawn.
	 * 
	 * @param board
	 * @return
	 */
	public void act(String[][] board)	{
		chooseAction(board);
		
	}
	
	/**
	 * Choose action.
	 * 
	 * @param board
	 * @return
	 */
	private void chooseAction(String[][] board) {
        Random rand = new Random();
        boolean placeWall = rand.nextBoolean(); 

        if (placeWall) {
            putWall(board);
        } else {
            movePeon(board);
        }
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
        int n2 = rand.nextInt(n*n - 12) + 13;
		int[] newPosition = generatePosition(new int[]	{n2/n,n2%n}, board.length);
		String type = generateType(board);
		if (!type.equals("None"))	{
		try	{
			char direction = generateDirection();
			board = putWall(newPosition,direction, type, board);
		} catch(QuoridorException e)	{
			movePeon(board);
		}	}
		quoripoob.updateBoard(board);
	}
	
	
	/**
	 * Generate direction for barrier.
	 * 
	 * @return
	 */
	private char generateDirection()	{
		char[] directions = new char[]{'N','S','W','E'};		
		Random rand = new Random();
		char type = directions[rand.nextInt(directions.length)];
        return type;
	}
	
	
	
	/**
	 * Generate a type of wall.
	 * 
	 * @param board
	 * @return
	 */
	private String generateType(String[][] board) {
	    String[] types = new String[]{"Normal", "Temporary", "Ally"};
	    Random rand = new Random();

	    int maxIterations = 4; 
	    int iterationCount = 0;
	    boolean putWallNeeded = false;

	    String type;
	    do {
	        type = types[rand.nextInt(types.length)];
	        iterationCount++;
	        if (getRemainingWallsByType(type) <= 0 && iterationCount >= maxIterations) {
	            putWallNeeded = true; 
	        }
	    } while (getRemainingWallsByType(type) <= 0 && iterationCount < maxIterations);

	    
	    if (putWallNeeded) {
	    	movePeon(board);	        
	    }

	    return type;
	}

	
	
	/**
	 * Move the peon.
	 * 
	 * @param
	 * @return
	 */
	public void movePeon(String[][] board)	{
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
			movePeon(board);
		}
	}
	
	
	/**
	 * Generete the new position of the peon.
	 * 
	 * @param
	 * @return
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
}
