package chrominox.domains;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ChrominoGeneratorTest {

    @Test
    void generateChrominoes_returnsExactly80Chrominos() {
        ChrominoGenerator generator = new RandomChrominoGenerator();
        List<Chromino> chrominos = generator.generateChrominoes();
        
        assertNotNull(chrominos, "La liste ne doit pas être null.");
        assertEquals(80, chrominos.size(), "La liste doit contenir exactement 80 chrominos.");
    }

    @Test
    void generateChrominoes_contains75UniqueAnd5CameleonChrominos() {
        ChrominoGenerator generator = new RandomChrominoGenerator();
        List<Chromino> chrominos = generator.generateChrominoes();

        int cameleonCount = 0;
        Set<String> uniquePatterns = new HashSet<>();

        for (Chromino c : chrominos) {
            assertNotNull(c);
            if (c.getPart2() == TileType.CAMELEON) {
                cameleonCount++;
            } else {
                // On encode les 3 couleurs dans une String pour tester l’unicité (ex : "RED-GREEN-BLUE")
                String key = c.getPart1() + "-" + c.getPart2() + "-" + c.getPart3();
                String reversed = c.getPart3() + "-" + c.getPart2() + "-" + c.getPart1();
                // Vérifie qu’il n’est pas dans les deux sens
                assertFalse(uniquePatterns.contains(key) || uniquePatterns.contains(reversed),
                        "Doublon détecté : " + key + " ou " + reversed);
                uniquePatterns.add(key);
            }
        }

        assertEquals(5, cameleonCount, "Il doit y avoir exactement 5 chrominos caméléon.");
        assertEquals(75, uniquePatterns.size(), "Il doit y avoir 75 chrominos uniques.");
    }

}
