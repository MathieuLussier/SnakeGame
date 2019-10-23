package com.snakegame.content;

import com.snakegame.Engine;
import com.snakegame.GameConstants;
import com.snakegame.SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Snake extends GameObject {
    private int tails;
    private Backtrack[] backtracks = new Backtrack[tails];
    private int scale = GameConstants.scale;

    public Snake(int x, int y, ObjectType snake) {
        super(x, y, snake, Direction.RIGHT);
    }

    private void updateSnakePos() {
        int xPos = x;
        int yPos = y;
        if (xPos > GameConstants.W_WIDTH || x < 0)
            SnakeGame.gameOver();
        else if (yPos > GameConstants.W_HEIGHT || y < 0)
            SnakeGame.gameOver();
        else {
            x += direction.x;
            y += direction.y;
        }
    }

    private void checkAppleCollision() {
        GameObject[] objects = Engine.getObjects();
        for (GameObject object : objects) {
            if (object.type == ObjectType.FOOD) {
                for (int i = 0; i < scale; i++) {
                    if (((x + i) > object.x) && ((x + i) < (object.x + GameConstants.scale)) && ((y + i) > object.y) && ((y + i) < (object.y + GameConstants.scale))) {
                        Engine.removeObject(object);
                        Food.spawnFood();
                        tails++;
                        break;
                    }
                }
            }
            else
                continue;
        }
    }

    private void checkTailsCollision() {
        for (int i = 0; i < backtracks.length; i++) {
            Backtrack b = backtracks[i];
            for (int j = 0; j < scale; j++) {
                try {
                    if (((x + j) > b.x) && ((x + j) < (b.x + scale)) && ((y + j) > b.y) && ((y + j) < (b.y + scale))) {
                        SnakeGame.gameOver();
                        break;
                    }
                } catch (Exception err) {
                    continue;
                }
            }
        }
    }

    private void fixBacktracks() {
        if (backtracks.length > tails) {
            Backtrack[] newArr = new Backtrack[tails];
            Backtrack[] oldArr = backtracks;
            for (int i = 0; i < newArr.length; i++) {
                newArr[i] = oldArr[i];
            }
            backtracks = newArr;
        }
    }

    private Backtrack[] recursiveUpdateBacktrack(Backtrack newBacktrack, Backtrack[] newArr, Backtrack[] oldArr, int index) {
        if (index < oldArr.length && newBacktrack != null && index < newArr.length) {
            newArr[index] = newBacktrack;
            newBacktrack = oldArr[index];
            index++;
            recursiveUpdateBacktrack(newBacktrack, newArr, oldArr, index);
        }
        return newArr;
    }

    private void updateBacktrack() {
        try {
            Backtrack[] newArr = new Backtrack[tails];
            Backtrack[] oldArr = backtracks;
            newArr = recursiveUpdateBacktrack(new Backtrack(x, y), newArr, oldArr, 0);
            backtracks = newArr;
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private void printDebug(Graphics g) {
        if (g instanceof Graphics2D && GameConstants.isDebugging) {
            g.setColor(Color.white);
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawString("x: " + x, 0, 10);
            g.drawString("y: " + y, 0, 25);
            g.drawString("ScreenX: " + GameConstants.W_WIDTH, 0, 40);
            g.drawString("ScreenY: " + GameConstants.W_HEIGHT, 0, 55);
            g.drawString("Tails: " + tails, 0, 70);
            g.drawString("Backtrack Lenght: " + backtracks.length, 0, 85);
        }
    }

    private void printTails(Graphics g) {
        Backtrack[] bs = backtracks;
        if (bs.length > 0) {
            for (int i = 0; i < bs.length; i++) {
                try {
                    Backtrack b = bs[i];
                    g.drawRect(b.x, b.y, scale, scale);
                    continue;
                } catch (Exception err) {
                    break;
                }
            }
            fixBacktracks();
        }
    }

    @Override
    public void tick() {
        updateBacktrack();
        updateSnakePos();
        checkAppleCollision();
        checkTailsCollision();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.drawRect(x, y, scale, scale);
        g.fillRect(x, y, scale, scale);

        printTails(g);
        g.setColor(Color.white);
        g.drawString("Score: " + tails, (GameConstants.W_WIDTH / 2) - 40, 10);
        if (g instanceof Graphics2D && GameConstants.isDebugging)
            printDebug(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (direction != Direction.RIGHT))
            setDirection(Direction.LEFT);
        if ((key == KeyEvent.VK_RIGHT) && (direction != Direction.LEFT))
            setDirection(Direction.RIGHT);
        if ((key == KeyEvent.VK_UP) && (direction != Direction.DOWN))
            setDirection(Direction.UP);
        if ((key == KeyEvent.VK_DOWN) && (direction != Direction.UP))
            setDirection(Direction.DOWN);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    class Backtrack {
        public int x,y;

        public Backtrack(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
