package com.snakegame;

import com.snakegame.content.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import javax.swing.*;

public class SnakeGame extends JComponent implements Runnable {
    private Thread thread;
    public static boolean isRunning = false;
    public static int frames;

    public SnakeGame() {
        createWindow();
        SnakeGame.startGame();
    }

    public void createWindow() {
        JFrame frame = new JFrame();
        frame.setTitle("Snake Game");
        frame.setPreferredSize(new Dimension(GameConstants.W_WIDTH, GameConstants.W_HEIGHT));
        frame.setMaximumSize(new Dimension(GameConstants.W_WIDTH, GameConstants.W_HEIGHT));
        frame.setMinimumSize(new Dimension(GameConstants.W_WIDTH, GameConstants.W_HEIGHT));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(new Controller());
        frame.getContentPane().add(this);
        frame.getContentPane().setBackground(Color.black);
        frame.pack();
        frame.setVisible(true);
        this.start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            isRunning = false;
        } catch(Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 20.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while(SnakeGame.isRunning)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1)
            {
                tick();
                delta--;
            }
            if(SnakeGame.isRunning)
                render();
            SnakeGame.frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
//                System.out.println("FPS: "+ SnakeGame.frames);
                SnakeGame.frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        Engine.tick();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Engine.render(g);
        g.setColor(Color.white);
        String spawnFood = "Press [ c ] to spawn food";
        String unspawnFood = "Press [ v ] to remove food";
        String enableDebugging = "Press [ b ] to enable debugging";
        String quitGame = "Press [ q ] to exit";
        String gameFrames = "Fps: " + SnakeGame.frames;
        g.drawString(spawnFood, (GameConstants.W_WIDTH - (g.getFontMetrics().stringWidth(spawnFood) + 10)), 10);
        g.drawString(unspawnFood, (GameConstants.W_WIDTH - (g.getFontMetrics().stringWidth(unspawnFood) + 10)), 25);
        g.drawString(enableDebugging, (GameConstants.W_WIDTH - (g.getFontMetrics().stringWidth(enableDebugging) + 10)), 40);
        g.drawString(quitGame, (GameConstants.W_WIDTH - (g.getFontMetrics().stringWidth(quitGame) + 10)), 55);
        g.drawString(gameFrames, (GameConstants.W_WIDTH - (g.getFontMetrics().stringWidth(gameFrames) + 10)), 70);
    }

    private void render() {
        repaint();
    }

    public static void startGame() {
        Engine.addObject(new Snake(100, 100, ObjectType.SNAKE));
        Food.spawnFood();
    }

    public static void clearGame() {
        Engine.objects.clear();
    }

    public static void gameOver() {
        SnakeGame.clearGame();
        SnakeGame.startGame();
    }

    public static void main (String[] args) {
        new SnakeGame();
    }
}