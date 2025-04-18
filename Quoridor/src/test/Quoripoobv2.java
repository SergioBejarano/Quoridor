package test;

import domain.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Quoripoobv2 {
	
	
	@Test
	public void shouldActDoubleTurn()	{
		try {
	        int size1 = 9;	        
	        String p1 = "cdvfb1";
	        String color1 = "Red";
	        String p2 = "Player2";
	        String color2 = "Blue";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Larga", 4); walls.put("Temporal", 5);  
                walls.put("Normal", 1); walls.put("Aliada", 0);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 0);
	        squares.put("Regresar", 0);	squares.put("Turno Doble", 80); 
	        Quoridor.setNull();
	        Quoridor quoridor = Quoridor.getQuoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,0);	        
	        quoridor.movePeon(new int[]{7,4});
	        quoridor.movePeon(new int[]{6,4});        
	    } catch (Exception e) {
	    	fail("Threw a exception");	    		    	
	    }
		
	}
	
	@Test
	public void shouldActRevert()	{
		try {
	        int size1 = 9;	        
	        String p1 = "cdvfb1";
	        String color1 = "Red";
	        String p2 = "Player2";
	        String color2 = "Blue";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Larga", 3); walls.put("Temporal", 5);  
            walls.put("Normal", 2); walls.put("Aliada", 0);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 0);
	        squares.put("Regresar", 80);	squares.put("Turno Doble", 0); 
	        Quoridor quoridor = Quoridor.getQuoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,0);	        
	        quoridor.movePeon(new int[]{7,4});
	        quoridor.movePeon(new int[]{1,4});
	        quoridor.movePeon(new int[]{6,4});
	        quoridor.putWall(new int[]{3,3}, 'N', "Temporal"); 
	        quoridor.movePeon(new int[]{8,5});
	    } catch (Exception e) {
	    	fail("Threw a exception");	    		    	
	    }
		
	}
	
	@Test
    public void shouldBegginerActAfterPlayerPerson(){
    	try {
	        int size1 = 9;	        
	        String p1 = "Player1";
	        String color1 = "Red";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Long", 4); walls.put("Temporary", 4);  
                walls.put("Normal", 1); walls.put("Ally", 1);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 0);
	        squares.put("Regresar", 0);	squares.put("Turno Doble", 0); 
	        Quoridor quoridor = Quoridor.getQuoridor(size1, p1, color1,"Begginer", difficulty, walls, squares,0);	        
	        quoridor.movePeon(new int[]{7,4});
	 	    } catch (Exception e) {
	    	fail("Threw a exception"); 	  
	    }	
    }
	
	

	@Test
    public void shouldNotPutWallIfNotAvailableWalls(){
    	try	{
        	int size = 9;
            String p1 = "Player1"; String color1 = "Red";                        
            String difficulty = "Untimed";
            HashMap<String, Integer> walls = new HashMap<>();
            walls.put("Larga", 4); walls.put("Temporal", 4);  
            walls.put("Normal", 1); walls.put("Aliada", 1);
            HashMap<String, Integer> squares = new HashMap<>();
            squares.put("Teletransportador", 1);
            squares.put("Regresar", 1);
            squares.put("Turno Doble", 1); 
            Quoridor quoridor = Quoridor.getQuoridor(size, p1, color1, "Intermediate", difficulty, walls, squares,0);
            quoridor.putWall(new int[]{4,0}, 'N', "Normal");
            quoridor.putWall(new int[]{4,2}, 'N', "Normal");  
            fail("Did not throw exception");
            
        }catch(QuoridorException e){
        	assertEquals(QuoridorException.NOT_WALL_AVAILABLE, e.getMessage());
        }
    }
	
	@Test
	public void shouldCreateBoardsWithMachines()	{
	try {
        int size1 = 9;
        int size2 = 11;	        
        String p1 = "dfvn1";
        String color1 = "Red";
        String difficulty = "Untimed";
        HashMap<String, Integer> walls1 = new HashMap<>(); 
        HashMap<String, Integer> walls2 = new HashMap<>();  
        walls1.put("Temporal", 10); walls2.put("Temporal", 12);
        HashMap<String, Integer> squares = new HashMap<>();
        squares.put("Teletransportador", 2);
        squares.put("Regresar", 2);
        squares.put("Turno Doble", 2);	  
        Quoridor quoridor1 = Quoridor.getQuoridor(size1, p1, color1,"Begginer", difficulty, walls1, squares,0);
        Quoridor quoridor2 = Quoridor.getQuoridor(size2, p1, color1, "Intermediate", difficulty, walls2, squares,0);	        
        assertEquals(size1, quoridor1.getBoard().length);
        assertEquals(size1, quoridor1.getBoard()[0].length);	               
        assertEquals(size1, quoridor2.getBoard()[0].length);
        
    } catch (Exception e) {
    	fail("Threw a exception");
    }
	}
	
	@Test
	public void shouldActTeleporter()	{
		try {
	        int size1 = 9;	        
	        String p1 = "cdvfb1";
	        String color1 = "Red";
	        String p2 = "Player2";
	        String color2 = "Blue";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Larga", 4); walls.put("Temporal", 5);  
                walls.put("Normal", 1); walls.put("Aliada", 0);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 81);
	        Quoridor quoridor = Quoridor.getQuoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,0);	        
	        quoridor.movePeon(new int[]{7,4});
	        quoridor.movePeon(new int[]{1,4});
	        quoridor.putWall(new int[]{6,3}, 'S', "Normal"); 
	        quoridor.putWall(new int[]{7,5}, 'W', "Normal");
	        quoridor.movePeon(new int[]{2,4});
	        quoridor.movePeon(new int[]{7,5});
	    } catch (Exception e) {
	    	fail("Threw a exception");	    		    	
	    }
		
	}
	
	@Test
	public void shouldMovePawnInTimeTrialMode()	{
		try {
	        int size1 = 9;	        
	        String p1 = "cdvfb1";
	        String color1 = "Red";
	        String p2 = "Player2";
	        String color2 = "Blue";
	        String difficulty = "Timetrial";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Larga", 4); walls.put("Temporal", 5);  
                walls.put("Normal", 1); walls.put("Aliada", 0);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 2); 
	        squares.put("Regresar", 3);	squares.put("Turno Doble", 1);        
	        Quoridor quoridor = new Quoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,2);
	        Thread.sleep(1000);
	        quoridor.movePeon(new int[]{7,4});
	        quoridor.putWall(new int[]{7,6}, 'W', "Normal");  
	    } catch (Exception e) {
	    	fail("Threw a exception");	    	
	    }
		
	}
	
	@Test
	public void shouldNotMovePawnsInTimedMode()	{
		try {
	        int size1 = 9;	        
	        String p1 = "cdvfb1";
	        String color1 = "Red";
	        String p2 = "Player2";
	        String color2 = "Blue";
	        String difficulty = "Timetrial";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Larga", 4); walls.put("Temporal", 5);  
                walls.put("Normal", 1); walls.put("Aliada", 0);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 2); 
	        squares.put("Regresar", 3);	squares.put("Turno Doble", 1);        
	        Quoridor quoridor = new Quoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,1);
	        Thread.sleep(2000);
	        quoridor.movePeon(new int[]{7,4});
	        quoridor.putWall(new int[]{7,6}, 'W', "Normal");  
	        fail("Did not throw exception");
	    } catch (Exception e) {
	    	assertEquals(QuoridorException.EXCEEDED_TIME, e.getMessage());    	
	    }	
	}
	
	@Test
    public void shouldAdvancedActAfterPlayerPersonTimeTrialMode(){
    	try {
	        int size1 = 9;	        
	        String p1 = "Player1";
	        String color1 = "Red";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Long", 4); walls.put("Temporary", 4);  
                walls.put("Normal", 1); walls.put("Ally", 1);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 0);
	        squares.put("Regresar", 0);	squares.put("Turno Doble", 0); 
	        Quoridor quoridor = Quoridor.getQuoridor(size1, p1, color1,"Advanced", difficulty, walls, squares,10);	        
	        quoridor.movePeon(new int[]{7,4});
	 	    } catch (Exception e) {
	    	fail("Threw a exception"); 	  
	    }	
    }

}
