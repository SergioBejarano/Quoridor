package domain;
import java.time.Duration;

/**
 * The Untimed class represents a game mode where there is no time limit for player actions.
 */
public class Untimed implements Difficulty {

    /**
     * Constructs an Untimed object with the specified player names and no time limit for actions.
     *
     * @param player1 Name of the first player.
     * @param player2 Name of the second player.
     * @param time    Ignored parameter as there is no time limit.
     */
    public Untimed(String player1, String player2, int time) {
        // No specific initialization needed for Untimed mode
    }
    
    /**
     * There is no time limit for player action in turn.
     *
     * @param player Ignored parameter as there is no time limit.
     * @param time   Ignored parameter as there is no time limit.
     */
    public void act(String player, Duration time)	{
        // No action required in Untimed mode
    }
    
    /**
     * Obtain the state based on time without limit.
     *
     * @param player Ignored parameter as there is no time limit.
     * @return Always returns true as there is no time limit.
     */
    public boolean getState(String player)	{
        return true; // No time limit, always return true
    }    
    
    /**
     * 
     * @param player
     */
    public void setState(String player)	{
    }
}
