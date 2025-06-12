package com.lurch.time;

/**
 * Time management utility for smooth/fixed-step game loops.
 */
public class Time 
{
    /** 
     * Last recorded timestamp in nanoseconds. 
     */
    private long previous_time;









    /** 
     * Current timestamp in nanoseconds. 
     */
    private long current_time;









    /** 
     * Time elapsed between current and previous update in nanoseconds. 
     */
    private long delta_time;










    /** 
     * Accumulated unprocessed time in nanoseconds. 
     * Used to determine how many update steps need to run.
     */
    private long accumulated_time = 0;










    /** 
     * Number of unprocessed update steps to consume. 
     */
    private int accumulated_updates = 0;










    /** 
     * Fixed time interval between updates in nanoseconds. 
     * Default is ~16.67ms, equivalent to 60 updates per second.
     */
    private long update_interval = 16_666_667;










    /** 
     * Updates per second target (UPS). 
     */
    private float updates_per_second = 60.0f;










    /**
     * Starts Timer
     * - Records the initial start time.
     */
    public void start()
    {
        previous_time = System.nanoTime();
    }










    /**
     * Stops Timer
     * - Stores current time
     * - Clears accumulated time & updates
     */
    public void stop()
    {
        previous_time = current_time;
        accumulated_time = 0;
        accumulated_updates = 0;
    }










    /**
     * Updates the internal time markers and accumulates elapsed unprocessed time.
     */
    public void update()
    {
        current_time = System.nanoTime();
        delta_time = current_time - previous_time;
        accumulated_time += delta_time;
        previous_time = current_time;

        // Compute how many fixed updates are pending based on accumulated time.
        accumulated_updates = (int) (accumulated_time / update_interval);
    }











    /**
     * Consumes the time corresponding to processed updates.
     */
    public void consume()
    {
        accumulated_time -= accumulated_updates * update_interval;
    }










    /**
     * Sets the target updates per second and adjusts the update interval accordingly.
     * 
     * @param UPS Updates per second to target.
     */
    public void setUPS(float UPS) 
    {
        updates_per_second = UPS;
        update_interval = (long) (1_000_000_000L / UPS);
    }










    /**
     * @return Number of pending update steps to process this frame.
     */
    public int getAccumulatedUpdates()
    {
        return accumulated_updates;
    }










    /**
     * @return Time between current and previous frame in nanoseconds.
     */
    public long getDeltaTime()
    {
        return delta_time;
    }










    /**
     * @return The fixed update interval in nanoseconds.
     */
    public long getUpdateInterval()
    {
        return update_interval;
    }









    /**
     * @return Target updates per second (UPS).
     */
    public float getUPS()
    {
        return updates_per_second;
    }






    


    /**
     * @return Total accumulated unprocessed time in nanoseconds.
     */
    public long getAccumulatedTime()
    {
        return accumulated_time;
    }
}
