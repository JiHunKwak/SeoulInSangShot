package onetwopunch.seoulinsangshot.com.seoulinsangshot.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONObject;

import java.util.Arrays;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by MG_PARK on 2017-10-06.
 */

public class CustomDialog extends DialogFragment implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private Button btn_cancel;
    private Button btn_complete;
    private TextView txt_dialog_comment;
    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;
    public GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private static CustomDialog customDialog = new CustomDialog();

    public static CustomDialog getInstance(){
        return customDialog;
    }

    private LoginButton btn_facebook;
    private CallbackManager callback;

    private View view;

    private String email;
    private String name;

    public interface OnCompleteListener{
        void onInputedData(String name, String email);
    }

    private OnCompleteListener mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnCompleteListener) activity;
        }
        catch (ClassCastException e) {
            Log.d("DialogFragmentExample", "Activity doesn't implement the OnCompleteListener interface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.CYAN));
        view = inflater.inflate(R.layout.login_dialog, container);
        view.findViewById(R.id.sign_in_button).setOnClickListener(this);
        view.findViewById(R.id.sign_out_button).setOnClickListener(this);

        btn_cancel=(Button)view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String msg = "취소되었습니다.";
                if(!BaseActivity.isKorean) msg = "It was canceled.";
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                dismiss();
            }
        });


        callback = CallbackManager.Factory.create();

        btn_facebook = (LoginButton) view.findViewById(R.id.btn_facebook);
        btn_facebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        btn_facebook.setFragment(this);
        btn_facebook.registerCallback(callback, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("result",object.toString());

                        try {
                            email = object.getString("email");
                            name = object.getString("name");
                            BaseActivity.isLogined=2;

                            mCallback.onInputedData(name,email);

                            updateUI(false,2);

                            if(BaseActivity.isLogined==1){
                                revokeAccess();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr",error.toString());
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        if(mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity(), this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        SignInButton signInButton = (SignInButton) view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

        btn_complete=(Button)view.findViewById(R.id.btn_complete);
        btn_complete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                AccessToken accessToken = AccessToken.getCurrentAccessToken();

                if(BaseActivity.isLogined==2 && accessToken ==null){
                    email=null;
                    name=null;
                    BaseActivity.isLogined=0;
                }

                mCallback.onInputedData(name,email);

                dismiss();

            }
        });

        txt_dialog_comment=(TextView)view.findViewById(R.id.txt_dialog_comment);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken!=null){
            updateUI(false,2);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken!=null){
            updateUI(false,2);
        }

        callback.onActivityResult(requestCode, resultCode, data);


    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            email=acct.getEmail();
            name=acct.getDisplayName();
            updateUI(true,1);
            BaseActivity.isLogined=1;
            mCallback.onInputedData(name,email);
        } else {

            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if(accessToken!=null){
                updateUI(false,2);
            }else{
                updateUI(false,1);
            }
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void revokeAccess() {


        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        email=null;
                        name=null;
                        BaseActivity.isLogined=0;
                        mCallback.onInputedData(name,email);
                        updateUI(false,1);
                        // [END_EXCLUDE]
                    }
                });

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn, int chk) {

        if(chk==1) {
            if (signedIn) {
                view.findViewById(R.id.sign_in_button).setVisibility(View.GONE);
                view.findViewById(R.id.btn_facebook).setVisibility(View.GONE);
                view.findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
                txt_dialog_comment.setText(R.string.dialog_msg_lo);
            } else {

                view.findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
                view.findViewById(R.id.btn_facebook).setVisibility(View.VISIBLE);
                view.findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
            }
        }else if(chk==2 ){

            view.findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            view.findViewById(R.id.btn_facebook).setVisibility(View.VISIBLE);
            view.findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
            txt_dialog_comment.setText(R.string.dialog_msg_lo);


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                revokeAccess();
        }
    }
}