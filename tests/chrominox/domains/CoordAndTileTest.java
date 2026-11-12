package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Cette class va tester toutes les méthodes de la class CoordAndTile.java
 */
class CoordAndTileTest {

    private Coordinate coord;
    private CoordAndTile coordAndTile;

    @BeforeEach
    void setup() {
        coord = new Coordinate(3, 5);
        coordAndTile = new CoordAndTile(coord, TileType.RED);
    }

    @Test
    void testGetCoord() {
        assertEquals(coord, coordAndTile.getCoord());
    }

    @Test
    void testGetTileType() {
        assertEquals(TileType.RED, coordAndTile.getTileType());
    }

    @Test
    void testGetCol() {
        assertEquals(5, coordAndTile.getCol());
    }

    @Test
    void testGetRow() {
        assertEquals(3, coordAndTile.getRow());
    }

    @Test
    void testGetColorName() {
        assertEquals("RED", coordAndTile.getColorName());
    }

    @Test
    void testSetCol() {
        coordAndTile.setCol(10);
        assertEquals(10, coord.getCol());
        assertEquals(10, coordAndTile.getCol());
    }

    @Test
    void testSetRow() {
        coordAndTile.setRow(7);
        assertEquals(7, coord.getRow());
        assertEquals(7, coordAndTile.getRow());
    }
    
    @Test
    void testGetAdjacentCoordinates() {
        Coordinate coord = new Coordinate(5, 5);
        TileType tile = TileType.RED;
        CoordAndTile ct = new CoordAndTile(coord, tile);

        List<Coordinate> adjacents = ct.getAdjacentCoordinates();

        assertEquals(4, adjacents.size());

        assertTrue(adjacents.contains(new Coordinate(4, 5)));
        assertTrue(adjacents.contains(new Coordinate(6, 5)));
        assertTrue(adjacents.contains(new Coordinate(5, 4)));
        assertTrue(adjacents.contains(new Coordinate(5, 6)));
    }
}
