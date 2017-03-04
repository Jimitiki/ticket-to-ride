package tests;

import org.junit.Test;

import delta.monstarz.model.GameManager;

/**
 * @author bradcarter
 */
public class GameManagerTest
{
	GameManager manager = GameManager.getInstance();

	@Test
	public void gameManagementTesting(){
		manager.clear();

		// ToDo: Update these test
		/*

		assertEquals(0, model.getGames().size());
		try{
			model.register("person_01", "password_01");
			model.register("person_02", "password_02");
			model.register("person_03", "password_03");
		}
		catch (Exception e){
			assertTrue(false);
		}


		// Make a game
		model.createGame("person_01", "game_01");
		assertEquals(1, model.getGames().size());
		assertEquals(1, model.getGamesIn("person_01").size());

		// See if the creator is in the game
		GameInfo gameInfoA = model.getGamesIn("person_01").get(0);
		GameInfo gameInfoB = new GameInfo("game_01", "person_01", 0, new Date(), 1, false);
		assertEquals(gameInfoA, gameInfoB);

		// Join a second person to the game
		model.joinGame("person_02", 0);
		gameInfoA = model.getGamesIn("person_01").get(0);
		gameInfoB = new GameInfo("game_01", "person_01", 0, new Date(), 2, false);
		assertEquals(gameInfoA, gameInfoB);

		// Make a second game
		model.createGame("person_01", "game_02");
		assertEquals(2, model.getGames().size());
		assertEquals(2, model.getGamesIn("person_01").size());

		// Make a third game
		model.createGame("person_01", "game_03");
		assertEquals(3, model.getGames().size());
		assertEquals(3, model.getGamesIn("person_01").size());

		// Make a fourth game by a different person
		model.createGame("person_02", "game_04");
		assertEquals(4, model.getGames().size());
		assertEquals(2, model.getGamesIn("person_02").size());
		gameInfoA = model.getGamesIn("person_02").get(1);
		gameInfoB = new GameInfo("game_04", "person_02", 3, new Date(), 1, false);
		assertEquals(gameInfoA, gameInfoB);


		// Test getJoinableGames() along with startGame()
		assertEquals(4, model.getJoinableGames().size());
		model.startGame(0);
		assertEquals(3, model.getJoinableGames().size());
		assertEquals(3, model.getGamesIn("person_01").size());

		*/

	}
}
