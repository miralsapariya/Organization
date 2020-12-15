package com.onlineeducationsystemorganization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.onlineeducationsystemorganization.util.AppSharedPreference;

public class ThankYouActivity extends AppCompatActivity {

    private TextView tvBack;
    private ImageView imgBack;
    private String course_name;
    private TextView tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b=getIntent().getExtras();

        if(b != null)
            course_name =b.getString("course_name");

        imgBack=findViewById(R.id.imgBack);
        tvDescription =findViewById(R.id.tvDescription);
        tvDescription.setText(getResources().getString(R.string.congrat_success)+" "+course_name+" "+getResources().getString(R.string.course));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AppSharedPreference.getInstance().getString(ThankYouActivity.this, AppSharedPreference.USER_TYPE).equalsIgnoreCase("1")) {
                    Intent intent = new Intent(ThankYouActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.putExtra("from", "thankyou");
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(ThankYouActivity.this, MainUserActivity.class);
                    intent.putExtra("from", "thankyou");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
        tvBack=findViewById(R.id.tvBack);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AppSharedPreference.getInstance().getString(ThankYouActivity.this, AppSharedPreference.USER_TYPE).equalsIgnoreCase("1")) {
                    Intent intent = new Intent(ThankYouActivity.this, MainActivity.class);
                    intent.putExtra("from", "thankyou");
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(ThankYouActivity.this, MainUserActivity.class);
                    intent.putExtra("from", "thankyou");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}