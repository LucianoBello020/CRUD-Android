package com.lucianobello.micafe;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PrepararCafeActivity extends AppCompatActivity {

    // Declara variables para los campos de entrada de texto (`EditText`) y los botones (`Button`).

    private EditText ed_nombre, ed_apellido, ed_edad;
    private Button b_agregar, b_ver;

    @Override
    // Método `onCreate` que se ejecuta al iniciar la actividad. Establece el diseño asociado a la actividad desde el archivo XML `activity_preparar_cafe`.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparar_cafe);

        // Asocia las variables EditText y Button con los elementos de la interfaz
        ed_nombre = findViewById(R.id.et_nombre);
        ed_apellido = findViewById(R.id.et_apellido);
        ed_edad = findViewById(R.id.et_edad);

        b_agregar = findViewById(R.id.btn_agregar);
        b_ver = findViewById(R.id.btn_ver);

        // Configura el botón "Ver" para abrir la actividad Leer
        b_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Leer.class);
                startActivity(i);
            }
        });

        // Configura el botón "Agregar" para ejecutar el método insertar
        b_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });
    }

    // Método para insertar un nuevo registro en la base de datos
    public void insertar() {
        try {
            // Obtiene el texto ingresado por el usuario en los campos de nombre, apellido y edad.
            String nombre = ed_nombre.getText().toString();
            String apellido = ed_apellido.getText().toString();
            String edad = ed_edad.getText().toString();

            // Abre o crea la base de datos llamada "BD_LUCIANO" en modo privado.
            SQLiteDatabase db = openOrCreateDatabase("BD_LUCIANO", Context.MODE_PRIVATE, null);

            // Crea la tabla "persona" si no existe, con las columnas id (clave primaria auto-incremental), nombre, apellido y edad.
            db.execSQL("CREATE TABLE IF NOT EXISTS persona(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR, apellido VARCHAR, edad VARCHAR)");

            // Prepara una instrucción SQL para insertar los valores de nombre, apellido y edad en la tabla "persona".
            String sql = "insert into persona(nombre, apellido, edad) values(?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);

            // Vincula los valores ingresados por el usuario a los parámetros de la instrucción SQL.
            statement.bindString(1, nombre);
            statement.bindString(2, apellido);
            statement.bindString(3, edad);

            // Ejecuta la instrucción SQL para insertar el registro en la base de datos.
            statement.execute();

            Toast.makeText(this, "Datos agregados satisfactoriamente en la base de datos.", Toast.LENGTH_LONG).show();

            // Limpia los campos de entrada
            ed_nombre.setText("");
            ed_apellido.setText("");
            ed_edad.setText("");
            ed_nombre.requestFocus();

            // Captura cualquier excepción y muestra un mensaje de error si ocurre un problema.
        } catch (Exception ex) {
            Toast.makeText(this, "Error: no se pudieron guardar los datos.", Toast.LENGTH_LONG).show();
        }
    }

    // Método para volver al menú principal
    public void volverMenuPrincipal(View view) {
        finish();
    }
}
