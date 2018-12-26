package com.worldSimulator.world.creatures.plants;

import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;
import com.worldSimulator.world.creatures.Animal;
import com.worldSimulator.world.creatures.Creature;
import com.worldSimulator.world.creatures.Plant;

import java.awt.*;
import java.util.ArrayList;

public class SosnowskyHogweed extends Plant {

    public SosnowskyHogweed(int strength, char symbol, Point position, World world, Color color) {
        super(strength, symbol, position, world, color);
    }

    @Override
    public void action() {
        super.action();
        ArrayList<Creature> neighbours = world.getMap().getNeighbours(position);
        for (Creature creature: neighbours) {
            if (Animal.isAnimal(creature.getSymbol(true)) && creature.getSymbol(true) != symbol)
                world.kill(this, creature);
        }
    }

    @Override
    public void die(Creature aggressor) {
        super.die(aggressor);
        if(aggressor.getSymbol(true) != 'X')
            world.kill(this, aggressor);
    }

}
