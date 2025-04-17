package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main {
    private static Language language;
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setTitle("Chess Game");
        Color backColor = new Color(10, 95, 95);
        Color barColor = new Color(100, 95, 95);
        Color buttonColor = new Color(70, 130, 180);
        Color barColorVertical = new Color(20, 100, 75);
        Font barFontHorizontal = new Font("Arial", Font.BOLD, 20);
        Font barFontVertical = new Font("Arial", Font.BOLD, 24);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        frame.getContentPane().setBackground(backColor);
        //Fen bar
        JLabel fenLabel = new JLabel("", SwingConstants.CENTER);

        //Minimum window size
        frame.setMinimumSize(new Dimension(1080, 1000));
        //Centered on screen
        frame.setLocationRelativeTo(null);

        //Centered text positions in up & down panels
        GridBagConstraints barTextPos = new GridBagConstraints();
        barTextPos.gridx = 0;
        barTextPos.gridy = 0;
        barTextPos.weightx = 1;
        barTextPos.weighty = 1;
        barTextPos.anchor = GridBagConstraints.CENTER;

        //Centered text positions in sides panels
        GridBagConstraints sideTextPos = new GridBagConstraints();
        sideTextPos.gridx = 0;
        sideTextPos.weightx = 1;
        sideTextPos.weighty = 1;
        sideTextPos.anchor = GridBagConstraints.CENTER;

        //Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backColor);

        //Top panel (info about turn)
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(barColor);

        JLabel turnLabel = new JLabel("Current player: White", SwingConstants.CENTER);
        turnLabel.setFont(barFontHorizontal);
        turnLabel.setForeground(Color.WHITE);
        topPanel.add(turnLabel, barTextPos);

        //Center panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setBackground(backColor);
        //Board
        Board board = new Board(turnLabel, fenLabel);
        //Space around board
        JPanel boardWrap = new JPanel();
        boardWrap.setBackground(backColor);
        boardWrap.setBorder(BorderFactory.createLineBorder(backColor, 20));
        boardWrap.add(board);
        centerPanel.add(boardWrap);

        //Bottom Panel
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(barColor);
        fenLabel.setFont(barFontHorizontal);
        fenLabel.setForeground(Color.WHITE);
        //Centered
        bottomPanel.add(fenLabel, barTextPos);

        //Right panel
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(barColorVertical);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel bClockPanel = new JPanel(new GridBagLayout());
        bClockPanel.setBackground(barColorVertical);
        JPanel gClockPanel = new JPanel(new GridBagLayout());
        gClockPanel.setBackground(barColorVertical);
        JPanel wClockPanel = new JPanel(new GridBagLayout());
        wClockPanel.setBackground(barColorVertical);
        JLabel btTag = new JLabel("Black:", SwingConstants.CENTER);
        btTag.setForeground(Color.WHITE);
        btTag.setPreferredSize(new Dimension(80, 30));
        JLabel gtTag = new JLabel("Game:", SwingConstants.CENTER);
        gtTag.setForeground(Color.WHITE);
        gtTag.setPreferredSize(new Dimension(80, 30));
        JLabel wtTag = new JLabel("White:", SwingConstants.CENTER);
        wtTag.setForeground(Color.WHITE);
        wtTag.setPreferredSize(new Dimension(80, 30));

        //Black timer
        JLabel blackClock = new JLabel(String.valueOf(board.getClock().getBlackTime()), SwingConstants.CENTER);
        blackClock.setFont(barFontVertical);
        blackClock.setForeground(Color.WHITE);
        bClockPanel.add(btTag, sideTextPos);
        bClockPanel.add(blackClock, sideTextPos);
        //Game timer
        JLabel gameClock = new JLabel(String.valueOf(board.getClock().getTotalGameTime()), SwingConstants.CENTER);
        gameClock.setFont(barFontVertical);
        gameClock.setForeground(Color.WHITE);
        gClockPanel.add(gtTag, sideTextPos);
        gClockPanel.add(gameClock, sideTextPos);
        //White timer
        JLabel whiteClock = new JLabel(String.valueOf(board.getClock().getWhiteTime()), SwingConstants.CENTER);
        whiteClock.setFont(barFontVertical);
        whiteClock.setForeground(Color.WHITE);
        wClockPanel.add(wtTag, sideTextPos);
        wClockPanel.add(whiteClock, sideTextPos);

        sideTextPos.gridy = 0;
        rightPanel.add(bClockPanel, sideTextPos);
        sideTextPos.gridy = 1;
        rightPanel.add(gClockPanel, sideTextPos);
        sideTextPos.gridy = 2;
        rightPanel.add(wClockPanel, sideTextPos);

        //Left panel
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(barColorVertical);

        //Reset button
        JButton restartButton = new JButton("Restart game");
        restartButton.addActionListener(e -> {
            board.resetGame();
        });
        sideTextPos.gridy = 0;
        restartButton.setBackground(buttonColor);
        restartButton.setForeground(Color.WHITE);
        restartButton.setFont(buttonFont);
        restartButton.setFocusPainted(false);
        leftPanel.add(restartButton, sideTextPos);

        //Language button
        language = new Language(turnLabel, btTag, gtTag, wtTag);

        JButton langButton = new JButton("PL/EN");
        langButton.addActionListener(e -> language.toggleLanguage());

        sideTextPos.gridy = 1;
        langButton.setBackground(buttonColor);
        langButton.setForeground(Color.WHITE);
        langButton.setFont(buttonFont);
        restartButton.setFocusPainted(false);
        leftPanel.add(langButton, sideTextPos);

        //Empty space
        sideTextPos.gridy = 2;
        leftPanel.add(Box.createVerticalGlue(), sideTextPos);

        //Interface
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(leftPanel, BorderLayout.WEST);

        //Frame content
        frame.add(mainPanel);
        frame.setVisible(true);

        //Update clocks
        //White
        Timer whiteTimer = new Timer(1000, e -> {
            whiteClock.setText(String.valueOf(board.getClock().getWhiteTime() / 1000));  //In seconds
        });
        whiteTimer.start();
        //Black
        Timer blackTimer = new Timer(1000, e -> {
            blackClock.setText(String.valueOf(board.getClock().getBlackTime() / 1000));
        });
        blackTimer.start();
        //Game
        Timer gameTimer = new Timer(1000, e -> {
            gameClock.setText(String.valueOf(board.getClock().getTotalGameTime() / 1000));
        });
        gameTimer.start();

        //Scale interface
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int width = frame.getWidth();
                int height = frame.getHeight();

                topPanel.setPreferredSize(new Dimension(0, height / 20));
                bottomPanel.setPreferredSize(new Dimension(0, height / 20));
                leftPanel.setPreferredSize(new Dimension(width / 8, 0));
                rightPanel.setPreferredSize(new Dimension(width / 8, 0));

                mainPanel.revalidate();
            }
        });
    }
}