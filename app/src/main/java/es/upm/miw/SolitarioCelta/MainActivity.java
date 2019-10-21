package es.upm.miw.SolitarioCelta;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;

import es.upm.miw.SolitarioCelta.models.RepositorioResultados;
import es.upm.miw.SolitarioCelta.models.Resultado;

public class MainActivity extends AppCompatActivity {

    SCeltaViewModel miJuego;
    public final String LOG_KEY = "MiW";
    String partidaGuardada;

    TextView tvFichasRestantes;
    Chronometer crono;

    List<Resultado> listaResultados;
    RepositorioResultados repositorioResultados;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFichasRestantes = findViewById(R.id.tvFichasRestantes);

        // Crear Repositorio
        repositorioResultados = new RepositorioResultados(getApplicationContext());
        Log.i("hola", "Numero resultados=" + repositorioResultados.count());

        // Obtener resultados
        listaResultados = repositorioResultados.readAll();
        Log.i("listaResultados", listaResultados.toString());

        crono = findViewById(R.id.chronometer);
        crono.setBase(SystemClock.uptimeMillis());
        crono.start();

        miJuego = ViewModelProviders.of(this).get(SCeltaViewModel.class);
        mostrarTablero();
    }

    public void reiniciarCronometro() {
        crono.setBase(SystemClock.uptimeMillis());
        crono.start();

    }

    /**
     * Se ejecuta al pulsar una ficha
     * Las coordenadas (i, j) se obtienen a partir del nombre del recurso, ya que el botón
     * tiene un identificador en formato pXY, donde X es la fila e Y la columna
     *
     * @param v Vista de la ficha pulsada
     */
    public void fichaPulsada(@NotNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';   // fila
        int j = resourceName.charAt(2) - '0';   // columna

        Log.i(LOG_KEY, "fichaPulsada(" + i + ", " + j + ") - " + resourceName);
        miJuego.jugar(i, j);
        Log.i(LOG_KEY, "#fichas=" + miJuego.numeroFichas());

        mostrarTablero();
        if (miJuego.juegoTerminado()) {
            // TODO guardar puntuación
            new AlertDialogFragment().show(getFragmentManager(), "ALERT_DIALOG");
        }
    }

    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        RadioButton button;
        String strRId;
        String prefijoIdentificador = getPackageName() + ":id/p"; // formato: package:type/entry
        int idBoton;

        for (int i = 0; i < JuegoCelta.TAMANIO; i++)
            for (int j = 0; j < JuegoCelta.TAMANIO; j++) {
                strRId = prefijoIdentificador + i + j;
                idBoton = getResources().getIdentifier(strRId, null, null);
                if (idBoton != 0) { // existe el recurso identificador del botón
                    button = findViewById(idBoton);
                    button.setChecked(miJuego.obtenerFicha(i, j) == JuegoCelta.FICHA);
                }
            }

        tvFichasRestantes.setText(getString(R.string.txtFichasRestantes) + miJuego.numeroFichas());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public void guardarPartida() {
        FileOutputStream fos;
        File file = new File(getApplicationContext().getFilesDir(), "PartidaGuardada");
        if (file.exists()) {
            file.delete();
        }
        try {  // Añadir al fichero
            fos = openFileOutput("PartidaGuardada", Context.MODE_APPEND); // Memoria interna

            fos.write(miJuego.serializaTablero().getBytes());
            fos.close();
            crearSnackbar(getString(R.string.txtPartidaGuardada));
            Log.i("Guardando partida", miJuego.serializaTablero());
        } catch (Exception e) {
            Log.e("Error Guardar partida", "FILE I/O ERROR: " + e.getMessage());
            e.printStackTrace();
            crearSnackbar(R.string.txtErrorPartidaGuardada + e.getMessage());
        }

    }

    //    Porque (partidaActual == partidaGuardada) no me funciona
    public Boolean stringsIguales(String string1, String string2) {
        Boolean iguales = true;
        for (int i = 0; i < string1.length(); i++) {
            iguales = string1.charAt(i) == string2.charAt(i);
            if (!iguales) {
                break;
            }
        }
        return iguales;
    }


    public void recuperarPartida() {
        if (new File(getApplicationContext().getFilesDir(), "PartidaGuardada").exists()) {
            String partidaActual = miJuego.serializaTablero();

            try {
                BufferedReader fin = new BufferedReader(
                        new InputStreamReader(openFileInput("PartidaGuardada"))); // Memoria interna
                partidaGuardada = fin.readLine();
                fin.close();

                if (stringsIguales(partidaActual, partidaGuardada)) {
                    miJuego.deserializaTablero(partidaGuardada);
                    reiniciarCronometro();
                    crearSnackbar(getString(R.string.txtPartidaRecuperada));
                } else {
                    new RecuperarPartidaDialogFragment().show(getFragmentManager(), "REINICIO_DIALOG");
                }

            } catch (Exception e) {
                Log.e("Error Recuperar partida", "FILE I/O ERROR: " + e.getMessage());
                e.printStackTrace();
                crearSnackbar(R.string.txtErrorRecuperarPartida + e.getMessage());
            }

        } else {
            crearSnackbar(getString(R.string.txtNoFichero));
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcAjustes:
                startActivity(new Intent(this, SCeltaPrefs.class));
                return true;
            case R.id.opcAcercaDe:
                startActivity(new Intent(this, AcercaDe.class));
                return true;
            case R.id.opcReiniciarPartida:
                new ReinicioDialogFragment().show(getFragmentManager(), "REINICIO_DIALOG");
                return true;
            case R.id.opcGuardarPartida:
                guardarPartida();
                return true;
            case R.id.opcRecuperarPartida:
                recuperarPartida();
                return true;

            // TODO!!! resto opciones

            default:
                crearSnackbar(getString(R.string.txtSinImplementar));
        }
        return true;
    }

    public void crearSnackbar(String text) {
        Snackbar.make(findViewById(android.R.id.content),
                text,
                Snackbar.LENGTH_LONG).show();
    }

}
