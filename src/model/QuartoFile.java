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
        if (save.next != null)
            return true;
        return false;
    }

    public boolean canUndo() {
        if (save.precedent != null)
            return true;
        return false;
    }

    public void saveFile(String fileName) throws IOException {
        try {
            // We create the variable to write in the filename
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            QuartoHistory head_cp = head;
            while (head_cp != null) { //while we go through all the history we have
                if (head_cp.equals(save)) {//If we are on the current move, we'll write a special caractere to know which move we'll return.
                    if (head_cp.state == 0) {// If we have placed a pawn
                        //we write all the information of the pawn
                        printWriter.print(head_cp.indexPawn + " *\n");
                    } else if (head_cp.state == 1) {//If we choosed a pawn to give to the next player
                        //we write the position of the pawn we placed.
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
					temp.next.precedent = temp;
					temp = temp.next;
				} else if (line.length == 2) {//(state == 1) or (state == 0 and actual state)
					if (line[1].equals("*")) {
					    temp.next = new QuartoHistory(Integer.parseInt(line[0]), temp);
                        temp.next.precedent = temp;
                        temp = temp.next;
                        save = temp;
                    } else {
                        temp.next = new QuartoHistory(Integer.parseInt(line[0]), Integer.parseInt(line[1]), temp);
                        temp.next.precedent = temp;
                        temp = temp.next;
                    }
                } else { //state == 1 and actual state
                    temp.next = new QuartoHistory(Integer.parseInt(line[0]), Integer.parseInt(line[1]), temp);
                    temp.next.precedent = temp;
                    temp = temp.next;
                    save = temp;
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.err.println("impossible de trouver le fichier " + fileName);
		}
    }
}
