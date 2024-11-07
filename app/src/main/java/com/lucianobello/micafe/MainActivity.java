package com.lucianobello.micafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Use lambda for setting OnItemSelectedListener
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_preparar_cafe) {
                startActivity(new Intent(MainActivity.this, PrepararCafeActivity.class));
                return true;
            } else if (item.getItemId() == R.id.nav_menu_principal) {
                // Sin accion necesaria, ya se estan en el MainActivity
                return true;
            } else if (item.getItemId() == R.id.nav_comprar) {
                startActivity(new Intent(MainActivity.this, ComprarActivity.class));
                return true;
            }
            return false;
        });

        // Establecer una seleccion por defecto
        bottomNav.setSelectedItemId(R.id.nav_menu_principal);
    }
}
