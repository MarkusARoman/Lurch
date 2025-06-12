package com.lurch.time;

/**
 * TimeBuffer is a utility class that helps manage time-based updates in a game loop.
 * It accumulates elapsed time and allows for processing of updates at a specified rate.
 * This is useful for ensuring consistent game logic updates regardless of frame rate.
 */
public class TimeBuffer 
{
    // Instance variables in nanoseconds
    private long prev;
    private long accumulator = 0;

    // Intended update interval in nanoseconds
    private final long UPDATE_INTERVAL;

    // Updates per second
    private final float UPS;


    /**
     * Initializes the timer for the desired updates per second.
     * 
     * @param ups the intended updates per second.
     */
    public TimeBuffer(float ups) 
    {
        // Convert ups to the update interval in nanoseconds
        UPDATE_INTERVAL = (long) (1_000_000_000L / ups);
        UPS = ups;
    }

    /**
     * Starts the timer by capturing the current time.
     */
    public void create() 
    {
        // Capture the current time in nanoseconds
        prev = System.nanoTime();
    }

    /**
     * Resets the accumulator.
     */
    public void reset()
    {
        accumulator = 0;
    }

    /**
     * Stops the timer by resetting the previous time and accumulator.
     */
    public void delete()
    {
        // Reset the previous time and accumulator
        prev = 0;
        accumulator = 0;
    }


    /**
     * Refreshes the timer by accumulating elapsed time.
     */
    public void refresh() 
    {
        final long curr = System.nanoTime();
        final long delta = curr - prev;

        accumulator += delta;
        prev = curr;
    }


    /**
     * Calculates the number of full update steps that can be processed based on the accumulated time.
     * It also updates the accumulator to hold any leftover time (less than one update interval).
     * 
     * @return the number of full update steps that can be processed.
     */
    public int getPendingUpdates() 
    {
        // Calculate the number of pending updates based on the accumulated time
        int pendingUpdates = (int) (accumulator / UPDATE_INTERVAL);

        accumulator -= UPDATE_INTERVAL * pendingUpdates;
        
        // Return the number of pending updates
        return pendingUpdates;
    }

    /**
     * @return Updates per second as a float
     */
    public float getUPS()
    {
        return UPS;
    }
}
