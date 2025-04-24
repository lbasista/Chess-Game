package main;

import pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class SettingsMenu extends JDialog {
    private boolean isDogStyle = true;

    public class AppSettings {
        public static boolean useStandardGraphics = false;
        public static boolean languagePL = false;
        public static boolean pauseTimeInMenu = false;
    }

    public SettingsMenu(JFrame parent) {
        super(parent, "Settings", true);
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

        //Language
        JButton languageButton = new JButton("PL/EN");
        languageButton.setFont(Main.pixelFont);
        languageButton.setBackground(Main.buttonColor);
        languageButton.setForeground(Main.fontColor);
        languageButton.setMaximumSize(Main.buttonSize);
        languageButton.setBorder(BorderFactory.createLineBorder(Main.buttonBorderColor, 3));
        languageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        languageButton.addActionListener(e -> {
            AppSettings.languagePL = languageButton.isSelected();
            JOptionPane.showMessageDialog(this, "Language switch WIP");
        });

        //Stop timer in menu
        JCheckBox timePause = new JCheckBox("Stop timers in menu");
        timePause.setSelected(false);
        timePause.setFont(Main.pixelFont);
        timePause.setForeground(Main.fontColor);
        timePause.setOpaque(false);
        timePause.setAlignmentX(Component.CENTER_ALIGNMENT);
        timePause.addActionListener(e -> {
            AppSettings.pauseTimeInMenu = timePause.isSelected();
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
        add(languageButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(timePause);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(backButton);
        add(Box.createVerticalGlue());
    }

    public boolean isDogStyleSelected() {
        return isDogStyle;
    }
}
