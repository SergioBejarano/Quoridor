package domain;

import java.util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.io.*;
import java.awt.Color;

import java.lang.reflect.*;

/**
 * It represents a Quoridor with players, walls and squares.
 * 
 * @authors: Sergio Bejarano and Manuel Robayo
 * @version 1.0
 */
public class Quoridor implements Serializable{
	
	
	
	private int size;
	private Difficulty difficulty;
	protected String[][] board;
	private HashMap<String, Player> players;
	private HashMap<String, Square> squares;
	private String currentPlayerName;
	private String first;
	private HashMap<String,Integer> win;
	private Time time;
	private int shifts;
	private boolean state;
	
	private static Quoridor quoripoob;
    

	
    /**
     * Factory method to get the Quoridor singleton object.
     * 
     * @param
     * @return
     */
    public static Quoridor getQuoridor(int size, String p1, String color1, String color2,String p2, String difficulty,
			HashMap<String, Integer> walls, HashMap<String, Integer> squares, int time) throws QuoridorException{
        if(quoripoob == null) {
        	quoripoob = new Quoridor(size, p1,  color1, color2, p2,  difficulty, walls,  squares, time);
        }
        return quoripoob;
    }
    
    /**
     * Factory method to get the Quoridor singleton object.
     * 
     * @param
     * @return
     */
    public static Quoridor getQuoridor(int size, String p1, String color1, String profile, String difficulty,
			HashMap<String, Integer> walls, HashMap<String, Integer> squares, int time) throws QuoridorException{
        if(quoripoob == null) {
        	quoripoob = new Quoridor(size, p1,  color1, profile,  difficulty, walls,  squares, time);
        }
        return quoripoob;
    }
    
    
    /**
     * Get the quoridor.
     * 
     * @param
     * @return quoripoob 
     * 
     */
    public static Quoridor getQuoridor() {
    	return quoripoob;
    }


	/**
	 * @return the squares
	 */
	public HashMap<String, Square> getSquares() {
		return squares;
	}




	/**
	 * Constructor for Quoridor in Player vs. Player mode
	 * 
	 * @param size       The size of the game board.
	 * @param p1         Name of player 1.
	 * @param color1     Color of player 1.
	 * @param color2     Color of player 2.
	 * @param p2         Name of player 2.
	 * @param difficulty The difficulty level of the game.
	 * @param walls      The walls configuration.
	 * @param squares    The squares configuration.
	 * @param time       The time limit for the game.
	 * @throws QuoridorException If the size of the board is invalid or the number of walls is incorrect.
	 */
	public Quoridor(int size, String p1, String color1, String color2,String p2, String difficulty,
					HashMap<String, Integer> walls, HashMap<String, Integer> squares, int time) throws QuoridorException	{		
		if (size<7 || size > 19) throw new QuoridorException(QuoridorException.NOT_SIZE);	
		this.size = size; this.first = p1; 
		try {
			Class<?> clazz = Class.forName("domain."+difficulty);
			Constructor<?> constructor = clazz.getConstructor(String.class, String.class, int.class);
			Difficulty d = (Difficulty) constructor.newInstance(p1,p2,time);
			this.difficulty = d;
		} catch (Exception e) {
			Log.record(e);
		}
		this.size = size;
		board = new String[size][size];
		boolean res = sumWalls(walls);
		if (!res) throw new QuoridorException(QuoridorException.NOT_NUMBER_WALLS);
        this.currentPlayerName = p1;
		Person player1 = new Person(p1, color1,walls, new int[]{size-1,size/2});
		Person player2 = new Person(p2, color2, walls, new int[]{0,size/2});
		players = new HashMap<>();
		players.put(p1, player1);
		players.put(p2, player2);
		addSquares(size, squares);
		board[size-1][size/2] += "|"+p1;
		board[0][size/2] += "|"+p2;		
		this.win = new HashMap<>();
		win.put(p1,0);
		win.put(p2,size-1); this.state = true;
		this.time = new Time(); this.time.startTimer();
	}
	
	
	
