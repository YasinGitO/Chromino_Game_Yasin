package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Cette class va tester toutes les méthodes de la class Coordinate.java
 */
class CoordinateTest {

	@Test
	void testCoordinateCreation() {
		Coordinate coordinate = new Coordinate(5, 10);
		assertEquals(5, coordinate.getRow());
		assertEquals(10, coordinate.getCol());
	}

	@Test
	void testSetRow() {
		Coordinate coordinate = new Coordinate(5, 10);
		coordinate.setRow(7);
		assertEquals(7, coordinate.getRow());
	}

	@Test
	void testSetCol() {
		Coordinate coordinate = new Coordinate(5, 10);
		coordinate.setCol(12);
		assertEquals(12, coordinate.getCol());
	}

	@Test
	void testEquals() {
		Coordinate coordinate1 = new Coordinate(5, 10);
		Coordinate coordinate2 = new Coordinate(5, 10);
		Coordinate coordinate3 = new Coordinate(7, 10);

		assertEquals(coordinate1, coordinate2);
		assertNotEquals(coordinate1, coordinate3);
	}

	@Test
	void testHashCode() {
		Coordinate coordinate1 = new Coordinate(5, 10);
		Coordinate coordinate2 = new Coordinate(5, 10);

		assertEquals(coordinate1.hashCode(), coordinate2.hashCode());
	}

	@Test
	void testToString() {
		Coordinate coordinate = new Coordinate(5, 10);
		assertEquals("5, 10", coordinate.toString());
	}

	@Test
    void testEquals_sameValues() {
        Coordinate coordinate1 = new Coordinate(5, 10);
        Coordinate coordinate2 = new Coordinate(5, 10);
        assertEquals(coordinate1, coordinate2);
    }

    @Test
    void testEquals_differentRow() {
        Coordinate coordinate1 = new Coordinate(5, 10);
        Coordinate coordinate2 = new Coordinate(6, 10);
        assertNotEquals(coordinate1, coordinate2);
    }

    @Test
    void testEquals_differentCol() {
        Coordinate coordinate1 = new Coordinate(5, 10);
        Coordinate coordinate2 = new Coordinate(5, 11);
        assertNotEquals(coordinate1, coordinate2);
    }

    @Test
    void testEquals_sameObject() {
        Coordinate coordinate = new Coordinate(5, 10);
        assertEquals(coordinate, coordinate);
    }

    @Test
    void testEquals_nullObject() {
        Coordinate coordinate = new Coordinate(5, 10);
        assertNotNull(coordinate);
    }

    @Test
    void testHashCode_sameValues() {
        Coordinate coordinate1 = new Coordinate(5, 10);
        Coordinate coordinate2 = new Coordinate(5, 10);
        assertEquals(coordinate1.hashCode(), coordinate2.hashCode());
    }

    @Test
    void testMoveNorth() {
        Coordinate coordinate = new Coordinate(5, 10);
        Coordinate moved = coordinate.move(Orientation.UP, 2);
        assertEquals(3, moved.getRow());
        assertEquals(10, moved.getCol());
    }

    @Test
    void testMoveSouth() {
        Coordinate coordinate = new Coordinate(5, 10);
        Coordinate moved = coordinate.move(Orientation.DOWN, 3);
        assertEquals(8, moved.getRow());
        assertEquals(10, moved.getCol());
    }

    @Test
    void testMoveEast() {
        Coordinate coordinate = new Coordinate(5, 10);
        Coordinate moved = coordinate.move(Orientation.RIGHT, 4);
        assertEquals(5, moved.getRow());
        assertEquals(14, moved.getCol());
    }

    @Test
    void testMoveWest() {
        Coordinate coordinate = new Coordinate(5, 10);
        Coordinate moved = coordinate.move(Orientation.LEFT, 1);
        assertEquals(5, moved.getRow());
        assertEquals(9, moved.getCol());
    }
    
    @Test
    void testEqualsWithNull() {
        Coordinate coord = new Coordinate(2, 3);
        assertNotNull(coord);
    }
    
    @Test
    void testHashCodeConsistency() {
        Coordinate c1 = new Coordinate(2, 3);
        Coordinate c2 = new Coordinate(2, 3);

        assertEquals(c1.hashCode(), c2.hashCode());
    }
    
    @Test
    void testToStringFormat() {
        Coordinate coord = new Coordinate(7, 9);
        assertEquals("7, 9", coord.toString());
    }
    
    @Test
    void testMoveDirectionSteps() {
        Coordinate original = new Coordinate(0, 0);
        Coordinate moved = original.move(Orientation.UP, 2);

        assertEquals(new Coordinate(-2, 0), moved);
    }
    
    @Test
    void testEqualsVariousCases() {
        Coordinate coord = new Coordinate(1, 1);
        Coordinate same = new Coordinate(1, 1);
        Coordinate diff = new Coordinate(2, 2);

        assertTrue(coord.equals(same));
        assertFalse(coord.equals(diff));
        assertFalse(coord.equals(null));
    }
    
    @Test
    void testGetAdjacentCoordinates() {
        Coordinate center = new Coordinate(5, 5);
        List<Coordinate> adjacents = center.getAdjacentCoordinates();

        assertEquals(4, adjacents.size());

        assertTrue(adjacents.contains(new Coordinate(4, 5)));
        assertTrue(adjacents.contains(new Coordinate(6, 5)));
        assertTrue(adjacents.contains(new Coordinate(5, 4)));
        assertTrue(adjacents.contains(new Coordinate(5, 6)));
    }
    
}
