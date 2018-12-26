package com.worldSimulator.world.creatures.animals;

import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;
import com.worldSimulator.world.creatures.Animal;

import java.awt.*;

public class CyberSheep extends Animal {

    public CyberSheep(int strength, int initiative, char symbol, Point position, World world, Color color) {
        super(strength, initiative, symbol, position, world, color);
    }

    @Override
    protected Point getMovePos() {
        Point newPos = world.findClosestCreature('B', position);
        if(newPos == null)
            return super.getMovePos();

        int xDiff = newPos.x - position.x, xStep = 1;
        int yDiff = newPos.y - position.y, yStep = 1;

        newPos = new Point(position);

        int diff, aStep, bStep;

        if (xDiff < 0) {
            xStep *= -1;
            xDiff *= -1;
        } if (yDiff < 0) {
            yStep *= -1;
            yDiff *= -1;
        }
        if (xDiff > yDiff) {// - X
            aStep = 2 * (yDiff - xDiff);
            bStep = 2 * yDiff;
            diff = bStep - xDiff;

            newPos.x += xStep;
            if (diff >= 0) {
                newPos.y += yStep;
                diff += aStep;
            }
            else {
                diff += bStep;
            }
        } else {// - Y
            aStep = 2 * (xDiff - yDiff);
            bStep = 2 * xDiff;
            diff = bStep - yDiff;

            newPos.y += yStep;
            if (diff >= 0) {
                newPos.x += xStep;
                diff += aStep;
            }
            else {
                diff += bStep;
            }
        }
        return newPos;
    }

}
