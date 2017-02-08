package deltamonstarz.tickettoride;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Trevor on 2/6/2017.
 */

public class LoginAndRegisterTests {

	@Test
	public void AuthenticationTest(){
		// Be sure to start the server up fresh without any user accounts when running these tests
		try {
			assertTrue(Presenter.getInstance().register("localhost", "8080", "joe", "passwords"));
			assertFalse(Presenter.getInstance().register("localhost", "8080", "joe", "passwords"));
			assertTrue(Presenter.getInstance().login("localhost", "8080", "joe", "passwords"));
			assertFalse(Presenter.getInstance().register("localhost", "8080", "joe", "notSame"));

			assertFalse(Presenter.getInstance().login("localhost", "8080", "bob", "pass"));

			assertTrue(Presenter.getInstance().login("localhost", "8080", "joe", "passwords"));

			// Malformed Input Tests
			assertFalse(Presenter.getInstance().register("localhost", "8080", "", "passwords"));
			assertFalse(Presenter.getInstance().register("localhost", "8080", "jeff", ""));
			assertFalse(Presenter.getInstance().login("localhost", "8080", "", "passwords"));
			assertFalse(Presenter.getInstance().login("localhost", "8080", "jeff", ""));
		}
		catch (Exception e){
			assertTrue(false);
		}

	}

}
