package chrominox.domains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChroGameFactoryTest {

    private ChroGameFactory factory;

    static class DummyChrominoGenerator implements ChrominoGenerator {
        @Override
        public List<Chromino> generateChrominoes() {
            List<Chromino> list = new ArrayList<>();
            for (int i = 0; i < 80; i++) {
                list.add(new Chromino(TileType.RED, TileType.GREEN, TileType.CYAN));
            }
            return list;
        }
    }

    @BeforeEach
    void setUp() {
        factory = new ChroGameFactory(new DummyChrominoGenerator());
    }

    @Test
    void testConstructor_initializesWithTwoPlayers() {
        ChroGame game;
        factory.createClassicGame();
        game = factory.getCurrentGame();
        assertNotNull(game);
        assertEquals(2, game.getPlayerList().size());
    }


    @Test
    void testGetCurrentGame_returnsNullBeforeCreation() {
        ChroGameFactory emptyFactory = new ChroGameFactory(new DummyChrominoGenerator());
        assertNull(emptyFactory.getCurrentGame());
    }

    @Test
    void testPrivateAddPlayer_nullInput() {
        // Reflexion utilisée pour forcer l'appel d'une méthode privée avec null
        try {
            var method = ChroGameFactory.class.getDeclaredMethod("addPlayer", Player.class);
            method.setAccessible(true);
            method.invoke(factory, (Object) null); // ne doit rien casser
        } catch (Exception e) {
            fail("L'appel à addPlayer(null) ne doit pas planter : " + e.getMessage());
        }
    }
}
