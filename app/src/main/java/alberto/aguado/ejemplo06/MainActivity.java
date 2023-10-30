package alberto.aguado.ejemplo06;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import alberto.aguado.ejemplo06.adapters.ToDoAdapter;
import alberto.aguado.ejemplo06.databinding.ActivityMainBinding;
import alberto.aguado.ejemplo06.modelos.ToDo;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private ArrayList<ToDo> todoList;
    private ToDoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        todoList= new ArrayList<>();
        //crearTareas();
        adapter= new ToDoAdapter(todoList,R.layout.todo_view_model,MainActivity.this);
        binding.contentMain.contenedor.setAdapter(adapter);
        layoutManager= new LinearLayoutManager(MainActivity.this);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            crearToDo().show();
            }
        });
    }

    private void crearTareas() {
        for (int i = 0; i < 1000000; i++) {
            todoList.add(new ToDo("titulo"+i,"contenido"+i));
        }
    }

    private AlertDialog crearToDo(){
        AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Crear Tarea");
        builder.setCancelable(false);
        View todoAlert = LayoutInflater.from(this).inflate(R.layout.todo_model_alert,null);
        EditText txtTitulo= todoAlert.findViewById(R.id.txtTituloToDoModelAlert);
        EditText txtContenido= todoAlert.findViewById(R.id.txtContenidoToDoModelAlert);
        builder.setView(todoAlert);


        builder.setNegativeButton("cancelar",null);
        builder.setPositiveButton("crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (txtTitulo.getText().toString().isEmpty()|| txtContenido.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Faltan Datos", Toast.LENGTH_SHORT).show();
                }else{
                    todoList.add(new ToDo(txtTitulo.getText().toString(),txtContenido.getText().toString()));
                    adapter.notifyDataSetChanged();
                }
            }
        });
        return builder.create();
    }
}