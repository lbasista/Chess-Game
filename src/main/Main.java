package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setTitle("Chess Game");
        Color backColor = new Color(10, 95, 95);
        frame.getContentPane().setBackground(backColor);
        //frame.setLayout(new GridBagLayout());

        //Minimum window size
        frame.setMinimumSize(new Dimension(1000, 1000));
        //Centered on screen
        frame.setLocationRelativeTo(null);

        //Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backColor);

        //Turn info (Upper panel)
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBackground(new Color(100, 95, 95));

        JLabel turnLabel = new JLabel("Current player: White", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setForeground(Color.WHITE);
            //Centered position in upper panel
            GridBagConstraints position = new GridBagConstraints();
            position.gridx = 0;
            position.gridy = 0;
            position.weightx = 1;
            position.weighty = 1;
            position.anchor = GridBagConstraints.CENTER;
            topPanel.add(turnLabel, position);

        //Center panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setBackground(backColor);
            //Board
            Board board = new Board(turnLabel);
                //Space around board
                JPanel boardWrap = new JPanel();
                boardWrap.setBackground(backColor);
                boardWrap.setBorder(BorderFactory.createLineBorder(backColor, 25));
                boardWrap.add(board);
                centerPanel.add(boardWrap);

        //Interface
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        //Frame content
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}