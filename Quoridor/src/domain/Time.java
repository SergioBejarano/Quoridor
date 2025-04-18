package domain;
import java.io.Serializable;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents a timer that counts the elapsed time
 * from start to stop.
 */
public class Time implements Serializable{
    private transient Timer timer; 
    private long startTime; 
    private Duration elapsedTime; 

    /**
     * Starts the timer.
     * 
     * @param
     * @return
     */
    public void startTimer() {
        
        if (timer != null) {
            return; 
        }

    
        startTime = System.currentTimeMillis();

       
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                long elapsedMilliseconds = currentTime - startTime;
                long elapsedSeconds = elapsedMilliseconds / 1000; 
                elapsedTime = Duration.ofSeconds(elapsedSeconds);
            }
        }, 0, 1000); 
    }

    /**
     * Stops the timer.
     * 
     * @param
     * @return
     */
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null; 
        }
    }

    /**
     * Gets the elapsed time since the start of the timer.
     * 
     * @param
     * @return The elapsed time as a Duration object.
     */
    public Duration getElapsedTime() {
        return elapsedTime;
    }
    
    
    /**
     * Gets the elapsed time in seconds since the start of the timer.
     * 
     * @return The elapsed time in seconds.
     */
    public String getElapsedTimeInSeconds() {
    	int elapsedSeconds = (int)getElapsedTime().getSeconds();
        return String.valueOf(elapsedSeconds);
    }

    
}
