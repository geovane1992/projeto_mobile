package br.ufg.UFGNotify.activity;

import java.sql.SQLException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import br.ufg.UFGNotify.R;
import br.ufg.UFGNotify.model.Notification;
import br.ufg.UFGNotify.model.User;
import br.ufg.UFGNotify.persistence.helper.DatabaseHelper;
import br.ufg.UFGNotify.util.DefaultNotification;
import br.ufg.UFGNotify.util.Preference;

import com.j256.ormlite.table.TableUtils;


public class SplashScreenActivity extends FragmentActivity {

	private final String LOG_TAG = getClass().getSimpleName();

	// Persistence
	private DatabaseHelper mHelper;

	// View
	TextView textSplash;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		initView();

		execute();
	}

	private void initView() {
		textSplash = (TextView) findViewById(R.id.textSplash);
	}

	private void execute() {
		AsyncTask<Void, String, Boolean> asyncTask = new AsyncTask<Void, String, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					TableUtils.clearTable(getHelper().getConnectionSource(),
							Notification.class);
					for (Notification notification : DefaultNotification
							.getList(SplashScreenActivity.this)) {
						getHelper().getNotificationDao().create(notification);
						
					}

					TableUtils.clearTable(getHelper().getConnectionSource(),
							User.class);
					// Criar um usuário padrão
					int user = getHelper().getUserDao().create(
							new User("Geovane", "geovane@gmail.com",
									"geovane", "geovane", User.STUDENT));
					Log.d(LOG_TAG, "User created -> " + user);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onProgressUpdate(String... values) {
				String value = values[0];
				if (value != null) {
					textSplash.setText(values[0]);
				}
			}

			@Override
			protected void onPostExecute(Boolean result) {
				// Enquanto o usuário estiver logado, abrir a tela com as
				// notificações
				if (Preference.getBoolean(SplashScreenActivity.this, Preference.LOGGED)) {
					openActivity(ManageNotificationsActivity.class);
				} else {
					// Abrir a tela principal
					openActivity(MainActivity.class);
				}
			}

		};

		asyncTask.execute();

	}

	private void openActivity(Class activity) {
		Intent intent = new Intent(SplashScreenActivity.this, activity);
		startActivity(intent);
		finish();
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	private DatabaseHelper getHelper() throws SQLException {
		if (mHelper == null) {
			mHelper = DatabaseHelper.getHelperInstance(this);
		}
		return mHelper;
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		if (mHelper != null) {
			mHelper.close();
			mHelper = null;
		}
		super.onDestroy();
	}

}
