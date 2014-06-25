package br.ufg.UFGNotify.activity;

import java.sql.SQLException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import br.ufg.UFGNotify.R;
import br.ufg.UFGNotify.model.Notification;
import br.ufg.UFGNotify.model.Sender;
import br.ufg.UFGNotify.persistence.helper.DatabaseHelper;
import br.ufg.UFGNotify.util.DefaultSender;

import com.j256.ormlite.stmt.UpdateBuilder;

public class ViewNotificationActivity extends Activity {
	private final String LOG_TAG = getClass().getSimpleName();

	public static final String PARAM_REQUEST_NOTIFICATION = "request_notification";

	// Persistence
	private DatabaseHelper mHelper;

	// View
	TextView mDate;
	TextView mDescription;
	TextView mSender;

	// Object
	Notification mNotification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selected_notification);

		initView();

		mNotification = (Notification) getIntent().getExtras().getSerializable(
				PARAM_REQUEST_NOTIFICATION);

		if (mNotification != null) {
			mDate.setText(getDateFormatted(mNotification.getDate()));
			mDescription.setText(mNotification.getContent());
			Sender sender = DefaultSender.create(mNotification.getSender()
					.intValue(), this);
			if (sender != null) {
				mSender.setText(sender.getName());
			}
			
			// Atualizar a visualização da notificação
			if (setNotificationAsViewed(true)) {
				Toast.makeText(this, "Notificação visualizada",
						Toast.LENGTH_SHORT).show();
				Log.i(LOG_TAG, "Notificação visualizada -> true");
			} else {
				Log.i(LOG_TAG,
						"Erro ao atualizar a visualização da notificação");
			}
		} else {
			// Fechar tela
			finish();
		}

	}

	private void initView() {
		mDate = (TextView) findViewById(R.id.notificationDate);
		mDescription = (TextView) findViewById(R.id.notificationDescription);
		mSender = (TextView) findViewById(R.id.notificationSender);
	}
	
	public String getDateFormatted(DateTime date) {
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm");
		return dtfOut.print(date);
	}

	/**
	 * Atualiza a notificação selecionada, atribuindo a data atual do sistema
	 * como sendo a data de visualização mais recente desta notificação.
	 * 
	 * @return <b>True</b> se a atualização foi realizada com sucesso, ou
	 *         <b>False</b> se nenhuma atualização foi realizada.
	 * @throws SQLException
	 */
	private boolean setNotificationAsViewed(Boolean read) {

		// Notificação já visualizada
		if (getSelectedNotification().isRead() == read) {
			return true;
		}

		// Alterar a visualização da notificação
		// True, indicando que a mensagem já foi viaulizada
		// False, mensagem não visualizada
		boolean successfullyUpdated = false;

		try {

			final UpdateBuilder<Notification, Long> updateBuilder = getHelper()
					.getNotificationDao().updateBuilder();

			// Atualiza a visualização
			updateBuilder.updateColumnValue(Notification.COLUMN_READ, read);
			updateBuilder.where().idEq(getSelectedNotification().getId());

			successfullyUpdated = updateBuilder.update() == 1;

		} catch (SQLException e) {
			Log.i(LOG_TAG,
					"Erro ao atualizar a visualização da notificação -> "
							+ read.toString());
		}

		if (successfullyUpdated) {
			getSelectedNotification().setRead(read);
			Log.i(LOG_TAG,
					"Visualização da notificação atualizada com sucesso -> "
							+ read.toString());
		}

		return successfullyUpdated;
	}

	private DatabaseHelper getHelper() {
		if (mHelper == null) {
			mHelper = DatabaseHelper.getHelperInstance(ViewNotificationActivity.this);
		}
		return mHelper;
	}

	private Notification getSelectedNotification() {
		return mNotification;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.selected_notification, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_mark:
			updateMenuRead(item);
			break;

		default:
			break;
		}
		return true;
	}

	private void updateMenuRead(MenuItem item) {

		if (getSelectedNotification().isRead()) {
			// Marcar notificação como não lida
			item.setTitle(R.string.action_mark_no_read);
			setNotificationAsViewed(false);
		} else {
			// Marcar notificação como lida
			item.setTitle(R.string.action_mark_read);
			setNotificationAsViewed(true);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mHelper != null) {
			mHelper.close();
			mHelper = null;
		}
	}
}
