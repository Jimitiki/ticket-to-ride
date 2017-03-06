package tests;

import org.junit.Test;

import delta.monstarz.exceptions.loginExceptions.InvalidCredentialsException;
import delta.monstarz.exceptions.loginExceptions.LoginException;
import delta.monstarz.exceptions.loginExceptions.UsernameInUseException;
import delta.monstarz.model.account.PersonManager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author bradcarter
 */
public class PersonManagerTest
{
	PersonManager manager = PersonManager.getInstance();

	@Test
	public void LoginTests(){
		manager.clear();
		String token;

		// Login should work
		try {
			token = manager.register("name_01", "password_01");
			assertTrue(manager.authTokenIsValid(token));

			token = manager.register("name_02", "password_02");
			assertTrue(manager.authTokenIsValid(token));

			token = manager.register("name_03", "password_03");
			assertTrue(manager.authTokenIsValid(token));
		}
		catch (LoginException e){
			assertTrue(false);
		}

		// Username in use test
		try{
			token = manager.register("name_01", "password_01");
		}
		catch (UsernameInUseException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Username in use test
		try{
			token = manager.register("name_02", "password_02");
		}
		catch (UsernameInUseException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Correct format test
		try{
			token = manager.register("", "password");
			assertTrue(false);
		}
		catch (InvalidCredentialsException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Correct format test
		try{
			token = manager.register("a name", "");
			assertTrue(false);
		}
		catch (InvalidCredentialsException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Correct format test
		try{
			token = manager.register("", "");
			assertTrue(false);
		}
		catch (InvalidCredentialsException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}

	}

	@Test
	public void registerTests(){
		manager.clear();
		String token;

		// Correct format test
		try{
			token = manager.login("", "password");
			assertTrue(false);
		}
		catch (InvalidCredentialsException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Correct format test
		try{
			token = manager.login("a name", "");
			assertTrue(false);
		}
		catch (InvalidCredentialsException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Correct format test
		try{
			token = manager.login("", "");
			assertTrue(false);
		}
		catch (InvalidCredentialsException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Logging in before making an account
		try {
			manager.login("name_01", "password_01");
			assertTrue(false);
		}
		catch (InvalidCredentialsException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Logging in before making an account
		try {
			manager.login("@#$%", "^&*(");
			assertTrue(false);
		}
		catch (InvalidCredentialsException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}


		// Register accounts
		try{
			manager.register("person_01", "password_01");
			manager.register("person_02", "password_02");
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Test logging in
		try{
			token = manager.login("person_01", "password_01");
			assertTrue(manager.authTokenIsValid(token));

			token = manager.login("person_02", "password_02");
			assertTrue(manager.authTokenIsValid(token));
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Account that exists but wrong password
		try{
			manager.login("person_01", "wrong password");
		}
		catch(InvalidCredentialsException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Account that exists but wrong password
		try{
			manager.login("person_02", "wrong password");
		}
		catch(InvalidCredentialsException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void authTokenValidationTest(){
		manager.clear();
		String token = null;
		final long halfSecond = 500;
		final long oneSecond = 1000;
		final long threeSeconds = 3000;

		manager.setAuthTokenLifeTime(oneSecond);
		manager.setRemovalFrequency(0); // Will remove old tokens during each token validation

		//---------------------------------------------------------------------
		// Basic validation test
		try{
			assertFalse(manager.authTokenIsValid("random token"));
		}
		catch (Exception e){
			assertTrue(false);
		}

		//---------------------------------------------------------------------
		// Test to see if a token is still valid after 3 seconds(It should not be valid)
		// Token valid before expiration
		try{
			token = manager.register("person_01", "password_01");
			assertTrue(manager.authTokenIsValid(token));
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Sleep three seconds
		try {
			Thread.sleep(threeSeconds);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		// Check if the token still valid
		try{
			boolean result = manager.authTokenIsValid(token);
			assertFalse(result);
		}
		catch (Exception e){
			assertTrue(false);
		}

		//---------------------------------------------------------------------
		// Check to see if a token is valid after 1/2 second(It should be valid)
		// Token valid before expiration
		try{
			token = manager.login("person_01", "password_01");
			assertTrue(manager.authTokenIsValid(token));
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Sleep three seconds
		try {
			Thread.sleep(halfSecond);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		// Check if the token still valid
		try{
			boolean result = manager.authTokenIsValid(token);
			assertTrue(result);
		}
		catch (Exception e){
			assertTrue(false);
		}


	}
}