	/**
	 * Sums the values of walls stored in a HashMap and checks if the total count matches the expected size.
	 * 
	 * @param walls A HashMap where keys represent wall types and values represent their counts.
	 * @return True if the sum of wall counts matches the expected size (size + 1), otherwise false.
	 */
	private boolean sumWalls(HashMap<String, Integer> walls)	{
		boolean res = true;
		int total = 0;
		for(Integer n : walls.values()){
			total += n;
		}
		if (!(size+1 == total))	{
			res = false; 
		}
		return res;
	}
	
	/**
	 * Constructor for Quoridor in Player vs. Machine mode
	 *
	 * @param size       The size of the game board.
	 * @param p          Name of the player.
	 * @param color      Color of the player.
	 * @param profile    Profile of the machine player.
	 * @param difficulty The difficulty level of the game.
	 * @param walls      The walls configuration.
	 * @param squares    The squares configuration.
	 * @param time       The time limit for the game.
	 * @throws QuoridorException If the size of the board is invalid or the number of walls is incorrect.
	 */
	public Quoridor(int size,String p, String color, String profile, String difficulty, 
			        HashMap<String, Integer> walls, HashMap<String, Integer> squares, int time)	throws QuoridorException{
		shifts = 1; if (size<7 || size > 19) throw new QuoridorException(QuoridorException.NOT_SIZE);	
		this.size = size; this.first = p;
		players = new HashMap<>();	
		board = new String[size][size];	 boolean res = sumWalls(walls);			
		if (!res) throw new QuoridorException(QuoridorException.NOT_NUMBER_WALLS);
        Person player = new Person(p,color, walls, new int[]{size-1,size/2}); this.win = new HashMap<>();
        players.put(p, player);
        addSquares(size, squares);
		try {   
            Class<?> machineClass = Class.forName("domain."+profile);
            java.lang.reflect.Constructor<?> constructor = machineClass.getConstructor(String.class, String.class, HashMap.class, int[].class);           
            String m = generateRandomName();
            Machine machine = (Machine) constructor.newInstance(m,"Verde", walls, new int[]{0,size/2});            
            board[0][size/2]  += "|"+machine.getName();
		    players.put(machine.getName(), machine); win.put(machine.getName(),size-1);   		
            try {
    			Class<?> clazz = Class.forName("domain."+difficulty);
    			Constructor<?> c = clazz.getConstructor(String.class, String.class, int.class);
    			Difficulty d = (Difficulty) c.newInstance(p,machine.getName(),time); this.difficulty = d;   			
    		} catch (Exception ep) {
    			Log.record(ep);
    		}          
        } catch (Exception e)  {
        	Log.record(e);
        }
		
		win.put(p,0);	 this.currentPlayerName = p;	
		board[size-1][size/2] += "|"+p; this.state = true;		
		this.time = new Time(); this.time.startTimer(); 
	}
	 
	
	/**
	 * Places a wall on the game board.
	 * 
	 * @param l           the coordinates [row, column] where the wall will be placed
	 * @param o           the orientation of the wall ('H' for horizontal, 'V' for vertical)
	 * @param type        the type of wall to be placed ("Normal", "Temporary", "Larga", "Aliada")
	 * @throws QuoridorException if the wall cannot be placed due to game rules or invalid input
	 */
	public void putWall(int[] l, char o, String type) throws QuoridorException {
		this.time.stopTimer();
		
		difficulty.act(currentPlayerName,this.time.getElapsedTime());
		boolean res = difficulty.getState(currentPlayerName);
		if (!res) 	{
			difficulty.setState(currentPlayerName);
			changeTurn(); 
			throw new QuoridorException(QuoridorException.EXCEEDED_TIME);
		
		}
	    String wallType = convertClassWall(type);
	    
	    Player p = (Player) players.get(currentPlayerName);
	    this.board = p.putWall(l, o, wallType, board);
	    
	    changeTurn(); 
	    				
	}

 
	/**
	 * Calculates the final coordinate of a wall based on its starting coordinate and direction.
	 * 
	 * @param n the starting coordinate of the wall
	 * @param d the direction of the wall ('N' for north, 'S' for south, 'W' for west, 'E' for east)
	 * @return the final coordinate of the wall in the specified direction, or -1 if the direction is invalid
	 */
	public int getPointFinalWall(int n,char d)	{
		if (d == 'N' || d == 'S')	{
			return n+1;
		} else if (d == 'W' || d == 'E')	{
			return n+size;
		} else 	{
			return -1;
		}
	}
	
	 
	/**
	 * Updates the player in turn
	 * 
	 * @param
	 * @return
	 */
	public void changeTurn() {
		Player p0 = players.get(currentPlayerName);
		this.board = p0.deleteTemporary(this.board);
	    Set<String> playerNames = players.keySet();
	    List<String> playerList = new ArrayList<>(playerNames);
	    int currentPlayerIndex = playerList.indexOf(currentPlayerName);
	    int nextPlayerIndex = (currentPlayerIndex + 1) % playerList.size();
	    String nextPlayerName = playerList.get(nextPlayerIndex);
	    currentPlayerName = nextPlayerName;
	    shifts++;	    
	    Player nextplayer = players.get(nextPlayerName);
	    this.time.startTimer(); 
	    if (nextplayer instanceof Machine)	{
	    	Machine m = (Machine) nextplayer; 
	    	m.act(this.board);
	    	changeTurnToPerson();	
	    }	
	}
	
