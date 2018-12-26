package com.worldSimulator.world.creatures;

import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;

import java.awt.*;

public abstract class Creature {

    private final int CHILDHOOD_TIME = 4, MATED_TIME = 2;

    protected int strength, initiative, age, lastMated;

    protected char childSymbol, symbol;
    boolean mature, mated, dead;

    protected Point position;

    protected Color color;
    protected World world;

    public Creature(int strength, int initiative, char symbol, Point position, World world, Color color) {
        this.strength = strength;
        this.initiative = initiative;
        this.symbol = symbol;
        this.position = position;
        this.world = world;
        this.color = color;
        childSymbol = Character.toLowerCase(symbol);

        dead = false;
        mature = false;
        age = 0;
        mated = false;
        lastMated = -1;
    }

    public void action() {
        age++;
        if (age >= CHILDHOOD_TIME)
            mature = true;
        if (lastMated >= MATED_TIME)
            mated = false;
        if (mated)
            lastMated++;
    }

    public boolean defend(Creature aggressor) {
        return false;
    }

    public void die(Creature aggressor) {
        dead = true;
    }

    public void mate() {
        mated = true;
        lastMated = 0;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setLastMated(int lastMated) {
        this.lastMated = lastMated;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getAge() {
        return age;
    }

    public int getLastMated() {
        return lastMated;
    }

    public char getSymbol(boolean mature) {
        if(mature)
            return symbol;
        return childSymbol;
    }

    public char getSymbol() {
        if(mature)
            return symbol;
        return childSymbol;
    }

    public Color getColor() {
        return color;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isMature() {
        return mature;
    }

    public boolean isMated() {
        return mated;
    }

    public boolean isDead() {
        return dead;
    }
}
