package es.upm.miw.solitarioCelta.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Resultado implements Parcelable {
    private int id;
    private String nombre;
    private String fecha;
    private Integer piezas;

    public Resultado(int id, String nombre, String fecha, Integer piezas) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.piezas = piezas;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getPiezas() {
        return piezas;
    }

    public void setPiezas(Integer piezas) {
        this.piezas = piezas;
    }


    protected Resultado(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        fecha = in.readString();
        piezas = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(fecha);
        if (piezas == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(piezas);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Resultado> CREATOR = new Parcelable.Creator<Resultado>() {
        @Override
        public Resultado createFromParcel(Parcel in) {
            return new Resultado(in);
        }

        @Override
        public Resultado[] newArray(int size) {
            return new Resultado[size];
        }
    };


    @Override
    public String toString() {
        return "Resultado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", piezas=" + piezas +
                '}';
    }
}