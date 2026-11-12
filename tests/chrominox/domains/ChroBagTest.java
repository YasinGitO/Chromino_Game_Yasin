package chrominox.domains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChroBagTest {

    private Chromino cam1, cam2, normal1;
    private List<Chromino> fullBag;
    private List<Chromino> onlyNormals;
    private List<Chromino> emptyBag;

    @BeforeEach
    void setUp() {
        cam1 = new Chromino(TileType.RED, TileType.CAMELEON, TileType.GREEN);
        cam2 = new Chromino(TileType.YELLOW, TileType.CAMELEON, TileType.CYAN);
        normal1 = new Chromino(TileType.RED, TileType.GREEN, TileType.MAGENTA);

        fullBag = new ArrayList<>();
        for (int i = 0; i < 78; i++) fullBag.add(normal1);
        fullBag.add(cam1);
        fullBag.add(cam2);

        onlyNormals = new ArrayList<>();
        for (int i = 0; i < 80; i++) onlyNormals.add(normal1);

        emptyBag = new ArrayList<>();
    }

    @Test
    void retirerUnSeulChromino_retourneEtRetire() {
        ChroBag bag = new ChroBag(fullBag);
        Chromino c = bag.retirerUnSeulChromino();
        assertNotNull(c);
        assertEquals(79, bag.retirerNChrominos(79).size());
    }

    @Test
    void retirerUnSeulChromino_quandVide() {
        ChroBag bag = new ChroBag(emptyBag);
        assertNull(bag.retirerUnSeulChromino());
    }

    @Test
    void retirerNChrominos_retireExactementN() {
        ChroBag bag = new ChroBag(fullBag);
        List<Chromino> result = bag.retirerNChrominos(10);
        assertEquals(10, result.size());
        assertEquals(70, bag.retirerNChrominos(70).size());
    }

    @Test
    void retirerNChrominos_lanceExceptionSiPasAssez() {
        ChroBag bag = new ChroBag(emptyBag);
        assertThrows(IndexOutOfBoundsException.class, () -> bag.retirerNChrominos(1));
    }

    @Test
    void seeFirstCameleon_present() {
        ChroBag bag = new ChroBag(fullBag);
        Chromino seen = bag.seeFirstCameleon();
        assertEquals(TileType.CAMELEON, seen.getPart2());
    }

    @Test
    void seeFirstCameleon_absent() {
        ChroBag bag = new ChroBag(onlyNormals);
        Chromino seen = bag.seeFirstCameleon();
        assertEquals(TileType.CAMELEON, seen.getPart2());
    }

    @Test
    void getFirstCameleon_presentEtRetire() {
        ChroBag bag = new ChroBag(fullBag);
        Chromino extracted = bag.getFirstCameleon();
        assertEquals(TileType.CAMELEON, extracted.getPart2());
        assertEquals(79, bag.retirerNChrominos(79).size());
    }

    @Test
    void getFirstCameleon_absentRetourneRef() {
        ChroBag bag = new ChroBag(onlyNormals);
        Chromino result = bag.getFirstCameleon();
        assertEquals(TileType.CAMELEON, result.getPart2());
    }

    @Test
    void isEmpty_vraiSiVide() {
        ChroBag bag = new ChroBag(emptyBag);
        assertTrue(bag.isEmpty());
    }

    @Test
    void isEmpty_fauxSiNonVide() {
        ChroBag bag = new ChroBag(fullBag);
        assertFalse(bag.isEmpty());
    }
}
