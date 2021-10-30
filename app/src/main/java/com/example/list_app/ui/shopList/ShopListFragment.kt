package com.example.list_app.ui.shopList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.list_app.MySingleton
import com.example.list_app.R
import com.example.list_app.adapters.ShopListAdapter
import com.example.list_app.databinding.FragmentShopListBinding
import com.example.list_app.entities.*
import com.example.list_app.ui.dashboard.ShopListViewModel
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class ShopListFragment : Fragment() {
    val API_URL = "https://listapp2021.herokuapp.com"

    private lateinit var dashboardViewModel: ShopListViewModel
    private var _binding: FragmentShopListBinding? = null
    lateinit var v: View
    var shopList: MutableList<Producto> = ArrayList<Producto>()
    lateinit var recyclerShopList: RecyclerView
    private lateinit var shopListAdapter: ShopListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        showShopList()
    }

    fun showShopList(){
        val prefs = requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
        val arrayShopList = JSONArray(prefs.getString("listaPendientes", ""))

        for (i in 0 until arrayShopList.length()) {
            val item = arrayShopList.getJSONObject(i)

            shopList.add(
                Producto(
                    item.getString("nombreGenerico").toString(),
                    item.getString("categoria").toString(),
                    item.getInt("cantidad")
                )
            )
        }

        shopListAdapter = ShopListAdapter(shopList);
        recyclerShopList.adapter = shopListAdapter

        recyclerShopList.setHasFixedSize(true)
        recyclerShopList.layoutManager = GridLayoutManager(context, 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //return inflater.inflate(R.layout.fragment_shop_list, container, false)

        shopList = ArrayList<Producto>()

        v = inflater.inflate(R.layout.fragment_shop_list, container, false)
        recyclerShopList = v.findViewById(R.id.recycler_shop_list)

        shopListAdapter = ShopListAdapter(shopList);

        recyclerShopList.adapter = shopListAdapter

        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}