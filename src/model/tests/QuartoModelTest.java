package src.model.tests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import src.model.game.QuartoModel;
import src.model.game.QuartoPawn;

public class QuartoModelTest {
  @Test
  public void testIsTableEmpty() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        Assertions.assertTrue(quartoModel.isTableEmpty(i, j));
      }
    }
  }

  @Test
  public void testTableFull() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    // Completely filling the table with pawns
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        quartoModel.setSelectedPawn(new QuartoPawn((byte) 15));
        quartoModel.playShot(i, j);
      }
    }
    // Check if all the table is not empty
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        Assertions.assertFalse(quartoModel.isTableEmpty(i, j));
      }
    }
  }

  @Test
  public void testSelectPawn() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    Assertions.assertNull(quartoModel.getSelectedPawn());
    quartoModel.selectPawn(0);
    Assertions.assertNotNull(quartoModel.getSelectedPawn());
    Assertions.assertEquals(quartoModel.getSelectedPawn().getPawn(), 0);
    quartoModel.playShot(0, 0);
    Assertions.assertNull(quartoModel.getSelectedPawn());
  }


  @Test
  public void testIsPawnListEmpty() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    Assertions.assertFalse(quartoModel.isPawnListEmpty());
    int index = 0;
    for(int i = 0; i < 4; i++){
      for(int j = 0; j < 4; j++) {
        Assertions.assertFalse(quartoModel.isPawnListEmpty());
        quartoModel.selectPawn(index);
        quartoModel.playShot(i, j);
        index++;
      }
    }
    Assertions.assertTrue(quartoModel.isPawnListEmpty());
  }

  @Test
  public void testSwitchPlayer() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    Assertions.assertEquals(1, quartoModel.getCurrentPlayer());
    quartoModel.switchPlayer();
    Assertions.assertEquals(2, quartoModel.getCurrentPlayer());
    quartoModel.switchPlayer();
    Assertions.assertEquals(1, quartoModel.getCurrentPlayer());
    quartoModel.switchPlayer();
    Assertions.assertEquals(2, quartoModel.getCurrentPlayer());
    quartoModel.switchPlayer();
    Assertions.assertEquals(1, quartoModel.getCurrentPlayer());
  }

  @Test
  public void testPlayShot() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
    Assertions.assertTrue(quartoModel.isTableEmpty(1, 1));
    quartoModel.playShot(1, 1);
    Assertions.assertFalse(quartoModel.isTableEmpty(1, 1));
    Assertions.assertEquals(quartoModel.getPawnAtPosition(1, 1).getPawn(), 10);
    Assertions.assertNull(quartoModel.getSelectedPawn());
  }

  @Test
  public void testPlayShotOnOccupiedCell() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");

    // Creating pawns
    QuartoPawn pawn1 = new QuartoPawn((byte) 10);
    QuartoPawn pawn2 = new QuartoPawn((byte) 5);
    int line = 1;
    int column = 1;
    // Playing a pawn on (1, 1)
    quartoModel.setSelectedPawn(pawn1);
    quartoModel.playShot(line, column);

    // Playing again on (1, 1)
    quartoModel.setSelectedPawn(pawn2);
    quartoModel.playShot(line, column);

    Assertions.assertSame(quartoModel.getPawnAtPosition(1, 1), pawn1);
    Assertions.assertEquals(quartoModel.getSelectedPawn(), pawn2);
  }

  @Test
  public void testObviousWinSituationColumn() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    for (int i = 0; i < 4; i++) {
      quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
      quartoModel.playShot(i, 1);
    }
    for (int i = 0; i < 4; i++) {
      Assertions.assertTrue(quartoModel.winSituation(i, 1));
    }
  }

  @Test
  public void testObviousWinSituationLine() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    for (int i = 0; i < 4; i++) {
      quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
      quartoModel.playShot(1, i);
    }
    for (int i = 0; i < 4; i++) {
      Assertions.assertTrue(quartoModel.winSituation(1, i));
    }
  }

  @Test
  public void testObviousWinSituationDiagonalRight() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
    quartoModel.playShot(0, 0);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
    quartoModel.playShot(1, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
    quartoModel.playShot(2, 2);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
    quartoModel.playShot(3, 3);
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (i == j) {
          Assertions.assertTrue(quartoModel.winSituation(i, j));
        } else {
          Assertions.assertFalse(quartoModel.winSituation(i, j));
        }
      }
    }
  }

  @Test
  public void testObviousWinSituationDiagonalLeft() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
    quartoModel.playShot(0, 3);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
    quartoModel.playShot(1, 2);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
    quartoModel.playShot(2, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
    quartoModel.playShot(3, 0);
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (i + j == 3) {
          Assertions.assertTrue(quartoModel.winSituation(i, j));
        } else {
          Assertions.assertFalse(quartoModel.winSituation(i, j));
        }
      }
    }
  }

  @Test
  public void testNotSoObviousWinSituationColumn() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
    quartoModel.playShot(0, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 8));
    quartoModel.playShot(2, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 2));
    quartoModel.playShot(1, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 1));
    quartoModel.playShot(3, 1);

    for (int i = 0; i < 4; i++) {
      Assertions.assertTrue(quartoModel.winSituation(i, 1));
    }
  }

  @Test
  public void testNotSoObviousWinSituationLine() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 15));
    quartoModel.playShot(1, 0);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 13));
    quartoModel.playShot(1, 2);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 3));
    quartoModel.playShot(1, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 5));
    quartoModel.playShot(1, 3);

    for (int i = 0; i < 4; i++) {
      Assertions.assertTrue(quartoModel.winSituation(1, i));
    }
  }

  @Test
  public void testNotSoObviousWinSituationDiagonalRight() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
    quartoModel.playShot(0, 0);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 12));
    quartoModel.playShot(1, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 11));
    quartoModel.playShot(2, 2);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 15));
    quartoModel.playShot(3, 3);
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (i == j) {
          Assertions.assertTrue(quartoModel.winSituation(i, j));
        } else {
          Assertions.assertFalse(quartoModel.winSituation(i, j));
        }
      }
    }
  }

  @Test
  public void testNotSoObviousWinSituationDiagonalLeft() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 9));
    quartoModel.playShot(0, 3);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 0));
    quartoModel.playShot(1, 2);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 8));
    quartoModel.playShot(2, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 5));
    quartoModel.playShot(3, 0);
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (i + j == 3) {
          Assertions.assertTrue(quartoModel.winSituation(i, j));
        } else {
          Assertions.assertFalse(quartoModel.winSituation(i, j));
        }
      }
    }
  }

  @Test
  public void testLooseSituationLine() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 15));
    quartoModel.playShot(1, 0);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 0));
    quartoModel.playShot(1, 2);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 3));
    quartoModel.playShot(1, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 5));
    quartoModel.playShot(1, 3);

    for (int i = 0; i < 4; i++) {
      Assertions.assertFalse(quartoModel.winSituation(1, i));
    }
  }

  @Test
  public void testLooseSituationColumn() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 8));
    quartoModel.playShot(0, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 0));
    quartoModel.playShot(2, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 3));
    quartoModel.playShot(1, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 5));
    quartoModel.playShot(3, 1);

    for (int i = 0; i < 4; i++) {
      Assertions.assertFalse(quartoModel.winSituation(i, 2));
    }
  }

  @Test
  public void testLooseSituationDiagonal1() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 5));
    quartoModel.playShot(1, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 2));
    quartoModel.playShot(2, 2);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 12));
    quartoModel.playShot(0, 0);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 15));
    quartoModel.playShot(3, 3);

    for (int i = 0; i < 4; i++) {
      Assertions.assertFalse(quartoModel.winSituation(i, i));
    }
  }

  @Test
  public void testLooseSituationDiagonal2() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 1));
    quartoModel.playShot(2, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 12));
    quartoModel.playShot(1, 2);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 3));
    quartoModel.playShot(3, 0);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 14));
    quartoModel.playShot(0, 3);

    Assertions.assertFalse(quartoModel.winSituation(0, 3));
    Assertions.assertFalse(quartoModel.winSituation(1, 2));
    Assertions.assertFalse(quartoModel.winSituation(2, 1));
    Assertions.assertFalse(quartoModel.winSituation(3, 0));
  }

  @Test
  public void testRealisticMultipleWinSituation() {
    QuartoModel quartoModel = new QuartoModel(0, 0, "", "");
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 1));
    quartoModel.playShot(0, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 8));
    quartoModel.playShot(2, 1);// diagonal win and line win
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 2));
    quartoModel.playShot(0, 2);// column win
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 5));
    quartoModel.playShot(3, 0);// diagonal win
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 4));
    quartoModel.playShot(1, 0);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 0));
    quartoModel.playShot(2, 0);// line win
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 10));
    quartoModel.playShot(2, 2);// line win and column win
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 13));
    quartoModel.playShot(3, 1);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 14));
    quartoModel.playShot(3, 2);// column win
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 7));
    quartoModel.playShot(1, 3);
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 9));
    quartoModel.playShot(0, 3);// diagonal win
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 0));
    quartoModel.playShot(1, 2);// diagonal win and column win
    quartoModel.setSelectedPawn(new QuartoPawn((byte) 11));
    quartoModel.playShot(2, 3);// line win

    Assertions.assertFalse(quartoModel.winSituation(0, 0));
    Assertions.assertFalse(quartoModel.winSituation(0, 1));
    Assertions.assertTrue(quartoModel.winSituation(0, 2));
    Assertions.assertTrue(quartoModel.winSituation(0, 3));
    Assertions.assertFalse(quartoModel.winSituation(1, 0));
    Assertions.assertFalse(quartoModel.winSituation(1, 1));
    Assertions.assertTrue(quartoModel.winSituation(1, 2));
    Assertions.assertFalse(quartoModel.winSituation(1, 3));
    Assertions.assertTrue(quartoModel.winSituation(2, 0));
    Assertions.assertTrue(quartoModel.winSituation(2, 1));
    Assertions.assertTrue(quartoModel.winSituation(2, 2));
    Assertions.assertTrue(quartoModel.winSituation(2, 3));
    Assertions.assertTrue(quartoModel.winSituation(3, 0));
    Assertions.assertFalse(quartoModel.winSituation(3, 1));
    Assertions.assertTrue(quartoModel.winSituation(3, 2));
    Assertions.assertFalse(quartoModel.winSituation(3, 3));

  }
}