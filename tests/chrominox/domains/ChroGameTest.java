package chrominox.domains;

import org.junit.jupiter.api.Test;


import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
public class ChroGameTest {

	
	 private class DummyChrominoGenerator implements ChrominoGenerator {
	        private final UniqueChrominoGenerator uniqueChromino = new UniqueChrominoGenerator();
	        private final CameleonChrominoGenerator cameleonChromino = new CameleonChrominoGenerator();

	        @Override
	        public List<Chromino> generateChrominoes() {
	            List<Chromino> allChrominoes = new ArrayList<>();
	            allChrominoes.addAll(uniqueChromino.generateChrominoes());
	            allChrominoes.addAll(cameleonChromino.generateChrominoes());
	            Collections.shuffle(allChrominoes);
	            return allChrominoes;
	        }
	    }

	    private final DummyChrominoGenerator generator = new DummyChrominoGenerator();

	    private ChroGame createGameWithPlayers(int numChrominos) {
	        ChrominoGenerator generator = () -> {
	            List<Chromino> chrominos = new ArrayList<>();
	            for (int i = 0; i < 80; i++) { 
	                chrominos.add(new Chromino(TileType.RED, TileType.GREEN, TileType.CYAN));
	            }
	            return chrominos;
	        };
	        ChroGame game = new ChroGame(GameMode.QUICK_GAME, generator.generateChrominoes(),
	                List.of(new Player("Alice", "ORANGE"), new Player("Bob", "PINK")));
	        return game;
	    }

	    @Test
	    void testNextPlayerAlternance() {
	        ChroGame game = createGameWithPlayers(10);
	        Player current = game.nextPlayer();
	        Player next = game.nextPlayer();
	        assertNotEquals(current, next);
	    }

	    @Test
	    void testGetCurrentPlayerProperties() {
	        ChroGame game = createGameWithPlayers(10);
	        assertNotNull(game.getCurrentPlayerName());
	        assertTrue(game.getCurrentPlayerHandSize() > 0);
	        assertNotNull(game.getCurrentPlayerColor());
	        assertNotNull(game.getCurrentPlayerHandForView());
	    }

	    @Test
	    void testRotateAndMoveChromino() {
	        ChroGame game = createGameWithPlayers(10);
	        game.selectChrominoForPlacement(0);
	        Chromino c = game.getActiveChrominoCurrentPlayer();
	        game.moveActualChromino(1, 1);
	        game.rotateActiveChromino();
	        assertEquals(c, game.getActiveChrominoCurrentPlayer());
	    }

	    @Test
	    void testPickChrominoBehavior() {
	        ChroGame game = createGameWithPlayers(0);
	        var result1 = game.pickOneChromino();
	        var result2 = game.pickOneChromino();
	        assertTrue(result1.isSuccess());
	        assertFalse(result2.isSuccess());
	    }

	    @Test
	    void testTryPlaceChromino() {
	        ChroGame game = createGameWithPlayers(10);
	        game.selectChrominoForPlacement(0);
	        var result = game.tryToPlaceActiveChromino();
	        assertNotNull(result);
	    }

	    @Test
	    void testGetCoordAndTiles1() {
	        ChroGame game = createGameWithPlayers(10);
	        List<CoordAndTile> tiles = game.getCoordAndTiles();
	        assertNotNull(tiles);
	    }
//
//	    @Test
//	    void testWinnerLogic() {
//	        ChroGame game = createGameWithPlayers(10);
//	        List<Player> players = game.getPlayerList();
//	        players.get(0).addToScore(10);
//	        players.get(1).addToScore(5);
//	        PlayerTurnState rslt = game.playersWithTurnState.get(players.get(0));
//	        rslt.setDrawn(true);
//	        rslt.setPlaced(false);
//	        game.playersWithTurnState.get(players.get(1)).setDrawn(true);
//	        game.playersWithTurnState.get(players.get(1)).setPlaced(false);
//	        game.tryToPlaceActiveChromino();
//	        assertEquals(players.get(0), game.getWinner());
//	        assertEquals(players.get(0).getName(), game.getWinnerName());
//	    }

