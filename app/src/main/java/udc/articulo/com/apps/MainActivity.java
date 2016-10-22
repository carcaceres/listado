package udc.articulo.com.apps;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText codView;
    EditText desView;
    EditText precView;
    Vibrator v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        codView= (EditText) findViewById(R.id.codView);
        desView= (EditText) findViewById(R.id.desView);
        precView= (EditText) findViewById(R.id.precView);
        codView.requestFocus();

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ver) {
            Intent i = new Intent(this, ListaActivity.class);
            startActivity(i);
        }
        else if(id==R.id.salir){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    public void insertarArticulo(View view) {
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        AdminSqlOpenHelper admin = new AdminSqlOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = codView.getText().toString();
        String descri = desView.getText().toString();
        String prec = precView.getText().toString();

        if(cod.equals("") || descri.equals("") || prec.equals("")){
            Toast.makeText(this,"Campo Vacio!!",Toast.LENGTH_SHORT).show();
          v.vibrate(3000);
        }
        else {
             System.out.println("entro al else");

            System.out.println("codigo " + cod);
            System.out.println("descripcion " + descri);
            System.out.println("prec " + prec);


            /////////////////////////////////////////////////////////////////////////////////////////////
            Cursor fila = bd.rawQuery(
                    "select codigo from articulos where codigo='" + cod + "'", null);
            if (fila.moveToFirst()) {
                Toast.makeText(this, "codigo " + cod + " ya Existe!!",
                        Toast.LENGTH_SHORT).show();

                v.vibrate(3000);
            } else {


                ContentValues registro = new ContentValues();
                registro.put("codigo", cod);
                registro.put("descripcion", descri);
                registro.put("precio", prec);

                bd.insert("articulos", null, registro);
                //bd.execSQL("delete from articulos");
                bd.close();
                codView.setText("");
                desView.setText("");
                precView.setText("");
                codView.requestFocus();
                Toast.makeText(this, "Se guardaron los datos del artículo",
                        Toast.LENGTH_SHORT).show();

            }
        }
        ////////////////////////////////////////////////////////////////////////////////7

    }


        public void modificacion(View view ) {
            AdminSqlOpenHelper admin = new AdminSqlOpenHelper(this,
                    "administracion", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            String cod = codView.getText().toString();
            String descri = desView.getText().toString();
            String prec = precView.getText().toString();
            String pre = precView.getText().toString();
            ContentValues registro = new ContentValues();
            registro.put("codigo", cod);
            registro.put("descripcion", descri);
            registro.put("precio", pre);
            int cant = bd.update("articulos", registro, "codigo=" + cod, null);
            bd.close();
            if (cant == 1)
                Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                        .show();
            else {
                Toast.makeText(this, "no existe un artículo con el código ingresado",
                        Toast.LENGTH_SHORT).show();
            }
        }

    public void eliminarArticulo(View v) {
        AdminSqlOpenHelper admin = new AdminSqlOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = codView.getText().toString();
        String descri = desView.getText().toString();
        String prec = precView.getText().toString();
        String pre = precView.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo", cod);
        registro.put("descripcion", descri);
        registro.put("precio", pre);
        int cant = bd.delete("articulos", "codigo=" + cod, null);
        bd.close();
        codView.setText("");
        desView.setText("");
        precView.setText("");
        if (cant == 1)
            Toast.makeText(this, "Se borró el artículo con dicho código",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe un artículo con dicho código",
                    Toast.LENGTH_SHORT).show();
    }


    public void borrarArticulos(View view){
        AdminSqlOpenHelper admin = new AdminSqlOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        bd.execSQL("delete from articulos");
        Toast.makeText(this, "Elimino Todos los Articulos",
                Toast.LENGTH_SHORT).show();
        bd.close();
    }









}
