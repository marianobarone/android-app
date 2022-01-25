import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.list_app.MySingleton
import com.example.list_app.R
import com.example.list_app.entities.Producto
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class BarcodeFragment : Fragment() {

    val API_URL = "https://listapp2021.herokuapp.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_barcode, container, false)
        return v
    }

    lateinit var btnBarcode: Button
    lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBarcode = view.findViewById(R.id.button)
        textView = view.findViewById(R.id.txtContent)
        btnBarcode.setOnClickListener {
            val intentIntegrator = IntentIntegrator.forSupportFragment(this)
            intentIntegrator.setDesiredBarcodeFormats(BarcodeFormat.EAN_13.toString());
            intentIntegrator.setBeepEnabled(true)
            intentIntegrator.setCameraId(0)
            intentIntegrator.setPrompt("SCAN")
            intentIntegrator.setBarcodeImageEnabled(false)
            intentIntegrator.initiateScan()
        }
        val intentIntegrator = IntentIntegrator.forSupportFragment(this)
        intentIntegrator.setDesiredBarcodeFormats(BarcodeFormat.EAN_13.toString());
        intentIntegrator.setBeepEnabled(true)
        intentIntegrator.setCameraId(0)
        intentIntegrator.setPrompt("SCAN")
        intentIntegrator.setBarcodeImageEnabled(false)
        intentIntegrator.initiateScan()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Escaneo cancelado", Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(context, "Producto escaneado correctamente", Toast.LENGTH_SHORT)
//                    .show()
                var barcode = "0" + result.contents.toString().subSequence(0,12 )


                textView.text = String.format("Barcode escaneado: %s", barcode)
                getProdBarcode(barcode)

                Log.d("Fragment", "$result")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    fun pushProdToStock(productoAgregar: Producto) {
        //SE HACE LLAMADO A LA API Y SE OBTIENE LA DATA DEL USUARIO
        val objBody = JSONObject();
        val producto = JSONObject();
        producto.put("id", productoAgregar.id);

        objBody.put("product", producto)
        objBody.put("quantity", 1)
        objBody.toString()

        val prefs = v.context.getSharedPreferences("credentials", Context.MODE_PRIVATE)

        val addProductRequest: StringRequest = object : StringRequest(
            Method.PUT, "https://listapp2021.herokuapp.com/groups/${prefs.getString("SelectedGroupId","")}/stock",
            Response.Listener { response ->
                Log.i("LOG_RESPONSE", response!!)

                val mySnackbar = Snackbar.make(
                    v,
                    "El producto " + productoAgregar.nombreProducto + " se agrego al stock",
                    LENGTH_LONG
                )
                val layoutParams = LinearLayout.LayoutParams(mySnackbar.view.layoutParams)
                layoutParams.gravity = Gravity.TOP
                mySnackbar.view.setPadding(0, 10, 0, 0)
                mySnackbar.view.layoutParams = layoutParams
                mySnackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
                mySnackbar.show()

                v.findNavController().navigate(R.id.action_barcodeFragment_to_navigation_home)


            },
            Response.ErrorListener { error ->
                Log.e("LOG_RESPONSE", error.toString())
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray? {
                return try {
                    if (objBody == null) null else objBody.toString().toByteArray(charset("utf-8"))
                } catch (uee: UnsupportedEncodingException) {
                    VolleyLog.wtf(
                        "Unsupported Encoding while trying to get the bytes of %s using %s",
                        objBody,
                        "utf-8"
                    )
                    null
                }
            }

            override fun getHeaders(): Map<String, String> {
                val prefs = v.context.getSharedPreferences("credentials", Context.MODE_PRIVATE)
                val token = prefs.getString("token", null);
                val headers = HashMap<String, String>()
                headers["Authorization"] = token!!
                return headers
            }
        }

        MySingleton.getInstance(v.context).addToRequestQueue(addProductRequest);

    }

    fun getProdBarcode(barcode : String) {
        //SE HACE LLAMADO A LA API Y SE OBTIENE LA DATA DEL USUARIO
        val userRequest: JsonObjectRequest =
            object : JsonObjectRequest(Request.Method.GET, API_URL + "/products/barcode/" + barcode, null,
                Response.Listener { response ->
                    val res = "Response: %s".format(response.toString())

                    System.out.println(res)

                    var producto = Producto(response.getString("nombreProducto").toString(),response.getString("categoria").toString(), response.getString("foto"),response.getInt("cantidad"),false)

                    producto.id = response.getString("id")
                    val gson = Gson()
                    println(gson.toJson(producto))

                    pushProdToStock(producto)




                }, Response.ErrorListener { error ->
                    // handle error
                    val mySnackbar = Snackbar.make(
                        v,
                        "No se encontro el producto escaneado, prueba la carga manual",
                        LENGTH_LONG
                    )
                    val layoutParams = LinearLayout.LayoutParams(mySnackbar.view.layoutParams)
                    layoutParams.gravity = Gravity.TOP
                    mySnackbar.view.setPadding(0, 10, 0, 0)
                    mySnackbar.view.layoutParams = layoutParams
                    mySnackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
                    mySnackbar.show()

                    System.out.println("Response: %s".format(error.toString()))
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    val prefs =
                        requireActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE)
                    val token = prefs.getString("token", null);

                    headers["Authorization"] = token!!
                    //headers["ANOTHER_CUSTOM_HEADER"] = "Google"
                    return headers
                }
            }

        // Add the request to the RequestQueue.
        MySingleton.getInstance(v.context).addToRequestQueue(userRequest);

    }

}