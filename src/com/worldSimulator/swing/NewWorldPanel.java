package com.worldSimulator.swing;

import com.worldSimulator.map.MapType;
import com.worldSimulator.math.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewWorldPanel extends JDialog implements ActionListener {

    private JPanel sizePanel, typePanel, buttonPanel;

    private MainFrame mainFrame;

    private JTextField xSizeBox, ySizeBox;
    private JComboBox<MapType> mapTypeBox;
    private JButton createButton, cancelButton;
    private JLabel xSizeLabel, ySizeLabel, mapTypeLabel;

    private final int BORDER_X = 10, BORDER_Y = 20;
    private final int TEXT_FIELD_X = 40, TEXT_FIELD_Y = 20;

    private final String WORLD_SIZE_LABEL = "World Size: ";
    private final String MAP_TYPE_LABEL = "Map Type: ";

    public NewWorldPanel(MainFrame owner, String title, boolean modal, Point defSize, MapType mapType) {
        super(owner, title, modal);
        mainFrame = owner;
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        sizePanel = new JPanel(new FlowLayout());
        xSizeBox = new JTextField(String.valueOf(defSize.x));
        xSizeBox.setPreferredSize(new Dimension(TEXT_FIELD_X, TEXT_FIELD_Y));
        xSizeLabel = new JLabel(WORLD_SIZE_LABEL + "x ");
        xSizeLabel.setLabelFor(xSizeBox);
        sizePanel.add(xSizeLabel);
        sizePanel.add(xSizeBox);

        ySizeBox = new JTextField(String.valueOf(defSize.y));
        ySizeBox.setPreferredSize(new Dimension(TEXT_FIELD_X, TEXT_FIELD_Y));
        ySizeLabel = new JLabel("y ");
        ySizeLabel.setLabelFor(ySizeBox);
        sizePanel.add(ySizeLabel);
        sizePanel.add(ySizeBox);
        sizePanel.setBorder(mainFrame.getPanelBorder());
        add(sizePanel);

        typePanel = new JPanel(new FlowLayout());
        mapTypeBox = new JComboBox<>(MapType.values());
        mapTypeBox.setSelectedItem(mapType);
        mapTypeBox.setBackground(mainFrame.getAccentColor());
        mapTypeLabel = new JLabel(MAP_TYPE_LABEL);
        mapTypeLabel.setLabelFor(mapTypeBox);
        typePanel.add(mapTypeLabel);
        typePanel.add(mapTypeBox);
        typePanel.setBorder(mainFrame.getPanelBorder());
        add(typePanel);

        buttonPanel = new JPanel(new FlowLayout());
        cancelButton = new JButton("Cancel");
        setUpButton(cancelButton);
        createButton = new JButton("Create");
        setUpButton(createButton);
        buttonPanel.setBorder(mainFrame.getEmptyBorder());
        add(buttonPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setUpButton(JButton button) {
        button.addActionListener(this);
        button.setBackground(mainFrame.getAccentColor());
        buttonPanel.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == createButton) {
            mainFrame.getGamePanel().createNewWorld(new Point(Integer.parseInt(xSizeBox.getText()), Integer.parseInt(ySizeBox.getText())), (MapType) mapTypeBox.getSelectedItem());
            setVisible(false);
        } else if (e.getSource() == cancelButton) {
            setVisible(false);
        }
    }

}
