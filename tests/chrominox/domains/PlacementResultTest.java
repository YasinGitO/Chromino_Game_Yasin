package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Cette class de test Teste Les méthodes de la class PlacementResult
 */
public class PlacementResultTest {

    @Test
    void testSuccessFactoryMethod() {
        PlacementResult result = PlacementResult.success();
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());
    }

    @Test
    void testFailureFactoryMethod() {
        String reason = "Erreur de placement";
        PlacementResult result = PlacementResult.failure(reason);
        assertFalse(result.isSuccess());
        assertEquals(reason, result.getMessage());
    }

    @Test
    void testPrivateConstructorWithMessage() throws Exception {
        var constructor = PlacementResult.class.getDeclaredConstructor(boolean.class, String.class);
        constructor.setAccessible(true);
        PlacementResult result = constructor.newInstance(true, "test");
        assertTrue(result.isSuccess());
        assertEquals("test", result.getMessage());
    }

    @Test
    void testPrivateConstructorWithoutMessage() throws Exception {
        var constructor = PlacementResult.class.getDeclaredConstructor(boolean.class);
        constructor.setAccessible(true);
        PlacementResult result = constructor.newInstance(false);
        assertFalse(result.isSuccess());
        assertNull(result.getMessage());
    }
}