	    @Test
	    void testSelectChrominoFlow() {
	        ChroGame game = createGameWithPlayers(10);
	        game.selectNextChrominoInHand();
	        assertEquals(1, game.getSelectedChrominoIndex());
	        game.selectChrominoForPlacement(0);
	        assertNotNull(game.getActiveChrominoCurrentPlayer());
	    }

	    @Test
	    void testPutChrominoBackInHand() {
	        ChroGame game = createGameWithPlayers(10);
	        game.selectChrominoForPlacement(0);
	        game.putChrominoBackInHand();
	        assertNull(game.getActiveChrominoCurrentPlayer());
	    }

	    @Test
	    void testPlayerScores() {
	        ChroGame game = createGameWithPlayers(10);
	        List<Player> players = game.getPlayerList();
	        players.get(0).addToScore(13);
	        players.get(1).addToScore(21);
	        assertEquals(13, game.getPlayerScore(0));
	        assertEquals(21, game.getPlayerScore(1));
	    }

	    @Test
	    void testResetSelectedChrominoIndex() {
	        ChroGame game = createGameWithPlayers(10);
	        game.selectNextChrominoInHand();
	        game.resetSelectedChrominoInHand();
	        assertEquals(0, game.getSelectedChrominoIndex());
	    }

	    
	    
	    @Test
	    void testConstructor_ValidInput_InitializesCorrectly() {
	        List<Player> players = Arrays.asList(new Player("Player1", "Red"), new Player("Player2", "Blue"));
	        List<Chromino> chrominoes = generator.generateChrominoes();
	        GameMode gameMode = GameMode.CLASSIC_GAME;

	        ChroGame chroGame = new ChroGame(gameMode, chrominoes, players);

	        assertNotNull(chroGame);
	        assertEquals(2, chroGame.getPlayerList().size());
	        assertEquals(gameMode.getNbChrominoesInHand(), players.get(0).getHandSize());
	        assertEquals(gameMode.getNbChrominoesInHand(), players.get(1).getHandSize());
	    }

   

   

   
   

   
  
    
    @Test
    void testConstructor_NullPlayers_ThrowsIllegalArgumentException() {
        List<Chromino> chrominoes = Arrays.asList(
                new Chromino(TileType.RED, TileType.GREEN, TileType.CYAN),
                new Chromino(TileType.RED, TileType.YELLOW, TileType.CYAN));
        GameMode gameMode = GameMode.CLASSIC_GAME;
        assertThrows(IllegalArgumentException.class, () -> new ChroGame(gameMode, chrominoes, null));
    }

    @Test
    void testConstructor_WrongNumberOfPlayers_ThrowsIllegalArgumentException() {
        List<Chromino> chrominoes = Arrays.asList(
                new Chromino(TileType.RED, TileType.GREEN, TileType.CYAN),
                new Chromino(TileType.RED, TileType.YELLOW, TileType.CYAN));
        GameMode gameMode = GameMode.CLASSIC_GAME;
        List<Player> singlePlayer = Collections.singletonList(new Player("Player1", "Red"));
        assertThrows(IllegalArgumentException.class, () -> new ChroGame(gameMode, chrominoes, singlePlayer));
    }

    @Test
    void testResetGame_ResetsGameState() {
        List<Player> players = Arrays.asList(new Player("Player1", "Red"), new Player("Player2", "Blue"));
        List<Chromino> chrominoes = generator.generateChrominoes();
        GameMode gameMode = GameMode.CLASSIC_GAME;
        ChroGame chroGame = new ChroGame(gameMode, chrominoes, players);

        players.get(0).addToScore(10);
        players.get(1).addToScore(5);
        chroGame.resetGame();

        assertEquals(0, players.get(0).getScore());
        assertEquals(0, players.get(1).getScore());
        assertEquals(gameMode.getNbChrominoesInHand(), players.get(0).getHandSize());
        assertEquals(gameMode.getNbChrominoesInHand(), players.get(1).getHandSize());
    }

