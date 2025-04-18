package domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Peon implements Serializable{
	
	private int[] location;
	private String color;
	private String state;
	private ArrayList<int[]> path;
	private HashMap<String, Integer> visitedSquares;
	
	
	/**
	 * Creates a pawn (Peon) with the specified color and initial location.
	 *
	 * @param color The color of the pawn.
	 * @param location The initial location of the pawn, represented as an array with two elements [row, column].
	 */
	public Peon(String color, int[] location)	{
		this.state = "Ordinary";
		this.location = new int[2];
		this.location = location;
		this.path = new ArrayList<>();
		path.add(location);
		this.color = color;
		this.visitedSquares = new HashMap<>();
		visitedSquares.put("Ordinary",0);
		visitedSquares.put("Teleporter",0);
		visitedSquares.put("Revert",0);
		visitedSquares.put("DoubleTurn",0);
	
	}
	
	
	/**
	 * Returns the color of the pawn.
	 *
	 * @return The color of the pawn as a String.
	 */
	public String getColor() {
	    return color;
	}

	/**
	 * Returns the path the pawn has taken.
	 *
	 * @return An ArrayList of int arrays, where each int array represents a position [row, column] the pawn has occupied.
	 */
	public ArrayList<int[]> getPath() {
	    return path;
	}


	/**
	 * Returns visited squares.
	 * 
	 * @return the visitedSquares
	 */
	public HashMap<String, Integer> getVisitedSquares() {
		return visitedSquares;
	}


	/**
	 * Moves the pawn to a new position on the board.
	 *
	 * @param player The player making the move.
	 * @param n The target position for the move, represented as an array with two elements [row, column].
	 * @param board The game board, represented as a 2D array of strings.
	 * @param walls A map of walls on the board, with the wall identifiers as keys and Wall objects as values.
	 * @param squares A map of squares on the board, with the square identifiers as keys and Square objects as values.
	 * @param players A map of players in the game, with player identifiers as keys and Player objects as values.
	 * @throws QuoridorException If the move is invalid or cannot be completed for any reason.
	 */
	public void move(String player, int[] n, String[][] board, HashMap<String,Wall> walls, HashMap<String,Square> squares,  HashMap<String, Player> players)	throws QuoridorException{
		boolean res = true;			
		for (Square s : squares.values()) {
			Class<?> objClass = s.getClass(); String simpleName = objClass.getSimpleName();			
			if ( !simpleName.equals("Teleporter") &&  Arrays.equals(n,s.getLocation()))	{	
				s.act();  this.state = simpleName; }			
			if ( simpleName.equals("Teleporter") &&  Arrays.equals(location,s.getLocation()))	{	
				s.act();  this.state = simpleName; }			
		}		
		if (!state.equals("Teleporter"))	{			
			res =  validateMove(player,n, board, walls); }	
		if (!res)	{
			boolean passOver = pawnOverPawn(player,players,walls,board,n);
			if (passOver) { res = true; }	}
		boolean res2 = true;
		if (n[0]==location[0]-1 && n[1]==location[1]-1)	{
			res2 = validateDiagonal(board); res = true; }			
		if (res && res2) {
			registerSquareVisited(n,board,squares);		
			if (!state.equals("Revert")) {
			location = n; path.add(n);
			} else	{	
				if (path.size() - 2 >= 0)	{
				int[] newP = path.get(path.size() - 2); location = newP; path.add(newP); }				
			}
		} else 	{
			throw new QuoridorException(QuoridorException.NOT_MOVE_PEON); }		
		if (state.equals("DoubleTurn") && players.get(player) instanceof Machine)	{
			Machine m = (Machine)players.get(player);
			m.act(board);
		}
	}
	
	
	
	
	/**
	 * Validate next position
	 * 
	 * @param n
	 * @return location over a pawn
	 */
	private boolean pawnOverPawn(String player,HashMap<String, Player> players, HashMap<String,Wall> walls, String[][] board, int[] nextPos) {
	    int dx = nextPos[0] - location[0];
	    int dy = nextPos[1] - location[1];
	    int[] newPos = new int[]{-1, -1};
	    boolean res2 = false;
	    if (dx == 0 && dy == 2) {
	        newPos = new int[]{nextPos[0], nextPos[1] - 1};
	        res2 = validateMove(player,newPos, board, walls);
	    } else if (dx == 0 && dy == -2) {
	        newPos = new int[]{nextPos[0], nextPos[1] + 1};
	        res2 = validateMove(player,newPos, board, walls);
	    } else if (dx == 2 && dy == 0) {
	        newPos = new int[]{nextPos[0] - 1, nextPos[1]};
	        res2 = validateMove(player,newPos, board, walls);
	    } else if (dx == -2 && dy == 0) {
	        newPos = new int[]{nextPos[0] + 1, nextPos[1]};
	        res2 = validateMove(player,newPos, board, walls);
	    }
	    if (newPos[0] < 0 || newPos[0] >= board.length || newPos[1] < 0 || newPos[1] >= board[0].length) {
	        return false;
	    }
	    boolean res = false;
	    for (String p : players.keySet()) {
	        if (board[newPos[0]][newPos[1]].contains("|" + p)) {
	            res = true;
	        }
	    }
	    return res && res2;
	}


	
	/**
	 * Change the state of this pawn.
	 * 
	 * @param state
	 */
	public void changeState(String state){
		this.state = state;
	}
	
	/**
	 * Obtain the state of this pawn.
	 * 
	 * @param 
	 * @return
	 */
	public String  getState(){
		return state;
	}
	
	
	/**
	 * 
	 * @param n
	 * @param board
	 * @param squares
	 */
	private void registerSquareVisited(int[] n, String[][] board, HashMap<String,Square> squares)	{
		
		String place = board[n[0]][n[1]].split("\\|")[0];
		String type;
		char x = place.charAt(1); 
		type = place.substring(1);
        if (Character.isDigit(x)) {
        	type = place.substring(2);
        } 
        if (visitedSquares.containsKey(type)) {
            int count = visitedSquares.get(type);
            visitedSquares.put(type, count + 1);
        }
	
		
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	public int getVisitedSquaresByType(String type) {
		
		return visitedSquares.get(type);
		
	}
	
	
	/**
	 * 
	 * @param direction
	 * @param board
	 * @return
	 */
	private boolean  validateMove(String player,int[] n, String[][] board, HashMap<String,Wall> walls)	{
		char direction = validateDirection(n);
		
		if (direction == 'N')	{
			int[] p = new int[]	{location[0]-1,location[1]}; 
			return !thereIsBarrier(player,'S', board[p[0]][p[1]], walls);

			
		} else if (direction == 'S')	{
			int[] p = new int[]	{location[0]+1,location[1]};
			
			return !thereIsBarrier(player,'N',board[p[0]][p[1]], walls);

			
		} else if (direction == 'W')	{
			int[] p = new int[]	{location[0],location[1]-1};
			return !thereIsBarrier(player,'E',board[p[0]][p[1]], walls);

			
		} else if (direction  == 'E')	{
			int[] p = new int[]	{location[0],location[1]+1};
			return !thereIsBarrier(player,'W',board[p[0]][p[1]], walls);
			
		}
		return false;
	}
	
	/**
	 * Validate the direction of this pawn.
	 * 
	 * @param n
	 * @return
	 */
	private char validateDirection(int[] n)	{
		char res = 'C';
		if (n[0]==location[0]-1 && n[1] == location[1])	{
			res = 'N';
		} else if (n[0]==location[0]+1 && n[1] == location[1])	{
			res = 'S';
		} else if (n[0]==location[0] && n[1] == location[1]+1)	{
			res = 'E';
		} else if (n[0]==location[0] && n[1] == location[1]-1)	{
			res = 'W';
		}
		return res;
		
	}
	
	
	/**
	 * Validate if there is barrier in the next position of this pawn.
	 * 
	 * @param item
	 * @param board
	 * @param walls
	 * @return
	 */
	private boolean thereIsBarrier(String player,char item, String place, HashMap<String,Wall> walls) {
		boolean res = false;
	    String[] list = place.split("\\|");
	    for (String w : list) {
	        if (w.contains(".")) {
	            char item2 = w.charAt(0);
	            
	            if (item == item2) {
	            	boolean res2 = validateAlly(player, w, walls);	            	
	                if (res2) res = true;
	                break; 
	            }
	        }
	    }
	    return res;
	}

	
	
	/**
	 * Validate of there is ally barrier of current player.
	 * 
	 * @param player
	 * @param w
	 * @param walls
	 * @return
	 */
	private boolean validateAlly(String player, String w, HashMap<String,Wall> walls)	{
		boolean res = true;
		if(w.contains("Ally") && w.contains(player)) {			
			res = false;
		}
		return res;
	}
	
	

	/**
	 * @return the location
	 */
	public int[] getLocation() {
		return location;
	}

	/**
	 * Validate the next position (diagonal movement)
	 * 
	 * @param board
	 * @return
	 */
	private boolean validateDiagonal(String[][] board)	{
		boolean res = true;		
		if (!(board[location[0]][location[1]].contains("|W") && board[location[0]][location[1]].contains("|N")))	{
			res = false;
		}
		return res;
		
	}
	
	/**
	 * @param location the location to set
	 */
	public void setLocation(int[] location) {
		this.location = location;
	}
	
	
	
}
