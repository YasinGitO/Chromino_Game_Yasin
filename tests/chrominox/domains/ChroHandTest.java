
package chrominox.domains;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour ChroHand avec couverture à 100 % (branches + instructions)
 */
class ChroHandTest {

    private Chromino chromino(TileType a, TileType b, TileType c) {
        return new Chromino(a, b, c);
    }

    @Test
    void testAddChrominoAndCount() {
        ChroHand hand = new ChroHand();
        Chromino c = chromino(TileType.RED, TileType.GREEN, TileType.MAGENTA);

        hand.addChromino(c);
        assertEquals(1, hand.count());

        hand.addChromino(null);
        assertEquals(1, hand.count());
    }

    @Test
    void testContainsAndCopyHand() {
        ChroHand hand = new ChroHand();
        Chromino c = chromino(TileType.RED, TileType.GREEN, TileType.MAGENTA);
        hand.addChromino(c);

        assertTrue(hand.contains(c));
        List<Chromino> copy = hand.copyHand();
        assertEquals(1, copy.size());
        assertThrows(UnsupportedOperationException.class, () -> copy.add(c));
    }

    @Test
    void testBuildHand() {
        ChroHand hand = new ChroHand();
        List<Chromino> chrominos = List.of(chromino(TileType.RED, TileType.GREEN, TileType.CYAN));
        hand.buildHand(chrominos);
        assertEquals(1, hand.count());
    }

    @Test
    void testGetChrominoValidAndInvalid() {
        ChroHand hand = new ChroHand();
        Chromino c = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        hand.addChromino(c);

        assertEquals(c, hand.getChromino(0));
        assertNull(hand.getChromino(5));
    }

    @Test
    void testRemoveChrominoAt() {
        ChroHand hand = new ChroHand();
        Chromino c = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        hand.addChromino(c);

        assertEquals(c, hand.removeChrominoAt(0));
        assertNull(hand.removeChrominoAt(0));
    }

    @Test
    void testRemoveChrominoByObject() {
        ChroHand hand = new ChroHand();
        Chromino c = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        hand.addChromino(c);
        hand.removeChromino(c);
        assertFalse(hand.contains(c));
    }

    @Test
    void testSelectedChrominoNavigation() {
        ChroHand hand = new ChroHand();
        Chromino c1 = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        Chromino c2 = chromino(TileType.YELLOW, TileType.CYAN, TileType.MAGENTA);
        hand.addChromino(c1);
        hand.addChromino(c2);

        assertEquals(0, hand.getSelectedChrominoIndex());
        assertEquals(c1, hand.getSelectedChromino());

        hand.selectNextChromino();
        assertEquals(1, hand.getSelectedChrominoIndex());
        assertEquals(c2, hand.getSelectedChromino());

        hand.selectNextChromino();
        assertEquals(0, hand.getSelectedChrominoIndex());

        hand.resetSelectedChromino();
        assertEquals(0, hand.getSelectedChrominoIndex());
    }

    @Test
    void testIsEmptyAndSize() {
        ChroHand hand = new ChroHand();
        assertTrue(hand.isEmpty());
        assertEquals(0, hand.size());

        hand.addChromino(chromino(TileType.RED, TileType.GREEN, TileType.CYAN));
        assertFalse(hand.isEmpty());
        assertEquals(1, hand.size());
    }

    @Test
    void testGetAndRemoveChrominoFromHand() {
        ChroHand hand = new ChroHand();
        Chromino c = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        hand.addChromino(c);

        Chromino removed = hand.getAndRemoveChrominoFromHand(0);
        assertEquals(c, removed);
        assertNull(hand.getAndRemoveChrominoFromHand(0));
    }
    
    @Test
    void testSelectNextChrominoWhenHandIsEmpty() {
        ChroHand hand = new ChroHand();

        assertEquals(0, hand.getSelectedChrominoIndex());

        hand.selectNextChromino();

        assertEquals(0, hand.getSelectedChrominoIndex());
    }
    
    
    
    @Test
    void testGetChrominoAtCoversAllBranches() {
        ChroHand hand = new ChroHand();
        Chromino chromino = new Chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        hand.addChromino(chromino);

        assertNull(hand.getChrominoAt(-1));

        assertEquals(chromino, hand.getChrominoAt(0));

        assertNull(hand.getChrominoAt(1));
    }
    
}
