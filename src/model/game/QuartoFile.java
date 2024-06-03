/**
 * The QuartoFile class represents the file management system for saving and loading game states.
 * It handles file operations such as saving the game state to a file and loading a game state from a file.
 */
package src.model.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class QuartoFile {
    private QuartoHistory save, head;

    /**
     * Initializes a new QuartoFile instance.
     * Constructs the QuartoHistory objects for the head and save pointers.
     */
    public QuartoFile() {
        head = new QuartoHistory();
        save = head;
    }

    /**
     * Checks if there are any redo operations available.
     *
     * @return true if redo is possible, false otherwise.
     */
    public boolean canRedo() {
        return save.getNext() != null;
    }

    /**
     * Checks if there are any undo operations available.
     *
     * @return true if undo is possible, false otherwise.
     */
    public boolean canUndo() {
        return save.getPrevious() != null;
    }

    /**
     * Saves the current game state to a file.
     *
     * @param fileName        The name of the file to save.
     * @param firstPlayerName The name of the first player.
     * @param secondPlayerName The name of the second player.
     * @param playerType      An array containing the type of each player.
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    public void saveFile(String fileName, String firstPlayerName, String secondPlayerName, int[] playerType) throws IOException {
        try {
            // We create the variable to write in the filename
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            QuartoHistory head_cp = head;
            printWriter.print(firstPlayerName + " " + playerType[0] + "\n");
            printWriter.print(secondPlayerName + " " + playerType[1] + "\n");
            while (head_cp != null) { // while we go through all the history
                if (head_cp.equals(save)) {// If we are on the current move, we'll write a special caractere to know which
                    // move we'll return.
                    if (head_cp.state == 0) {// If we placed a pawn
                        // we write the information about the pawn
                        printWriter.print(head_cp.indexPawn + " *\n");
                    } else if (head_cp.state == 1) {// If we chose a pawn to give to the next player
                        // we write the position of the pawn we chose.
                        printWriter.print(head_cp.line + " " + head_cp.column + " *\n");
                    }
                } else {
                    if (head_cp.state == 0) {
                        printWriter.print(+head_cp.indexPawn + "\n");
                    } else if (head_cp.state == 1) {
                        printWriter.print(head_cp.line + " " + head_cp.column + "\n");
                    }
                }
                head_cp = head_cp.getNext();
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println("File " + fileName + " not found.");
        }
    }

    /**
     * Loads game state from a file.
     *
     * @param fileName The name of the file to load.
     * @return An array containing information about the players if successfully loaded, null otherwise.
     */
    public String[] chargeFile(String fileName) {
        InputStream in = null;
        String[] line;
        head = new QuartoHistory();
        QuartoHistory temp = head;
        try {
            in = new FileInputStream("slots/" + fileName);
            Scanner s = new Scanner(in);
            String[] infoPlayer = new String[2];
            for (int countPlayer = 0; countPlayer < 2; countPlayer++) {
                if (s.hasNextLine()) {
                    infoPlayer[countPlayer] = s.nextLine();
                } else {
                    System.err.println("File do not contain informations about the players\n");
                    s.close();
                    return null;
                }
            }
            int currentPlayer = 1;
            save = head;
            while (s.hasNextLine()) {
                line = s.nextLine().split(" ");
                if (line.length == 1) { // state == 0
                    temp.setNext(chargePawn(temp, currentPlayer, infoPlayer[0].split(" ")[0], infoPlayer[1].split(" ")[0], Integer.parseInt(line[0])));
                    temp.getNext().setPrevious(temp);
                    temp = temp.getNext();
                    currentPlayer = (currentPlayer == 1) ? 2 : 1;
                } else if (line.length == 2) {// (state == 1) or (state == 0 and actual state)
                    if (line[1].equals("*")) {
                        temp.setNext(chargePawn(temp, currentPlayer, infoPlayer[0].split(" ")[0], infoPlayer[1].split(" ")[0], Integer.parseInt(line[0])));
                        temp.getNext().setPrevious(temp);
                        temp = temp.getNext();
                        save = temp;
                        currentPlayer = (currentPlayer == 1) ? 2 : 1;
                    } else {
                        temp.setNext(chargePlace(temp, currentPlayer, infoPlayer[0].split(" ")[0], infoPlayer[1].split(" ")[0], Integer.parseInt(line[0]), Integer.parseInt(line[1])));
                        temp.getNext().setPrevious(temp);
                        temp = temp.getNext();
                    }
                } else { // state == 1 and actual state
                    temp.setNext(chargePlace(temp, currentPlayer, infoPlayer[0].split(" ")[0], infoPlayer[1].split(" ")[0], Integer.parseInt(line[0]), Integer.parseInt(line[1])));
                    temp.getNext().setPrevious(temp);
                    temp = temp.getNext();
                    save = temp;
                }
            }
            s.close();
            return infoPlayer;
        } catch (FileNotFoundException e) {
            System.err.println("impossible de trouver le fichier slots/" + fileName);
            return null;
        }
    }

    /**
     * Creates a new history node for a pawn placement.
     *
     * @param previous    The previous game state.
     * @param currentPlayer The current player (1 or 2).
     * @param playerOne   The name of the first player.
     * @param playerTwo   The name of the second player.
     * @param indexPawn   The index of the pawn.
     * @return A new history node representing the pawn placement.
     */
    private QuartoHistory chargePawn(QuartoHistory previous, int currentPlayer, String playerOne, String playerTwo, int indexPawn) {
        if (currentPlayer == 1) {
            return new QuartoHistory(indexPawn, previous, playerOne, currentPlayer);
        }
        return new QuartoHistory(indexPawn, previous, playerTwo, currentPlayer);
    }

    /**
     * Creates a new history node for a pawn placement on the board.
     *
     * @param previous    The previous game state.
     * @param currentPlayer The current player (1 or 2).
     * @param playerOne   The name of the first player.
     * @param playerTwo   The name of the second player.
     * @param line        The line where the pawn is placed.
     * @param column      The column where the pawn is placed.
     * @return A new history node representing the pawn placement on the board.
     */
    private QuartoHistory chargePlace(QuartoHistory previous, int currentPlayer, String playerOne, String playerTwo, int line, int column) {
        if (currentPlayer == 1) {
            return new QuartoHistory(line, column, previous, playerOne, currentPlayer);
        }
        return new QuartoHistory(line, column, previous, playerTwo, currentPlayer);
    }

    /**
     * Gets the current save state of the game.
     *
     * @return The current save state.
     */
    public QuartoHistory getSave() {
        return save;
    }

    /**
     * Gets the head of the game history.
     *
     * @return The head of the game history.
     */
    public QuartoHistory getHead() {
        return head;
    }

    /**
     * Sets the save state of the game.
     *
     * @param s The new save state.
     */
    public void setSave(QuartoHistory s) {
        save = s;
    }

    // Methods for retrieving information about the current game state

    /**
     * Gets the index of the pawn in the next move.
     *
     * @return The index of the pawn in the next move.
     */
    public int getNextIndexPawn() {
        return save.getNext().getIndexPawn();
    }

    /**
     * Gets the line of the next move on the board.
     *
     * @return The line of the next move on the board.
     */
    public int getNextLine() {
        return save.getNext().getLine();
    }

    /**
     * Gets the column of the next move on the board.
     *
     * @return The column of the next move on the board.
     */
    public int getNextColumn() {
        return save.getNext().getColumn();
    }

    /**
     * Gets the state of the next move.
     *
     * @return The state of the next move.
     */
    public int getNextState() {
        return save.getNext().getState();
    }

    /**
     * Gets the index of the pawn in the current move.
     *
     * @return The index of the pawn in the current move.
     */
    public int getIndexPawn() {
        return save.getIndexPawn();
    }

    /**
     * Gets the line of the current move on the board.
     *
     * @return The line of the current move on the board.
     */
    public int getLine() {
        return save.getLine();
    }

    /**
     * Gets the column of the current move on the board.
     *
     * @return The column of the current move on the board.
     */
    public int getColumn() {
        return save.getColumn();
    }

    /**
     * Gets the state of the current move.
     *
     * @return The state of the current move.
     */
    public int getState() {
        return save.getState();
    }


    // Methods for retrieving information about the previous game state

    /**
     * Gets the index of the pawn in the previous move.
     *
     * @return The index of the pawn in the previous move.
     */
    public int getPreviousIndexPawn() {
        return save.getPrevious().getIndexPawn();
    }

    /**
     * Gets the line of the previous move on the board.
     *
     * @return The line of the previous move on the board.
     */
    public int getPreviousLine() {
        return save.getPrevious().getLine();
    }

    /**
     * Gets the column of the previous move on the board.
     *
     * @return The column of the previous move on the board.
     */
    public int getPreviousColumn() {
        return save.getPrevious().getColumn();
    }

    /**
     * Gets the state of the previous move.
     *
     * @return The state of the previous move.
     */
    public int getPreviousState() {
        return save.getPrevious().getState();
    }
}
