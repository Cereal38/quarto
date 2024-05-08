package src.views.rules;

import src.views.components.TranslatedString;

import javax.swing.*;
import java.awt.*;


public class RulesPage extends JPanel {
    public RulesPage(boolean isAboutGame) {
        setLayout(new BorderLayout());

        // Load HTML content for rules from en.json
        TranslatedString rulesHtml = new TranslatedString(isAboutGame ? "about" :"game-rules");

        // Create a styled text pane for displaying HTML-formatted rules
        JEditorPane rulesPane = new JEditorPane();
        rulesPane.setContentType("text/html"); // Set content type to HTML
        rulesPane.setEditable(false); // Make it read-only
        rulesPane.setMargin(new Insets(20, 20, 20, 20)); // Add margins

        // Apply custom CSS styles to the HTML content
        String styledHtmlContent = "<html><head>" +
                "<style>" +
                "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; font-size: 16px; line-height: 1.6; color: #333; margin: 20px; background-color: #f8f9fa; }" +
                "h2 { color: #007BFF; border-bottom: 2px solid #007BFF; padding-bottom: 5px; }" +
                "p { margin-bottom: 15px; }" +
                "ul, ol { margin-left: 20px; margin-bottom: 15px; }" +
                "li { margin-bottom: 5px; }" +
                "a { color: #007BFF; text-decoration: none; }" +
                "a:hover { text-decoration: underline; }" +
                "</style>" +
                "</head><body>" +
                rulesHtml.getText() +
                "</body></html>";



        rulesPane.setText(styledHtmlContent); // Set styled HTML content

        // Create a JScrollPane to display the rules pane with scrolling
        JScrollPane scrollPane = new JScrollPane(rulesPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add the scroll pane to the center of the panel
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Create a JFrame to display the RulesPage
        JFrame frame = new JFrame("Quarto Rules");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a RulesPage panel and add it to the frame
        RulesPage rulesPage = new RulesPage(false);
        frame.add(rulesPage);

        // Set the size of the frame
        frame.setSize(800, 600);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Display the frame
        frame.setVisible(true);
    }
}
