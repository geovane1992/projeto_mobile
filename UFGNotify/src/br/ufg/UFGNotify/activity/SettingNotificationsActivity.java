package br.ufg.UFGNotify.activity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import br.ufg.UFGNotify.R;
import br.ufg.UFGNotify.util.Preference;


public class SettingNotificationsActivity extends Activity {
	
	// View
	Switch switchReitoria;
	Switch switchProReitoria;
	Switch switchBiblioteca;
	Switch switchCoordenador;
	Switch switchDirecao;
	Switch switchGeral;

	String mUser;

	public static SettingNotificationsActivity newInstance() {
		return new SettingNotificationsActivity();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_notifications_dialog);

		initView();
	}

	private void initView() {
		mUser = Preference.getString(this, Preference.USER_LOGGED);

		switchGeral = (Switch) this.findViewById(R.id.generalView);
		switchGeral.setOnCheckedChangeListener(checkedChangeListener);
		switchGeral.setTag(Preference.GENERAL);
		switchGeral.setChecked(Preference.getBoolean(this,
				Preference.getUserPreference(mUser, Preference.GENERAL)));

		switchReitoria = (Switch) this.findViewById(R.id.rectoryView);
		switchReitoria.setOnCheckedChangeListener(checkedChangeListener);
		switchReitoria.setTag(Preference.RECTORY);
		switchReitoria.setChecked(Preference.getBoolean(this,
				Preference.getUserPreference(mUser, Preference.RECTORY)));

		switchProReitoria = (Switch) this.findViewById(R.id.proRectoryView);
		switchProReitoria.setOnCheckedChangeListener(checkedChangeListener);
		switchProReitoria.setTag(Preference.PRO_RECTORY);
		switchProReitoria.setChecked(Preference.getBoolean(this,
				Preference.getUserPreference(mUser, Preference.PRO_RECTORY)));

		switchBiblioteca = (Switch) this.findViewById(R.id.libraryView);
		switchBiblioteca.setOnCheckedChangeListener(checkedChangeListener);
		switchBiblioteca.setTag(Preference.LIBRARY);
		switchBiblioteca.setChecked(Preference.getBoolean(this,
				Preference.getUserPreference(mUser, Preference.LIBRARY)));

		switchCoordenador = (Switch) this
				.findViewById(R.id.courseCoordinatorView);
		switchCoordenador.setOnCheckedChangeListener(checkedChangeListener);
		switchCoordenador.setTag(Preference.COURSE_COORDINATOR);
		switchCoordenador.setChecked(Preference.getBoolean(this, Preference
				.getUserPreference(mUser, Preference.COURSE_COORDINATOR)));

		switchDirecao = (Switch) this.findViewById(R.id.boardUnityView);
		switchDirecao.setOnCheckedChangeListener(checkedChangeListener);
		switchDirecao.setTag(Preference.BOARD_UNITY);
		switchDirecao.setChecked(Preference.getBoolean(this, Preference
				.getUserPreference(mUser, Preference.BOARD_UNITY)));
	}

	OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			Object object = buttonView.getTag();
			if (object != null) {
				if (object instanceof String) {
					String key = (String) object;

					Preference
							.setBoolean(SettingNotificationsActivity.this,
									Preference.getUserPreference(mUser, key),
									isChecked);
				}
			}
		}
	};
}