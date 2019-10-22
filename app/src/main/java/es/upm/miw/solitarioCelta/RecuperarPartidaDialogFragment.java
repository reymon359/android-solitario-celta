package es.upm.miw.solitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class RecuperarPartidaDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity main = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder
                .setTitle(R.string.txtDialogoRecuperarPartidaTitulo)
                .setMessage(R.string.txtDialogoRecuperarPartidaPregunta)
                .setPositiveButton(
                        getString(R.string.txtDialogoRecuperarPartidaAfirmativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                main.miJuego.deserializaTablero(main.partidaGuardada);
                                main.crearSnackbar(getString(R.string.txtPartidaRecuperada));
                                main.reiniciarCronometro();
                                main.mostrarTablero();
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtDialogoRecuperarPartidaNegativo),
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
