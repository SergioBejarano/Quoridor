package domain;


import java.util.HashMap;

public class Normal extends Wall{
	
	
	/**
	 * Constructor to create an instance of the Normal class.
	 * 
	 * @param number the number associated with this instance
	 * @param player the player associated with this instance
	 * @param location the array of integers representing the location of this instance
	 * @param orientation the orientation of this instance
	 */
	public Normal(int number,String player, int[] location, char orientation)	{
		
		super(number,player, location, orientation);
		this.identification = orientation+String.valueOf(number)+"Normal"+"."+player;	
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
	public String[][] putWall(int[] l, char o, HashMap<String, Wall> walls, String[][] board, String type, String name) throws QuoridorException {
		
		if (!validateLocation(l, o, board))	throw new QuoridorException(QuoridorException.NOT_PUT_WALL);   	
		
		walls.put(identification, this);  
		String[][] b = setWallBorders(board, l[0], l[1], o, type, name);
		return b;
	}
	
	/**
	 * Delete this wall.
	 * 
	 * @param board
	 * @param walls
	 * @return
	 */
	public  String[][] delete(String[][] board,HashMap<String, Wall> walls)	{
		return board;
	}
	
	
	/**
	 * Sets the wall borders on the game board.
	 * 
	 * @param board the game board represented as a 2D array of strings
	 * @param row the row index where the wall is being placed
	 * @param col the column index where the wall is being placed
	 * @param o the orientation of the wall ('N', 'S', 'E', or 'W')
	 * @param type the type of wall (e.g., "Normal", "Special")
	 * @param playerName the name of the player who placed the wall
	 * @return the updated game board with the wall borders set
	 */
	private String[][] setWallBorders(String[][] board, int row, int col, char o, String type, String playerName) {
	    String wallPart = "|" + o + String.valueOf(row * board.length + col) + type + "." + playerName;
	    if (o == 'S' || o == 'N') {
	        board[row][col] += wallPart;
	        board[row][col + 1] += wallPart;

	        if (o == 'S') {
	        	wallPart = "|" + 'N' + String.valueOf(row * board.length + col) + type + "." + playerName;
	            board[row + 1][col] += wallPart;
	            board[row + 1][col + 1] += wallPart;
	        } else {
	        	wallPart = "|" + 'S' + String.valueOf(row * board.length + col) + type + "." + playerName;
	            board[row - 1][col] += wallPart;
	            board[row - 1][col + 1] += wallPart;
	        }
	    } else if (o == 'W' || o == 'E') {
	        board[row][col] += wallPart;
	        board[row + 1][col] += wallPart;
	        if (o == 'W') {
	        	wallPart = "|" + 'E' + String.valueOf(row * board.length + col) + type + "." + playerName;
	            board[row][col - 1] += wallPart;
	            board[row + 1][col - 1] += wallPart;
	        } else {
	        	wallPart = "|" + 'W' + String.valueOf(row * board.length + col) + type + "." + playerName;
	            board[row][col + 1] += wallPart;
	            board[row + 1][col + 1] += wallPart;
	        }
	    }
	    return board;
	}
	
	
	/**
	 * Validates if it is possible to place a wall at the given location.
	 *  
	 * @param location
	 * @param orientation
	 * @param board
	 * @return true if no problem, false if no problem, false otherwise
	 */
	private boolean validateLocation(int[] l, char orientation,String[][] board)	{
		boolean answer = false;
		boolean res = notWall(l,orientation, board);
		if(!res) return false;
		int row = 0; int column = 0;		
		if (orientation== 'N' || orientation== 'S') {
	        row = l[0]; column = l[1];
	        if (column + 1 < board.length && notWall(new int[]{l[0],l[1]+1},orientation, board)) {	        	
		        if (orientation == 'N') {
			        if (notWall(new int[]{l[0]-1,l[1]+1},'S', board) && notWall(new int[]{l[0]-1,l[1]},'S', board)) {	        	
			        	answer = true;   
			        }
			    } else if (orientation == 'S') 	{
			        if ( notWall(new int[]{l[0]+1,l[1]+1},'N', board) && notWall(new int[]{l[0]+1,l[1]},'N', board)) {
			        	answer = true;
			        }
			    }
	        }   
	    } else if (orientation=='W' || orientation== 'E') 	{
	        row = l[0]; column = l[1];	        
	        if (row + 1 < board.length && notWall(new int[]{l[0]+1,l[1]},orientation, board)) {
	        	if (orientation == 'W') 	{
		    		if (notWall(new int[]{l[0]+1,l[1]-1},'E', board) && notWall(new int[]{l[0],l[1]-1},'E', board)) {
			    		answer = true;
			    		}
			    } else if (orientation == 'E') 	{
			    	if ( notWall(new int[]{l[0]+1,l[1]+1},'W', board) && notWall(new int[]{l[0],l[1]+1},'W', board)) {
			    		answer = true;
			    	}
			    }
	        }
	    }
		return answer;
	}
	
	
	/**
	 * Checks if a given location on the game board is not occupied by a wall.
	 * 
	 * @param l the location on the game board represented as an array of integers [row, col]
	 * @param orientation the orientation of the player ('N', 'S', 'E', or 'W')
	 * @param board the game board represented as a 2D array of strings
	 * @return true if the location is not occupied by a wall, false otherwise
	 */
	private boolean notWall(int[] l, char orientation,String[][] board)	{
		
		String place = board[l[0]][l[1]];
		String[] list = place.split("\\|");		
		
		if (list.length > 1) {
			String a = list[1];
			if (orientation == a.charAt(0))	{
				return false;	
			}
		}
		return true;
	}
	

}
