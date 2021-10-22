package com.example.list_app.entities

import android.os.Parcel
import android.os.Parcelable

class Producto(nombre: String, categoria: String, imgProducto: String, cantidadStock: Int, esFrecuente: Boolean) :
    Parcelable {
    var nombre: String

    var categoria: String

    var imgProducto: String

    var cantidadStock: Int

    var esFrecuente: Boolean

    constructor() : this("", "","",0, false)


    init {
        this.nombre = nombre!!
        this.categoria = categoria!!
        this.imgProducto = imgProducto!!
        this.cantidadStock = cantidadStock!!
        this.esFrecuente = esFrecuente!!
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(nombre)

    }

    override fun toString(): String {
        return "Producto(nombre='$nombre')"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Categoria> = object : Parcelable.Creator<Categoria> {
            override fun createFromParcel(source: Parcel): Categoria = Categoria(source)
            override fun newArray(size: Int): Array<Categoria?> = arrayOfNulls(size)
        }
    }
}