package com.worldSimulator.world.creatures;

import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;

import java.awt.*;

public class Plant extends Creature {

    private final double SPREAD_CHANCE_PERCENT = 0.1;

    public Plant(int strength, char symbol, Point position, World world, Color color) {
        super(strength, 0, symbol, position, world, color);
    }

    @Override
    public void action() {
        super.action();
        spread();
    }

    protected void spread () {
        if (!mature || mated || Math.random() > SPREAD_CHANCE_PERCENT)
            return;
        Point seedPos = world.getMap().getPosNearby(position, true);
        if(seedPos == null)
            return;
        mate();
        world.giveBirth(world.getFactory().spawnNew(symbol, seedPos, world));
    }

    public static boolean isPlant(char symbol) {
        return symbol == 'J' || symbol == 'T' || symbol == 'G' || symbol == 'M' || symbol == 'B';
    }

}
