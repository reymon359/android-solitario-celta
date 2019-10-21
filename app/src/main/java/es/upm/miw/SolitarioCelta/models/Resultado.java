package es.upm.miw.SolitarioCelta.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Resultado implements Parcelable {
    private int id;
    private String nombre;
    private Date fecha;
    private Integer piezas;

    public Resultado(int id, String nombre, Date fecha, Integer piezas) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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
        long tmpFecha = in.readLong();
        fecha = tmpFecha != -1 ? new Date(tmpFecha) : null;
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
        dest.writeLong(fecha != null ? fecha.getTime() : -1L);
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
}