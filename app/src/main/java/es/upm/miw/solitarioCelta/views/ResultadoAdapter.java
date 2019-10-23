package es.upm.miw.solitarioCelta.views;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import es.upm.miw.solitarioCelta.R;
import es.upm.miw.solitarioCelta.databinding.ItemListaBinding;
import es.upm.miw.solitarioCelta.models.Resultado;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class ResultadoAdapter extends ArrayAdapter {
    private Context context;
    private int idLayout;
    private List<Resultado> listaResultados;
    private static LayoutInflater layoutInflater = null;

    /**
     * Constructor
     *
     * @param context    The current context.
     * @param resource   The resource ID for a layout file containing a TextView to use when
     *                   instantiating views.
     * @param resultados The resultados to represent in the ListView.
     */
    public ResultadoAdapter(@NonNull Context context, int resource, @NonNull List resultados) {
        super(context, resource, resultados);
        this.context = context;
        this.idLayout = resource;
        this.listaResultados = resultados;
        setNotifyOnChange(true);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        // Clase generada automáticamente: nombre del layout en camel case + "Binding"
        ItemListaBinding itemListaBinding = DataBindingUtil.getBinding(convertView);
        if (itemListaBinding == null) {
            itemListaBinding = DataBindingUtil.inflate(layoutInflater, idLayout, parent, false);
        }

        // Enlaza la entidad (Bebida) con la vista
        itemListaBinding.setResultado(listaResultados.get(position));

//        TextView tvNombre = vista.findViewById(R.id.tvResultadoNombre);
//        TextView tvFecha = vista.findViewById(R.id.tvResultadoFecha);
//        TextView tvPiezas = vista.findViewById(R.id.tvResultadoPiezas);
//
//
//        // Asignar contenido
//        tvNombre.setText(resultado.getNombre());
//        tvFecha.setText(resultado.getFecha().toString());
//        tvPiezas.setText(resultado.getPiezas());

        return itemListaBinding.getRoot();
    }
}