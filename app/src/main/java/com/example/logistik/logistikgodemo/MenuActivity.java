package com.example.logistik.logistikgodemo;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, InformacionTab.OnFragmentInteractionListener {
    TextView textUsuario;
    View view;
    String Nameusuario;
    String IDViajeProceso;
    String StatusProceso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        view = navigationView.getHeaderView(0);

        textUsuario = (TextView) view.findViewById(R.id.textUsuario);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            Nameusuario = bundle.getString("NameUsuario");
            textUsuario.setText(Nameusuario);
            IDViajeProceso = bundle.getString("IDViajeProceso");
            StatusProceso = bundle.getString("StatusProceso");
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textUsuario.setText(savedInstanceState.getString("Name_Usuario"));
        Nameusuario = savedInstanceState.getString("NameUser");
        Toast.makeText(this, "Usuario " + Nameusuario, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("NameUser", Nameusuario);
        outState.putString("Name_Usuario", textUsuario.getText().toString());

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_acercade) {
            Intent intent = new Intent(MenuActivity.this, AcercadeActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.nav_incio) {
            // Handle the camera action
        } else if (id == R.id.nav_viajecurso) {
            intent = new Intent(MenuActivity.this, ViajeCursoActivity.class);
            intent.putExtra("IDViajeProceso", IDViajeProceso);
            intent.putExtra("StatusProceso", StatusProceso);



        } else if (id == R.id.nav_evidencias) {
            intent = new Intent(MenuActivity.this, Activity_seguimientoViaje.class);
        } else if (id == R.id.nav_ajustes) {

        } else if (id == R.id.nav_acercade) {
            intent = new Intent(MenuActivity.this, AcercadeActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