	/**
	 * Set the state of game.
	 * 
	 * @param
	 * @return 
	 */
	public void setState()	{
		state = false;
	}
	
	/**
	 * Change turn to person.
	 * 
	 * @param
	 * @return
	 */
	private void changeTurnToPerson(){
    	Set<String> playerNames = players.keySet();
	    List<String> playerList = new ArrayList<>(playerNames);
	    int currentPlayerIndex = playerList.indexOf(currentPlayerName);
	    int nextPlayerIndex = (currentPlayerIndex + 1) % playerList.size();
	    String nextPlayerName = playerList.get(nextPlayerIndex);
	    currentPlayerName = nextPlayerName;
	}
	
	/**
	 * Obtain the state of game.
	 * 
	 * @param
	 * @return 
	 */
	public boolean getState()	{
		return state;
	}
	
	
	
	/**
	 * Moves a player's pawn to a new location on the board.
	 *
	 * @param n The new coordinates to which the pawn will be moved.
	 * @throws QuoridorException if the player exceeds the time limit for their turn.
	 */
	public void movePeon(int[] n) throws QuoridorException	{
		this.time.stopTimer();
		
		difficulty.act(currentPlayerName,this.time.getElapsedTime());
		boolean res = difficulty.getState(currentPlayerName);
		if (!res) 	{
			difficulty.setState(currentPlayerName);
			changeTurn();
			throw new QuoridorException(QuoridorException.EXCEEDED_TIME);	
		}
		Person p = (Person) players.get(currentPlayerName);		
		String name = p.getName(); int[] lo = Arrays.copyOf(p.getLocation(), p.getLocation().length);;
		p.movePeon(n, board, squares, players);
		removeName(name,lo);
		int[] l = p.getLocation();		
		board[l[0]][l[1]] += "|"+name;	
		boolean gameOver = won();
		if (gameOver) 	{
			this.state = false;
			return;
		}
		changeTurn(); 
	}
	
	/**
	 * Checks if the current player has won the game.
	 *
	 * @param
	 * @return true if the current player has reached the winning location, false otherwise.
	 */
	private boolean won() {
	    Player p = players.get(currentPlayerName);
	    int[] current = p.getLocation();
	    for (String name : players.keySet()){
	    	if (!name.equals(currentPlayerName) && players.get(name) instanceof Machine)	{
	    		Player p2 = (Player)players.get(name);
	    		int[] pos = p2.getLocation();
	    		if (pos[0] == size-1) 	{
	    			this.currentPlayerName = p2.getName();
	    			return true;
	    		}
	    	}
	    }
	    return win.get(currentPlayerName) == current[0];
	}
	
	
	
	/**
	 * Obtain state of pawn.
	 * 
	 * @param
	 * @return
	 */
	public String getStatePeon()	{
		Player p = (Player) players.get(currentPlayerName);
		return p.getStatePeon();
	}
	
	
	
	/**
	 * Retrieves the location of the pawn for the current player.
	 * 
	 * @param
	 * @return An array representing the location of the pawn, where index 0 
	 * corresponds to the row and index 1 corresponds to the column.
	 */
	public int[] getLocationPeon()	{
		Player p = (Player) players.get(currentPlayerName);
		return p.getLocation();
	}
	
