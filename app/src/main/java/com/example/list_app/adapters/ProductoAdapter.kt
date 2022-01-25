package com.example.list_app.adapters

import com.example.list_app.holders.ProductoHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import com.example.list_app.entities.Producto

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

        holder.setAtributosProducto(productosList[i].nombreProducto, productosList[i].categoria, productosList[i].foto, productosList[i].cantidad, productosList[i].isEsFrecuente)

        holder.disminuirStock.setOnClickListener {
            System.out.println(productosList[i].nombreProducto)

            if ((productosList[i].cantidad - 1 ) >= 0) {
                holder.setCantidadStock(--productosList[i].cantidad, productosList[i].id, -1, productosList[i].isEsFrecuente)
                if (productosList[i].cantidad == 0) {
                    productosList.removeAt(i)
                    this.notifyDataSetChanged()
                }
            }

            //System.out.println(productosList[i].cantidad)
        }

        holder.aumentarStock.setOnClickListener {
            System.out.println(productosList[i].nombreProducto)
            holder.setCantidadStock(++productosList[i].cantidad, productosList[i].id, 1, productosList[i].isEsFrecuente)
            System.out.println(productosList[i].cantidad)
        }

        holder.iconEsFrecuente.setOnClickListener {
            System.out.println(productosList[i].isEsFrecuente)

            holder.setEsProductoFrecuente(!productosList[i].isEsFrecuente, productosList[i].id)

            productosList[i].isEsFrecuente = !productosList[i].isEsFrecuente

            System.out.println(productosList[i].isEsFrecuente)
        }
    }
}