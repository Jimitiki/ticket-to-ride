package deltamonstarz.tickettoride.views;

import android.os.Bundle;

import deltamonstarz.tickettoride.Presenter;
import deltamonstarz.tickettoride.R;

public class LoginActivity extends BaseView {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		try {
			Presenter.getInstance().register("localhost", "8080", "joe", "passwords");
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public void update() {

	}
}
