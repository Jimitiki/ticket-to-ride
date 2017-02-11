package deltamonstarz.tickettoride.views;

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

		mPresenter = LoginPresenter.getInstance();
		mPresenter.setActivity(this);

		mRegisterButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View pView)
			{
				String host = mHostText.getText().toString();
				String port = mPortText.getText().toString();
				String username = mUsernameText.getText().toString();
				String password = mPasswordText.getText().toString();
				try
				{
					mPresenter.register(host, port, username, password);
				}
				catch(ConnectionException pE)
				{
					Toast.makeText(getApplicationContext(), "Error: Couldn't connect to server.", Toast.LENGTH_SHORT).show();
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
				try
				{
					mPresenter.login(host, port, username, password);
				}
				catch(ConnectionException pE)
				{
					Toast.makeText(getApplicationContext(), "Error: Couldn't connect to server.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void onLogin()
	{

	}

	public void onLoginFailed() {}
}
