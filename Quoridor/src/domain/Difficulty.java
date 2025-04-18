package domain;
import java.time.Duration;
import java.io.*;

public interface Difficulty extends Serializable{
	
	
	/**
     * Determines the action to be taken based on the player and the elapsed time.
     * 
     * @param player The current player.
     * @param time   The elapsed time.
     */
	public void act(String player,Duration time);
	
	
	
	/**
     * Obtains the state based on the player.
     * 
     * @param player The player for which to obtain the state.
     * @return True if the player's state is active, false otherwise.
     */
	public boolean getState(String player);
	
	
	
	/**
     * Sets the state for the specified player.
     * 
     * @param player The player for which to set the state.
     */
	public void setState(String player);
	
}
