package src.views.utils;

import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import src.controller.ViewModelController;
import src.views.components.DialogPanel;
import src.views.components.SnackbarPanel;
import src.views.components.TranslatedString;
import src.views.game.history.MovesHistory;
import src.views.main.menu.MainMenu;

/**
 * A custom class that handles events for the main frame.
 */
public class EventsHandler {

  private static ViewModelController controller = new ViewModelController();
  private static CardLayout cardLayout;
  private static JPanel mainPanel;
  private static DialogPanel dialog;
  private static SnackbarPanel snackbar;
  private static int theme = 0; // 0: light, 1: dark
  private static MainMenu mainMenu;
  private static MovesHistory movesHistory;

  public static void setMainMenu(MainMenu mainMenu) {
    EventsHandler.mainMenu = mainMenu;
  }

  public static void setMovesHistory(MovesHistory movesHistory) {
    EventsHandler.movesHistory = movesHistory;
  }

  public static void setCardLayout(CardLayout cardLayout) {
    EventsHandler.cardLayout = cardLayout;
  }

  public static void setMainPanel(JPanel mainPanel) {
    EventsHandler.mainPanel = mainPanel;
  }

  public static JPanel getMainPanel() {
    return mainPanel;
  }

  public static void setDialog(DialogPanel dialog) {
    EventsHandler.dialog = dialog;
  }

  public static void setSnackbar(SnackbarPanel snackbar) {
    EventsHandler.snackbar = snackbar;
  }

  public static ViewModelController getController() {
    return controller;
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
   * @param closeable     whether the dialog can be closed by clicking outside of
   *                      it
   */
  public static void showDialog(JPanel dialogContent, boolean closeable) {
    dialog.setContent(dialogContent);
    dialog.setVisible(true);
    dialog.setCloseable(closeable);
  }

  public static void showSnackbar(String message) {
    snackbar.setMessage(message);
    snackbar.setVisible(true);
  }

  /**
   * Hides the dialog.
   */
  public static void hideDialog() {
    dialog.setVisible(false);
  }

  public static void closeApp() {
    Object[] options = { new TranslatedString("yes"), new TranslatedString("no") };
    int response = JOptionPane.showOptionDialog(mainPanel, new TranslatedString("close-confirm"),
        new TranslatedString("close-title").getText(), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
        options, options[0]);

    if (response == 0) {
      System.exit(0); // Exit the application
    }
  }

}