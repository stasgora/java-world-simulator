package com.worldSimulator.world.creatures;

import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;

import java.awt.*;

public abstract class Animal extends Creature {

    protected int range;

    public Animal(int strength, int initiative, char symbol, Point position, World world, Color color) {
        super(strength, initiative, symbol, position, world, color);
        range = 1;
    }

    @Override
    public void action() {
        super.action();
        Point newPos = getMovePos();
        if(newPos == null || newPos.equals(position))
            return;
        if(world.getMap().get(newPos) != null) {
            collide(world.getMap().get(newPos));
        } else
            move(newPos);
    }

    protected Point getMovePos() {
        return world.getMap().getPosNearby(this.position, false, range);
    }

    public void move(Point position) {
        world.getMap().move(this.position, position);
        this.position = position;
    }

    protected void reproduce(Creature parent) {
        Point newPos = world.getMap().getBirthPos(position, parent.getPosition());
        if(newPos == null)
            return;
        mate();
        parent.mate();
        world.giveBirth(world.getFactory().spawnNew(symbol, newPos, world));
    }

    public void collide(Creature creature) {
        if (symbol != creature.getSymbol(true)) {
            attack(creature);
        } else if(mature && creature.isMature() && !mated && !creature.isMated()) {
            reproduce(creature);
        }
    }

    public void attack(Creature creature) {
        if(creature.defend(this))
            return;
        if (strength >= creature.getStrength()) {
            world.kill(this, creature);
            move(creature.getPosition());
        } else {
            world.kill(creature, this);
        }
    }

    public static boolean isAnimal(char symbol) {
        return symbol == 'A' || symbol == 'X' || symbol == 'L' || symbol == 'O' || symbol == 'Z' || symbol == 'W' || symbol == 'C';
    }

}
