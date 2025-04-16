package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setTitle("Chess Game");
        Color backColor = new Color(10, 95, 95);
        Color barColor = new Color(100, 95, 95);
        Font barFont = new Font("Arial", Font.BOLD, 20);
        frame.getContentPane().setBackground(backColor);
        //Fen bar
        JLabel fenLabel = new JLabel("", SwingConstants.CENTER);

        //Minimum window size
        frame.setMinimumSize(new Dimension(1000, 1000));
        //Centered on screen
        frame.setLocationRelativeTo(null);

        //Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backColor);

        //Top panel (info about game)
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBackground(barColor);

        JLabel turnLabel = new JLabel("Current player: White", SwingConstants.CENTER);
        turnLabel.setFont(barFont);
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
            Board board = new Board(turnLabel, fenLabel);
                //Space around board
                JPanel boardWrap = new JPanel();
                boardWrap.setBackground(backColor);
                boardWrap.setBorder(BorderFactory.createLineBorder(backColor, 25));
                boardWrap.add(board);
                centerPanel.add(boardWrap);

        //Bottom Panel
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setPreferredSize(new Dimension(0, 60));
        bottomPanel.setBackground(barColor);
        fenLabel.setFont(barFont);
        fenLabel.setForeground(Color.WHITE);
            //Centered
            bottomPanel.add(fenLabel, position);

        //Interface
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        //Frame content
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}