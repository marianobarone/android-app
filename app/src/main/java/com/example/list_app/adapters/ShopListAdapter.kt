package com.example.list_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import com.example.list_app.entities.Producto
import com.example.list_app.holders.ShopListHolder

class ShopListAdapter(
    private var shopList: MutableList<Producto>

) : RecyclerView.Adapter<ShopListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.shop_list_item,parent,false)
        return (ShopListHolder(view))
    }

    override fun getItemCount(): Int {

        return shopList.size
    }

    fun setData(newData: ArrayList<Producto>) {
        this.shopList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ShopListHolder, i: Int) {

        holder.setAtributosShopList(shopList[i].nombre, shopList[i].cantidadStock)

        holder.disminuirCantidadShopList.setOnClickListener {
            //AUMENTAR LA CANTIDAD DE PRODUCTO EN LISTA
            System.out.println(shopList[i].nombre)

            if ((shopList[i].cantidadStock - 1 ) >= 0) {
                holder.setCantidadAComprar(--shopList[i].cantidadStock)
            }

            System.out.println(shopList[i].cantidadStock)
        }

        holder.aumentarCantidadShopList.setOnClickListener {
            //DISMINUIR LA CANTIDAD DE PRODUCTO EN LISTA
            System.out.println(shopList[i].nombre)
            holder.setCantidadAComprar(++shopList[i].cantidadStock)
            System.out.println(shopList[i].cantidadStock)
        }
    }
}