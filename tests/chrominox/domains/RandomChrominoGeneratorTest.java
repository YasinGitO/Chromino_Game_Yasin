package chrominox.domains;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class RandomChrominoGeneratorTest {

	RandomChrominoGenerator chrominoTest = new RandomChrominoGenerator();
	
	@Test
	void testGenerateChrominoes() {
		assertEquals(80, chrominoTest.generateChrominoes().size());
	}
	
	@Test
	void testGenerateChrominoes_shuffleIsWorking() {
		List<Chromino> chrominoListTest = chrominoTest.generateChrominoes();
		for(int i = chrominoListTest.size(); i < 5; i-- ) {
			assertNotEquals(chrominoListTest.get(i).getPart2(), new Chromino(TileType.CYAN, TileType.CAMELEON,TileType.GREEN).getPart2());
		}
	}
	
	@Test
	void testGenerateChrominoes_Generate5Cameleons() {
		List<Chromino> chrominoListTest = chrominoTest.generateChrominoes();
		int count = 0;
		for(int i = 0; i < chrominoListTest.size(); i++ ) {
			if(chrominoListTest.get(i).getPart2() == new Chromino(TileType.CYAN, TileType.CAMELEON,TileType.GREEN).getPart2() ) {
				count++;
			}
		}
		assertEquals(5, count);
	}
	
	@Test
	void testGenerateChrominoes_Generate75Chrominoes() {
		List<Chromino> chrominoListTest = chrominoTest.generateChrominoes();
		int count = 0;
		for(int i = 0; i < chrominoListTest.size(); i++ ) {
			if(chrominoListTest.get(i).getPart2() != new Chromino(TileType.CYAN, TileType.CAMELEON,TileType.GREEN).getPart2() ) {
				count++;
			}
		}
		assertEquals(75, count);
	}
	
	@Test
	  public void testGenerateChrominoes_ContainsNull() {
		  List<Chromino> chrominoes = chrominoTest.generateChrominoes();
		  for (Chromino chromino : chrominoes) {
			assertNotNull(chromino);
		}
	  }
	
}
