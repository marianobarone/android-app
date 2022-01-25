package com.example.list_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.list_app.R
import com.example.list_app.entities.Categoria
import androidx.navigation.findNavController
import com.example.list_app.ui.home.HomeFragmentDirections
import androidx.core.os.bundleOf
import com.example.list_app.holders.CategoriaAddProductHolder


class CategoriaAddProductAdapter(
    private var categoriasList: MutableList<Categoria>,
    private var view: View

) : RecyclerView.Adapter<CategoriaAddProductHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaAddProductHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.categoria_item,parent,false)
        return (CategoriaAddProductHolder(view))
    }

    override fun getItemCount(): Int {

        return categoriasList.size
    }

    fun setData(newData: ArrayList<Categoria>) {
        this.categoriasList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CategoriaAddProductHolder, i: Int) {

        holder.setName(categoriasList[i].nombre)

        holder.cardCategoria.setOnClickListener {
            System.out.println(categoriasList[i].nombre)

            val action = HomeFragmentDirections.actionNavigationHomeToListaProductos()

            val bundle = bundleOf( "categoria" to categoriasList[i].nombre)
            view.findNavController().navigate(R.id.action_addProductManuallyFragment_to_productsToAddListFragment, bundle)
        }

    }
}