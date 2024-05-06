package src.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class QuartoHistoryTest {
    //Before executing these tests, make sure the tests of the model all pass
    @Test
    public void testCanRedo() {
        QuartoModel game = new QuartoModel();
        Assertions.assertFalse(game.histo.canRedo()); // redo cant be possible at start

        // playing a shot
        game.playShot(0, 0);
        Assertions.assertFalse(game.histo.canRedo()); // After a shot played, redo still cannot be possible

        //undoing last played shot
        game.undo();

        Assertions.assertTrue(game.histo.canRedo()); // After a shot is undone, redo is possible

        game.redo();

        Assertions.assertFalse(game.histo.canRedo()); // After a shot is redone, redo is not possible
    }

    @Test
    public void testRedo() {
        QuartoModel game = new QuartoModel();

        // play a shot
        game.playShot(0, 0);

        // saving in the history
        QuartoHistory save = game.histo.save;

        // Undo the movement
        game.undo();

        // Redo the undone movement
        game.redo();

        // next saved element must be equal to the previous saved state
        Assertions.assertEquals(save, game.histo.save);
    }

    @Test
    public void testCanUndo() {
        QuartoModel game = new QuartoModel();
        Assertions.assertFalse(game.histo.canUndo()); // undo should not be possible at start

        // playing a shot
        game.playShot(0, 0);
        Assertions.assertTrue(game.histo.canUndo()); // After a shot played, undo should be possible
    }

    @Test
    public void testUndo() {
        QuartoModel game = new QuartoModel();

        // saving actual state
        QuartoHistory save = game.histo.save;

        // playing a shot
        game.playShot(0, 0);

        // Undo the movement
        game.undo();

        // next saved element must be equal to the previous saved state
        Assertions.assertEquals(save, game.histo.save);
    }

    @Test
    public void testRealisticUndoRedo(){
        QuartoModel game = new QuartoModel();

        //playing shot 00
        game.selectPawn(0);
        //playing shot 05
        game.playShot(0, 0);

        //playing shot 10
        game.selectPawn(1);
        //playing shot 15
        game.playShot(0, 1);

        //playing shot 20
        game.selectPawn(2);

        QuartoPawn pion2 = game.getSelectedPawn();
        //playing shot 25
        game.playShot(0, 2);

        //playing shot 30
        game.selectPawn(3);

        QuartoPawn pion3 = game.getSelectedPawn();
        //playing shot 35
        game.playShot(0, 3);

        //undoing shot 35
        game.undo();

        Assertions.assertTrue(game.win.isTableEmpty(game.getTable(), 0, 3));

        //undoing shot 30
        game.undo();

        Assertions.assertNull(game.getSelectedPawn());

        //undoing shot 25
        game.undo();

        Assertions.assertTrue(game.win.isTableEmpty(game.getTable(), 0, 2));

        System.out.println("pion selectionné : " + game.getSelectedPawn().getPawn());
        System.out.println("pion attendu : " + pion2.getPawn());
        Assertions.assertEquals(game.getSelectedPawn(), pion2);

        //redoing shot 25
        game.redo();

        Assertions.assertEquals(game.getPawnAtPosition(0, 2), pion2);
        Assertions.assertNull(game.getSelectedPawn());//the pawn was placed, selected pawn must be null

        //redoing shot 30
        game.redo();
        Assertions.assertEquals(game.getSelectedPawn(), pion3);
        Assertions.assertNull(game.getPawnAtPosition(0, 3));

        //replaying another shot 35
        game.playShot(3, 3);
        Assertions.assertNull(game.getPawnAtPosition(0, 3));//still null
        Assertions.assertEquals(game.getPawnAtPosition(3, 3), pion3);

        game.undo();
        game.undo();
        game.redo();
        game.redo();

        Assertions.assertNull(game.getPawnAtPosition(0, 3));//still null
        Assertions.assertEquals(game.getPawnAtPosition(3, 3), pion3);

    }
}
