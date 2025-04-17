package main;

import javax.swing.*;

public class Language {
    private boolean isPolish = false;
    private JLabel turnLabel;
    private JLabel btTag;
    private JLabel gtTag;
    private JLabel wtTag;

    public Language(JLabel turnLabel, JLabel btTag, JLabel gtTag, JLabel wtTag) {
        this.turnLabel = turnLabel;
        this.btTag = btTag;
        this.gtTag = gtTag;
        this.wtTag = wtTag;
    }

    public void toggleLanguage() {
        isPolish = !isPolish;
        updateTexts();
    }

    private void updateTexts() {
        if (isPolish) {
            //Tłumaczenie na Polski
            switch (turnLabel.getText()) {
                case "Current player: White" -> turnLabel.setText("Aktualny gracz: Biały");
                case "Current player: Black" -> turnLabel.setText("Aktualny gracz: Czarny");
                case "Stalemate" -> turnLabel.setText("Pat");
                case "Insufficient Material" -> turnLabel.setText("Brak wystarczającej ilości figur");
                case "Draw by 50-move rule" -> turnLabel.setText("Remis (Zasada 50 ruchów)");
            }
            btTag.setText("Czarny:");
            gtTag.setText("Gra:");
            wtTag.setText("Biały:");
        } else {
            //Translation to English
            switch (turnLabel.getText()) {
                case "Aktualny gracz: Biały" -> turnLabel.setText("Current player: White");
                case "Aktualny gracz: Czarny" -> turnLabel.setText("Current player: Black");
                case "Pat" -> turnLabel.setText("Stalemate");
                case "Brak wystarczającej ilości figur" -> turnLabel.setText("Insufficient Material");
                case "Remis (Zasada 50 ruchów)" -> turnLabel.setText("Draw by 50-move rule");
            }
            btTag.setText("Black:");
            gtTag.setText("Game:");
            wtTag.setText("White:");
        }
    }

    public String getPlayerText(String color) {
        if (isPolish) {
            return "Aktualny gracz: " + (color.equals("White") ? "Biały" : "Czarny");
        } else {
            return "Current player: " + color;
        }
    }

}