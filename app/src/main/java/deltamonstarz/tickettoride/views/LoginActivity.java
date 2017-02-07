package deltamonstarz.tickettoride.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.Presenter;

public class LoginActivity extends BaseView {

	//Widgets
	private EditText mHostText;
	private EditText mPortText;
	private EditText mUsernameText;
	private EditText mPasswordText;
	private Button mRegisterButton;
	private Button mLoginButton;

	private Presenter mPresenter;

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

		mPresenter = new Presenter();

		mRegisterButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View pView)
			{
				String host = mHostText.getText().toString();
				String port = mPortText.getText().toString();
				String username = mUsernameText.getText().toString();
				String password = mPasswordText.getText().toString();
				mPresenter.register(host, port, username, password);
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
				mPresenter.login(host, port, username, password);
			}
		});
	}

	@Override
	public void update() {

	}
}

//Toast.makeText(getApplicationContext(), "That port is not a number.", Toast.LENGTH_SHORT).show();