	/**
	 * Retrieves the location of the pawn for the a player.
	 * 
	 * @param name Given player.
	 * @return An array representing the location of the pawn, where index 0 
	 * corresponds to the row and index 1 corresponds to the column.
	 */
	public int[] getLocationPeon(String name)	{
		Player p = (Player) players.get(name);
		return p.getLocation();
	}
	
	/**
	 * Retrieves the number of squares visited by the current player of a specific type.
	 * 
	 * @param type The type of squares to count visits for.
	 * @return The number of squares visited by the current player of the specified type.
	 */
	public int getVisitedSquaresByType(String type) {
		Player p = (Player) players.get(currentPlayerName);
		return p.getVisitedSquaresByType(type);
		
	}
	
	/**
	 * Update the board.
	 * 
	 * @param
	 * @return
	 */
	public void updateBoard(String[][] board) {
		
		this.board = board;
		
	}
	
	
	/**
	 * Deletes the specified name from the given location on the board.
	 * 
	 * @param name The name to remove.
	 * @param lo The location on the board.
	 * @return The string updated after removing the name.
	 */
	private void removeName(String name, int[] lo) {
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
	}
	
	
	/**
	 * Retrieves the number of moves made by the specified player.
	 * 
	 * @param player The name of the player.
	 * @return The number of moves made by the player, or -1 if the player is not found.
	 */
	public int getMovesPlayer()  {
		
		Player p = players.get(currentPlayerName);
		
		return p.getMoves();
	}
	
	
	
	/**
	 * Initializes and adds squares to the game board based on the specified size and square types.
	 * 
	 * @param size The size of the game board.
	 * @param squaresTypesN A map containing the types and quantities of squares to be added.
	 */
	private void addSquares(int size,HashMap<String, Integer> squaresTypesN) {
		this.squares = new HashMap<>();
		int n = getNumberSpecialSquares(squaresTypesN);
		squaresTypesN.put("Normal", this.size*this.size - n);
	    String[] numbers = getNumbersBoard(size);
	    Random random = new Random();
	    for (Map.Entry<String, Integer> entry : squaresTypesN.entrySet()) {
	        String s = entry.getKey();
	        String squareType = convertClassSquare(s);
	        int selectedNumber = entry.getValue();
	        numbers = addSquare(squareType, selectedNumber, numbers, size, random);
	      
	    }
	}
	
	
	/**
	 * Calculates the total number of special squares based on the provided square types and quantities.
	 * 
	 * @param squaresTypesN A map containing the types and quantities of special squares.
	 * @return The total number of special squares.
	 */
	private int getNumberSpecialSquares(HashMap<String, Integer> squaresTypesN)	{
		int suma = 0;
        for (int valor : squaresTypesN.values()) {
            suma += valor;
        }
        return suma;
		
	}
	
	
	
	/**
	 * Converts the Spanish name of a square class to its English equivalent.
	 * 
	 * @param s The Spanish name of the square class.
	 * @return The English name of the square class.
	 */
	private String convertClassSquare(String s)	{
		String englishClassName;
	    switch (s) {
	        case "Normal":
	            englishClassName = "Ordinary";
	            break;
	        case "Teletransportador":
	            englishClassName = "Teleporter";
	            break;
	        case "Regresar":
	            englishClassName = "Revert";
	            break;
	        case "Turno Doble":
	            englishClassName = "DoubleTurn";
	            break;
	        default:
	            englishClassName = "";
	            break;
	    }
	    return englishClassName;
		
	}
	
	
	
	/**
	 * Converts the Spanish name of a wall class to its English equivalent.
	 * 
	 * @param s The Spanish name of the wall class.
	 * @return The English name of the wall class.
	 */
	private String convertClassWall(String s)	{
		String englishClassName;
	    switch (s) {
	        case "Normal":
	            englishClassName = "Normal";
	            break;
	        case "Temporal":
	            englishClassName = "Temporary";
	            break;
	        case "Larga":
	            englishClassName = "Long";
	            break;
	        case "Aliada":
	            englishClassName = "Ally";
	            break;
	        default:
	            englishClassName = "";
	            break;
	    }
	    return englishClassName;
		
	}
	
