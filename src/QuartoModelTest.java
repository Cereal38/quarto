import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QuartoModelTest {
    @Test
    public void testIsTableEmpty(){
        QuartoModel quartoModel = new QuartoModel();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                Assertions.assertTrue(quartoModel.isTableEmpty(i, j));
            }
        }
    }

    @Test
    public void testTableFull(){
        QuartoModel quartoModel = new QuartoModel();
        //Completely filling the table with pawns
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                quartoModel.playShot(new QuartoPawn(1, 1, 1, 1), i, j);
            }
        }
        //Check if all the table is not empty
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Assertions.assertFalse(quartoModel.isTableEmpty(i, j));
            }
        }
    }

    @Test
    public void testSelectPawn() {
        QuartoModel quartoModel = new QuartoModel();
        quartoModel.selectPawn(0);
        Assertions.assertNotNull(quartoModel.getSelectedPawn());
    }

    @Test
    public void testAllPawnsSelected() {
        QuartoModel quartoModel = new QuartoModel();
        // Select all pawns available
        for (int i = 0; i < 16; i++) {
            // Check if isPawnListEmpty returns false
            Assertions.assertFalse(quartoModel.isPawnListEmpty());
            quartoModel.selectPawn(0);
            //System.out.println(quartoModel.getPawnAvailable().length);
        }
        // Check if isPawnListEmpty returns true
        Assertions.assertTrue(quartoModel.isPawnListEmpty());
    }

    @Test
    public void testIsPawnListEmpty() {
        QuartoModel quartoModel = new QuartoModel();
        Assertions.assertFalse(quartoModel.isPawnListEmpty());
    }

    @Test
    public void testSwitchPlayer() {
        QuartoModel quartoModel = new QuartoModel();
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
        QuartoModel quartoModel = new QuartoModel();
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), 1, 1);
        Assertions.assertFalse(quartoModel.isTableEmpty(1, 1));
    }

    @Test
    public void testPlayShotOnOccupiedCell() {
        QuartoModel quartoModel = new QuartoModel();

        // Creating pawns
        QuartoPawn pawn1 = new QuartoPawn(1, 0, 1, 0);
        QuartoPawn pawn2 = new QuartoPawn(0, 1, 0, 1);
        int line = 1;
        int column = 1;
        // Playing a pawn on (1, 1)
        quartoModel.playShot(pawn1, line, column);

        // Playing again on (1, 1)
        quartoModel.playShot(pawn2, line, column);

        Assertions.assertSame(quartoModel.getPawnAtPosition(1, 1), pawn1);
    }

    @Test
    public void testObviousWinSituationColumn() {
        QuartoModel quartoModel = new QuartoModel();
        for(int i = 0; i < 4; i++){
            quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), i, 1);
        }
        for(int i = 0; i < 4; i++) {
            Assertions.assertTrue(quartoModel.winSituation(i, 1));
        }
    }

    @Test
    public void testObviousWinSituationLine() {
        QuartoModel quartoModel = new QuartoModel();
        for(int i = 0; i < 4; i++){
            quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), 1, i);
        }
        for(int i = 0; i < 4; i++) {
            Assertions.assertTrue(quartoModel.winSituation(1, i));
        }
    }

    @Test
    public void testObviousWinSituationDiagonalRight() {
        QuartoModel quartoModel = new QuartoModel();
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), 0, 0);
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), 1, 1);
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), 2, 2);
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), 3, 3);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(i == j){
                    Assertions.assertTrue(quartoModel.winSituation(i, j));
                }
                else{
                    Assertions.assertFalse(quartoModel.winSituation(i, j));
                }
            }
        }
    }

    @Test
    public void testObviousWinSituationDiagonalLeft() {
        QuartoModel quartoModel = new QuartoModel();
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), 0, 3);
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), 1, 2);
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), 2, 1);
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), 3, 0);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(i + j == 3){
                    Assertions.assertTrue(quartoModel.winSituation(i, j));
                }
                else{
                    Assertions.assertFalse(quartoModel.winSituation(i, j));
                }
            }
        }
    }

    @Test
    public void testNotSoObviousWinSituationColumn() {
        QuartoModel quartoModel = new QuartoModel();
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), 0, 1);
        quartoModel.playShot(new QuartoPawn(1, 0, 0, 0), 2, 1);
        quartoModel.playShot(new QuartoPawn(0, 0, 1, 0), 1, 1);
        quartoModel.playShot(new QuartoPawn(0, 0, 0, 1), 3, 1);

        for(int i = 0; i < 4; i++) {
            Assertions.assertTrue(quartoModel.winSituation(i, 1));
        }
    }

    @Test
    public void testNotSoObviousWinSituationLine() {
        QuartoModel quartoModel = new QuartoModel();
        quartoModel.playShot(new QuartoPawn(1, 1, 1, 1), 1, 0);
        quartoModel.playShot(new QuartoPawn(1, 1, 0, 1), 1, 2);
        quartoModel.playShot(new QuartoPawn(0, 0, 1, 1), 1, 1);
        quartoModel.playShot(new QuartoPawn(0, 1, 0, 1), 1, 3);

        for(int i = 0; i < 4; i++) {
            Assertions.assertTrue(quartoModel.winSituation(1, i));
        }
    }

    @Test
    public void testNotSoObviousWinSituationDiagonalRight() {
        QuartoModel quartoModel = new QuartoModel();
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), 0, 0);
        quartoModel.playShot(new QuartoPawn(1, 1, 0, 0), 1, 1);
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 1), 2, 2);
        quartoModel.playShot(new QuartoPawn(1, 1, 1, 1), 3, 3);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(i == j){
                    Assertions.assertTrue(quartoModel.winSituation(i, j));
                }
                else{
                    Assertions.assertFalse(quartoModel.winSituation(i, j));
                }
            }
        }
    }

    @Test
    public void testNotSoObviousWinSituationDiagonalLeft() {
        QuartoModel quartoModel = new QuartoModel();
        quartoModel.playShot(new QuartoPawn(1, 0, 0, 1), 0, 3);
        quartoModel.playShot(new QuartoPawn(0, 0, 0, 0), 1, 2);
        quartoModel.playShot(new QuartoPawn(1, 0, 0, 0), 2, 1);
        quartoModel.playShot(new QuartoPawn(0, 1, 0, 1), 3, 0);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(i + j == 3){
                    Assertions.assertTrue(quartoModel.winSituation(i, j));
                }
                else{
                    Assertions.assertFalse(quartoModel.winSituation(i, j));
                }
            }
        }
    }

    @Test
    public void testRealisticMultipleWinSituation() {
        QuartoModel quartoModel = new QuartoModel();
        quartoModel.playShot(new QuartoPawn(0, 0, 0, 1), 0, 1);
        quartoModel.playShot(new QuartoPawn(1, 0, 0, 0), 2, 1);//diagonal win and line win
        quartoModel.playShot(new QuartoPawn(0, 0, 1, 0), 0, 2);//column win
        quartoModel.playShot(new QuartoPawn(0, 1, 0, 1), 3, 0);//diagonal win
        quartoModel.playShot(new QuartoPawn(0, 1, 0, 0), 1, 0);
        quartoModel.playShot(new QuartoPawn(0, 0, 0, 0), 2, 0);//line win
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 0), 2, 2);//line win and column win
        quartoModel.playShot(new QuartoPawn(1, 1, 0, 1), 3, 1);
        quartoModel.playShot(new QuartoPawn(1, 1, 1, 0), 3, 2);//column win
        quartoModel.playShot(new QuartoPawn(0, 1, 1, 1), 1, 3);
        quartoModel.playShot(new QuartoPawn(1, 0, 0, 1), 0, 3);//diagonal win
        quartoModel.playShot(new QuartoPawn(0, 0, 0, 0), 1, 2);//diagonal win and column win
        quartoModel.playShot(new QuartoPawn(1, 0, 1, 1), 2, 3);//line win

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
