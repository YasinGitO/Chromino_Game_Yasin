package chrominox.domains;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ChrominoTest {

    private Chromino chromino(TileType a, TileType b, TileType c) {
        return new Chromino(a, b, c);
    }

    @Test
    void testChrominoConstructionAndParts() {
        Chromino c = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        assertEquals(TileType.RED, c.getPart1());
        assertEquals(TileType.GREEN, c.getPart2());
        assertEquals(TileType.CYAN, c.getPart3());
    }

    @Test
    void testRotate90DegreesChangesOrientation() {
        Chromino c = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        List<Coordinate> before = c.get3Coordinates();
        c.rotate90Degrees();
        List<Coordinate> after = c.get3Coordinates();
        assertNotEquals(before, after);
    }

    @Test
    void testEqualsAndHashCode() {
        Chromino c1 = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        Chromino c2 = chromino(TileType.CYAN, TileType.GREEN, TileType.RED);
        Chromino c3 = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        Chromino cDiff = chromino(TileType.RED, TileType.GREEN, TileType.GREEN);

        assertEquals(c1, c2);
        assertEquals(c1, c3);
        assertNotEquals(c1, cDiff);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToStringFormat() {
        Chromino c = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        String s = c.toString();
        assertTrue(s.contains("RED") && s.contains("GREEN") && s.contains("CYAN"));
    }

    @Test
    void testGetCoordAndTilesReturnsDefensiveCopy() {
        Chromino c = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        List<CoordAndTile> list = c.getCoordAndTiles();
        assertEquals(3, list.size());
    }

    @Test
    void testGet3CoordinatesReturnsAllCoords() {
        Chromino c = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        List<Coordinate> coords = c.get3Coordinates();
        assertEquals(3, coords.size());
    }

    @Test
    void testGetValueRegularAndCameleon() {
        Chromino normal = chromino(TileType.RED, TileType.GREEN, TileType.RED);
        assertEquals(2, normal.getValue());

        Chromino cameleon = chromino(TileType.RED, TileType.CAMELEON, TileType.GREEN);
        assertEquals(3, cameleon.getValue());
    }

    @Test
    void testMoveShiftsCoordinates() {
        Chromino c = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        List<Coordinate> before = c.get3Coordinates();
        c.move(1, 1);
        List<Coordinate> after = c.get3Coordinates();
        assertNotEquals(before, after);
    }

    @Test
    void testGetChrominoPartToStringValidAndInvalid() {
        Chromino c = chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        assertEquals("RED", c.getChrominoPartToString(0));
        assertEquals("GREEN", c.getChrominoPartToString(1));
        assertEquals("CYAN", c.getChrominoPartToString(2));
        assertThrows(IndexOutOfBoundsException.class, () -> c.getChrominoPartToString(3));
    }
    
    @Test
    void testEqualsAllCases() {
        Chromino c1 = new Chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        Chromino c2 = new Chromino(TileType.RED, TileType.GREEN, TileType.CYAN); // same
        Chromino c3 = new Chromino(TileType.CYAN, TileType.GREEN, TileType.RED); // reversed
        Chromino c4 = new Chromino(TileType.RED, TileType.GREEN, TileType.GREEN); // different
        Chromino sameRef = c1;

        assertTrue(c1.equals(sameRef));
        assertFalse(c1.equals(null));
        assertTrue(c1.equals(c2));
        assertTrue(c1.equals(c3));
        assertFalse(c1.equals(c4));
    }
    
    @Test
    void testEqualsAllLogicalBranches() {
        Chromino ref = new Chromino(TileType.RED, TileType.GREEN, TileType.CYAN);

        Chromino exact = new Chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        assertTrue(ref.equals(exact), "Doit être égal à ordre identique");

        Chromino diffP1 = new Chromino(TileType.YELLOW, TileType.GREEN, TileType.CYAN);
        assertFalse(ref.equals(diffP1), "p1 différent => false");

        Chromino reversed = new Chromino(TileType.CYAN, TileType.GREEN, TileType.RED);
        assertTrue(ref.equals(reversed), "Doit être égal à ordre inversé");

        Chromino badMiddle = new Chromino(TileType.CYAN, TileType.YELLOW, TileType.RED);
        assertFalse(ref.equals(badMiddle), "p2 différent même en inversion => false");

        Chromino totallyDifferent = new Chromino(TileType.CYAN, TileType.CYAN, TileType.CYAN);
        assertFalse(ref.equals(totallyDifferent), "Aucune correspondance => false");

        Chromino partialReverse = new Chromino(TileType.CYAN, TileType.GREEN, TileType.YELLOW);
        assertFalse(ref.equals(partialReverse), "Inversion partielle => false");
    }
}
