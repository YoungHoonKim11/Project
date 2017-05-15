package com.example.user.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Floating Memo App");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(this, FloatWindow.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        startService(new Intent(this, FloatWindow.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.NewPage:
                Toast.makeText(this, "NewPage", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Hide:
                Toast.makeText(this, "Hide", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Exit:
                onExitPressed();
                return true;

            case R.id.ChangeFont:
                Toast.makeText(this, "ap", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.ChangeBackground:
                Toast.makeText(this, "grp", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.ChangeVisibility:
                Toast.makeText(this, "ban", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onExitPressed() {
        new AlertDialog.Builder(this).setTitle("Exit").setMessage("Finish?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }
}
