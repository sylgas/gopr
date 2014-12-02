package com.agh.gopr.app.ui.activity;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agh.gopr.app.R;
import com.agh.gopr.app.common.Preferences_;
import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.response.BasicActionInfo;
import com.agh.gopr.app.response.LoginResponse;
import com.agh.gopr.app.service.rest.RequestService;
import com.agh.gopr.app.service.rest.service.method.RestMethod;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import roboguice.util.Ln;

@EActivity(R.layout.login_activity)
public class LoginActivity extends AbstractActivity {

    @ViewById(R.id.login)
    protected EditText loginView;

    @ViewById(R.id.password)
    protected EditText passwordView;

    @ViewById(R.id.login_form)
    protected View loginFormView;

    @ViewById(R.id.sign_in_button)
    protected Button singInButton;

    @Pref
    protected Preferences_ preferences;

    private final RequestService.HttpCallback callback = new LoginCallback();
    private ProgressDialog progressDialog;

    @AfterViews
    protected void init() {
        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                attemptLogin();
                return true;
            }
        });
    }

    @Click(R.id.sign_in_button)
    public void attemptLogin() {
        loginView.setError(null);
        passwordView.setError(null);
        String login = loginView.getText().toString();
        String password = passwordView.getText().toString();

        if (!isEditTextFilled(passwordView, password) && !isEditTextFilled(loginView, login)) {
            progressDialog = ProgressDialog.show(LoginActivity.this, getString(R.string.progress_waiting),
                    getString(R.string.login_progress_message), true);
            try {
                RestMethod.LOGIN.run(this, callback, login, password);
            } catch (MethodException e) {
                Ln.e("Could not login: %s", e.getMessage());
                progressDialog.dismiss();
                displayErrorToast(getString(R.string.login_error));
            }
        }
    }

    private boolean isEditTextFilled(EditText editText, String data) {
        if (TextUtils.isEmpty(data)) {
            editText.setError(getString(R.string.error_field_required));
            editText.requestFocus();
            return true;
        }
        return false;
    }

    @UiThread
    protected void displayErrorToast(final String message) {
        dismissProgressBar();
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressBar();
    }

    private void dismissProgressBar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private class LoginCallback implements RequestService.HttpCallback {

        @Override
        public void handle(String json) {
            Gson gson = new Gson();
            LoginResponse loginResponse = gson.fromJson(json, LoginResponse.class);

            if (loginResponse.getStatus()) {
                List<BasicActionInfo> items = loginResponse.getBasicActionInfos();
                if (items.size() == 0) {
                    displayErrorToast(getString(R.string.not_assign_to_action_message));
                } else {
                    ActionListActivity_.intent(LoginActivity.this).responseList(loginResponse.getBasicActionInfos()).start();
                    LoginActivity.this.finish();
                }
            } else {
                displayErrorToast(getString(R.string.error_invalid_login_or_password));
            }
        }

        @Override
        public void onError(Throwable error) {
            Ln.e(error, "Could not login");
            progressDialog.dismiss();
            displayErrorToast(getString(R.string.login_error));
        }
    }
}