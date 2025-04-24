package main;

import javax.swing.*;
import java.awt.*;

public class InGameMenu extends JDialog {

    public InGameMenu(JFrame parent, Runnable onResume, Runnable onSettings, Runnable onMainMenu) {
        super(parent, "Menu", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(Main.backColor);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton resumeButton = new JButton("Resume");
        resumeButton.setFont(Main.pixelFont);
        resumeButton.setForeground(Main.fontColor);
        resumeButton.setBackground(Main.buttonColor);
        resumeButton.setBorder(BorderFactory.createLineBorder(Main.buttonBorderColor, 3));
        resumeButton.setMaximumSize(Main.buttonSize);
        resumeButton.setFocusPainted(false);
        resumeButton.addActionListener(e -> {
            dispose();
            onResume.run();
        });

        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(Main.pixelFont);
        settingsButton.setForeground(Main.fontColor);
        settingsButton.setBackground(Main.buttonColor);
        settingsButton.setBorder(BorderFactory.createLineBorder(Main.buttonBorderColor, 3));
        settingsButton.setMaximumSize(Main.buttonSize);
        settingsButton.setFocusPainted(false);
        settingsButton.addActionListener(e -> {
            new SettingsMenu((JFrame) getParent()).setVisible(true);
        });

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(Main.pixelFont);
        mainMenuButton.setForeground(Main.fontColor);
        mainMenuButton.setBackground(Main.buttonColor);
        mainMenuButton.setBorder(BorderFactory.createLineBorder(Main.buttonBorderColor, 3));
        mainMenuButton.setMaximumSize(Main.buttonSize);
        mainMenuButton.setFocusPainted(false);
        mainMenuButton.addActionListener(e -> {
            dispose();
            onMainMenu.run();
        });

        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(resumeButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(settingsButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(mainMenuButton);
        add(Box.createVerticalGlue());
    }
}
