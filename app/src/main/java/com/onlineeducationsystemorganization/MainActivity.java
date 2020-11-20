package com.onlineeducationsystemorganization;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onlineeducationsystemorganization.fragment.AccountFragment;
import com.onlineeducationsystemorganization.fragment.CartFragment;
import com.onlineeducationsystemorganization.fragment.CoursesFragment;
import com.onlineeducationsystemorganization.fragment.HomeFragment;
import com.onlineeducationsystemorganization.fragment.UsersFragment;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;

import java.util.Locale;

public class MainActivity extends BaseActivity {

    public BottomNavigationView nav_view;
    public ImageView imgAddUser;
    private TextView toolbar_title;
    private ImageView imgSearch,imgNotification;
    private Configuration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLanguage();
        setContentView(R.layout.activity_main);

        toolbar_title = findViewById(R.id.toolbar_title);
        imgNotification=findViewById(R.id.imgNotification);
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });
        initBottomNavigationBar();

        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey("from")) {
            //gotoMyCourses();
        } else {
            imgNotification.setVisibility(View.VISIBLE);
            loadFragment(new HomeFragment());
        }


    }


    private void setLanguage() {
        if (AppSharedPreference.getInstance().getString(this, AppSharedPreference.LANGUAGE_SELECTED) != null) {

            if (AppSharedPreference.getInstance().getString(this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
                String languageToLoad = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            } else {

                String languageToLoad = "ar"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }


    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
        }
    }

    private void initBottomNavigationBar() {
        nav_view = findViewById(R.id.nav_view);
        imgSearch = findViewById(R.id.imgSearch);
        imgAddUser = findViewById(R.id.imgAddUser);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                if (f instanceof HomeFragment) {
                    Intent intent = new Intent(MainActivity.this, HomeSearchActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, SearchCourseActivity.class);
                    startActivity(intent);
                }
            }
        });

        nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        imgSearch.setVisibility(View.VISIBLE);
                        imgNotification.setVisibility(View.VISIBLE);
                        imgAddUser.setVisibility(View.GONE);
                        toolbar_title.setText(getString(R.string.home));
                        loadFragment(new HomeFragment());
                        break;
                    case R.id.page_2:
                        imgSearch.setVisibility(View.GONE);
                        imgNotification.setVisibility(View.GONE);
                        imgAddUser.setVisibility(View.GONE);
                        toolbar_title.setText(getString(R.string.cart));
                        loadFragment(new CartFragment());

                        break;
                    case R.id.page_3:

                        imgSearch.setVisibility(View.VISIBLE);
                        imgNotification.setVisibility(View.GONE);
                        imgAddUser.setVisibility(View.GONE);
                        toolbar_title.setText(getString(R.string.courses));
                        loadFragment(new CoursesFragment());

                        break;
                    case R.id.page_4:
                        imgSearch.setVisibility(View.GONE);
                        imgNotification.setVisibility(View.GONE);
                        imgAddUser.setVisibility(View.VISIBLE);
                        toolbar_title.setText(getString(R.string.users));
                        loadFragment(new UsersFragment());

                        break;
                    case R.id.page_5:
                        imgSearch.setVisibility(View.GONE);
                        imgNotification.setVisibility(View.GONE);
                        imgAddUser.setVisibility(View.GONE);
                        toolbar_title.setText(getString(R.string.account));
                        loadFragment(new AccountFragment());
                        break;

                }
                return true;
            }
        });
    }

    public void gotoCategory() {

        // nav_view.getMenu().findItem(R.id.page_2).setChecked(true);

        imgSearch.setVisibility(View.GONE);
        imgNotification.setVisibility(View.GONE);
        //toolbar_title.setText(getString(R.string.category));
        // loadFragment(new CategoryFragment());
    }

   /* public void gotoMyCourses()
    {
        nav_view.getMenu().findItem(R.id.page_3).setChecked(true);

        imgSearch.setVisibility(View.GONE);
        loadFragment(new MyCoursesFragment());
    }*/

}
