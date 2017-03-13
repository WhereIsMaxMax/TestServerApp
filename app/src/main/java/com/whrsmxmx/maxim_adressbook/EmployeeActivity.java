package com.whrsmxmx.maxim_adressbook;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.whrsmxmx.maxim_adressbook.model.Employee;

public class EmployeeActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 901;
    private String TAG = EmployeeActivity.class.getSimpleName();

    TextView nameText;
    TextView titleText;
    TextView phoneText;
    TextView emailText;
    ImageView photoView;
    LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        final Employee employee = (Employee) getIntent().getExtras().getSerializable(App.CODE_EMPLOYEE);

        mLoginManager = new LoginManager(this);

        nameText = (TextView) findViewById(R.id.name);
        titleText = (TextView) findViewById(R.id.title);
        phoneText = (TextView) findViewById(R.id.phone);
        emailText = (TextView) findViewById(R.id.email);
        photoView = (ImageView) findViewById(R.id.photo);

        nameText.setText(employee.getName());
        titleText.setText(employee.getTitle());
        phoneText.setText(employee.getPhone());
        emailText.setText(employee.getEmail());

        phoneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onPhoneClick");
                if (ActivityCompat.checkSelfPermission(EmployeeActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EmployeeActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CODE);
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + employee.getPhone()));
                startActivity(intent);
            }
        });

        emailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND)
                        .setType("message/rfc822")
                        .putExtra(Intent.EXTRA_EMAIL, new String[]{employee.getEmail()});
                try {
                    startActivity(intent);
                }catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(EmployeeActivity.this, R.string.no_email, Toast.LENGTH_SHORT).show();
                }

            }
        });

        String path = getResources().getString(R.string.base_url)+getResources().getString(R.string.photo_url)
                +"login="+mLoginManager.getName()+"&"+"password="+mLoginManager.getPass()+"&"+"id="+employee.getID();

        Log.d("Photo path:", path);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels/2-10;

        Picasso.with(EmployeeActivity.this).load(path).
                resize(width, width)
                .centerCrop().into(photoView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.sure)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mLoginManager.logout();
                            setResult(MainActivity.CODE_LOGOUT);
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==PERMISSION_CODE){
            if(permissions.toString().contains(Manifest.permission.CALL_PHONE)){
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneText.getText().toString()));
                startActivity(intent);
            }else{
                Toast.makeText(EmployeeActivity.this, "Permission canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
