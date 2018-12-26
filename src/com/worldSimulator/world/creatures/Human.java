package com.worldSimulator.world.creatures;

import com.worldSimulator.map.MapType;
import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;

import java.awt.*;
import java.util.HashMap;

public class Human extends Animal {

    private Point nextMove;
    private HashMap<Character, Point> hexMoves, sqrMoves;

    private int abilityCounter;

    public Human(int strength, int initiative, char symbol, Point position, World world, Color color) {
        super(strength, initiative, symbol, position, world, color);
        hexMoves = new HashMap<>();
        hexMoves.put('a', new Point(-1, 0));
        hexMoves.put('w', new Point(0, -1));
        hexMoves.put('e', new Point(1, -1));
        hexMoves.put('d', new Point(1, 0));
        hexMoves.put('x', new Point(0, 1));
        hexMoves.put('z', new Point(-1, 1));

        sqrMoves = new HashMap<>();
        sqrMoves.put('w', new Point(0, -1));
        sqrMoves.put('s', new Point(0, 1));
        sqrMoves.put('a', new Point(-1, 0));
        sqrMoves.put('d', new Point(1, 0));
    }

    @Override
    public void action() {
        super.action();
        if(abilityCounter > 0) {
            if (abilityCounter > 5){
                strength--;
                world.getComentator().reportAbilityUse(this);
            }
            abilityCounter--;
        }
    }

    @Override
    protected Point getMovePos() {
        return nextMove;
    }

    public void setNextMove(char key) {
        if(world.getMap().getMapType() == MapType.SQUARE) {
            setNextMoveInternal(key, sqrMoves);
        } else if(world.getMap().getMapType() == MapType.HEXAGONAL) {
            setNextMoveInternal(key, hexMoves);
        }
    }

    private void setNextMoveInternal(char key, HashMap<Character, Point> moves) {
        if(moves.containsKey(key)){
            nextMove = new Point(position).add(moves.get(key));
            if(!world.getMap().positionFits(nextMove))
                nextMove = null;
        } else
            nextMove = null;
    }

    public void useAbility() {
        if (abilityCounter == 0) {
            abilityCounter = 10;
            strength += 5;
            world.getComentator().reportAbilityUse(this);
        }
    }

    public int getAbilityCounter() {
        return abilityCounter;
    }

    public void setAbilityCounter(int abilityCounter) {
        this.abilityCounter = abilityCounter;
    }

}
