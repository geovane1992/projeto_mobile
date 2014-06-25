package br.ufg.UFGNotify.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.ufg.UFGNotify.R;
import br.ufg.UFGNotify.util.Preference;


public class MainActivity extends Activity {

	private Button acess_visitor;
	private Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// acesso como visitante
		acess_visitor = (Button) findViewById(R.id.visitor);
		acess_visitor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Usuário que está acessando não está logando no sistema
				Preference.setBoolean(MainActivity.this, Preference.LOGGED, false);

				Intent intent = new Intent(MainActivity.this, ManageNotificationsActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});

		// logar no sistema
		login = (Button) findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
