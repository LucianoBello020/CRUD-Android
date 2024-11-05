package com.lucianobello.micafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPrepararCafe = findViewById(R.id.btn_preparar_cafe);
        Button btnComprar = findViewById(R.id.btn_comprar);
        Button btnVerMenu = findViewById(R.id.btn_ver_menu);

        btnPrepararCafe.setOnClickListener(v -> openActivity(PrepararCafeActivity.class));
        btnComprar.setOnClickListener(v -> openActivity(ComprarActivity.class));
        btnVerMenu.setOnClickListener(v -> openActivity(MenuOpcionesActivity.class));
    }

    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
    }
}
