package src.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class QuartoFile {
  private QuartoHistory save, head;

  public QuartoFile() {
    head = new QuartoHistory();
    save = head;
  }

  public boolean canRedo() {
    return save.getNext() != null;
  }

  public boolean canUndo() {
    return save.getPrevious() != null;
  }

  public void saveFile(String fileName, String firstPlayerName, String secondPlayerName, int[] playerType)
      throws IOException {
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

  private QuartoHistory chargePawn(QuartoHistory previous, int currentPlayer, String playerOne, String playerTwo, int indexPawn) {
    if (currentPlayer == 1) {
      return new QuartoHistory(indexPawn, previous, playerOne, currentPlayer);
    }
    return new QuartoHistory(indexPawn, previous, playerTwo, currentPlayer);
  }
  
  private QuartoHistory chargePlace(QuartoHistory previous, int currentPlayer, String playerOne, String playerTwo, int line, int column) {
    if (currentPlayer == 1) {
      return new QuartoHistory(line, column, previous, playerOne, currentPlayer);
    }
    return new QuartoHistory(line, column, previous, playerTwo, currentPlayer);
  }

  public QuartoHistory getSave() {
    return save;
  }

  public QuartoHistory getHead() {
    return head;
  }

  public void setSave(QuartoHistory s) {
    save = s;
  }

  public int getNextIndexPawn() {
    return save.getNext().getIndexPawn();
  }

  public int getNextLine() {
    return save.getNext().getLine();
  }

  public int getNextColumn() {
    return save.getNext().getColumn();
  }

  public int getNextState() {
    return save.getNext().getState();
  }

  public int getIndexPawn() {
    return save.getIndexPawn();
  }

  public int getLine() {
    return save.getLine();
  }

  public int getColumn() {
    return save.getColumn();
  }

  public int getState() {
    return save.getState();
  }

  public int getPreviousIndexPawn() {
    return save.getPrevious().getIndexPawn();
  }

  public int getPreviousLine() {
    return save.getPrevious().getLine();
  }

  public int getPreviousColumn() {
    return save.getPrevious().getColumn();
  }

  public int getPreviousState() {
    return save.getPrevious().getState();
  }

}
