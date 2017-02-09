package deltamonstarz.tickettoride.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import deltamonstarz.tickettoride.Presenter;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		try {
			LoginPresenter.getInstance().register("localhost", "8080", "joe", "passwords");
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

	public void onLogin() {

	}
}
