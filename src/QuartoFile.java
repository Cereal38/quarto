package src;

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
    //a changer
    public void chargeFile(String fileName) {
        InputStream in = null;
		boolean debut = true;
		Maillon m = null;
		String[] ligne;
		try {
      		in = new FileInputStream(arg);
			Scanner s = new Scanner(in);
			while (s.hasNextLine()) {
				ligne = s.nextLine().split(" ");
				if (debut) {
					cases = new int[Integer.parseInt(ligne[0])][Integer.parseInt(ligne[1])];
					for(int i = 0; i < Integer.parseInt(ligne[0]); i++) {
						for(int j = 0; j < Integer.parseInt(ligne[1]); j++) {
							cases[i][j] = 0;
						}
					}
					tete_cases = new Maillon(null, cases);
					actuel_cases = tete_cases;
					if (ligne.length == 3)
						m = actuel_cases;
					debut = false;
				} else {
					if (ligne[0].equals("joue")) {
						joue(Integer.parseInt(ligne[1]), Integer.parseInt(ligne[2]), Integer.parseInt(ligne[3]));
						if (ligne.length == 5)
						m = actuel_cases;
					} else if (ligne[0].equals("deplace")) {
						deplace(Integer.parseInt(ligne[1]), Integer.parseInt(ligne[2]), Integer.parseInt(ligne[3]),
								Integer.parseInt(ligne[4]));
						if (ligne.length == 6)
							m = actuel_cases;
					}
				}
			}
			s.close();
			actuel_cases = m;
			cases = new int[actuel_cases.cases.length][actuel_cases.cases[0].length];
			for(int i = 0; i < actuel_cases.cases.length; i++) {
				for(int j = 0; j < actuel_cases.cases[0].length; j++) {
					cases[i][j] = actuel_cases.cases[i][j];
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("impossible de trouver le fichier " + arg);
		}
    }
}
