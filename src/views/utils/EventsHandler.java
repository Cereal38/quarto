package src.views.utils;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import src.controller.ViewModelController;
import src.views.components.DialogPanel;
import src.views.components.ExitButton;
import src.views.components.LanguageButton;
import src.views.components.ManualButton;
import src.views.components.MusicButton;
import src.views.components.PauseMenuButton;
import src.views.components.RedoButton;
import src.views.components.UndoButton;

/**
 * A custom class that handles events for the main frame.
 */
public class EventsHandler {

    private static ViewModelController controller = new ViewModelController();
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static DialogPanel dialog;
    private static int theme = 0; // 0: light, 1: dark
    private static MusicButton musicButton;
    private static LanguageButton languageButton;
    private static ManualButton manualButton;
    private static ExitButton exitButton;
    private static UndoButton undoButton;
    private static RedoButton redoButton;
    private static PauseMenuButton pauseMenuButton;

    public static void setCardLayout(CardLayout cardLayout) {
        EventsHandler.cardLayout = cardLayout;
    }

    public static void setMainPanel(JPanel mainPanel) {
        EventsHandler.mainPanel = mainPanel;
    }

    public static void setDialog(DialogPanel dialog) {
        EventsHandler.dialog = dialog;
    }

    public static ViewModelController getController() {
        return controller;
    }

    public static void setMusicButton(MusicButton mb) {
        musicButton = mb;
    }

    public static void setLanguageButton(LanguageButton lb) {
        languageButton = lb;
    }

    public static void setManualButton(ManualButton mb) {
        manualButton = mb;
    }

    public static void setExitButton(ExitButton eb) {
        exitButton = eb;
    }

    public static void setUndoButton(UndoButton ub) {
        undoButton = ub;
    }

    public static void setRedoButton(RedoButton rb) {
        redoButton = rb;
    }

    public static void setPauseMenuButton(PauseMenuButton pm) {
        pauseMenuButton = pm;
    }

    /**
     * Navigates to the specified destination in the application.
     *
     * @param destination the destination to navigate to
     */
    public static void navigate(String destination) {
        cardLayout.show(mainPanel, destination);
    }

    /**
     * Displays a dialog with the specified content.
     *
     * @param dialogContent the content to be displayed in the dialog
     */
    public static void showDialog(JPanel dialogContent) {
        dialog.setContent(dialogContent);
        dialog.setVisible(true);
    }

    /**
     * Hides the dialog.
     */
    public static void hideDialog() {
        dialog.setVisible(false);
    }

    /**
     * Toggles the theme of the application.
     */
    public static void toggleTheme() {
        // TODO: Theme must be fixed
        theme = (theme + 1) % 2;
        switch (theme) {
        case 0:
            UIManager.put("Panel.background", new ColorUIResource(java.awt.Color.WHITE));
            UIManager.put("Panel.foreground", new ColorUIResource(java.awt.Color.BLACK));
            UIManager.put("Button.background", new ColorUIResource(java.awt.Color.WHITE));
            UIManager.put("Button.foreground", new ColorUIResource(java.awt.Color.BLACK));
            UIManager.put("Label.foreground", new ColorUIResource(java.awt.Color.BLACK));
            UIManager.put("Button.border", BorderFactory.createLineBorder(Color.GRAY));
            break;
        case 1:
            UIManager.put("Panel.background", new ColorUIResource(java.awt.Color.DARK_GRAY));
            UIManager.put("Panel.foreground", new ColorUIResource(java.awt.Color.WHITE));
            UIManager.put("Button.background", new ColorUIResource(java.awt.Color.DARK_GRAY));
            UIManager.put("Button.foreground", new ColorUIResource(java.awt.Color.BLACK));
            UIManager.put("Label.foreground", new ColorUIResource(java.awt.Color.WHITE));
            UIManager.put("Button.border", BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            break;
        }
        // Update the interface after changing the theme
        SwingUtilities.updateComponentTreeUI(mainPanel);

        // Update all buttons
        if (musicButton != null) {
            musicButton.updateIcon(theme == 0);
        }

        if (languageButton != null) {
            languageButton.updateIcon(theme == 0);
        }

        if (manualButton != null) {
            manualButton.updateIcon(theme == 0);
        }

        if (exitButton != null) {
            exitButton.updateIcon(theme == 0);
        }

        if (undoButton != null) {
            undoButton.updateIcon(theme == 0);
        }

        if (redoButton != null) {
            redoButton.updateIcon(theme == 0);
        }

        if (pauseMenuButton != null) {
            pauseMenuButton.updateIcon(theme == 0);
        }
    }
}
