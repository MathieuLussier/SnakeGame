package com.snakegame.content;

import com.snakegame.Engine;
import com.snakegame.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Food extends GameObject {
    private int scale = GameConstants.scale;

    public Food(int x, int y, ObjectType food) {
        super(x, y, food);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.drawArc(x, y, scale, scale, 0, 360);
        g.fillArc(x, y, scale, scale, 0, 360);

        if (g instanceof Graphics2D && GameConstants.isDebugging) {
            g.setColor(Color.white);
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GameObject[] gameObjs = Engine.getObjects();
            int foodLenght = 0;
            for (int i = 0; i < gameObjs.length; i++) {
                GameObject obj = gameObjs[i];
                if (obj.type == ObjectType.FOOD)
                    foodLenght++;
                else
                    continue;
            }
            int thisIndex = Engine.objects.indexOf(this);
            for (int i = 0; i < foodLenght; i++) {
                g.drawString("Food#" + thisIndex + ": x: " + x + " y: " + y, 0, (85 + (thisIndex * 15)));
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void spawnFood() {
         int ranX = Food.random(50, GameConstants.W_WIDTH - 50);
         int ranY = Food.random(50, GameConstants.W_HEIGHT - 50);
         Engine.addObject(new Food(ranX, ranY, ObjectType.FOOD));
    }


    public static int random(int min, int max) {
        try {
            if (min >= max) {
                throw new IllegalArgumentException("max must be greater than min");
            }
            Random r = new Random();
            int nb = r.nextInt((max - min) + 1) + min;
            int reste = nb % GameConstants.scale;
            return nb - reste;
        } catch (Exception err) {
            err.printStackTrace();
            return 0;
        }
    }
}
