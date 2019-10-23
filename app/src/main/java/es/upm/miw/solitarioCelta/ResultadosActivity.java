package es.upm.miw.solitarioCelta;

import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.upm.miw.solitarioCelta.models.RepositorioResultados;
import es.upm.miw.solitarioCelta.models.Resultado;
import es.upm.miw.solitarioCelta.views.ResultadoAdapter;

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
        Collections.sort(listaResultados, new Comparator<Resultado>() {
            @Override
            public int compare(Resultado o1, Resultado o2) {
                return o1.getPiezas().compareTo(o2.getPiezas());
            }
        });


        ListView lvListaResultados = findViewById(R.id.lvResultados);
        final ResultadoAdapter resultadoAdapter = new ResultadoAdapter(
                getApplicationContext(),
                R.layout.item_lista,
                listaResultados
        );
        lvListaResultados.setAdapter(resultadoAdapter);

    }
}
