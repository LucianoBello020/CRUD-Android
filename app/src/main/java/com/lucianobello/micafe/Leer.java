package com.lucianobello.micafe;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Leer extends AppCompatActivity {

    // Declara un ListView para mostrar datos, un ArrayList para almacenar los datos de la base de datos y un ArrayAdapter para gestionar la lista.
    private ListView lst1;
    private ArrayList<String> arreglo = new ArrayList<>();
    private ArrayAdapter arrayAdapter;

    // Método onCreate que se ejecuta al iniciar la actividad. Establece el diseño XML de la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        try {
            // Abre o crea la base de datos llamada "BD_EJEMPLO" en modo privado.
            SQLiteDatabase db = openOrCreateDatabase("BD_LUCIANO", Context.MODE_PRIVATE, null);

            // Asocia la variable `lst1` con el ListView definido en el archivo XML.
            lst1 = findViewById(R.id.lst1);

            // Ejecuta una consulta SQL para obtener todos los registros de la tabla "persona" y guarda el resultado en un cursor.
            final Cursor c = db.rawQuery("select * from persona", null);

            // Obtiene los índices de las columnas "id", "nombre", "apellido" y "edad" de la tabla.
            int id = c.getColumnIndex("id");
            int nombre = c.getColumnIndex("nombre");
            int apellido = c.getColumnIndex("apellido");
            int edad = c.getColumnIndex("edad");

            // Limpia el ArrayList `arreglo` para asegurarse de que no contenga datos antiguos.
            arreglo.clear();

            // Inicializa el ArrayAdapter con el contexto actual y un diseño simple para mostrar los elementos del ArrayList.
            arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arreglo);

            // Asocia el ArrayAdapter con el ListView para mostrar los datos.
            lst1.setAdapter(arrayAdapter);

            // Crea un ArrayList para almacenar objetos de la clase `Persona`.
            final ArrayList<Persona> lista = new ArrayList<>();

            // Verifica si el cursor tiene algún registro. Si es así, comienza a iterar sobre ellos.
            if (c.moveToFirst()) {
                do {
                    // Crea un objeto `Persona` y establece sus atributos con los valores obtenidos del cursor.
                    Persona persona = new Persona();
                    persona.id = c.getString(id);
                    persona.nombre = c.getString(nombre);
                    persona.apellido = c.getString(apellido);
                    persona.edad = c.getString(edad);

                    // Agrega la persona creada a la lista de personas.
                    lista.add(persona);

                    // Agrega una representación en texto de la persona al ArrayList `arreglo` para mostrarla en el ListView.
                    arreglo.add(c.getString(id) + " \t " + c.getString(nombre) + " \t " + c.getString(apellido) + " \t " + c.getString(edad));

                    // Repite el proceso mientras el cursor tenga más registros.
                } while (c.moveToNext());

                // Notifica al ArrayAdapter que los datos han cambiado para actualizar la lista.
                arrayAdapter.notifyDataSetChanged();

                // Refresca la vista del ListView para asegurarse de que los datos actualizados se muestren.
                lst1.invalidateViews();
            }

            lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Obtiene el objeto `Persona` en la posición seleccionada de la lista.
                    Persona persona = lista.get(position);

                    // Crea un Intent para iniciar la actividad `Editar`, pasando los datos de la persona seleccionada.
                    Intent i = new Intent(getApplicationContext(), Editar.class);
                    i.putExtra("id", persona.id);
                    i.putExtra("nombre", persona.nombre);
                    i.putExtra("apellido", persona.apellido);
                    i.putExtra("edad", persona.edad);

                    // Inicia la actividad `Editar`.
                    startActivity(i);
                }
            });
            // Establece un listener en los elementos del ListView. Cuando se hace clic en un elemento, se abre la actividad `Editar` con los datos de esa persona.

            // Captura cualquier excepción y muestra un mensaje de error si ocurre un problema.
        } catch (Exception e) {
            Toast.makeText(this, "Ha ocurrido un error, inténtalo nuevamente.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para regresar a la actividad anterior
    public void volverAtras(View view) {
        finish();
    }

    // Método para ir al Menú Principal
    public void irMenuPrincipal(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
