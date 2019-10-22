package es.upm.miw.SolitarioCelta;

import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import es.upm.miw.SolitarioCelta.models.RepositorioResultados;
import es.upm.miw.SolitarioCelta.models.Resultado;
import es.upm.miw.SolitarioCelta.views.ResultadoAdapter;

public class ResultadosActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MiW";

    RepositorioResultados repositorioResultados;
    List<Resultado> listaResultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        // Crear repositorio
        repositorioResultados = new RepositorioResultados(getApplicationContext());
        Log.i(LOG_TAG, "Número resultados = " + repositorioResultados.count());

        // Obtener resultados
        listaResultados = repositorioResultados.readAll();
        Log.i("ramon 2", listaResultados.toString());


        ListView lvListaResultados = findViewById(R.id.lvResultados);
        final ResultadoAdapter resultadoAdapter = new ResultadoAdapter(
                getApplicationContext(),
                R.layout.item_lista,
                listaResultados
        );
        lvListaResultados.setAdapter(resultadoAdapter);

    }
}
