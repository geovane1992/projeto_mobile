package br.ufg.UFGNotify.activity;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import br.ufg.UFGNotify.R;
import br.ufg.UFGNotify.model.User;
import br.ufg.UFGNotify.persistence.dao.UserDAO;
import br.ufg.UFGNotify.persistence.helper.DatabaseHelper;

import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.EmailValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;


public class SignUpActivity extends Activity {
	private final String LOG_TAG = getClass().getSimpleName();

	public final static String RESULT_USER = "USER";

	EditText mFullName;
	EditText mEmail;
	EditText mUserName;
	EditText mPassword;
	RadioGroup mRadioTypeGroup;
	RadioButton mRadioType;
	Button mRegister;

	DatabaseHelper mHelper;
	UserDAO mUserDAO;
	User mUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		initView();

		mHelper = DatabaseHelper.getHelperInstance(this);
		try {
			mUserDAO = new UserDAO(mHelper.getConnectionSource());
		} catch (SQLException e) {
			showMesage("Erro ao conectar no banco de dados");
		}
	}

	private void initView() {
		mFullName = (EditText) findViewById(R.id.userFullName);
		mEmail = (EditText) findViewById(R.id.userEmail);
		mUserName = (EditText) findViewById(R.id.userName);
		mPassword = (EditText) findViewById(R.id.userPassword);
		mRadioTypeGroup = (RadioGroup) findViewById(R.id.radioTypeGroup);

		mRegister = (Button) findViewById(R.id.register);
		mRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				registeringUser();
			}
		});
	}

	/**
	 * 
	 */
	private void registeringUser() {
		if (validateForm()) {
			/* Integer type = (Integer) mRadioType.getTag(); */
			String fullName = mFullName.getText().toString();
			String password = mPassword.getText().toString();
			String email = mEmail.getText().toString();
			String userName = mUserName.getText().toString();

			mUser = new User(fullName, email, userName, password, 1);
			try {
				mUserDAO.create(mUser);
				showMesage("Usuario criado -> " + mUser.toString());

				// Retornar a tela de login
				Intent returnIntent = new Intent();
				Bundle extras = new Bundle();
				extras.putSerializable(RESULT_USER, mUser);
				returnIntent.putExtras(extras);
				setResult(RESULT_OK, returnIntent);
				finish();
			} catch (SQLException e) {
				showMesage(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private boolean validateForm() {
		Validate fullNameField = new Validate(mFullName);
		fullNameField.addValidator(new NotEmptyValidator(this,
				R.string.fill_out_the_text_field));

		Validate emailField = new Validate(mEmail);
		emailField.addValidator(new NotEmptyValidator(this,
				R.string.fill_out_the_text_field));
		emailField
				.addValidator(new EmailValidator(this, R.string.email_invalid));

		Validate userNameField = new Validate(mUserName);
		userNameField.addValidator(new NotEmptyValidator(this,
				R.string.fill_out_the_text_field));

		Validate passwordField = new Validate(mPassword);
		passwordField.addValidator(new NotEmptyValidator(this,
				R.string.fill_out_the_text_field));

		Form form = new Form();
		form.addValidates(fullNameField);
		form.addValidates(emailField);
		form.addValidates(userNameField);
		form.addValidates(passwordField);

		if (form.validate()) {
			boolean valid = true;
			if (mUserDAO.isNameRegistred(userNameField.getSource().getText()
					.toString())) {
				mUserName.setError(getString(R.string.user_already_registered));
				mUserName.requestFocus();
				valid = false;
			}
			if (mUserDAO.isEmailRegistred(emailField.getSource().getText()
					.toString())) {
				mEmail.setError(getString(R.string.email_already_used));
				mEmail.requestFocus();
				valid = false;
			}
			return valid;
		}

		/*
		 * int selectedType = mRadioTypeGroup.getCheckedRadioButtonId(); if
		 * (selectedType > 0) { mRadioType = (RadioButton)
		 * findViewById(selectedType); switch (selectedType) { case
		 * R.id.radioStudent: mRadioType.setTag(new Integer(User.STUDENT));
		 * break; case R.id.radioFunctionary: mRadioType.setTag(new
		 * Integer(User.FUNCTIONARY)); break; default: break; } isValid = true;
		 * }
		 */

		return false;

	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		super.onBackPressed();
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