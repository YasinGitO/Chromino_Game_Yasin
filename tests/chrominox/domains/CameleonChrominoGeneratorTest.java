package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;


/**
 * Cette class teste les méthodes de la class CameleonChrominoGenerator
 */
class CameleonChrominoGeneratorTest {

	private final CameleonChrominoGenerator cameleonChromino = new CameleonChrominoGenerator();
	
	@Test
	void testGenerateCameleon_GenerateCameleonTile() {
		List<Chromino> cameleon = cameleonChromino.generateChrominoes();
		Chromino cameleonTest = new Chromino(TileType.CYAN, TileType.CAMELEON, TileType.GREEN);
		assertEquals(cameleon.get(0).getPart2(),cameleonTest.getPart2());
	}

	
	@Test
	void testGenerateChrominoes_generate5chrominoes(){
		List<Chromino> cameleons = cameleonChromino.generateChrominoes();
		assertEquals(5, cameleons.size());
	}
	
	@Test
    void testGenerateExactlyFiveChrominos() {
        ChrominoGenerator generator = new CameleonChrominoGenerator();
        List<Chromino> chrominos = generator.generateChrominoes();
        assertEquals(5, chrominos.size(), "La liste doit contenir exactement 5 chrominos");
    }

    @Test
    void testAllChrominosHaveCameleonInCenter() {
        ChrominoGenerator generator = new CameleonChrominoGenerator();
        List<Chromino> chrominos = generator.generateChrominoes();

        for (Chromino c : chrominos) {
            assertEquals(TileType.CAMELEON, c.getPart2(), "Le centre doit être un caméléon");
        }
    }

    @Test
    void testChrominosAreUniqueEvenSymmetrical() {
        ChrominoGenerator generator = new CameleonChrominoGenerator();
        List<Chromino> chrominos = generator.generateChrominoes();
        Set<Chromino> set = Set.copyOf(chrominos);

        assertEquals(5, set.size(), "Les chrominos doivent être uniques même par symétrie");
    }

    @Test
    void testColorsAreFromDefinedList() {
        ChrominoGenerator generator = new CameleonChrominoGenerator();
        List<Chromino> chrominos = generator.generateChrominoes();

        Set<TileType> valid = Set.of(TileType.CYAN, TileType.GREEN, TileType.MAGENTA, TileType.RED, TileType.YELLOW);

        for (Chromino c : chrominos) {
            assertTrue(valid.contains(c.getPart1()), "Part1 doit être une couleur valide");
            assertTrue(valid.contains(c.getPart3()), "Part3 doit être une couleur valide");
        }
    }

    @Test
    void testOrderIsDeterministic() {
        ChrominoGenerator generator1 = new CameleonChrominoGenerator();
        ChrominoGenerator generator2 = new CameleonChrominoGenerator();

        List<Chromino> list1 = generator1.generateChrominoes();
        List<Chromino> list2 = generator2.generateChrominoes();

        assertEquals(list1, list2, "Deux générateurs doivent produire les mêmes chrominos dans le même ordre");
    }
}
