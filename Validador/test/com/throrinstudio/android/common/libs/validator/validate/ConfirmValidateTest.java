package com.throrinstudio.android.common.libs.validator.validate;

import android.content.Context;
import android.widget.TextView;

import com.throrinstudio.android.common.libs.validator.validate.ConfirmValidate;
import com.throrinstudio.android.common.libs.validator.RobolectricGradleTestRunner;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;


@RunWith(RobolectricGradleTestRunner.class)
public class ConfirmValidateTest {

    private Context mContext;
    private TextView mFirstField;
    private TextView mSecondField;

    @Before
    public void setup() {
        mContext = Robolectric.getShadowApplication().getApplicationContext();
        mFirstField = new TextView(mContext);
        mSecondField = new TextView(mContext);
    }

    @Test
    public void validateEmptyFields() throws Exception {
        mFirstField.setText("");
        mSecondField.setText("");

        ConfirmValidate confirmValidate = new ConfirmValidate(mFirstField, mSecondField);
        assertFalse(confirmValidate.isValid());
    }

    @Test
    public void validateFirstFieldEmpty() throws Exception {
        mFirstField.setText("");
        mSecondField.setText("abcdef");

        ConfirmValidate confirmValidate = new ConfirmValidate(mFirstField, mSecondField);
        assertFalse(confirmValidate.isValid());
    }

    @Test
    public void validateSecondFieldEmpty() throws Exception {
        mFirstField.setText("abcdef");
        mSecondField.setText("");

        ConfirmValidate confirmValidate = new ConfirmValidate(mFirstField, mSecondField);
        assertFalse(confirmValidate.isValid());
    }

    @Test
    public void validate() throws Exception {
        mFirstField.setText("abcdef");
        mSecondField.setText("abcdef");

        ConfirmValidate confirmValidate = new ConfirmValidate(mFirstField, mSecondField);
        assertTrue(confirmValidate.isValid());

        // Change second text
        mSecondField.setText("fedcba");
        assertFalse(confirmValidate.isValid());
    }
}
