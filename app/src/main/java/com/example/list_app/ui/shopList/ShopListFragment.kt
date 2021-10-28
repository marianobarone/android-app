package com.example.list_app.ui.shopList

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

        getUserData()
    }


    fun getUserData() {
        val userRequest: JsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, API_URL + "/users", null,
            Response.Listener { response ->
                val res = "Response: %s".format(response.toString())

                System.out.println(res)

                val user = Usuario()
                val grupoSeleccionado = GrupoSeleccionado()
                val grupoAPI = response.getJSONObject("selectedGroup")
                val listaUsuario = grupoAPI.getJSONArray("shopList")

//                grupoSeleccionado.setListaPendientes(grupoAPI.getJSONArray("shopList"))

                for (i in 0 until listaUsuario.length()) {
                    val item = listaUsuario.getJSONObject(i)

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

            }, Response.ErrorListener { error ->
                // handle error
                System.out.println("Response: %s".format(error.toString()))
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjhmYmRmMjQxZTdjM2E2NTEzNTYwNmRkYzFmZWQyYzU1MjI2MzBhODciLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiTW96byBEaWdpdGFsIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FBVFhBSndNWWp6Z3Uxdlk5V2JMRmg0TEFLT0lkYUtxVnFKUWJDd0RpVElsPXM5Ni1jIiwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL2xpc3RhcHBkYiIsImF1ZCI6Imxpc3RhcHBkYiIsImF1dGhfdGltZSI6MTYzNTI3MzIyNCwidXNlcl9pZCI6InpEVkUyZHVFWGJNTzFmMFNhNjZrZGFEQlQzSjMiLCJzdWIiOiJ6RFZFMmR1RVhiTU8xZjBTYTY2a2RhREJUM0ozIiwiaWF0IjoxNjM1MjczMjI0LCJleHAiOjE2MzUyNzY4MjQsImVtYWlsIjoibW96by5kaWdpdGFsLmFwcEBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJnb29nbGUuY29tIjpbIjEwNjg4Mjc1Nzg5NDc0MDI1MDIxNiJdLCJlbWFpbCI6WyJtb3pvLmRpZ2l0YWwuYXBwQGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6Imdvb2dsZS5jb20ifX0.k0RobgqNrjbvlmyjmR5OL9IND_SqfJOBq4KHwSlNIvVhp_pPBCfUF2Q8xAWrafumaLQiAnt1T98nIjAO2Q5FdYyt8nzdWcOJ2r4-HUC4F_oW0ir2RjToJSAI2xODiqbkh7pBQ0DRBr4oVDImCH10HqLdGnNtX3k7hC9Ou9B8XbyxFoHnWeFbydh37Bs_36KpSEjoCLTxJmQxwFTEJmiwZ0N3kiIb9bVrzAVdpgjg48ZRiXqvx5pqUVp0S3YP9I5H6dlY89DYHpUBTPLN_rYNqzhjyn5uhZld0662yxG5OLP1gsKWLLZ0ujqTSF1SMYgucn-1Ei4RZ_dJvHP15fHUDA"
                //headers["ANOTHER_CUSTOM_HEADER"] = "Google"
                return headers
            }
        }

        // Add the request to the RequestQueue.
        MySingleton.getInstance(v.context).addToRequestQueue(userRequest);

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