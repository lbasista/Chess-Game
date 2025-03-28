package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setTitle("Chess Game");
        frame.getContentPane().setBackground(new Color(8,96,95));
        frame.setLayout(new GridBagLayout());

        //Minimum widow size
        frame.setMinimumSize(new Dimension(1000, 1000));
        //Centered on screen
        frame.setLocationRelativeTo(null);

        Board board = new Board();
        frame.add(board);
        frame.setVisible(true);
    }
}