	/**
	 * Adds squares of a specified type to the game board.
	 * 
	 * @param type           The type of square to add.
	 * @param selectedNumber The number of squares to add.
	 * @param numbers        An array containing available square positions.
	 * @param size           The size of the game board.
	 * @param random         A random number generator.
	 * @return An updated array of available square positions.
	 */
	private String[] addSquare(String type, int selectedNumber, String[] numbers, int size, Random random) {
	    for (int i = 0; i < selectedNumber; i++) {
	        int randomIndex = random.nextInt(numbers.length);
	        String number = numbers[randomIndex];
	        numbers = removeElement(numbers, randomIndex);

	        try {
	            Class<?> squareClass = Class.forName("domain." + type);
	            Constructor<?> constructor = squareClass.getConstructor(int[].class, String.class);
	            Object square = constructor.newInstance(indexToCoordinates(Integer.parseInt(number), size), number + type);
	            squares.put(((Square) square).getName(), (Square) square);	
	            int[] coordinates = ((Square) square).getLocation();
	            board[coordinates[0]][coordinates[1]] = ((Square) square).getName();
	        } catch (Exception e) {
	            Log.record(e);
	        }
	    }
	    return numbers;
	}


	
	/**
	 * Converts an index in the flat list to row and column coordinates.
	 * 
	 * @param index The index in the flat list.
	 * @param size  The size of the board (number of rows or columns).
	 * @return An array of integers with two elements, where the first element is the row and the second is the column.
	 */
	private int[] indexToCoordinates(int index, int size) {
	    int row = index / size;
	    int column = index % size;
	    return new int[]{row, column};
	}
	
	
	/**
	 * Converts row and column coordinates to an index in the flat list.
	 * 
	 * @param row    The row coordinate.
	 * @param column The column coordinate.
	 * @param size   The size of the board (number of rows or columns).
	 * @return The index in the flat list.
	 */
	private int coordinatesToIndex(int row, int column) {
	    return row * size + column;
	}

	
	/**
	 * Finds the indices of the players on the game board.
	 * 
	 * @param
	 * @return An array containing the indices of the players' positions.
	 */
	public int[] findPlayerIndixes() {
	    int[] playerIndices = new int[2]; 
	    String[] names = getPlayers();
	    for (int i = 0; i < size; i++) {
	        for (int j = 0; j < size; j++) {
	            if (board[i][j].contains(names[0])) {
	                playerIndices[0] = coordinatesToIndex(i, j); 
	            }
	            if (board[i][j].contains(names[1])) {
	                playerIndices[1] = coordinatesToIndex(i, j); 
	            }
	        }
	    }
	    return playerIndices;
	}
	
	

	
	/**
	 * Returns color.
	 * 
	 * @return color of pawn
	 */
	public String getColor(String name)	{
		Player p =  players.get(name);
		return p.getColor();
	}
	
	/**
	 * Return shifts.
	 * 
	 * @return shifts
	 */
	public int getShift()	{
		return shifts;
	}

	/**
	 * 
	 * @param array
	 * @param indexToRemove
	 * @return
	 */
	private String[] removeElement(String[] array, int indexToRemove) {
	    String[] newArray = new String[array.length - 1];
	    System.arraycopy(array, 0, newArray, 0, indexToRemove);
	    System.arraycopy(array, indexToRemove + 1, newArray, indexToRemove, array.length - indexToRemove - 1);
	    return newArray;
	}
	
	
	/**
	 * Generates an array of numbers representing the indices on a game board of given size.
	 * 
	 * @param size The size of the game board (number of rows or columns).
	 * @return An array of strings representing the indices on the game board.
	 */
	private String[] getNumbersBoard(int size)	{
		
		String[] numbers = new String[size*size];
		
		for (int i = 0; i<size*size; i++) {
			numbers[i] = String.valueOf(i);	
		}
		return numbers;
	}
	
	
	
	

	/**
	 * Set null the quoridor.
	 * 
	 * @param
	 * @return
	 */
	public static void setNull()	{
		quoripoob = null;
	}
    
