package com.icall.icall;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.icall.icall.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = null;
    private Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fragment = new LoginFragment();
        switchToFragment(fragment);

    }

    public void switchToFragment(Fragment fragment) {
        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.lfragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
