package test;

import domain.*;


import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import java.util.HashMap;

public class Quoripoobv1 {
	
	/**
     * Default constructor for test class Qupripoobv1
     */
    public Quoripoobv1() {
    
    }
	
	@Test
    public void shouldCreateBoardsOfDifferentSizes(){
        try {
	        int size1 = 9;
	        int size2 = 11;	        
	        String p1 = "dfvn1";
	        String color1 = "Red";
	        String p2 = "Plvfbvnr2";
	        String color2 = "Blue";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls1 = new HashMap<>(); 
	        HashMap<String, Integer> walls2 = new HashMap<>();  
	        walls1.put("Temporal", 10); walls2.put("Temporal", 12);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 2);
	        squares.put("Regresar", 2);
	        squares.put("Turno Doble", 2);	  
	        Quoridor quoridor1 = new Quoridor(size1, p1, color1, color2, p2, difficulty, walls1, squares,0);
	        Quoridor quoridor2 = new Quoridor(size2, p1, color1, color2, p2, difficulty, walls2, squares,0);	        
	        assertEquals(size1, quoridor1.getBoard().length);
	        assertEquals(size1, quoridor1.getBoard()[0].length);	        
	        assertEquals(size2, quoridor2.getBoard().length);
	        assertEquals(size2, quoridor2.getBoard()[0].length);
	        
	    } catch (Exception e) {
	    	fail("Threw a exception");
	    }
    }     
  
    
    @Test
    public void shouldAssignBarriersToPlayers(){
    	try {
	        int size1 = 9;	        
	        String p1 = "Pvdfbgnr1";
	        String color1 = "Red";
	        String p2 = "Plvfbr2";
	        String color2 = "Blue";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Larga", 4); walls.put("Temporal", 3);  
                walls.put("Normal", 1); walls.put("Aliada", 2);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 0);
	        squares.put("Regresar", 0);	squares.put("Turno Doble", 0);        
	        Quoridor quoridor = new Quoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,0);
	        quoridor.putWall(new int[]{4,2}, 'N', "Larga");
	        assertEquals(quoridor.getRemainingWallsByType("Long"),4);
	        quoridor.putWall(new int[]{7,5}, 'N', "Aliada");
	        assertEquals(quoridor.getRemainingWallsByType("Ally"),2);	               
	    } catch (Exception e) {
	    	fail("Threw a exception");
	    	
	    }
    }  
    
    
    @Test
    public void shouldMoveOrthogonallyAPawn(){
    	try {
	        int size1 = 9;	        
	        String p1 = "Player1";
	        String color1 = "Red";
	        String p2 = "Player2";
	        String color2 = "Blue";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Larga", 4); walls.put("Temporal", 5);  
                walls.put("Normal", 1); walls.put("Aliada", 0);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 0);
	        squares.put("Regresar", 0);	squares.put("Turno Doble", 0);        
	        Quoridor quoridor = new Quoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,0);	        
	        quoridor.movePeon(new int[]{7,4});
	        
	    } catch (Exception e) {
	    	fail("Threw a exception");
	    }
    	
    	
        
    }     
    

    @Test
    public void shouldMoveDiagonallyAPawn(){
    	try {
	        int size1 = 9;	        
	        String p1 = "Player1";
	        String color1 = "Red";
	        String p2 = "Player2";
	        String color2 = "Blue";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Long", 3); walls.put("Temporary", 5);  
                walls.put("Normal", 2); walls.put("Ally", 0);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 0);
	        squares.put("Regresar", 0);	squares.put("Turno Doble", 0);        
	        Quoridor quoridor = new Quoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,0);	        
	        quoridor.putWall(new int[]{6,4}, 'S', "Normal");
	        quoridor.putWall(new int[]{7,3}, 'E', "Normal");
	        quoridor.movePeon(new int[]{7,4});
	        quoridor.putWall(new int[]{7,5}, 'W', "Normal");
	        quoridor.movePeon(new int[]{6,3});
    	} catch (Exception e) {
	    	fail("Threw a exception");
	    }
    }  
    
    @Test
    public void shouldPlaceANormalBarrier(){
    	try {
	        int size1 = 9;	        
	        String p1 = "Player1";
	        String color1 = "Red";
	        String p2 = "Player2";
	        String color2 = "Blue";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Long", 4); walls.put("Temporary", 5);  
                walls.put("Normal", 1); walls.put("Ally", 0);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 0);
	        squares.put("Regresar", 0);	squares.put("Turno Doble", 0);        
	        Quoridor quoridor = new Quoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,0);	        
	        quoridor.putWall(new int[]{5,2}, 'N', "Normal");
	        assertEquals(quoridor.getCurrentPlayerName(),"Player2");
	    } catch (Exception e) {
	    	fail("Threw a exception");
	    }	
    }     
    
    
    @Test
    public void shouldMoveAPawnOverAPawn(){
    	try {
	        int size1 = 9;	        
	        String p1 = "Player1";
	        String color1 = "Red";
	        String p2 = "Player2";
	        String color2 = "Blue";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Long", 4); walls.put("Temporary", 5);  
                walls.put("Normal", 1); walls.put("Ally", 0);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 0);
	        squares.put("Regresar", 0);	squares.put("Turno Doble", 0);        
	        Quoridor quoridor = new Quoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,0);	        
	        quoridor.movePeon(new int[]	{7,4});
	        quoridor.movePeon(new int[]	{1,4});
	        quoridor.movePeon(new int[]	{6,4});
	        quoridor.movePeon(new int[]	{2,4});
	        quoridor.movePeon(new int[]	{5,4});
	        quoridor.movePeon(new int[]	{3,4});
	        quoridor.movePeon(new int[]	{4,4});
	        quoridor.movePeon(new int[]	{5,4});
	        assertTrue(quoridor.getBoard()[5][4].contains("Player2"));
	    } catch (Exception e) {
	    	fail("Threw a exception");
	    }	
    } 
    
    
    @Test
    public void shouldNotMoveAPawnOverANonAlliedBarrier(){
    	try	{
        	int size = 9;
            String p1 = "Player1"; String color1 = "Red";            
            String p2 = "Player2"; String color2 = "Blue";            
            String difficulty = "Untimed";
            HashMap<String, Integer> walls = new HashMap<>();
            walls.put("Larga", 2); walls.put("Temporal", 5);  
            walls.put("Normal", 1); walls.put("Aliada", 2);
            HashMap<String, Integer> squares = new HashMap<>();
            squares.put("Teletransportador", 0);
            squares.put("Regresar", 0);
            squares.put("Turno Doble", 0);
            Quoridor quoridor = Quoridor.getQuoridor(size, p1, color1, color2, p2, difficulty, walls, squares,0);
            quoridor.movePeon(new int[]	{7,4});
	        quoridor.movePeon(new int[]	{1,4});
            quoridor.putWall(new int[]{1,3}, 'S', "Aliada");        
            
            quoridor.movePeon(new int[]	{2,4});
            
            fail("Did not throw exception");
            
        }catch(QuoridorException e){
        	assertEquals(QuoridorException.NOT_MOVE_PEON , e.getMessage());
        }
    }  
    
    @Test
    public void shouldMoveAPawnOverAnAlliedBarrier(){
    	try	{
        	int size = 9;
            String p1 = "Player1"; String color1 = "Red";            
            String p2 = "Player2"; String color2 = "Blue";            
            String difficulty = "Untimed";
            HashMap<String, Integer> walls = new HashMap<>();
            walls.put("Larga", 2); walls.put("Temporal", 5);  
            walls.put("Normal", 1); walls.put("Aliada", 2);
            HashMap<String, Integer> squares = new HashMap<>();
            squares.put("Teletransportador", 0);
            squares.put("Regresar", 0);
            squares.put("Turno Doble", 0);
            Quoridor quoridor = new Quoridor(size, p1, color1, color2, p2, difficulty, walls, squares,0);
            quoridor.movePeon(new int[]	{7,4});
	        quoridor.movePeon(new int[]	{1,4});
	        quoridor.movePeon(new int[]	{6,4});
            quoridor.putWall(new int[]{1,4}, 'S', "Aliada");
            quoridor.movePeon(new int[]	{5,4});
            quoridor.movePeon(new int[]	{2,4});
 
        }catch(QuoridorException e){
        	fail("Threw exception");
        }
    } 
    
    
    @Test
    public void shouldKnowWhenSomeoneWonTheGame(){
    	try	{
        	int size = 9; String p1 = "Player1";
            String color1 = "Red"; String p2 = "Player2";          
            String color2 = "Blue"; String difficulty = "Untimed";                    
            HashMap<String, Integer> walls = new HashMap<>();
            walls.put("Larga", 2); walls.put("Temporal", 5);  
            walls.put("Normal", 1); walls.put("Aliada", 2);
            HashMap<String, Integer> squares = new HashMap<>();
            squares.put("Teletransportador", 0);
            squares.put("Regresar", 0); squares.put("Turno Doble", 0);            
            Quoridor quoridor = new Quoridor(size, p1, color1, color2, p2, difficulty, walls, squares,0);
            quoridor.movePeon(new int[]	{7,4});
	        quoridor.movePeon(new int[]	{1,4});
	        quoridor.movePeon(new int[]	{6,4});
            quoridor.movePeon(new int[]	{2,4});
            quoridor.movePeon(new int[]	{5,4});
            quoridor.movePeon(new int[]	{2,3});
            quoridor.movePeon(new int[]	{4,4});
            quoridor.movePeon(new int[]	{3,3});
            quoridor.movePeon(new int[]	{3,4});
            quoridor.movePeon(new int[]	{4,3});
            quoridor.movePeon(new int[]	{2,4});
            quoridor.movePeon(new int[]	{5,3});
            quoridor.movePeon(new int[]	{1,4});
            quoridor.movePeon(new int[]	{6,3});
            quoridor.movePeon(new int[]	{0,4});
            assertTrue(!quoridor.getState());
        }catch(QuoridorException e){
        	fail("Threw exception");
        
        }
    	
    	
    } 
    
    @Test
    public void shouldKnowTheBarriersLeftForEachPlayer(){
    	try {
	        int size1 = 9;	        
	        String p1 = "Player1";
	        String color1 = "Red";
	        String p2 = "Player2";
	        String color2 = "Blue";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Larga", 4); walls.put("Temporal", 4);  
                walls.put("Normal", 1); walls.put("Aliada", 1);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 0);
	        squares.put("Regresar", 0);	squares.put("Turno Doble", 0);        
	        Quoridor quoridor = new Quoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,0);
	        quoridor.putWall(new int[]{4,2}, 'N', "Larga");
	        quoridor.putWall(new int[]{7,5}, 'N', "Aliada");
	        assertEquals(quoridor.getRemainingWallsByType("Long"),3);
	        quoridor.putWall(new int[]{8,5}, 'N', "Normal");
	        assertEquals(quoridor.getRemainingWallsByType("Ally"),0);
	        
	    } catch (Exception e) {
	    	fail("Threw a exception");	    	
	    }
    } 
    
    @Test
    public void shouldNotBlockThePassageOfAPlayer(){
    	try	{
        	int size = 9;
            String p1 = "Player1"; String color1 = "Red";            
            String p2 = "Player2"; String color2 = "Blue";            
            String difficulty = "Untimed";
            HashMap<String, Integer> walls = new HashMap<>();
            walls.put("Larga", 4); walls.put("Temporal", 4);  
            walls.put("Normal", 1); walls.put("Aliada", 1);
            HashMap<String, Integer> squares = new HashMap<>();
            squares.put("Teletransportador", 1);
            squares.put("Regresar", 1);
            squares.put("Turno Doble", 1);
            Quoridor quoridor = new Quoridor(size, p1, color1, color2, p2, difficulty, walls, squares,0);
            
            quoridor.putWall(new int[]{4,0}, 'N', "Normal");
            quoridor.putWall(new int[]{4,2}, 'N', "Normal");  
            quoridor.putWall(new int[]{4,6}, 'N', "Larga");
            quoridor.putWall(new int[]{4,4}, 'N', "Aliada");
            fail("Did not throw exception");
            
        }catch(QuoridorException e){
        	assertEquals(QuoridorException.NOT_BLOCK, e.getMessage());
        }
    } 
    
    @Test
    public void shouldMeetNormalBarrierConditions(){
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
	        squares.put("Regresar", 0);	squares.put("Turno Doble", 0);        
	        Quoridor quoridor = new Quoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,0);
	        quoridor.movePeon(new int[]{7,4});
	        quoridor.putWall(new int[]{6,5}, 'S', "Normal");
	        quoridor.movePeon(new int[]{7,5});
	        quoridor.movePeon(new int[]{1,4});
	        quoridor.movePeon(new int[]{7,6});
	        quoridor.movePeon(new int[]{2,4});
	        quoridor.movePeon(new int[]{7,7});
	        quoridor.movePeon(new int[]{3,4});
	        quoridor.movePeon(new int[]{6,7});
	    } catch (Exception e) {
	    	fail("Threw a exception");
	    }
    } 
    
    @Test
    public void shouldMeetTemporalBarrierConditions(){
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
	        squares.put("Regresar", 0);	squares.put("Turno Doble", 0);        
	        Quoridor quoridor = new Quoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,0);
	        quoridor.movePeon(new int[]{7,4});
	        quoridor.putWall(new int[]{6,4}, 'N', "Temporal"); 
	        quoridor.putWall(new int[]{7,6}, 'W', "Normal");     
	        quoridor.movePeon(new int[]{1,4});
	        quoridor.movePeon(new int[]{7,3});
	        quoridor.movePeon(new int[]{2,4});
	        quoridor.movePeon(new int[]{6,3});
	    } catch (Exception e) {
	    	fail("Threw a exception");

	    }
    } 
    
    @Test
    public void shouldMeetLongBarrierConditions(){
    	try {
	        int size1 = 13;	        
	        String p1 = "cdvfb1";
	        String color1 = "Red";
	        String p2 = "Player2";
	        String color2 = "Blue";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Larga", 4); walls.put("Temporal", 5);  
                walls.put("Normal", 1); walls.put("Aliada", 4);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 0);
	        squares.put("Regresar", 0);	squares.put("Turno Doble", 0);        
	        Quoridor quoridor = new Quoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,0);
	        quoridor.putWall(new int[]{11,4}, 'S', "Larga");
	        
	        quoridor.putWall(new int[]{4,3}, 'N', "Aliada");
	        quoridor.movePeon(new int[]{11,6});
	        
	        quoridor.movePeon(new int[]{12,6});
	        fail("Did not throw exception");
	    } catch (Exception e) {
	    	
	    	assertEquals(QuoridorException.NOT_MOVE_PEON, e.getMessage());
	    }
    } 
    
    @Test
    public void shouldMeetAlliedBarrierConditions(){
    	try {
	        int size1 = 9;	        
	        String p1 = "Player1";
	        String color1 = "Red";
	        String p2 = "Player2";
	        String color2 = "Blue";
	        String difficulty = "Untimed";
	        HashMap<String, Integer> walls = new HashMap<>(); 
	        walls.put("Larga", 4); walls.put("Temporal", 2);  
                walls.put("Normal", 1); walls.put("Aliada", 3);
	        HashMap<String, Integer> squares = new HashMap<>();
	        squares.put("Teletransportador", 0);
	        squares.put("Regresar", 0);	squares.put("Turno Doble", 0);        
	        Quoridor quoridor = new Quoridor(size1, p1, color1, color2, p2, difficulty, walls, squares,0);	        
	        quoridor.putWall(new int[]{7,4}, 'S', "Aliada");
	        quoridor.movePeon(new int[]{1,4});
	        quoridor.movePeon(new int[]{7,4});
	        
	    } catch (Exception e) {
	    	fail("Threw a exception");
	    }
    } 
    
    
    @Test
    public void shouldNotCreateABoardIfItsNotPossible(){
    	try {
            int size = 20;
            String p1 = "Player1";
            String color1 = "Red";
            String p2 = "Player2";
            String color2 = "Blue";
            String difficulty = "Untimed";
            HashMap<String, Integer> walls = new HashMap<>();
            walls.put("Long", 4);
            walls.put("Temporary", 5);
            walls.put("Normal", 1);
            walls.put("Ally", 0);
            HashMap<String, Integer> squares = new HashMap<>();
            squares.put("Teletransportador", 0);
            squares.put("Regresar", 0);
            squares.put("Turno Doble", 0);
            Quoridor quoridor = new Quoridor(size, p1, color1, color2, p2, difficulty, walls, squares,0);
            fail("Did not throw exception");
        } catch (QuoridorException e) {
        	assertEquals(QuoridorException.NOT_SIZE, e.getMessage());
        }
    }     
  
 
    
    @Test
    public void shouldNotMoveOrthogonallyAPawnIfItsNotPossible(){
    	try	{
        	int size = 9;
            String p1 = "Player1"; String color1 = "Red";            
            String p2 = "Player2"; String color2 = "Blue";            
            String difficulty = "Untimed";
            HashMap<String, Integer> walls = new HashMap<>();
            walls.put("Larga", 0); walls.put("Temporal", 5);  
            walls.put("Normal", 5); walls.put("Aliada", 0);
            HashMap<String, Integer> squares = new HashMap<>();
            squares.put("Teletransportador", 0);
            squares.put("Regresar", 0);
            squares.put("Turno Doble", 0);
            Quoridor quoridor = new Quoridor(size, p1, color1, color2, p2, difficulty, walls, squares,0);         
            
            quoridor.putWall(new int[]{0,4}, 'S', "Normal");
            quoridor.putWall(new int[]{7,4}, 'S', "Normal");
            quoridor.movePeon(new int[]{1,4});
            
            fail("Did not throw exception");
            
        }catch(QuoridorException e){
        	assertEquals(QuoridorException.NOT_MOVE_PEON, e.getMessage());
        }
    }     
    

    @Test
    public void shouldNotMoveDiagonallyAPawnIfItsNotPossible(){
    	try	{
        	int size = 9;
            String p1 = "Player1"; String color1 = "Red";            
            String p2 = "Player2"; String color2 = "Blue";            
            String difficulty = "Untimed";
            HashMap<String, Integer> walls = new HashMap<>();
            walls.put("Larga", 2); walls.put("Temporal", 5);  
            walls.put("Normal", 3); walls.put("Aliada", 0);
            HashMap<String, Integer> squares = new HashMap<>();
            squares.put("Teletransportador", 0);
            squares.put("Regresar", 0);
            squares.put("Turno Doble", 0);
            Quoridor quoridor = new Quoridor(size, p1, color1, color2, p2, difficulty, walls, squares,0);           
            quoridor.movePeon(new int[]{7,4});
            quoridor.movePeon(new int[]{1,4});  
            quoridor.movePeon(new int[]{6,4}); 
            quoridor.movePeon(new int[]{1,5}); 
            quoridor.movePeon(new int[]{5,4}); 
            quoridor.movePeon(new int[]{0,4}); 
            fail("Did not throw exception");
        } catch(QuoridorException e){
        	assertEquals(QuoridorException.NOT_MOVE_PEON, e.getMessage());
        }
    }  
    
    @Test
    public void shouldNotPlaceANormalBarrierIfItsNotPossible() {
        try	{
        	int size = 9;
            String p1 = "Player1"; String color1 = "Red";            
            String p2 = "Player2"; String color2 = "Blue";            
            String difficulty = "Untimed";
            HashMap<String, Integer> walls = new HashMap<>();
            walls.put("Larga", 4); walls.put("Temporal", 5);  
            walls.put("Normal", 1); walls.put("Aliada", 0);
            HashMap<String, Integer> squares = new HashMap<>();
            squares.put("Teletransportador", 1);
            squares.put("Regresar", 1);
            squares.put("Turno Doble", 1);
            Quoridor quoridor = new Quoridor(size, p1, color1, color2, p2, difficulty, walls, squares,0);
            assertNotNull(quoridor);
            quoridor.putWall(new int[]{2,2}, 'N', "Temporal");
            
            quoridor.putWall(new int[]{2,2}, 'N', "Normal");
            
            fail("Did not throw exception");
            
        }catch(QuoridorException e){
        	assertEquals(QuoridorException.NOT_CREATE_WALL, e.getMessage());
        }
    }     
     
    
    @Test
    public void shouldNotMoveAPawnOverAPawnIfItsNotPossible(){
    	try	{
        	int size = 9;
            String p1 = "Player1"; String color1 = "Red";            
            String p2 = "Player2"; String color2 = "Blue";            
            String difficulty = "Untimed";
            HashMap<String, Integer> walls = new HashMap<>();
            walls.put("Larga", 4); walls.put("Temporal", 5);  
            walls.put("Normal", 1); walls.put("Aliada", 0);
            HashMap<String, Integer> squares = new HashMap<>();
            squares.put("Teletransportador", 0);
            squares.put("Regresar", 0);
            squares.put("Turno Doble", 0);
            Quoridor quoridor = new Quoridor(size, p1, color1, color2, p2, difficulty, walls, squares,0);           
            quoridor.putWall(new int[]{2,2}, 'E', "Normal");
            quoridor.movePeon(new int[]{1,4});
            quoridor.movePeon(new int[]{7,4});
            quoridor.movePeon(new int[]{2,4});
            quoridor.movePeon(new int[]{6,4});
            quoridor.movePeon(new int[]{3,4});
            quoridor.movePeon(new int[]{5,4});
            quoridor.putWall(new int[]{4,4}, 'N', "Normal");  
            quoridor.movePeon(new int[]{4,4});
            quoridor.movePeon(new int[]{5,4});
            fail("Did not throw exception");
        }catch(QuoridorException e){
        	assertEquals(QuoridorException.NOT_MOVE_PEON, e.getMessage());
        }
    }  
    
	
}
