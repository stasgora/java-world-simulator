package com.worldSimulator.swing;

import com.worldSimulator.map.MapType;
import com.worldSimulator.math.Point;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    private final int WINDOW_X = 1200, WINDOW_Y = 700;

    private final int DEF_WORLD_SIZE = 40;

    public final Color ACCENT_COLOR = new Color(106 / 255f, 191 / 255f, 247 / 255f);
    public Border PANEL_BORDER;

    public Border EMPTY_BORDER;
    public final int BORDER_X = 10, BORDER_Y = 10;

    private static final String PROGRAM_TITLE = "WorldSimulator 4K - Stanisław Góra 165696";
    private final String NEW_WORLD_TITLE = "Create new world";

    private NewWorldPanel newWorldPanel;
    private GamePanel gamePanel;

    private JPanel mainPanel, gameContainer, logPanel;
    private MenuPanel menuPanel;

    private JScrollPane logPane, gamePane;

    private JTextArea logArea;

    private final double WORLD_PANEL_X = 0.6;
    private final int SCROLL_SPEED = 14;

    public static void main (String[] args) {
        EventQueue.invokeLater(() -> new MainFrame(PROGRAM_TITLE));
    }

    public MainFrame(String title) throws HeadlessException {
        super(title);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(WINDOW_X, WINDOW_Y));

        EMPTY_BORDER = BorderFactory.createEmptyBorder(BORDER_X, BORDER_Y, BORDER_X, BORDER_Y);
        PANEL_BORDER = BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(EMPTY_BORDER, BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, ACCENT_COLOR, ACCENT_COLOR.darker())), EMPTY_BORDER);

        menuPanel = new MenuPanel(this);
        add(menuPanel);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, WORLD_PANEL_X, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        gameContainer = new JPanel(new BorderLayout());
        gameContainer.setBorder(PANEL_BORDER);

        gamePanel = new GamePanel(this);
        gamePane = new JScrollPane(gamePanel);
        gamePane.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
        gamePane.getHorizontalScrollBar().setUnitIncrement(SCROLL_SPEED);
        gameContainer.add(gamePane, BorderLayout.CENTER);
        mainPanel.add(gameContainer, constraints);

        logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(PANEL_BORDER);
        logArea = new JTextArea();
        logArea.setEditable(false);
        logPane = new JScrollPane(logArea);
        logPane.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
        logPane.getHorizontalScrollBar().setUnitIncrement(SCROLL_SPEED);
        logPanel.add(logPane, BorderLayout.CENTER);
        constraints.weightx = 1 - WORLD_PANEL_X;
        mainPanel.add(logPanel, constraints);

        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        newWorldPanel = new NewWorldPanel(this, NEW_WORLD_TITLE, true, new Point(DEF_WORLD_SIZE, DEF_WORLD_SIZE), MapType.SQUARE);

    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public NewWorldPanel getNewWorldPanel() {
        return newWorldPanel;
    }

    public Color getAccentColor() {
        return ACCENT_COLOR;
    }

    public Border getPanelBorder() {
        return PANEL_BORDER;
    }

    public int getBorderX() {
        return BORDER_X;
    }

    public Border getEmptyBorder() {
        return EMPTY_BORDER;
    }

    public void revalidateGamePane() {
        gamePane.revalidate();
    }

    public JTextArea getLogArea() {
        return logArea;
    }

    public JScrollPane getGamePane() {
        return gamePane;
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

}
