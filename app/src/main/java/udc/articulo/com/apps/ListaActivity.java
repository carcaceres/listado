package udc.articulo.com.apps;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class ListaActivity extends AppCompatActivity {
    List<String> lista;
    ListView listaView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        AdminSqlOpenHelper admin = new AdminSqlOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select codigo,descripcion,precio from articulos", null);
        lista= new ArrayList<String>();
        listaView = (ListView) findViewById(R.id.listView1);
        if(fila.getCount()==0){

            Toast.makeText(this, "No se encontró Articulos!!",
                    Toast.LENGTH_LONG).show();
        }
        else {
        if (fila.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                System.out.println("Codigo: " + fila.getString(0));
                System.out.println("Descripcion: " + fila.getString(1));
                System.out.println("Precio " + fila.getString(2));
                lista.add("Codigo:"+fila.getString(0)+"\n"+"Descripcion: "+fila.getString(1)+"\n"+"Precio: "+fila.getString(2));


            } while (fila.moveToNext());


                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
                listaView.setAdapter(adapter);
            }

            bd.close();

        }

    }

}
