package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/*
 * Cette class teste toutes les méthodes de la class Orientation.java
 * 
 */
public class OrientationTest {

	@Test
	void testGetDRowAndDCol() {
		assertEquals(-1, Orientation.UP.getDRow());
		assertEquals(0, Orientation.UP.getDCol());

		assertEquals(0, Orientation.RIGHT.getDRow());
		assertEquals(1, Orientation.RIGHT.getDCol());

		assertEquals(1, Orientation.DOWN.getDRow());
		assertEquals(0, Orientation.DOWN.getDCol());

		assertEquals(0, Orientation.LEFT.getDRow());
		assertEquals(-1, Orientation.LEFT.getDCol());
	}

	@Test
	void testRotate90Clockwise() {
		assertEquals(Orientation.RIGHT, Orientation.UP.rotate90Clockwise());
		assertEquals(Orientation.DOWN, Orientation.RIGHT.rotate90Clockwise());
		assertEquals(Orientation.LEFT, Orientation.DOWN.rotate90Clockwise());
		assertEquals(Orientation.UP, Orientation.LEFT.rotate90Clockwise());
	}

	@Test
	void testRotate90CounterClockwise() {
		assertEquals(Orientation.LEFT, Orientation.UP.rotate90CounterClockwise());
		assertEquals(Orientation.UP, Orientation.RIGHT.rotate90CounterClockwise());
		assertEquals(Orientation.RIGHT, Orientation.DOWN.rotate90CounterClockwise());
		assertEquals(Orientation.DOWN, Orientation.LEFT.rotate90CounterClockwise());
	}

	@Test
	void testOpposite() {
		assertEquals(Orientation.DOWN, Orientation.UP.opposite());
		assertEquals(Orientation.UP, Orientation.DOWN.opposite());
		assertEquals(Orientation.RIGHT, Orientation.LEFT.opposite());
		assertEquals(Orientation.LEFT, Orientation.RIGHT.opposite());
	}
}
