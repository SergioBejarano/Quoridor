package domain;

import java.util.HashMap;

public class Temporary extends Wall{
	
	int born;
	
	/**
	 * Creates a temporary wall.
	 * 
	 * @param player
	 * @param location
	 * @param orientation
	 */
	public Temporary(int number,String player, int[] location, char orientation)	{
		
		super(number,player, location, orientation);
		this.identification = orientation+String.valueOf(number)+"Temporary"+"."+player;
	}
	 
	
	/**
	 * @return the born
	 */
	public int getBorn() {
		return born;
	}


	/**
	 * @param born the born to set
	 */
	public void setBorn() {
		Quoridor quoripoob = Quoridor.getQuoridor();
		this.born = quoripoob.getShift();
	}


	/**
	 * Delete this wall.
	 * 
	 * @param board
	 * @param walls
	 * @return
	 */
	public  String[][] delete(String[][] board,HashMap<String, Wall> walls)	{
		Quoridor quoripoob = Quoridor.getQuoridor();
		String[][] b = board;
		int shifts = quoripoob.getShift();
		if (Math.abs(shifts-born) == 4)	{
			walls.remove(identification);
			
			b = withoutWall(board.length, board);
		}
		
		
		return b;
	
	}
	
	
	/**
	 * Modify the board to remove this wall.
	 * 
	 * @param n
	 * @param board
	 * @param number
	 * @return
	 */
	private String[][] withoutWall(int n, String[][] board) {
	    for(int i = 0; i < n; i++) {
	        for(int j = 0; j < n; j++) {
	            String place = board[i][j];
	            if (place.contains(String.valueOf(number) + "Temporary")) {
	                String[] parts = place.split("\\|");
	                for (int k = 0; k < parts.length; k++) {
	                    if (parts[k].contains(String.valueOf(number) + "Temporary")) {
	                        parts[k] = ""; 
	                    }
	                }
	                StringBuilder rebuiltPlace = new StringBuilder();
	                for (String part : parts) {
	                    if (!part.isEmpty()) {
	                        if (rebuiltPlace.length() > 0) {
	                            rebuiltPlace.append("|");
	                        }
	                        rebuiltPlace.append(part);
	                    }
	                }
	                board[i][j] = rebuiltPlace.toString();
	            }
	        }
	    }
	    return board;
	}
	
	
	/**
	 * Put wall on board.
	 * 
	 * @param location
	 * @param orientation
	 * @param board
	 */
	public String[][] putWall(int[] l, char o, HashMap<String, Wall> walls, String[][] board, String type, String name) throws QuoridorException {
		
		if (!validateLocation(l, o, board))	throw new QuoridorException(QuoridorException.NOT_PUT_WALL);   	
		
		walls.put(identification, this);  
		String[][] b = setWallBorders(board, l[0], l[1], o, type, name);
		return b;
	}
	
	
	/**
	 * Locate the wall according to the squares it covers.
	 * 
	 * @param row
	 * @param col
	 * @param o
	 * @param type
	 * @param playerName
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
	 * Verify that there is no barrier at the given location according to the pawn's direction of movement.
	 * 
	 * @param l
	 * @param orientation
	 * @param board
	 * @return
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
