package edu.rose_hulman.miskowbs.photorecommendationapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.common.SignInButton;

import edu.rose_hulman.miskowbs.photorecommendationapp.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {

    private View mLoginForm;
    private View mProgressSpinner;
    private boolean mLoggingIn;
    private OnLoginListener mListener;
    private SignInButton mGoogleSignInButton;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoggingIn = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        mLoginForm = rootView.findViewById(R.id.login_form);
        mProgressSpinner = rootView.findViewById(R.id.login_progress);
        mGoogleSignInButton = rootView.findViewById(R.id.google_sign_in_button);

        mGoogleSignInButton.setColorScheme(SignInButton.COLOR_LIGHT);
        mGoogleSignInButton.setSize(SignInButton.SIZE_WIDE);
        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithGoogle();
            }
        });
        return rootView;
    }

    private void loginWithGoogle() {
        if (mLoggingIn) {
            return;
        }
        showProgress(true);
        mLoggingIn = true;
        mListener.onGoogleLogin();
        hideKeyboard();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
    }

    private void showProgress(boolean show) {
        mProgressSpinner.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        mGoogleSignInButton.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnLoginListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onLoginError(String message) {
        new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getString(R.string.login_error))
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show();

        showProgress(false);
        mLoggingIn = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnLoginListener {
        void onGoogleLogin();
    }
}
