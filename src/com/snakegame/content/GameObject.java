package com.snakegame.content;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class GameObject {

    protected int x, y;
    protected Direction direction;
    protected ObjectType type;

    public GameObject(int x, int y, ObjectType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public GameObject(Dimension dim, ObjectType type) {
        this(dim.width, dim.height, type);
    }

    public GameObject(int x, int y, ObjectType type, Direction direction) {
        this(x, y, type);
        this.direction = Direction.RIGHT;
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public int getX() { return x; };
    public int getY() { return y; };

    public int getSpeedX() { return direction.x; };
    public int getSpeedY() { return direction.y; };

    public Direction getDirection() { return direction; };

    public void setDirection(Direction direction) { this.direction = direction; };

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract void keyPressed(KeyEvent e);

    public abstract void keyReleased(KeyEvent e);
}