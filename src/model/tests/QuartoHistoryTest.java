package src.model.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import src.model.game.QuartoHistory;
import src.model.game.QuartoModel;
import src.model.game.QuartoPawn;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class QuartoHistoryTest {
    //Before executing these tests, make sure the tests of the model all pass
    @Test
    public void testCanRedo() {
        QuartoModel game = new QuartoModel(0,0, "", "");
        Assertions.assertFalse(game.canRedo()); // redo cant be possible at start

        // playing a shot
        game.selectPawn(1);
        game.playShot(0, 0);
        Assertions.assertFalse(game.canRedo()); // After a shot played, redo still cannot be possible

        //undoing last played shot
        game.undo();

        Assertions.assertTrue(game.canRedo()); // After a shot is undone, redo is possible

        game.redo();

        Assertions.assertFalse(game.canRedo()); // After a shot is redone, redo is not possible
    }

    @Test
    public void testRedo() {
        QuartoModel game = new QuartoModel(0, 0, "", "");

        // play a shot
        game.playShot(0, 0);

        // saving in the history
        QuartoHistory save = game.getSave();

        // Undo the movement
        game.undo();

        // Redo the undone movement
        game.redo();

        // next saved element must be equal to the previous saved state
        Assertions.assertEquals(save, game.getSave());
    }

    @Test
    public void testCanUndo() {
        QuartoModel game = new QuartoModel(0, 0, "", "");
        Assertions.assertFalse(game.canUndo()); // undo should not be possible at start

        // playing a shot
        game.selectPawn(1);
        game.playShot(0, 0);
        Assertions.assertTrue(game.canUndo()); // After a shot played, undo should be possible

        game.undo();
        Assertions.assertTrue(game.canUndo());
        game.undo();
        Assertions.assertFalse(game.canUndo());
    }

    @Test
    public void testUndo() {
        QuartoModel game = new QuartoModel(0, 0, "", "");

        // saving actual state
        QuartoHistory save = game.getSave();

        //selecting a pawn
        game.selectPawn(5);

        // playing a shot
        game.playShot(0, 0);

        // Undo the shot
        game.undo();

        Assertions.assertNotEquals(save, game.getSave());

        // Undo the selection
        game.undo();

        // next saved element must be equal to the previous saved state
        Assertions.assertEquals(save, game.getSave());
    }

    @Test
    public void testRealisticUndoRedo(){
        QuartoModel game = new QuartoModel(0, 0, "", "");

        //current player must be 1
        Assertions.assertEquals(game.getCurrentPlayer(), 1);

        //playing shot 00
        game.selectPawn(0);

        //current player must be 2
        Assertions.assertEquals(game.getCurrentPlayer(), 2);

        //playing shot 05
        game.playShot(0, 0);

        //current player must be 2
        Assertions.assertEquals(game.getCurrentPlayer(), 2);

        //playing shot 10
        game.selectPawn(1);

        //current player must be 1
        Assertions.assertEquals(game.getCurrentPlayer(), 1);

        //playing shot 15
        game.playShot(0, 1);

        //current player must be 1
        Assertions.assertEquals(game.getCurrentPlayer(), 1);

        //playing shot 20
        game.selectPawn(2);

        //current player must be 2
        Assertions.assertEquals(game.getCurrentPlayer(), 2);

        QuartoPawn pion2 = game.getSelectedPawn();
        //playing shot 25
        game.playShot(0, 2);

        //current player must be 2
        Assertions.assertEquals(game.getCurrentPlayer(), 2);

        //playing shot 30
        game.selectPawn(3);

        //current player must be 1
        Assertions.assertEquals(game.getCurrentPlayer(), 1);

        QuartoPawn pion3 = game.getSelectedPawn();
        //playing shot 35
        game.playShot(0, 3);

        //current player must be 1
        Assertions.assertEquals(game.getCurrentPlayer(), 1);

        //undoing shot 35
        game.undo();

        //current player must be 1
        Assertions.assertEquals(game.getCurrentPlayer(), 1);

        Assertions.assertTrue(game.isTableEmpty(0, 3));

        //undoing shot 30
        game.undo();

        //current player must be 2
        Assertions.assertEquals(game.getCurrentPlayer(), 2);

        Assertions.assertNull(game.getSelectedPawn());

        //undoing shot 25
        game.undo();

        //current player must be 2
        Assertions.assertEquals(game.getCurrentPlayer(), 2);

        Assertions.assertTrue(game.isTableEmpty(0, 2));
        Assertions.assertEquals(game.getSelectedPawn(), pion2);

        //redoing shot 25
        game.redo();

        //current player must be 2
        Assertions.assertEquals(game.getCurrentPlayer(), 2);

        Assertions.assertEquals(game.getPawnAtPosition(0, 2), pion2);
        Assertions.assertNull(game.getSelectedPawn());//the pawn was placed, selected pawn must be null

        //redoing shot 30
        game.redo();
        Assertions.assertEquals(game.getSelectedPawn(), pion3);
        Assertions.assertNull(game.getPawnAtPosition(0, 3));

        //current player must be 1
        Assertions.assertEquals(game.getCurrentPlayer(), 1);

        //replaying another shot 35
        game.playShot(3, 3);
        Assertions.assertNull(game.getPawnAtPosition(0, 3));//still null
        Assertions.assertEquals(game.getPawnAtPosition(3, 3), pion3);

        //current player must be 1
        Assertions.assertEquals(game.getCurrentPlayer(), 1);

        game.undo();
        game.undo();
        game.redo();
        game.redo();

        //current player must be 1
        Assertions.assertEquals(game.getCurrentPlayer(), 1);

        Assertions.assertNull(game.getPawnAtPosition(0, 3));//still null
        Assertions.assertEquals(game.getPawnAtPosition(3, 3), pion3);

    }

    @Test
    public void testSaveFile() throws IOException {
        QuartoModel game = new QuartoModel(0, 0, "", "");

        // Playing some shots
        game.selectPawn(1);
        game.playShot(0, 0);
        game.selectPawn(2);
        game.playShot(0, 1);

        // Saving game state
        String fileName = "testSave";
        game.saveFile(fileName);

        // Checking if file was created
        File file = new File("slots" + File.separator + fileName);
        Assertions.assertTrue(file.exists(), "Save file not found.");

        // Checking if file is not empty
        Assertions.assertTrue(file.length() > 0, "Save file is empty.");

        // Deleting file after test
        Files.delete(file.toPath());
    }

    @Test
    public void testChargeGame() {
        // Playing some shots
        QuartoModel game = new QuartoModel(0, 0, "", "");
        game.selectPawn(1);
        game.playShot(0, 0);
        game.selectPawn(2);
        game.playShot(0, 1);

        // Saving game state
        String fileName = "testCharge";
        try {
            game.saveFile(fileName);
        } catch (IOException e) {
            Assertions.fail("Error when saving game : " + e.getMessage());
        }

        // Charging game state from file
        QuartoModel loadedGame = new QuartoModel(0);
        loadedGame.chargeGame(0);

        // Checking game state after charging
        QuartoPawn[][] table = loadedGame.getTable();
        Assertions.assertNotNull(table[0][0], "Pawn at position (0, 0) is missing.");
        Assertions.assertNotNull(table[0][1], "Pawn at position (0, 1) is missing.");
        Assertions.assertNull(table[0][2], "There shouldn't be any pawn at position (0, 2).");

        // Checking if the pawns are good
        Assertions.assertEquals(1, loadedGame.getPawnAtPosition(0, 0).getPawn(), "Pawn at position (0, 0) is not correct.");
        Assertions.assertEquals(2, loadedGame.getPawnAtPosition(0, 1).getPawn(), "Pawn at position (0, 1) is not correct.");

        // Deleting file after test
        File file = new File("slots" + File.separator + fileName);
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            Assertions.fail("Error when deleting test file : " + e.getMessage());
        }
    }
}