    @Test
    void testGetCoordAndTiles() {
        List<Player> players = Arrays.asList(new Player("Player1", "Red"), new Player("Player2", "Blue"));
        List<Chromino> chrominoes = generator.generateChrominoes();
        GameMode gameMode = GameMode.CLASSIC_GAME;
        ChroGame chroGame = new ChroGame(gameMode, chrominoes, players);

        List<CoordAndTile> coordAndTiles = chroGame.getCoordAndTiles();

        assertEquals(3, coordAndTiles.size());
    }

    @Test
    void testTryToPlaceActiveChromino_NoActiveChromino_Failure() {
        List<Player> players = Arrays.asList(new Player("Player1", "Red"), new Player("Player2", "Blue"));
        List<Chromino> chrominoes = generator.generateChrominoes();
        GameMode gameMode = GameMode.CLASSIC_GAME;
        ChroGame chroGame = new ChroGame(gameMode, chrominoes, players);

        PlacementResult result = chroGame.tryToPlaceActiveChromino();

        assertFalse(result.isSuccess());
        assertEquals("Aucun chromino actif.", result.getMessage());
    }


    
    @Test
    void testApplyPenaltyForRemainingChrominoes() {
   
        GameMode mode = GameMode.QUICK_GAME;

        List<Chromino> chrominos = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            chrominos.add(new Chromino(TileType.RED, TileType.GREEN, TileType.CYAN)); // valeur = 3
        }

     
        Player p1 = new Player("Alice", "RED");
        Player p2 = new Player("Bob", "BLUE");

        List<Player> players = Arrays.asList(p1, p2);
        ChroGame game = new ChroGame(mode, generator.generateChrominoes(), players);

      
        Chromino c1 = new Chromino(TileType.RED, TileType.RED, TileType.GREEN); 
        Chromino c2 = new Chromino(TileType.GREEN, TileType.GREEN, TileType.GREEN);

        p1.buildHand(Arrays.asList(c1, c2)); 
        p2.buildHand(Arrays.asList(c2, c2)); 

        game.pickOneChromino(); 
        game.nextPlayer();
        game.pickOneChromino(); 
        boolean isFinished = game.isGameOver();

        assertTrue(isFinished);
        assertEquals(-3, p1.getScore()); // 2 + 1 = 3
        assertEquals(-2, p2.getScore()); // 1 + 1 = 2
    }

    
    @Test
    void testTryToPlaceActiveChrominoSuccess() {
        // Arrange
        GameMode mode = GameMode.QUICK_GAME;


        
        Chromino c1 = new Chromino(TileType.CAMELEON, TileType.CAMELEON, TileType.CAMELEON); 
 
        Player p1 = new Player("Alice", "RED");
        Player p2 = new Player("Bob", "BLUE");
        
        ChroGame game = new ChroGame(mode, generator.generateChrominoes(), Arrays.asList(p1, p2));
        p1.addChrominoToHand(c1);

        game.selectChrominoForPlacement(5);
        Chromino actif = game.getActiveChrominoCurrentPlayer();
        actif.move(1, 0); 
        actif.rotate90Degrees(); 

      
        PlacementResult result = game.tryToPlaceActiveChromino();

        
        assertTrue(result.isSuccess());
        assertNull(game.getActiveChrominoCurrentPlayer());
        assertEquals(12, p1.getScore());
    }

    
    @Test
    void testHasAnyPlayerEmptyHandTrue() {
        GameMode mode = GameMode.QUICK_GAME;


        Player p1 = new Player("Alice", "RED"); 
        Player p2 = new Player("Bob", "BLUE");  

        ChroGame game = new ChroGame(mode, generator.generateChrominoes(), Arrays.asList(p1, p2));

       
        for (Player p : game.getPlayerList()) {
            if (p.getName().equals(game.getCurrentPlayerName())) {
                p.buildHand(new ArrayList<>()); 
                p.setActiveChromino(null);
                
            }else {
             game.playersWithTurnState.get(p).setDrawn(true);
            }
            game.nextPlayer(); 
           }
       

        boolean result = Arrays.stream(game.getClass().getDeclaredMethods())
            .anyMatch(m -> m.getName().equals("hasAnyPlayerEmptyHand"));

        assertTrue(game.isGameOver());
        assertTrue(result);
    }

   
    
}