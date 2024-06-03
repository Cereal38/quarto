package src;

import javax.swing.SwingUtilities;

import src.views.MainFrame;

/**
 * The main class of the Quarto game application.
 */
public class Main {

  /**
   * The entry point of the application.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // Using SwingUtilities.invokeLater to ensure that the interface is
    // created on the event dispatch thread
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new MainFrame(); // Create and display the main window
      }
    });
  }
}
