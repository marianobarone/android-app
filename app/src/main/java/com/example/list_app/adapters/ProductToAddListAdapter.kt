package com.example.list_app.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import com.example.list_app.entities.Producto
import com.example.list_app.holders.ProductToAddListHolder
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class ProductToAddListAdapter(
    private var productosList: MutableList<Producto>

) : RecyclerView.Adapter<ProductToAddListHolder>() {

    lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductToAddListHolder {
        view =  LayoutInflater.from(parent.context).inflate(R.layout.add_list_product_item,parent,false)
        return (ProductToAddListHolder(view))
    }

    override fun getItemCount(): Int {

        return productosList.size
    }

    fun setData(newData: ArrayList<Producto>) {
        this.productosList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductToAddListHolder, i: Int) {

        holder.setAtributosProducto(productosList[i].nombreProducto, productosList[i].categoria, productosList[i].foto, 0, productosList[i].isEsFrecuente)

        holder.disminuirCantidad.setOnClickListener {
            if ((productosList[i].cantidad - 1 ) >= 0) {
                holder.setCantidadStock(--productosList[i].cantidad)
            }

            System.out.println(productosList[i].cantidad)
        }

        holder.aumentarCantidad.setOnClickListener {
            System.out.println(productosList[i].nombreProducto)
            holder.setCantidadStock(++productosList[i].cantidad)
            System.out.println(productosList[i].cantidad)
        }

        holder.addProductBtn.setOnClickListener(){
            if (this.productosList[i].cantidad > 0 ){
                holder.agregarProductoAStock(productosList[i].id, productosList[i].nombreProducto, productosList[i].categoria, productosList[i].foto)
                this.productosList[i].cantidad = 0;
            }
            else {
                val mySnackbar = Snackbar.make(
                    view,
                    "Error! Se tiene que elegir la cantidad a agregar",
                    BaseTransientBottomBar.LENGTH_LONG
                )

                val layoutParams = LinearLayout.LayoutParams(mySnackbar.view.layoutParams)
                layoutParams.gravity = Gravity.TOP
                mySnackbar.view.setPadding(0, 10, 0, 0)
                mySnackbar.view.layoutParams = layoutParams
                mySnackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
                mySnackbar.show()
            }
        }

        holder.addProductShopListBtn.setOnClickListener(){
            if (this.productosList[i].cantidad > 0 ){
                holder.agregarProductoAShopList(productosList[i].id, productosList[i].nombreProducto, productosList[i].categoria, productosList[i].foto)
                this.productosList[i].cantidad = 0;
            }
            else {
                val mySnackbar = Snackbar.make(
                    view,
                    "Error! Se tiene que elegir la cantidad a agregar",
                    BaseTransientBottomBar.LENGTH_LONG
                )

                val layoutParams = LinearLayout.LayoutParams(mySnackbar.view.layoutParams)
                layoutParams.gravity = Gravity.TOP
                mySnackbar.view.setPadding(0, 10, 0, 0)
                mySnackbar.view.layoutParams = layoutParams
                mySnackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
                mySnackbar.show()
            }

        }
    }
}