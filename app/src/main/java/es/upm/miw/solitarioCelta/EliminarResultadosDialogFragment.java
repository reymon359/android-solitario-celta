package es.upm.miw.solitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import es.upm.miw.solitarioCelta.models.RepositorioResultados;

public class EliminarResultadosDialogFragment extends DialogFragment {
    RepositorioResultados repositorioResultados;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ResultadosActivity resultadosActivity = (ResultadosActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(resultadosActivity);
        builder
                .setTitle(R.string.txtDialogoEliminarResultadosTitulo)
                .setMessage(R.string.txtDialogoEliminarResultadosPregunta)
                .setPositiveButton(
                        getString(R.string.txtDialogoeliminarResultadosAfirmativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resultadosActivity.eliminarResultados();
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtDialogoEliminarResultadosNegativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        }
                );

        return builder.create();
    }

}
