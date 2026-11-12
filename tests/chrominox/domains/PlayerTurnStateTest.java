package chrominox.domains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTurnStateTest {

    private PlayerTurnState state;

    @BeforeEach
    void setUp() {
        state = new PlayerTurnState();
    }

    @Test
    void testInitialValues() {
        assertFalse(state.playerHasDrawned());
        assertFalse(state.playerHasPlaced());
        assertTrue(state.canDraw());
        assertFalse(state.canPass());
    }

    @Test
    void testSetDrawnTrue() {
        state.setDrawn(true);
        assertTrue(state.playerHasDrawned());
        assertFalse(state.canDraw());
        assertTrue(state.canPass());
    }

    @Test
    void testSetPlacedTrue() {
        state.setPlaced(true);
        assertTrue(state.playerHasPlaced());
    }

    @Test
    void testResetResetsAllStates() {
        state.setDrawn(true);
        state.setPlaced(true);
        state.reset();

        assertFalse(state.playerHasDrawned());
        assertFalse(state.playerHasPlaced());
        assertTrue(state.canDraw());
        assertFalse(state.canPass());
    }
}
