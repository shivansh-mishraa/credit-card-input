package com.assignment.creditcardinput;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.assignment.creditcardinput.data.CardInput;
import com.assignment.creditcardinput.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private TextInputLayout obCreditCardNumber;
    private TextInputLayout obExpirationDate;
    private TextInputLayout obCvv;
    private TextInputLayout obFirstName;
    private TextInputLayout obLastName;
    private TextInputEditText etCreditCardNumber;
    private TextInputEditText etExpirationDate;
    private TextInputEditText etCvv;
    private TextInputEditText etFirstName;
    private TextInputEditText etLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // get references to the TextInputLayout and TextInputEditText views
        obCreditCardNumber = mBinding.creditCardInputPanel.tilCreditCardNumber;
        obExpirationDate = mBinding.creditCardInputPanel.tilExpirationDate;
        obCvv = mBinding.creditCardInputPanel.tilCvv;
        obFirstName = mBinding.creditCardInputPanel.tilFirstName;
        obLastName = mBinding.creditCardInputPanel.tilLastName;
        etCreditCardNumber = mBinding.creditCardInputPanel.etCreditCardNumber;
        etExpirationDate = mBinding.creditCardInputPanel.etExpirationDate;
        etCvv = mBinding.creditCardInputPanel.etCvv;
        etFirstName = mBinding.creditCardInputPanel.etFirstName;
        etLastName = mBinding.creditCardInputPanel.etLastName;

        mBinding.creditCardInputPanel.btnSubmitPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCard(v);
            }
        });
    }

    public void validateCard(View view) {
        clearAnyPreviousErrorMessages();
        String creditCardNumber = etCreditCardNumber.getText().toString();
        String expirationDate = etExpirationDate.getText().toString();
        String cvv = etCvv.getText().toString();
        String firstName = CardInput.cleanName(etFirstName.getText().toString());
        etFirstName.setText(firstName);
        String lastName = CardInput.cleanName(etLastName.getText().toString());
        etLastName.setText(lastName);

        if (!CardInput.isValidCardNumber(creditCardNumber)) {
            obCreditCardNumber.setError(getString(R.string.invalid_card_number));
            etCreditCardNumber.requestFocus();
        } else if (!CardInput.isValidExpirationDate(expirationDate)) {
            obExpirationDate.setError(getString(R.string.invalid_expiration_date));
            etExpirationDate.requestFocus();
        } else if (!CardInput.isValidCvv(creditCardNumber, cvv)) {
            obCvv.setError(getString(R.string.invalid_cvv));
            etCvv.requestFocus();
        } else if (firstName.isEmpty()) {
            obFirstName.setError(getString(R.string.please_enter_first_name));
            etFirstName.requestFocus();
        } else if (lastName.isEmpty()) {
            obLastName.setError(getString(R.string.please_enter_last_name));
            etLastName.requestFocus();
        } else {
            closeSoftKeyboard(view);
            CardInput creditCard = new CardInput(creditCardNumber, expirationDate, cvv, firstName, lastName);
            alertDialog(submitCreditCard(creditCard), null, getString(R.string.ok));
        }
    }

    private String submitCreditCard(CardInput creditCard) {

        return getString(R.string.successful);
    }

    private void clearAnyPreviousErrorMessages() {
        obCreditCardNumber.setError(null);
        obExpirationDate.setError(null);
        obCvv.setError(null);
        obFirstName.setError(null);
        obLastName.setError(null);
    }

    public void closeSoftKeyboard(View view) {
     
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void alertDialog(String title, String message, String buttonText) {
        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonText, null)
                .show();
    }
}
