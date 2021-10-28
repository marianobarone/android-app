package com.example.list_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import com.example.list_app.entities.Categoria
import com.example.list_app.holders.CategoriaHolder
import androidx.navigation.findNavController
import com.example.list_app.ui.home.HomeFragmentDirections
import androidx.core.os.bundleOf


class CategoriaAdapter(
    private var categoriasList: MutableList<Categoria>,
    private var view: View

) : RecyclerView.Adapter<CategoriaHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.categoria_item,parent,false)
        return (CategoriaHolder(view))
    }

    override fun getItemCount(): Int {

        return categoriasList.size
    }

    fun setData(newData: ArrayList<Categoria>) {
        this.categoriasList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CategoriaHolder, i: Int) {

        holder.setName(categoriasList[i].nombre)

        holder.cardCategoria.setOnClickListener {
            System.out.println(categoriasList[i].nombre)

            val action = HomeFragmentDirections.actionNavigationHomeToListaProductos()

            val bundle = bundleOf( "categoria" to categoriasList[i].nombre)
            view.findNavController().navigate(R.id.action_navigation_home_to_listaProductos, bundle)
        }

    }
}