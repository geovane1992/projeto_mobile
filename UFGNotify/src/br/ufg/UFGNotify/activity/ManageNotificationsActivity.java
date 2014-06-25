package br.ufg.UFGNotify.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import br.ufg.UFGNotify.R;
import br.ufg.UFGNotify.adapter.SectionsPagerAdapter;
import br.ufg.UFGNotify.model.Sender;
import br.ufg.UFGNotify.util.DefaultSender;
import br.ufg.UFGNotify.util.Preference;


public class ManageNotificationsActivity extends FragmentActivity {

	private final String LOG_TAG = getClass().getSimpleName();

	// View
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);

		mContext = this;

		initView();

		createSwipeTabs();
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.pager);
	}

	/**
	 * Mostrar as notificações que o usuário selecionou para visualizar
	 */
	private void createSwipeTabs() {
		List<Sender> senders = new ArrayList<Sender>();

		boolean logged = Preference.getBoolean(this, Preference.LOGGED);

		if (logged) {
			String user = Preference.getString(this, Preference.USER_LOGGED);

			if (Preference.getBoolean(mContext,
					Preference.getUserPreference(user, Preference.LIBRARY))) {
				senders.add(DefaultSender.create(Sender.LIBRARY, this));
			}
			if (Preference.getBoolean(mContext, Preference.getUserPreference(
					user, Preference.COURSE_COORDINATOR))) {
				senders.add(DefaultSender.create(Sender.COURSE_COORDINATOR,
						this));
			}
			if (Preference.getBoolean(mContext, Preference.getUserPreference(
					user, Preference.BOARD_UNITY))) {
				senders.add(DefaultSender.create(Sender.BOARD_UNITY, this));
			}
			if (Preference.getBoolean(mContext, Preference.getUserPreference(
					user, Preference.DOCENTE_DISCIPLINA))) {
				senders.add(DefaultSender.create(Sender.DOCENTE_DISCIPLINA,
						this));
			}
			if (Preference.getBoolean(mContext, Preference.getUserPreference(
					user, Preference.PRO_RECTORY))) {
				senders.add(DefaultSender.create(Sender.PRO_RECTORY, this));
			}
			if (Preference.getBoolean(mContext,
					Preference.getUserPreference(user, Preference.RECTORY))) {
				senders.add(DefaultSender.create(Sender.RECTORY, this));
			}
			if (Preference.getBoolean(mContext,
					Preference.getUserPreference(user, Preference.GENERAL))) {
				senders.add(DefaultSender.create(Sender.GENERAL, this));
			}
		} else {
			// Notificações públicas será mostrada para todos os usuários
			// registrados ou visitantes
			senders.add(DefaultSender.create(Sender.GENERAL, this));
		}

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(), senders);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Apenas os usuários logados visualizarão o menu
		if (Preference.getBoolean(this, Preference.LOGGED)) {
			getMenuInflater().inflate(R.menu.notification, menu);
		}

		// Acesso como visitando não pode realizar a escolha do tipo de
		// notificações que deseja receber. Por padrão irá visualizar as
		// notificações públicas.

		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_setting_notifications:
			openActivity(SettingNotificationsActivity.class);
			break;

		case R.id.action_settings:
			openActivity(SettingsActivity.class);
			break;

		case R.id.action_logout:
			logout();
			break;

		default:
			break;
		}
		return true;
	}

	/**
	 * Abrir uma Activity
	 * 
	 * @param activiy
	 *            que deseja iniciar
	 */
	private void openActivity(Class activiy) {
		Intent intent = new Intent(ManageNotificationsActivity.this, activiy);
		startActivity(intent);
	}

	/**
	 * Desconectar usuário
	 */
	private void logout() {
		// Remover as preferencias do login
		Preference.setBoolean(this, Preference.LOGGED,
				Preference.DEFAULT_BOOLEAN);
		Preference.setString(this, Preference.USER_LOGGED,
				Preference.DEFAULT_STRING);

		// Fechar a activity atual
		finish();

		// Abrir a tela principal
		openActivity(MainActivity.class);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Ao retornar recriar as tabs
		createSwipeTabs();

		Log.d(LOG_TAG, "Resume");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(LOG_TAG, "Destroy");
	}
}
