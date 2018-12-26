package com.worldSimulator.swing;

import com.worldSimulator.world.creatures.CreatureType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.nio.file.Paths;

public class MenuPanel extends JPanel implements ActionListener {

    private final String IMAGES_FOLDER = "images";
    private final String LOAD_WORLD_ICON = "loadWorldIcon.png", LOAD_WORLD_TOOLTIP = "Load world state";
    private final String SAVE_WORLD_ICON = "saveWorldIcon.png", SAVE_WORLD_TOOLTIP = "Save world state";
    private final String NEXT_ROUND_ICON = "nextRoundIcon.png", NEXT_ROUND_TOOLTIP = "Next round";
    private final String USE_ABILITY_ICON = "useAbilityIcon.png", USE_ABILITY_TOOLTIP = "Use human ability";
    private final String NEW_WORLD_ICON = "newWorldIcon.png", NEW_WORLD_TOOLTIP = "Create new world";
    private final String SPAWN_TOOLTIP = "Choose creature to spawn";

    private MainFrame mainFrame;

    private JPanel savePanel, worldPanel, creaturePanel;
    private JButton nextRoundButton, saveButton, loadButton, useAbilityButton, newWorldButton;
    private JComboBox<CreatureType> spawnTypeBox;

    private final Insets buttonInsets = new Insets(2, 5, 2, 5);

    public MenuPanel(MainFrame mainFrame) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.mainFrame = mainFrame;

        worldPanel = new JPanel();
        configureMenuPanel(worldPanel);
        savePanel = new JPanel();
        configureMenuPanel(savePanel);
        creaturePanel = new JPanel();
        configureMenuPanel(creaturePanel);
        add(Box.createHorizontalGlue());

        newWorldButton = new JButton(createImageIcon(Paths.get(IMAGES_FOLDER, NEW_WORLD_ICON).toString()));
        configureButton(newWorldButton, worldPanel, NEW_WORLD_TOOLTIP);
        nextRoundButton = new JButton(createImageIcon(Paths.get(IMAGES_FOLDER, NEXT_ROUND_ICON).toString()));
        configureButton(nextRoundButton, worldPanel, NEXT_ROUND_TOOLTIP, true);
        saveButton = new JButton(createImageIcon(Paths.get(IMAGES_FOLDER, SAVE_WORLD_ICON).toString()));
        configureButton(saveButton, savePanel, SAVE_WORLD_TOOLTIP);
        loadButton = new JButton(createImageIcon(Paths.get(IMAGES_FOLDER, LOAD_WORLD_ICON).toString()));
        configureButton(loadButton, savePanel, LOAD_WORLD_TOOLTIP, true);
        useAbilityButton = new JButton(createImageIcon(Paths.get(IMAGES_FOLDER, USE_ABILITY_ICON).toString()));
        configureButton(useAbilityButton, creaturePanel, USE_ABILITY_TOOLTIP);

        spawnTypeBox = new JComboBox<>(CreatureType.values());
        spawnTypeBox.setBackground(mainFrame.getAccentColor());
        spawnTypeBox.setMaximumSize(new Dimension(150, 25));
        spawnTypeBox.addActionListener(this);
        spawnTypeBox.setToolTipText(SPAWN_TOOLTIP);
        creaturePanel.add(spawnTypeBox);
    }

    private void configureButton(JButton button, JPanel panel, String tooltip, boolean last) {
        button.setMargin(buttonInsets);
        button.setFocusPainted(false);
        button.setBackground(mainFrame.getAccentColor());
        button.setToolTipText(tooltip);
        button.addActionListener(this);
        panel.add(button);
        if(!last)
            panel.add(Box.createRigidArea(new Dimension(mainFrame.getBorderX(), 0)));
    }

    private void configureButton(JButton button, JPanel panel, String tooltip) {configureButton(button, panel, tooltip, false);}

    private void configureMenuPanel(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(mainFrame.getPanelBorder());
        add(panel);
    }

    private ImageIcon createImageIcon(String path) {
        URL urlPath = getClass().getResource(path);
        if (urlPath != null) {
            return new ImageIcon(urlPath);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newWorldButton) {
            mainFrame.getNewWorldPanel().setVisible(true);
        } else if(e.getSource() == saveButton) {
            mainFrame.getGamePanel().getWorld().saveWorldState();
        } else if(e.getSource() == loadButton) {
            mainFrame.getGamePanel().getWorld().loadWorldState();
        } else if(e.getSource() == nextRoundButton) {
            mainFrame.getGamePanel().nextRound();
        } else if(e.getSource() == useAbilityButton) {
            mainFrame.getGamePanel().getWorld().useHumanAbility();
        }
    }

    public String getCreatureChoice() {
        return spawnTypeBox.getSelectedItem().toString();
    }

}
