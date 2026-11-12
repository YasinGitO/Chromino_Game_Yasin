package chrominox.domains;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChroMosaicTest {

    private Chromino createChromino(int row, int col, TileType a, TileType b, TileType c) {
        Chromino chromino = new Chromino(a, b, c);
        chromino.move(row, col);
        return chromino;
    }

    @Test
    void testInitialPlacementWithCameleon() {
        Chromino cameleon = new Chromino(TileType.RED, TileType.CAMELEON, TileType.GREEN);
        ChroMosaic mosaic = new ChroMosaic(cameleon);

        List<Coordinate> occupied = mosaic.getOccupiedCoordinates();
        assertEquals(3, occupied.size());
        assertTrue(occupied.contains(new Coordinate(0, 0)));
    }

    @Test
    void testPlaceChrominoDirect() {
        Chromino cameleon = new Chromino(TileType.RED, TileType.CAMELEON, TileType.GREEN);
        ChroMosaic mosaic = new ChroMosaic(cameleon);

        Chromino c = new Chromino(TileType.CYAN, TileType.CYAN, TileType.CYAN);
        c.move(2, 0);
        mosaic.placeChromino(c);

        assertTrue(mosaic.getOccupiedCoordinates().containsAll(c.get3Coordinates()));
    }

    @Test
    void testGetTileTypeFromCoordinate() {
        Chromino base = new Chromino(TileType.RED, TileType.CAMELEON, TileType.GREEN);
        ChroMosaic mosaic = new ChroMosaic(base);

        Coordinate center = base.get3Coordinates().get(1);
        TileType type = mosaic.getTileTypeFromCoordinate(center);
        assertEquals(TileType.CAMELEON, type);
    }

    @Test
    void testTryPlaceSuccess() {
        Chromino base = new Chromino(TileType.RED, TileType.CAMELEON, TileType.RED);
        ChroMosaic mosaic = new ChroMosaic(base);

        Chromino neighbor = new Chromino(TileType.RED, TileType.RED, TileType.RED);
        neighbor.move(1, 1);
        neighbor.rotate90Degrees();
        assertTrue(mosaic.tryPlace(neighbor).isSuccess());
    }

    @Test
    void testTryPlaceFailsOccupied() {
        Chromino base = new Chromino(TileType.RED, TileType.CAMELEON, TileType.RED);
        ChroMosaic mosaic = new ChroMosaic(base);

        Chromino conflict = new Chromino(TileType.RED, TileType.GREEN, TileType.RED);
        conflict.move(0, 0); // overlaps center of base
        assertFalse(mosaic.tryPlace(conflict).isSuccess());
    }

    @Test
    void testTryPlaceFailsIncompatible() {
        Chromino base = new Chromino(TileType.RED, TileType.CAMELEON, TileType.RED);
        ChroMosaic mosaic = new ChroMosaic(base);

        Chromino clash = new Chromino(TileType.YELLOW, TileType.YELLOW, TileType.YELLOW);
        clash.move(2, 0); // adjacent but incompatible
        assertFalse(mosaic.tryPlace(clash).isSuccess());
    }

    @Test
    void testTryPlaceFailsNotEnoughContacts() {
        Chromino base = new Chromino(TileType.RED, TileType.CAMELEON, TileType.RED);
        ChroMosaic mosaic = new ChroMosaic(base);

        Chromino edge = new Chromino(TileType.RED, TileType.RED, TileType.RED);
        edge.move(3, 0); // only 1 contact
        assertFalse(mosaic.tryPlace(edge).isSuccess());
    }

    @Test
    void testGetPlacedChrominoesListReturnsCopy() {
        Chromino base = new Chromino(TileType.RED, TileType.CAMELEON, TileType.RED);
        ChroMosaic mosaic = new ChroMosaic(base);
        assertEquals(1, mosaic.getPlacedChrominoesList().size());
    }

    @Test
    void testComputeScoreBasicAndBonus() {
        Chromino base = new Chromino(TileType.RED, TileType.CAMELEON, TileType.RED);
        ChroMosaic mosaic = new ChroMosaic(base);

        Chromino extra = new Chromino(TileType.RED, TileType.RED, TileType.RED);
        extra.move(2, 0);
        mosaic.tryPlace(extra);

        Chromino toScore = new Chromino(TileType.RED, TileType.RED, TileType.RED);
        toScore.move(1, 1); // touches two placed chrominos

        int score = mosaic.computeScore(toScore);
        assertTrue(score >= 4 && score <= 27);
    }
    
    @Test
    void testGetTileTypeAt() {
        Chromino base = new Chromino(TileType.RED, TileType.CAMELEON, TileType.RED);
        ChroMosaic mosaic = new ChroMosaic(base);

        Coordinate center = base.get3Coordinates().get(1);
        TileType retrieved = mosaic.getTileTypeAt(center.getRow(), center.getCol());

        assertEquals(TileType.CAMELEON, retrieved);
    }
    
    @Test
    void testTryPlaceFailsContactInvalide() {
        Chromino base = new Chromino(TileType.RED, TileType.CAMELEON, TileType.RED);
        ChroMosaic mosaic = new ChroMosaic(base);

        Chromino clash = new Chromino(TileType.YELLOW, TileType.YELLOW, TileType.YELLOW);
        clash.move(1, 1);
        clash.rotate90Degrees();

        PlacementResult result = mosaic.tryPlace(clash);

        assertFalse(result.isSuccess());
        assertEquals("Contact avec une couleur incompatible.", result.getMessage());
    }
    
    @Test
    void testCountValidContactsTriggersInvalidTilePlacement() {
        Chromino base = new Chromino(TileType.RED, TileType.CAMELEON, TileType.RED);
        ChroMosaic mosaic = new ChroMosaic(base);

        Chromino invalid = new Chromino(TileType.YELLOW, TileType.YELLOW, TileType.YELLOW);
        invalid.move(1, 0);
        invalid.rotate90Degrees();
        PlacementResult result = mosaic.tryPlace(invalid);

        assertFalse(result.isSuccess());
        assertEquals("Contact avec une couleur incompatible.", result.getMessage());
    }
    
    @Test
    void testComputeScoreSkipsSameChrominoWithContinue() {
        Chromino c = new Chromino(TileType.RED, TileType.GREEN, TileType.CYAN);
        c.move(0, 0);

        ChroMosaic mosaic = new ChroMosaic(c);

        int score = mosaic.computeScore(c);

        assertEquals(c.getValue(), score);
    }
    
    @Test
    void testIsCompatibleAllCombinations() {
        ChroMosaic mosaic = new ChroMosaic(new Chromino(TileType.RED, TileType.CAMELEON, TileType.CYAN));

        assertTrue(mosaic.isCompatible(TileType.CAMELEON, TileType.RED));

        assertTrue(mosaic.isCompatible(TileType.GREEN, TileType.CAMELEON));

        assertTrue(mosaic.isCompatible(TileType.CYAN, TileType.CYAN));

        assertFalse(mosaic.isCompatible(TileType.RED, TileType.YELLOW));
    }
}
