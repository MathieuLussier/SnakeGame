package com.snakegame.content;

import com.snakegame.GameConstants;

public enum Direction {
    UP(0, -GameConstants.snakeSpeed),
    DOWN(0, GameConstants.snakeSpeed),
    LEFT( -GameConstants.snakeSpeed, 0),
    RIGHT( GameConstants.snakeSpeed, 0);

    public int x, y;
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
