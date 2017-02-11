package tests;

/**
 * Created by Trevor on 2/11/2017.
 */

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import delta.monstarz.exceptions.loginExceptions.InvalidCredentialsException;
import delta.monstarz.exceptions.loginExceptions.LoginException;
import delta.monstarz.exceptions.loginExceptions.UsernameInUseException;
import delta.monstarz.server.Server;
import delta.monstarz.server.ServerModelManager;

import static org.junit.Assert.*;

public class ServerModelTests {

	@Test
	public void LoginTests(){
		ServerModelManager.clearModel();
		ServerModelManager model = ServerModelManager.getInstance();
		String token;

		// Login should work
		try {
			token = model.register("name_01", "password_01");
			assertTrue(model.authTokenIsValid(token));

			token = model.register("name_02", "password_02");
			assertTrue(model.authTokenIsValid(token));

			token = model.register("name_03", "password_03");
			assertTrue(model.authTokenIsValid(token));
		}
		catch (LoginException e){
			assertTrue(false);
		}

		// Username in use test
		try{
			token = model.register("name_01", "password_01");
		}
		catch (UsernameInUseException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Username in use test
		try{
			token = model.register("name_02", "password_02");
		}
		catch (UsernameInUseException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Correct format test
		try{
			token = model.register("", "password");
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
			token = model.register("a name", "");
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
			token = model.register("", "");
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
		ServerModelManager.clearModel();
		ServerModelManager model = ServerModelManager.getInstance();
		String token;

		// Correct format test
		try{
			token = model.login("", "password");
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
			token = model.login("a name", "");
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
			token = model.login("", "");
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
			model.login("name_01", "password_01");
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
			model.login("@#$%", "^&*(");
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
			model.register("person_01", "password_01");
			model.register("person_02", "password_02");
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Test logging in
		try{
			token = model.login("person_01", "password_01");
			assertTrue(model.authTokenIsValid(token));

			token = model.login("person_02", "password_02");
			assertTrue(model.authTokenIsValid(token));
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Account that exists but wrong password
		try{
			model.login("person_01", "wrong password");
		}
		catch(InvalidCredentialsException e){
			// This is the proper execution
		}
		catch (Exception e){
			assertTrue(false);
		}

		// Account that exists but wrong password
		try{
			model.login("person_02", "wrong password");
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
		ServerModelManager.clearModel();
		ServerModelManager model = ServerModelManager.getInstance();
		String token = null;
		final long halfSecond = 500;
		final long oneSecond = 1000;
		final long threeSeconds = 3000;

		model.setAuthTokenLifeTime(oneSecond);
		model.setRemovalFrequency(0); // Will remove old tokens during each token validation

		//---------------------------------------------------------------------
		// Basic validation test
		try{
			assertFalse(model.authTokenIsValid("random token"));
		}
		catch (Exception e){
			assertTrue(false);
		}

		//---------------------------------------------------------------------
		// Test to see if a token is still valid after 3 seconds(It should not be valid)
		// Token valid before expiration
		try{
			token = model.register("person_01", "password_01");
			assertTrue(model.authTokenIsValid(token));
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
			boolean result = model.authTokenIsValid(token);
			assertFalse(result);
		}
		catch (Exception e){
			assertTrue(false);
		}

		//---------------------------------------------------------------------
		// Check to see if a token is valid after 1/2 second(It should be valid)
		// Token valid before expiration
		try{
			token = model.login("person_01", "password_01");
			assertTrue(model.authTokenIsValid(token));
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
			boolean result = model.authTokenIsValid(token);
			assertTrue(result);
		}
		catch (Exception e){
			assertTrue(false);
		}


	}

	@Test
	void gameManagementTesting(){
		ServerModelManager.clearModel();
		ServerModelManager model = ServerModelManager.getInstance();


	}
}
