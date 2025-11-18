package application;

import javafx.scene.Scene;

public class ThemeManager {
    public static void applyDark(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(ThemeManager.class.getResource("/css/dark.css").toExternalForm());
    }

    public static void applyLight(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(ThemeManager.class.getResource("/css/light.css").toExternalForm());
    }
}
