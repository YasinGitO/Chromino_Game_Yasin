package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Cette class teste la class UniqueChrominoGenerator.java pour voir si elle génère bien les 75 chrominos basique unique
 */
class UniqueChrominoGeneratorTest {

	private final UniqueChrominoGenerator uniqueChromino = new UniqueChrominoGenerator();
	

	  //Fonction generateChrominoes();
	  
	  @Test
	  public void testGenerateChrominoes_ListIsNotNull() {
		  assertNotNull(uniqueChromino.generateChrominoes());
	  }
	  
	  @Test
	  public void testGenerateChrominoes_SizeIs75() {
		  List<Chromino> chrominoes = uniqueChromino.generateChrominoes();
		  assertEquals(75, chrominoes.size());
	  }
	  
	  @Test
	  public void testGenerateChrominoes_ContainsNull() {
		  List<Chromino> chrominoes = uniqueChromino.generateChrominoes();
		  for (Chromino chromino : chrominoes) {
			assertNotNull(chromino);
		}
	  }
	  
	  @Test
		public void testPasDeDoublons() {
			ChrominoGenerator generator = new UniqueChrominoGenerator();
			List<Chromino> chrominos = generator.generateChrominoes();

			Set<Chromino> seen = new HashSet<>();
			for (Chromino c : chrominos) {
				assertFalse(seen.contains(c));
				seen.add(c);
			}
		}

		@Test
		public void testAllTileTypeValid() {
			ChrominoGenerator generator = new UniqueChrominoGenerator();
			List<Chromino> chrominos = generator.generateChrominoes();
			Set<TileType> valid = EnumSet.allOf(TileType.class);

			for (Chromino c : chrominos) {
				assertTrue(valid.contains(c.getPart1()));
				assertTrue(valid.contains(c.getPart2()));
				assertTrue(valid.contains(c.getPart3()));
			}
		}

		@Test
		public void testSymmetryManually() {
			Chromino a = new Chromino(TileType.RED, TileType.GREEN, TileType.YELLOW);
			Chromino b = new Chromino(TileType.YELLOW, TileType.GREEN, TileType.RED);

			assertEquals(a, b);
			assertEquals(a.hashCode(), b.hashCode());
		}
	  
	  
	  
}
