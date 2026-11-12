package chrominox.domains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private Chromino chromino1;
    private Chromino chromino2;

    @BeforeEach
    void setUp() {
        player = new Player("Alice", "BLUE");
        chromino1 = new Chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        chromino2 = new Chromino(TileType.YELLOW, TileType.CYAN, TileType.MAGENTA);
    }

    @Test
    void testGetNameAndColorAndToString() {
        assertEquals("Alice", player.getName());
        assertEquals("BLUE", player.getColor());
        assertEquals("Alice", player.toString());
    }

    @Test
    void testBuildHandAndHandSizeAndView() {
        player.buildHand(List.of(chromino1, chromino2));
        assertEquals(2, player.getHandSize());
        assertEquals(2, player.getHandForViewOnly().size());
    }

    @Test
    void testAddChrominoToHand() {
        player.addChrominoToHand(chromino1);
        assertEquals(1, player.getHandSize());
        assertTrue(player.getHandForViewOnly().contains(chromino1));
    }

    @Test
    void testSetAndGetActiveChromino() {
        player.setActiveChromino(chromino2);
        assertEquals(chromino2, player.getActiveChromino());
    }

    @Test
    void testRotateActiveChrominoDoesNotCrashWhenNull() {
        assertDoesNotThrow(() -> player.rotateActiveChromino90Degres());
    }

    @Test
    void testRotateActiveChrominoAppliesRotation() {
        player.setActiveChromino(chromino1);
        Coordinate before = chromino1.get3Coordinates().get(0);
        player.rotateActiveChromino90Degres();
        Coordinate after = chromino1.get3Coordinates().get(0);
        assertNotEquals(before, after);
    }

    @Test
    void testMoveActiveChromino() {
        player.setActiveChromino(chromino1);
        Coordinate before = chromino1.get3Coordinates().get(1);
        player.moveActiveChromino(2, -1);
        Coordinate after = chromino1.get3Coordinates().get(1);
        assertEquals(before.getRow() + 2, after.getRow());
        assertEquals(before.getCol() - 1, after.getCol());
    }

    @Test
    void testGetChrominoFromHandToActive() {
        player.buildHand(List.of(chromino1, chromino2));
        player.getChrominoFromHandToActive(1);
        assertEquals(chromino2, player.getActiveChromino());
        assertEquals(1, player.getHandSize());
    }

    @Test
    void testResetTurnDoesNotCrash() {
        player.resetTurn(); // no direct observable effect here
        assertTrue(true);   // success = no crash
    }

    @Test
    void testSelectNextChrominoIndexCycle() {
        player.buildHand(List.of(chromino1, chromino2));
        assertEquals(0, player.getSelectedChrominoIndex());
        player.selectNextChromino();
        assertEquals(1, player.getSelectedChrominoIndex());
        player.selectNextChromino();
        assertEquals(0, player.getSelectedChrominoIndex());
    }

    @Test
    void testResetSelectedChromino() {
        player.buildHand(List.of(chromino1, chromino2));
        player.selectNextChromino();
        assertEquals(1, player.getSelectedChrominoIndex());
        player.resetSelectedChromino();
        assertEquals(0, player.getSelectedChrominoIndex());
    }

    @Test
    void testScoreOperations() {
        assertEquals(0, player.getScore());
        player.addToScore(7);
        assertEquals(7, player.getScore());
        player.addToScore(-2);
        assertEquals(5, player.getScore());
        player.resetScore();
        assertEquals(0, player.getScore());
    }
    
    @Test
    void testMoveActiveChrominoWhenNull() {
        assertNull(player.getActiveChromino());

        assertDoesNotThrow(() -> player.moveActiveChromino(1, 1));
    }
    
}
