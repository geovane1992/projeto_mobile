package br.ufg.UFGNotify.activity;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import br.ufg.UFGNotify.R;
import br.ufg.UFGNotify.model.User;
import br.ufg.UFGNotify.persistence.dao.UserDAO;
import br.ufg.UFGNotify.persistence.helper.DatabaseHelper;
import br.ufg.UFGNotify.util.Preference;

import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.EmailValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;

public class LoginActivity extends Activity {
	public static int REQUEST_CODE = 1010;

	// View
	EditText mEmail;
	EditText mPassword;
	Button mLogin;
	ProgressBar mProgress;

	// Persistence
	DatabaseHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initView();
	}

	private void initView() {
		mEmail = (EditText) findViewById(R.id.userEmail);
		mPassword = (EditText) findViewById(R.id.userPassword);
		mProgress = (ProgressBar) findViewById(R.id.progressLogin);
		mLogin = (Button) findViewById(R.id.login_user);
		mLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginUser();
			}
		});
	}

	private void loginUser() {

		if (validateForm()) {
			String email = mEmail.getText().toString();
			String password = mPassword.getText().toString();

			User user = null;
			try {
				user = getUserDAO().findFomEmail(email);
			} catch (SQLException e) {
				showMesage(getString(R.string.error_connect_database));
			}

			if (user != null) {
				if (user.getPassword().equals(password)) {
					showMesage("Logado");
					Preference.setString(this, Preference.USER_LOGGED, user.getEmail());
					Preference.setBoolean(this, Preference.LOGGED, true);
					openNotificationActivity();
				} else {
					showMesage(getString(R.string.erro_login));
				}
			} else {
				showMesage(getString(R.string.erro_login));
			}
		}
	}

	private void openNotificationActivity() {
		Intent intent = new Intent(this, ManageNotificationsActivity.class);
		startActivity(intent);
	}

	private UserDAO getUserDAO() throws SQLException {
		if (mHelper == null) {
			mHelper = DatabaseHelper.getHelperInstance(this);
		}
		
		return mHelper.getUserDao();
	}

	private boolean validateForm() {
		Validate emailField = new Validate(mEmail);
		emailField.addValidator(new NotEmptyValidator(this,
				R.string.fill_out_the_text_field));
		emailField
				.addValidator(new EmailValidator(this, R.string.email_invalid));

		Validate passwordField = new Validate(mPassword);
		passwordField.addValidator(new NotEmptyValidator(this,
				R.string.fill_out_the_text_field));

		Form form = new Form();
		form.addValidates(emailField);
		form.addValidates(passwordField);

		return form.validate();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_sign_up:
			Intent intent = new Intent(this, SignUpActivity.class);
			this.startActivityForResult(intent, REQUEST_CODE);
			break;

		default:
			break;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				User user = (User) data
						.getSerializableExtra(SignUpActivity.RESULT_USER);
				mEmail.setText(user.getEmail());
				mPassword.setText(user.getPassword());

				loginUser();
			}
			if (resultCode == RESULT_CANCELED) {
				mEmail.setText("");
				mPassword.setText("");
			}
		}
	}

	/**
	 * 
	 */
	private void showProgress() {
		mProgress.setVisibility(View.VISIBLE);
		mLogin.requestFocus();
		enabledForm(false);
		hideKeyboard();
	}

	/**
	 * 
	 */
	private void hideProgress() {
		mProgress.setVisibility(View.GONE);
		enabledForm(true);
	}

	/**
	 * Habilitar campos do formulario
	 * 
	 * @param enabled
	 */
	private void enabledForm(boolean enabled) {
		mEmail.setEnabled(enabled);
		mPassword.setEnabled(enabled);
		mLogin.setEnabled(enabled);
	}

	/**
	 * Sumir com teclado
	 */
	private void hideKeyboard() {
		InputMethodManager inputManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@Override
	protected void onDestroy() {		
		if (mHelper != null) {
			mHelper.close();
			mHelper = null;
		}
		super.onDestroy();
	}

	/**
	 * 
	 * @param message
	 */
	private void showMesage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

}
