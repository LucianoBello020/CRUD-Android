package com.lucianobello.micafe;

// Importa las clases necesarias para trabajar con el contexto de la aplicación, bases de datos, interfaces de usuario, y mostrar mensajes.
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Importa la clase base para las actividades que usan la compatibilidad de AppCompat.
import androidx.appcompat.app.AppCompatActivity;

// Declara la clase `Editar`, que extiende `AppCompatActivity` para manejar la actividad.
public class Editar extends AppCompatActivity {

    // Declara los componentes de la interfaz de usuario (EditText para capturar texto y Button para interactuar).
    private EditText ed_nombre, ed_apellido, ed_edad, ed_id;
    private Button b_editar, b_eliminar, b_volver;

    // Método onCreate que se ejecuta al iniciar la actividad. Establece el diseño XML de la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Asocia las variables EditText con los elementos de la interfaz definidos en el archivo XML.
        ed_nombre = findViewById(R.id.et_nombre);
        ed_apellido = findViewById(R.id.et_apellido);
        ed_edad = findViewById(R.id.et_edad);
        ed_id = findViewById(R.id.id);

        // Asocia las variables Button con los botones definidos en el archivo XML.
        b_editar = findViewById(R.id.btn_editar);
        b_eliminar = findViewById(R.id.btn_eliminar);
        b_volver = findViewById(R.id.btn_volver);

        // Obtiene el Intent que inició esta actividad, el cual contiene datos pasados desde otra actividad.
        Intent i = getIntent();

        // Extrae los datos pasados en el Intent (id, nombre, apellido, edad) y los convierte a String.
        String et_id = i.getStringExtra("id").toString();
        String et_nombre = i.getStringExtra("nombre").toString();
        String et_apellido = i.getStringExtra("apellido").toString();
        String et_edad = i.getStringExtra("edad").toString();

        // Establece los valores extraídos del Intent en los campos de texto correspondientes.
        ed_id.setText(et_id);
        ed_nombre.setText(et_nombre);
        ed_apellido.setText(et_apellido);
        ed_edad.setText(et_edad);

        // Asigna un evento al botón de editar, que llama al método `editar` cuando se hace clic.
        b_editar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                editar();
            }
        });

        // Asigna un evento al botón de eliminar, que llama al método `eliminar` cuando se hace clic.
        b_eliminar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                eliminar();
            }
        });

        b_volver.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent i = new Intent(getApplicationContext(), Leer.class);
                startActivity(i);
            }
        });
        // Asigna un evento al botón de volver, que inicia la actividad `Leer` cuando se hace clic.
    }

    // Método que elimina un registro de la base de datos.
    public void eliminar() {
        try {
            String id = ed_id.getText().toString();
            // Obtiene el id del campo de texto `ed_id`.

            SQLiteDatabase db = openOrCreateDatabase("BD_LUCIANO", Context.MODE_PRIVATE, null);
            // Abre o crea la base de datos llamada "BD_LUCIANO" en modo privado.

            String sql = "delete from persona where id = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            // Prepara una sentencia SQL para eliminar un registro donde el id coincida.

            statement.bindString(1, id);
            statement.execute();
            // Vincula el id al primer parámetro de la sentencia SQL y ejecuta la eliminación.

            Toast.makeText(this, "Datos eliminados de la base de datos.", Toast.LENGTH_LONG).show();
            // Muestra un mensaje confirmando que los datos fueron eliminados.

            ed_nombre.setText("");
            ed_apellido.setText("");
            ed_edad.setText("");
            ed_nombre.requestFocus();
            // Limpia los campos de texto y establece el foco en el campo de nombre.

        } catch (Exception ex) {
            Toast.makeText(this, "Error, no se pudieron eliminar los datos.", Toast.LENGTH_LONG).show();
            // Captura cualquier excepción y muestra un mensaje de error si ocurre un problema.
        }
    }

    // Método que actualiza un registro en la base de datos.
    public void editar() {
        try {
            // Obtiene los valores de los campos de texto (nombre, apellido, edad, id).
            String nombre = ed_nombre.getText().toString();
            String apellido = ed_apellido.getText().toString();
            String edad = ed_edad.getText().toString();
            String id = ed_id.getText().toString();

            // Abre o crea la base de datos llamada "BD_LUCIANO" en modo privado.
            SQLiteDatabase db = openOrCreateDatabase("BD_LUCIANO", Context.MODE_PRIVATE, null);

            // Prepara una sentencia SQL para actualizar un registro donde el id coincida.
            String sql = "update persona set nombre = ?,apellido=?,edad=? where id= ?";
            SQLiteStatement statement = db.compileStatement(sql);

            // Vincula los valores a los parámetros de la sentencia SQL y ejecuta la actualización.
            statement.bindString(1, nombre);
            statement.bindString(2, apellido);
            statement.bindString(3, edad);
            statement.bindString(4, id);
            statement.execute();

            // Muestra un mensaje confirmando que los datos fueron actualizados.
            Toast.makeText(this, "Datos actualizados satisfactoriamente en la base de datos.", Toast.LENGTH_LONG).show();

            // Limpia los campos de texto y establece el foco en el campo de nombre.
            ed_nombre.setText("");
            ed_apellido.setText("");
            ed_edad.setText("");
            ed_nombre.requestFocus();
            
            // Captura cualquier excepción y muestra un mensaje de error si ocurre un problema.
        } catch (Exception ex) {
            Toast.makeText(this, "Error, no se pudieron actualizar los datos.", Toast.LENGTH_LONG).show();
        }

    }
}

