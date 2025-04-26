package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    private static JPanel mainMenu;
    private static CardLayout cardLayout;
    public static Font pixelFont;
    private static TiledGifPanel background;
    public static Color backColor = new Color(56, 148, 111);
    public static Color fontColor = new Color(255, 240, 219);
    public static Color buttonColor = new Color(63, 185, 136);
    public static Color greyLockedColor = new Color(132, 126, 135);
    public static Color buttonBorderColor = new Color(75, 104, 47);
    public static Dimension buttonSize = new Dimension(150, 45);
    public static Board board;
    public static boolean hasStartedGame = false;
    private static JButton resumeButton;

    public static void main(String[] args) {
        try {
            InputStream importFont = Main.class.getResourceAsStream("/res/Jersey10-Regular.ttf");
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, importFont).deriveFont(30f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        background = new TiledGifPanel("/res/Logo.jpg", 19, true);

        JFrame frame = new JFrame();
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/res/Logo.jpg")));
        frame.setTitle("Psiachy");
        cardLayout = new CardLayout();
        mainMenu = new JPanel(cardLayout);

        mainMenu.add(createMenuPanel(frame), "MENU");
        mainMenu.add(createGamePanel(frame), "GAME");

        frame.setContentPane(mainMenu);
        frame.setSize(1080, 1000);
        frame.setMinimumSize(new Dimension(1080, 1000));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static JComponent createMenuPanel(JFrame frame) {
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Psiachy");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(pixelFont.deriveFont(75f));
        title.setForeground(fontColor);

        JLabel author = new JLabel("Łukasz Basista");
        author.setAlignmentX(Component.CENTER_ALIGNMENT);
        author.setFont(pixelFont.deriveFont(16f));
        author.setForeground(fontColor);

        JButton playButton = new JButton("New Game");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setFont(pixelFont);
        playButton.addActionListener(e -> {
            board.resetGame();
            cardLayout.show(mainMenu, "GAME");
        });
        playButton.setBackground(buttonColor);
        playButton.setForeground(fontColor);
        playButton.setFocusPainted(false);
        playButton.setBorder(BorderFactory.createLineBorder(buttonBorderColor, 3));
        playButton.setMaximumSize(buttonSize);

        resumeButton = new JButton("Resume");
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeButton.setFont(pixelFont);
        resumeButton.addActionListener(e -> {
            if (hasStartedGame){
                board.getClock().setPause(false);
                cardLayout.show(mainMenu, "GAME");
            }
        });
        updateResumeButton();
        resumeButton.setForeground(fontColor);
        resumeButton.setFocusPainted(false);
        resumeButton.setBorder(BorderFactory.createLineBorder(buttonBorderColor, 3));
        resumeButton.setMaximumSize(buttonSize);

        JButton settingsButton = new JButton("Settings");
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setFont(pixelFont);
        settingsButton.addActionListener(e -> {});
        settingsButton.setBackground(buttonColor);
        settingsButton.setForeground(fontColor);
        settingsButton.setFocusPainted(false);
        settingsButton.setBorder(BorderFactory.createLineBorder(buttonBorderColor, 3));
        settingsButton.setMaximumSize(buttonSize);
        settingsButton.addActionListener(e -> new SettingsMenu(frame, board).setVisible(true));
        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setFont(pixelFont);
        exitButton.addActionListener(e -> frame.dispose());
        exitButton.setBackground(buttonColor);
        exitButton.setForeground(fontColor);
        exitButton.setFocusPainted(false);
        exitButton.setBorder(BorderFactory.createLineBorder(buttonBorderColor, 3));
        exitButton.setMaximumSize(buttonSize);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(title);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        buttonsPanel.add(author);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        buttonsPanel.add(playButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonsPanel.add(resumeButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonsPanel.add(settingsButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonsPanel.add(exitButton);

        JPanel backBar = new JPanel();
        backBar.setBackground(backColor);
        backBar.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        backBar.setPreferredSize(new Dimension(275, 0));
        backBar.setAlignmentX(0.5f);
        backBar.setOpaque(true);

        JPanel overlayPanel = new JPanel();
        overlayPanel.setLayout(new OverlayLayout(overlayPanel));
        overlayPanel.setOpaque(false);
        overlayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        overlayPanel.add(buttonsPanel);
        overlayPanel.add(backBar);
        JPanel centerAlignWrapper = new JPanel();
        centerAlignWrapper.setLayout(new BoxLayout(centerAlignWrapper, BoxLayout.X_AXIS));
        centerAlignWrapper.setOpaque(false);
        centerAlignWrapper.add(Box.createHorizontalGlue());
        centerAlignWrapper.add(overlayPanel);
        centerAlignWrapper.add(Box.createHorizontalGlue());

        background.removeAll();
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.add(Box.createVerticalGlue());
        background.add(centerAlignWrapper);
        background.add(Box.createVerticalGlue());

        return background;
    }

    private static void updateResumeButton() {
        if (resumeButton != null) {
            resumeButton.setEnabled(hasStartedGame);
            resumeButton.setBackground(hasStartedGame ? buttonColor : greyLockedColor);
        }
    }

    private static JPanel createGamePanel(JFrame frame) {
        JPanel gamePanel = new JPanel(new BorderLayout());

        frame.getContentPane().setBackground(backColor);

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
        topPanel.setBackground(greyLockedColor);

        JLabel turnLabel = new JLabel("Current player: White", SwingConstants.CENTER);
        turnLabel.setFont(pixelFont);
        turnLabel.setForeground(Color.WHITE);
        topPanel.add(turnLabel, barTextPos);

        //Center panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setBackground(backColor);
        //Board
        board = new Board(turnLabel);
        //Space around board
        JPanel boardWrap = new JPanel();
        boardWrap.setBackground(backColor);
        boardWrap.setBorder(BorderFactory.createLineBorder(backColor, 20));
        boardWrap.add(board);
        centerPanel.add(boardWrap);

        //Right panel
        TiledGifPanel rightPanel = new TiledGifPanel("/res/Logo.jpg", 15, false);
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel bClockPanel = new JPanel(new GridBagLayout());
        bClockPanel.setOpaque(false);
        JPanel gClockPanel = new JPanel(new GridBagLayout());
        gClockPanel.setOpaque(false);
        JPanel wClockPanel = new JPanel(new GridBagLayout());
        wClockPanel.setOpaque(false);
        JLabel btTag = new JLabel("Black:", SwingConstants.CENTER);
        btTag.setForeground(Color.WHITE);
        btTag.setFont(pixelFont);
        btTag.setPreferredSize(new Dimension(80, 30));
        JLabel gtTag = new JLabel("Game:", SwingConstants.CENTER);
        gtTag.setForeground(Color.WHITE);
        gtTag.setFont(pixelFont);
        gtTag.setPreferredSize(new Dimension(80, 30));
        JLabel wtTag = new JLabel("White:", SwingConstants.CENTER);
        wtTag.setForeground(Color.WHITE);
        wtTag.setPreferredSize(new Dimension(80, 30));
        wtTag.setFont(pixelFont);

        //Black timer
        JLabel blackClock = new JLabel(String.valueOf(board.getClock().getBlackTime()), SwingConstants.CENTER);
        blackClock.setFont(pixelFont);
        blackClock.setForeground(Color.WHITE);
        bClockPanel.add(btTag, sideTextPos);
        bClockPanel.add(blackClock, sideTextPos);
        //Game timer
        JLabel gameClock = new JLabel(String.valueOf(board.getClock().getTotalGameTime()), SwingConstants.CENTER);
        gameClock.setFont(pixelFont);
        gameClock.setForeground(Color.WHITE);
        gClockPanel.add(gtTag, sideTextPos);
        gClockPanel.add(gameClock, sideTextPos);
        //Check detector
        JLabel isCheck = new JLabel(board.isCheck ? "Check" : "", SwingConstants.CENTER);
        isCheck.setFont(pixelFont);
        isCheck.setForeground(Color.WHITE);
        gClockPanel.add(isCheck, sideTextPos);
        //White timer
        JLabel whiteClock = new JLabel(String.valueOf(board.getClock().getWhiteTime()), SwingConstants.CENTER);
        whiteClock.setFont(pixelFont);
        whiteClock.setForeground(Color.WHITE);
        wClockPanel.add(wtTag, sideTextPos);
        wClockPanel.add(whiteClock, sideTextPos);

        sideTextPos.gridy = 0;
        rightPanel.add(bClockPanel, sideTextPos);
        sideTextPos.gridy = 1;
        rightPanel.add(gClockPanel, sideTextPos);
        sideTextPos.gridy = 2;
        rightPanel.add(wClockPanel, sideTextPos);

        TiledGifPanel leftPanel = new TiledGifPanel("/res/Logo.jpg", 15, false);
        leftPanel.setLayout(new GridBagLayout());

        //Reset button
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> {
            board.resetGame();
        });
        sideTextPos.gridy = 0;
        restartButton.setBackground(buttonColor);
        restartButton.setForeground(Color.WHITE);

        leftPanel.add(restartButton, sideTextPos);

        restartButton.setFont(pixelFont);

        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(e -> {
            Main.board.getClock().setPause(true);
            new InGameMenu(frame,
                    () -> Main.board.getClock().setPause(false),
                    () -> {}, //Settings
                    () -> {
                        Main.board.getClock().setPause(false);
                        updateResumeButton();
                        cardLayout.show(mainMenu, "MENU");
                    }
            ).setVisible(true);
        });

        sideTextPos.gridy = 1;
        menuButton.setBackground(buttonColor);
        menuButton.setForeground(Color.WHITE);
        menuButton.setFont(pixelFont);
        restartButton.setFocusPainted(false);
        leftPanel.add(menuButton, sideTextPos);

        //Empty space
        sideTextPos.gridy = 2;
        leftPanel.add(Box.createVerticalGlue(), sideTextPos);

        //Interface
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(leftPanel, BorderLayout.WEST);

        //Update clocks
        //White
        Timer whiteTimer = new Timer(250, e -> {
            if (board.getClock().isPause()) {
                whiteClock.setText("Pause");
            } else {
                long min = (board.getClock().getWhiteTime() / 1000) / 60; //minutes
                long sec = (board.getClock().getWhiteTime() / 1000) % 60; //seconds
                whiteClock.setText(min == 0 ? String.format("%ds", sec) : String.format("%dm %ds", min, sec));
            }
        });
        whiteTimer.start();
        //Black
        Timer blackTimer = new Timer(250, e -> {
            if (board.getClock().isPause()) {
                blackClock.setText("Pause");
            } else {
                long min = (board.getClock().getWhiteTime() / 1000) / 60; //minutes
                long sec = (board.getClock().getWhiteTime() / 1000) % 60; //seconds
                blackClock.setText(min == 0 ? String.format("%ds", sec) : String.format("%dm %ds", min, sec));
            }
        });
        blackTimer.start();
        //Game
        Timer gameTimer = new Timer(250, e -> {
            if (board.getClock().isPause()) {
                gameClock.setText("Pause");
            } else {
                long min = (board.getClock().getTotalGameTime() / 1000) / 60; //minutes
                long sec = (board.getClock().getTotalGameTime() / 1000) % 60; //seconds
                gameClock.setText(min == 0 ? String.format("%ds", sec) : String.format("%dm %ds", min, sec));
            }
        });
        gameTimer.start();

        //Scale interface
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int width = frame.getWidth();
                int height = frame.getHeight();

                topPanel.setPreferredSize(new Dimension(0, height / 20));
                leftPanel.setPreferredSize(new Dimension(width / 8, 0));
                rightPanel.setPreferredSize(new Dimension(width / 8, 0));

                mainPanel.revalidate();
            }
        });
        return mainPanel;
    }
}