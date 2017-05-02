package com.world.bolandian.actionbarintents;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , ColorPickerFragment.OnColorChangedListener {

    private static final int REQUEST_CODE_CALL = 1;
    private CoordinatorLayout layout;
     Button done, cancel;
    private int id;
    private EditText phone;
    private AlertDialog.Builder builder;
    private FrameLayout fragmentContainer;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_CALL){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                buildDialog();
            }else {
                Toast.makeText(this, "No Permission...", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentContainer = (FrameLayout)findViewById(R.id.fragmentContainer);
        layout = (CoordinatorLayout) findViewById(R.id.mainActivityId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        id = item.getItemId();

        switch (id) {
            case (R.id.action_call):
                buildDialog();
                break;
            case (R.id.action_dial):
                buildDialog();
                break;
            case(R.id.action_color_picker):
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ColorPickerFragment(),"colorPickerFrag").commit();
                break;
            case (R.id.action_settings):
                Toast.makeText(this, "Version 0.1", Toast.LENGTH_SHORT).show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public void buildDialog() {
        builder = new AlertDialog.Builder(this);

        View v = getLayoutInflater().inflate(R.layout.dialog_getphone, layout, false);

        phone = (EditText) v.findViewById(R.id.etPhoneNumber);
        done = (Button) v.findViewById(R.id.btnDone);
        cancel = (Button) v.findViewById(R.id.btnCancel);

        done.setOnClickListener(this);
        cancel.setOnClickListener(this);

        builder.setView(v);
        builder.show();
    }


    @Override
    public void onClick(View v) {
        AlertDialog dialog = builder.create();
        switch (v.getId()) {
            case R.id.btnDone:
                String phoneNumber = phone.getText().toString();
                if (!(phoneNumber.equals(""))) {
                    if (id == R.id.action_call) {
                        Uri tel = Uri.parse("tel:" + phoneNumber);
                        Intent dial = new Intent(Intent.ACTION_CALL,/*action */tel /*data*/);
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this,  new String[]{
                                    Manifest.permission.CALL_PHONE
                            }, REQUEST_CODE_CALL);

                            return;
                        }
                        startActivity(dial);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + phoneNumber));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                }else{
                    Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCancel:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void onColorChanged(int color) {
        layout.setBackgroundColor(color);
    }
}