	/**
	 * Generates a random name for a machine.
	 * 
	 * @return name
	 */
	private String generateRandomName() {
	    StringBuilder nameBuilder = new StringBuilder();

	    for (int i = 0; i < 5; i++) {
	        char letter = (char) ('A' + (int)(Math.random() * ('Z' - 'A' + 1)));
	        nameBuilder.append(letter);
	    }

	    for (int i = 0; i < 3; i++) {
	        int number = (int) (Math.random() * 10);
	        nameBuilder.append(number);
	    }

	    return nameBuilder.toString();
	}


	/**
	 * Returns size of the board.
	 * 
	 * @param
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns the board of this.
	 * @param
	 * @return the board
	 */
	public String[][] getBoard() {
		return board;
	}

	/**
	 * Return name of current player.
	 * 
	 * @return the currentPlayerName
	 */
	public String getCurrentPlayerName() {
		return currentPlayerName;
	}
	
	/**
	 * Get name of players.
	 * 
	 * @return
	 */
	public String[] getPlayers(){
		String[] res = new String[2]; int i = 0;
		res[0] = first;
		for(String key : players.keySet())	{
			if (!key.equals(first)) res[1] = key;
			i++;
		}
		return res;
	}
	
	/**
	 * Get players.
	 * 
	 * @param
	 * @return
	 */
	public HashMap<String, Player> getPLayers()	{
		return players;
	}
	
	/**
	 * Retrieves the number of remaining walls of the specified type for the current player.
	 * 
	 * @param type The type of wall to query (e.g., "Normal", "Temporary", "Long", "Ally").
	 * @return The number of remaining walls of the specified type for the current player.
	 */
	public int getRemainingWallsByType(String type) {
		
		Player p = players.get(currentPlayerName);
		
		return p.getRemainingWallsByType(type); 
		
	}

	
	/**
	 * Open a Quoridor from a file.
	 * 
	 * @param file The file from which to open the Quoridor.
	 * @return The Quoridor object loaded from the file.
	 * @throws QuoridorException If unable to open the file or deserialize the Quoridor object.
	 */
	public void open(File file) throws QuoridorException {
		try (FileInputStream fileIn = new FileInputStream(file);
			 ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {		
			Object obj = objectIn.readObject();			
			if (obj instanceof Quoridor) {
				quoripoob = (Quoridor) obj;
			} else {
				throw new QuoridorException(QuoridorException.NOT_GAME_QUORIDOR);
			}
		} catch (Exception e) {
			throw new QuoridorException(QuoridorException.NOT_OPEN_QUORIDOR +": "+file.getName());
		} 
	}
	


	
	/**
	 * Save the Quoridor to a file.
	 * 
	 * @param file The file to which the Quoridor will be saved.
	 * @throws QuoridorException If unable to save the Quoridor to the file.
	 */
	public void save(File file) throws QuoridorException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(quoripoob);
		} catch (Exception e) {
		
			throw new QuoridorException(QuoridorException.NOT_SAVE_QUORIDOR +": "+file.getName());
		}
	}
	
	
	/**
	 * Change the state of current pawn.
	 * 
	 * @param state
	 */
	public void changeState(String state)	{
		Player p = players.get(currentPlayerName);
		p.changeState(state);
	}
	
	/**
	 * Get time.
	 * 
	 * @return
	 */
	public String getTime()	{
		return time.getElapsedTimeInSeconds();
	}
	
	/**
	 * Get time in seconds for time trial.
	 * 
	 * @return
	 */
	public String getTimeInSeconds()	{
		if (difficulty instanceof Timed) 	{
			Timed t = (Timed) difficulty;
			return t.getPlayerTime(currentPlayerName);
		}
		return "";
	}
	
	
	/**
	 * If a pawn falls on a double turn square, the next turn is ceded to it.
	 * 
	 * @param
	 * @return
	 */
	public void stealingTurn()	{
		Player p0 = players.get(currentPlayerName);
		this.board = p0.deleteTemporary(this.board);
	    Set<String> playerNames = players.keySet();
	    List<String> playerList = new ArrayList<>(playerNames);
	    int currentPlayerIndex = playerList.indexOf(currentPlayerName);
	    int nextPlayerIndex = (currentPlayerIndex + 1) % playerList.size();
	    String nextPlayerName = playerList.get(nextPlayerIndex);
	    currentPlayerName = nextPlayerName;
	    shifts++;
		
	}
	
}




