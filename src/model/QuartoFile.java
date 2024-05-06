package src.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class QuartoFile {
    QuartoHistory save, head;

    public QuartoFile() {
        head = new QuartoHistory();
        save = head;
    }

    public boolean canRedo() {
        return save.next != null;
    }

    public boolean canUndo() {
        return save.previous != null;
    }

    public void saveFile(String fileName) throws IOException {
        try {
            // We create the variable to write in the filename
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            QuartoHistory head_cp = head;
            while (head_cp != null) { //while we go through all the history
                if (head_cp.equals(save)) {//If we are on the current move, we'll write a special caractere to know which move we'll return.
                    if (head_cp.state == 0) {// If we placed a pawn
                        //we write the information about the pawn
                        printWriter.print(head_cp.indexPawn + " *\n");
                    } else if (head_cp.state == 1) {//If we chose a pawn to give to the next player
                        //we write the position of the pawn we chose.
                        printWriter.print(head_cp.line + " " + head_cp.column + " *\n");
                    }
                } else {
                    if (head_cp.state == 0) {
                        printWriter.print(+head_cp.indexPawn + "\n");
                    } else if (head_cp.state == 1) {
                        printWriter.print(head_cp.line + " " + head_cp.column + "\n");
                    }
                }
                head_cp = head_cp.next;
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println("impossible de trouver le fichier " + fileName);
        }
    }

    public void chargeFile(String fileName) {
        InputStream in = null;
        String[] line;
        head = new QuartoHistory();
        QuartoHistory temp = head;
        try {
            in = new FileInputStream(fileName);
            Scanner s = new Scanner(in);
            while (s.hasNextLine()) {
                line = s.nextLine().split(" ");
                if (line.length == 1) { //state == 0
                    temp.next = new QuartoHistory(Integer.parseInt(line[0]), temp);
                    temp.next.previous = temp;
                    temp = temp.next;
                } else if (line.length == 2) {//(state == 1) or (state == 0 and actual state)
                    if (line[1].equals("*")) {
                        temp.next = new QuartoHistory(Integer.parseInt(line[0]), temp);
                        temp.next.previous = temp;
                        temp = temp.next;
                        save = temp;
                    } else {
                        temp.next = new QuartoHistory(Integer.parseInt(line[0]), Integer.parseInt(line[1]), temp);
                        temp.next.previous = temp;
                        temp = temp.next;
                    }
                } else { //state == 1 and actual state
                    temp.next = new QuartoHistory(Integer.parseInt(line[0]), Integer.parseInt(line[1]), temp);
                    temp.next.previous = temp;
                    temp = temp.next;
                    save = temp;
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.err.println("impossible de trouver le fichier " + fileName);
        }
    }
    
    public QuartoHistory getSave() {
        return save;
    }

    public void setSave(QuartoHistory s) {
        save = s;
    }

    public int getNextIndexPawn() {
        return save.next.getIndexPawn();
    }

    public int getNextLine() {
        return save.next.getLine();
    }

    public int getNextColumn() {
        return save.next.getColumn();
    }

    public int getNextState() {
        return save.next.getState();
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
        return save.previous.getIndexPawn();
    }
    
    public int getPreviousLine() {
        return save.previous.getLine();
    }

    public int getPreviousColumn(){
        return save.previous.getColumn();
    }

    public int getPreviousState() {
        return save.previous.getState();
    }

}
