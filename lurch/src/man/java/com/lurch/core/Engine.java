package com.lurch.core;

public class Engine 
{
    protected final Window window;
    private final Timer timer;

    private boolean running = true;

    private final float deltaTime;

    public Engine(int width, int height, String title) 
    {
        window = new Window(width, height, title);
        timer = new Timer();

        deltaTime = 1f / timer.getUPS();
    }

    public Engine(String title) 
    {
        this(0, 0, title);
    }

    public void run()
    {
        init();
        loop();
        free();
    }

    protected void init() 
    {
        timer.start();
    }

    private void loop() 
    {
        while(running && !window.shouldClose())
        {
            timer.update();

            int updates = timer.getAccumulatedUpdates();
            for(int i = 0; i < updates; i++)
            {
                update(deltaTime);
            }

            timer.consume();

            render();

            window.refresh();
        }
    }

    protected void free() 
    {
        timer.stop();
        window.delete();
    }


    protected void update(float deltaTime) 
    {
    }


    protected void render() 
    {
    }

    protected Window getWindow() {
        return window;
    }
}
