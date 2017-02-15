package deltamonstarz.tickettoride.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.exceptions.ConnectionException;
import deltamonstarz.tickettoride.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity {

	//Widgets
	private EditText mHostText;
	private EditText mPortText;
	private EditText mUsernameText;
	private EditText mPasswordText;
	private Button mRegisterButton;
	private Button mLoginButton;

	private LoginPresenter mPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mHostText = (EditText) findViewById(R.id.host_input);
		mPortText = (EditText) findViewById(R.id.port_input);
		mUsernameText = (EditText) findViewById(R.id.username_input);
		mPasswordText = (EditText) findViewById(R.id.password_input);
		mRegisterButton = (Button) findViewById(R.id.register_button);
		mLoginButton = (Button) findViewById(R.id.login_button);

		mHostText.setText("10.0.2.2");
		mPortText.setText("8080");

		mPresenter = LoginPresenter.getInstance();
		mPresenter.setActivity(this);

		mRegisterButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View pView) {
				String host = mHostText.getText().toString();
				String port = mPortText.getText().toString();
				String username = mUsernameText.getText().toString();
				String password = mPasswordText.getText().toString();
				if (validateInput(username, password, host, port)) {
					mPresenter.register(host, port, username, password);
				} else {
					Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
				}
			}
		});

		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View pView)
			{
				String host = mHostText.getText().toString();
				String port = mPortText.getText().toString();
				String username = mUsernameText.getText().toString();
				String password = mPasswordText.getText().toString();
				if (validateInput(username, password, host, port)) {
					mPresenter.login(host, port, username, password);
				} else {
					Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mPresenter.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mPresenter.onPause();
	}

	public static Intent newIntent(Context packageContext)
	{
		return new Intent(packageContext, LoginActivity.class);
	}

	public void onLogin()
	{
		mPresenter.endObserve();
		Intent i = GameSelectorActivity.newIntent(LoginActivity.this);
		startActivity(i);
	}

	public void onLoginFailed() {
		Toast toast = Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_LONG);
		toast.show();
	}

	public void onRegisterFailed() {
		Toast toast = Toast.makeText(this, "Username already in use", Toast.LENGTH_LONG);
		toast.show();
	}

	public void onConnectionError() {
		Toast toast = Toast.makeText(this, "Network Error: Could not connect to server", Toast.LENGTH_LONG);
		toast.show();
	}

	private boolean validateInput(String username, String password, String IPAddress, String port) {
		return !(username.equals("") || password.equals("") || IPAddress.equals("") || port.equals(""));
	}
}
