package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

public class AlertDialogFragment extends DialogFragment {

    @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final MainActivity main = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder
                .setTitle(R.string.txtDialogoFinalTitulo)
                .setMessage(R.string.txtDialogoFinalPregunta)
                .setPositiveButton(
                        getString(R.string.txtDialogoFinalAfirmativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                main.miJuego.reiniciar();
                                main.reiniciarCronometro();
                                main.mostrarTablero();
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtDialogoFinalNegativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                main.finish();
                            }
                        }
                );

		return builder.create();
	}
}
