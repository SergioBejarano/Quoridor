package domain;

import java.time.Duration;

public class Timetrial implements Difficulty{
	private String player1;
    private String player2;
    private Duration time1;
    private Duration time2;
    private boolean state1;
    private boolean state2;

    /**
     * Constructs a Timed object with the specified player names and initializes the time for each player to 5 minutes.
     *
     * @param player1 Name of the first player.
     * @param player2 Name of the second player.
     */
    public Timetrial(String player1, String player2, int time) {
        this.player1 = player1;
        this.player2 = player2;
        this.time1 = Duration.ofSeconds(time);
        this.time2 = Duration.ofSeconds(time);
        this.state1 = true;
        this.state2 = true;
    }
    

    /**
     * Updates the time for the specified player by subtracting the given amount of time.
     *
     * @param player The player whose time will be updated.
     * @param time   The amount of time to subtract from the player's time.
     */
    public void act(String player, Duration time) {
        if (player.equals(player1)) {
            Duration d = time1.minus(time);
            
            if (d.compareTo(Duration.ZERO) < 0)	{
            	state1 = false;
            }
        } else if (player.equals(player2)) {
        	Duration d = time2.minus(time);
            if (d.compareTo(Duration.ZERO) < 0)	{
            	state2 = false;
            }
        }
        
        
    }
    
    /**
     * Obtain the state based on time
     *
     * @param player The player whose state needs to be checked.
     * @return true if the player's time has not expired, false otherwise.
     */
    public boolean getState(String player) {
        if (player.equals(player1)) {
            return state1;
        } else if (player.equals(player2)) {
            return state2;
        }
        return false;
    }
    
    /**
     * Set the state in true.
     * 
     * @param player
     */
    public void setState(String player)	{
    	if (player.equals(player1)) {
            state1 = true;
        } else if (player.equals(player2)) {
            state2 = true;
        }

    }
}
