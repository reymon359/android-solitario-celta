package es.upm.miw.solitarioCelta;

import android.os.Bundle;


import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        Button button = findViewById(R.id.btEliminarResultados);
        // Crear repositorio
        repositorioResultados = new RepositorioResultados(getApplicationContext());

        getResultados();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (repositorioResultados.count() > 0) {
                    new EliminarResultadosDialogFragment().show(getFragmentManager(), "ELIMINAR DIALOG");
                } else {
                    Snackbar.make(findViewById(android.R.id.content),
                            getString(R.string.txtNoResultadosParaEliminar),
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    public void getResultados() {
        listaResultados = repositorioResultados.readAll();

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

    public void eliminarResultados() {
        repositorioResultados.deleteAll();
        getResultados();
    }
}
