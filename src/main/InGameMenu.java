package main;

import javax.swing.*;
import java.awt.*;

public class InGameMenu extends JDialog {
    public InGameMenu(JFrame parent, Font font, Runnable onResume, Runnable onSettings, Runnable onMainMenu) {
        super(parent, "Menu", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton resumeButton = new JButton("Resume");
        resumeButton.setFont(font);
        resumeButton.addActionListener(e -> {
            dispose();
            onResume.run();
        });

        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(font);
        settingsButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Settings window [WIP]");
            if (onSettings != null) onSettings.run();
        });

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(font);
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
