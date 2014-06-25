package com.throrinstudio.android.common.libs.validator.validate;

import android.content.Context;
import android.widget.TextView;

import com.throrinstudio.android.common.libs.validator.AbstractValidate;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;


public class OrTwoRequiredValidate extends AbstractValidate {

    private TextView mFirstField;
    private TextView mSecondField;
    private Context mContext;

    public OrTwoRequiredValidate(TextView field1, TextView field2) {
        mFirstField = field1;
        mSecondField = field2;
        mContext = mFirstField.getContext();
    }

    @Override
    public boolean isValid() {
        Validate firstFieldValidator = new Validate(mFirstField);
        NotEmptyValidator notEmptyValidator = new NotEmptyValidator(mContext);
        firstFieldValidator.addValidator(notEmptyValidator);

        Validate secondFieldValidator = new Validate(mSecondField);
        secondFieldValidator.addValidator(notEmptyValidator);

        if (firstFieldValidator.isValid() || secondFieldValidator.isValid()) {
            mFirstField.setError(null);
            return true;
        } else {
            mFirstField.setError(notEmptyValidator.getMessage());
            return false;
        }
    }
}
