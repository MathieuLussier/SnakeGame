package com.snakegame;

import com.snakegame.content.GameObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.LinkedList;

public class Engine {
    public static LinkedList<GameObject> objects = new LinkedList<GameObject>();

    static public void tick() {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).tick();
        }
    }

    public static void render(Graphics g) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).render(g);
        }
    }

    public static void keyPressed(KeyEvent e) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).keyPressed(e);
        }
    }

    public static void keyReleased(KeyEvent e) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).keyReleased(e);
        }
    }

    public static void addObject(GameObject object) {
        Engine.objects.add(object);
    }

    public static GameObject getObject(int index) { return Engine.objects.get(index); }

    public static GameObject[] getObjects() {
        GameObject[] arr = new GameObject[objects.size()];
        for (int i = 0; i < objects.size(); i++) {
            arr[i] = objects.get(i);
        }
        return arr;
    }

    public static void removeObject(GameObject object) { Engine.objects.remove(object); }

    public static void log(String msg) {
        System.out.println(msg);
    }
}
