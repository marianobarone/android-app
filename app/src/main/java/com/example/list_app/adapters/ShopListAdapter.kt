package com.example.list_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import com.example.list_app.entities.Producto
import com.example.list_app.holders.ShopListHolder

class ShopListAdapter(
    private var shopList: MutableList<Producto>,
    private var agregarTodoStock: Button

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

        holder.setAtributosShopList(shopList[i].nombreProducto, shopList[i].cantidad)

        holder.disminuirCantidadShopList.setOnClickListener {
            //AUMENTAR LA CANTIDAD DE PRODUCTO EN LISTA
            System.out.println(shopList[i].nombreProducto)

            if ((shopList[i].cantidad - 1 ) >= 0) {
                holder.setCantidadAComprar(--shopList[i].cantidad, shopList[i].id, -1)
            }
            if (shopList[i].cantidad == 0) {
                shopList.removeAt(i)
                this.notifyDataSetChanged()
            }

        }

        holder.aumentarCantidadShopList.setOnClickListener {
            //DISMINUIR LA CANTIDAD DE PRODUCTO EN LISTA
            System.out.println(shopList[i].nombreProducto)
            holder.setCantidadAComprar(++shopList[i].cantidad, shopList[i].id, 1)
            System.out.println(shopList[i].cantidad)
        }

        holder.agregarItemStock.setOnClickListener {
            holder.agregarItemStock(shopList[i].cantidad, shopList[i].id)
            shopList.removeAt(i)
            this.notifyDataSetChanged();
        }

        agregarTodoStock.setOnClickListener{
            holder.agregarTodoStock(shopList)
            shopList.clear()
            this.notifyDataSetChanged();
        }

    }
}