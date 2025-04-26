package main;

import javax.swing.*;
import java.awt.*;

public class SettingsMenu extends JDialog {
    private boolean isDogStyle = true;
    private Board board;

    public SettingsMenu(JFrame parent, Board board) {
        super(parent, "Settings", true);
        this.board = board;
        setSize(400, 300);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(Main.backColor);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        //Theme
        JCheckBox themeToggle = new JCheckBox("Use classic piece design");
        themeToggle.setSelected(PieceSpriteManager.getStyle() == PieceSpriteManager.Style.PIXEL);
        themeToggle.setFont(Main.pixelFont);
        themeToggle.setForeground(Main.fontColor);
        themeToggle.setOpaque(false);
        themeToggle.setAlignmentX(Component.CENTER_ALIGNMENT);
        themeToggle.addActionListener(e -> {
            PieceSpriteManager.setStyle(themeToggle.isSelected() ? PieceSpriteManager.Style.PIXEL : PieceSpriteManager.Style.DOGS);
            Main.board.updateAllSprites();
        });

        //Stop timer in menu
        JCheckBox timePause = new JCheckBox("Stop timers in menu");
        timePause.setSelected(board.getClock().isPause());
        timePause.setFont(Main.pixelFont);
        timePause.setForeground(Main.fontColor);
        timePause.setOpaque(false);
        timePause.setAlignmentX(Component.CENTER_ALIGNMENT);
        timePause.addActionListener(e -> {
            board.getClock().setPause(timePause.isSelected());
        });

        //Exit settings
        JButton backButton = new JButton("Back");
        backButton.setFont(Main.pixelFont);
        backButton.setBackground(Main.buttonColor);
        backButton.setForeground(Main.fontColor);
        backButton.setMaximumSize(Main.buttonSize);
        backButton.setBorder(BorderFactory.createLineBorder(Main.buttonBorderColor, 3));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> dispose());

        add(Box.createVerticalGlue());
        add(themeToggle);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(timePause);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(backButton);
        add(Box.createVerticalGlue());
    }
}