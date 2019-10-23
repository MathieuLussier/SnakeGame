package com.snakegame.content;

import com.snakegame.Engine;
import com.snakegame.GameConstants;
import com.snakegame.SnakeGame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        Engine.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        Engine.keyReleased(e);

        int key = e.getKeyCode();

        if ( key == KeyEvent.VK_C ) {
            Food.spawnFood();
        }

        if ( key == KeyEvent.VK_V ) {
            GameObject[] objects = Engine.getObjects();
            for (GameObject object : objects) {
                if (object.type == ObjectType.FOOD) {
                    Engine.removeObject(object);
                    break;
                }
            }
        }

        if ( key == KeyEvent.VK_B ) {
            GameConstants.isDebugging = !GameConstants.isDebugging;
        }

        if ( key == KeyEvent.VK_Q ) {
            SnakeGame.isRunning = false;
            System.exit(1);
        }
    }
}
