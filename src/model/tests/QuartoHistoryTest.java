package src.model.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import src.model.game.QuartoHistory;
import src.model.game.QuartoModel;
import src.model.game.QuartoPawn;

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
    }

    @Test
    public void testUndo() {
        QuartoModel game = new QuartoModel(0, 0, "", "");

        // saving actual state
        QuartoHistory save = game.getSave();

        // playing a shot
        game.playShot(0, 0);

        // Undo the movement
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
}
