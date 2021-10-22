package com.example.list_app.adapters

import com.example.list_app.entities.Producto
import com.example.list_app.holders.ProductoHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import com.example.list_app.ui.home.HomeFragmentDirections

class ProductoAdapter(
    private var productosList: MutableList<Producto>

) : RecyclerView.Adapter<ProductoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.producto_item,parent,false)
        return (ProductoHolder(view))
    }

    override fun getItemCount(): Int {

        return productosList.size
    }

    fun setData(newData: ArrayList<Producto>) {
        this.productosList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductoHolder, i: Int) {

        holder.setAtributosProducto(productosList[i].nombre, productosList[i].categoria, productosList[i].imgProducto, productosList[i].cantidadStock, productosList[i].esFrecuente)

        holder.disminuirStock.setOnClickListener {
            System.out.println(productosList[i].nombre)

            if ((productosList[i].cantidadStock - 1 ) >= 0) {
                holder.setCantidadStock(--productosList[i].cantidadStock)
            }

            System.out.println(productosList[i].cantidadStock)
        }

        holder.aumentarStock.setOnClickListener {
            System.out.println(productosList[i].nombre)
            holder.setCantidadStock(++productosList[i].cantidadStock)
            System.out.println(productosList[i].cantidadStock)
        }

        holder.iconEsFrecuente.setOnClickListener {
            System.out.println(productosList[i].esFrecuente)

            holder.setEsProductoFrecuente(!productosList[i].esFrecuente)

            productosList[i].esFrecuente = !productosList[i].esFrecuente

            System.out.println(productosList[i].esFrecuente)

        }

    }
}