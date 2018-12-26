package com.worldSimulator.world.creatures.animals;

import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;
import com.worldSimulator.world.creatures.Animal;

import java.awt.*;

public class Fox extends Animal {

    public Fox(int strength, int initiative, char symbol, Point position, World world, Color color) {
        super(strength, initiative, symbol, position, world, color);
    }

    @Override
    protected Point getMovePos() {
        Point newPos = world.getMap().getPosNearby(position, false, 1, strength);
        return newPos;
    }

}
