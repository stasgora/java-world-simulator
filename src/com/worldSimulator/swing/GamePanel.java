package com.worldSimulator.swing;

import com.sun.org.apache.bcel.internal.generic.FLOAD;
import com.worldSimulator.map.MapType;
import com.worldSimulator.math.Point;
import com.worldSimulator.world.World;
import com.worldSimulator.world.creatures.Creature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;

public class GamePanel extends JPanel implements MouseListener {

    public MainFrame mainFrame;


    private int tileSize = 25;
    private World world;
    private final float triHeight = (float) Math.sqrt(3) / 2f;
    private Point offset = new Point(tileSize, tileSize);

    private final int SQR_TILE_SIZE = Math.round(tileSize * triHeight);
    private final int HEX_TILE_SIZE = Math.round(tileSize * 1.1f);
    private final float EMPTY_TILE = 0.8f;
    private final Color EMPTY_COLOR = new Color(0.9f, 0.9f, 0.9f);

    private final char NEXT_ROUND_KEY = 'q';
    private final char[] humanMoveKeys = new char[]{'a', 'w', 'e', 's', 'd', 'z', 'x'};
    Action humanMoveKeyAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(world.getHuman() != null) {
                world.setHumanMove(e.getActionCommand().charAt(0));
                nextRound();
            }
        }
    };

    public GamePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        addMouseListener(this);

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(NEXT_ROUND_KEY), "nextRoundAction");
        getActionMap().put("nextRoundAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextRound();
            }
        });
        for(char key : humanMoveKeys) {
            getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
            getActionMap().put(key, humanMoveKeyAction);
        }
    }

    public void createNewWorld(Point size, MapType mapType) {
        if(world == null)
            world = new World(size, mapType, this, mainFrame.getLogArea());
        else
            world.reset(size, mapType);
        setup(size, mapType);
    }

    public void setup(Point size, MapType mapType) {
        if(mapType == MapType.SQUARE) {
            tileSize = SQR_TILE_SIZE;
            setPreferredSize(new Dimension(offset.x * 2 + tileSize * size.x, offset.y * 2 + tileSize * size.y));
        } else if(mapType == MapType.HEXAGONAL) {
            tileSize = HEX_TILE_SIZE;
            setPreferredSize(new Dimension(Math.round(offset.x * 2 + tileSize * size.x * triHeight), Math.round(offset.y * 2 + tileSize * size.y * 0.75f)));
        }
        mainFrame.revalidateGamePane();
        repaint();
    }

    public void nextRound() {
        world.getComentator().clearLog();
        world.update();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(world == null)
            return;
        Graphics2D g2D = (Graphics2D) g;
        Point size = world.getMap().getSize();
        Point pos;
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                pos = new Point(i, j);
                drawTile(pos, EMPTY_COLOR, Math.round(tileSize * EMPTY_TILE), true, g2D);
            }
        }
        for(Creature creature : world.getList())
            drawTile(new Point(creature.getPosition()), creature.getColor(), tileSize, false, g2D);
    }

    private void drawTile(Point position, Color color, int radius, boolean empty, Graphics2D g2D) {
        g2D.setColor(color);

        if(world.getMap().getMapType() == MapType.SQUARE) {
            if(!empty)
                g2D.fillRect(offset.x + position.x * tileSize, offset.y + position.y * tileSize, tileSize, tileSize);
            else
                g2D.fillRect(Math.round(offset.x + (position.x + 0.5f) * tileSize - radius * 0.5f), Math.round(offset.y + (position.y + 0.5f) * tileSize - radius * 0.5f), radius, radius);
        } else if(world.getMap().getMapType() == MapType.HEXAGONAL) {
            if(!empty)
                position = world.getMap().fromHexCoord(position);

            GeneralPath hex = getHexPath(position, radius);
            g2D.fill(hex);
        }
    }

    private GeneralPath getHexPath(Point pos, int radius) {
        Point offset = new Point(this.offset).add(tileSize / 2);
        GeneralPath hex = new GeneralPath();
        hex.moveTo(calcHexX(pos, offset, radius, -Math.PI / 6d), calcHexY(pos, offset, radius, -Math.PI / 6d));
        for (int i = 0; i < 5; i++) {
            hex.lineTo(calcHexX(pos, offset, radius, Math.PI / 6d * (i * 2 + 1)),
                    calcHexY(pos, offset, radius, Math.PI / 6d * (i * 2 + 1)));
        }
        hex.closePath();
        return hex;
    }

    private double calcHexX(Point pos, Point offset, int radius, double angle) {
        return offset.x + pos.x * tileSize * triHeight + (pos.y % 2 == 0 ? 0 : tileSize / 2d * triHeight) + radius / 2d * Math.cos(angle);
    }

    private double calcHexY(Point pos, Point offset, int radius, double angle) {
        return offset.y + pos.y * tileSize * 0.75d + radius / 2d * Math.sin(angle);
    }

    public World getWorld() {
        return world;
    }

    public void spawnCreature(int x, int y) {
        Point pixel = new Point(x, y);
        Point coord = null;
        pixel.substract(offset);
        if(world.getMap().getMapType() == MapType.SQUARE){
            coord = new Point(pixel).divide(tileSize);
        } else {
            pixel.x /= tileSize * triHeight;
            pixel.y /= tileSize * 3d / 4d;
            GeneralPath path;
            Point temp;
            for (int i = -1; i <= 1; i++)
                for (int j =  -1; j <= 1; j++) {
                    temp = new Point(pixel).add(new Point(i, j));
                    path = getHexPath(temp, tileSize);
                    if(path.contains(x, y)) {
                        coord = world.getMap().toHexCoord(temp);
                        break;
                    }
                }
            if(coord == null)
                return;
        }

        world.giveBirth(world.getFactory().spawnNew(mainFrame.getMenuPanel().getCreatureChoice(), coord, world), true);
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(world != null)
            spawnCreature(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(world != null)
            spawnCreature(e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
