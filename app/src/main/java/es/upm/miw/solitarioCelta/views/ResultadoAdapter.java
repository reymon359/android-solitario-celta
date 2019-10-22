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

//        TextView tvNombre     = vista.findViewById(R.id.tvBebidaNombre);
//        TextView tvOrigen     = vista.findViewById(R.id.tvBebidaOrigen);
//        TextView tvGraduacion = vista.findViewById(R.id.tvBebidaGraduacion);
//        TextView tvAzucar     = vista.findViewById(R.id.tvBebidaAzucar);
//
//        // Asignar contenido
//        tvNombre.setText(bebida.getNombre());
//        tvOrigen.setText(bebida.getOrigen());
//        tvGraduacion.setText(String.format(Locale.getDefault(), "%4.1f", bebida.getGraduacion()));
//        tvAzucar.setText(bebida.getAzucar() ? "sí" : "no");

        return itemListaBinding.getRoot();
    }
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LinearLayout vista;
//        Resultado resultado = listaResultados.get(position);
//
//        if (null == convertView) {
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//            vista = (LinearLayout) layoutInflater.inflate(idLayout, parent, false);
//        } else {
//            vista = (LinearLayout) convertView;
//        }
//
//        TextView tvNombre = vista.findViewById(R.id.tvResultadoNombre);
//        TextView tvFecha = vista.findViewById(R.id.tvResultadoFecha);
//        TextView tvPiezas = vista.findViewById(R.id.tvResultadoPiezas);
//
//
//        // Asignar contenido
//        tvNombre.setText(resultado.getNombre());
//        tvFecha.setText(resultado.getFecha().toString());
//        tvPiezas.setText(resultado.getPiezas());
//
//        return vista;
//    }
}
