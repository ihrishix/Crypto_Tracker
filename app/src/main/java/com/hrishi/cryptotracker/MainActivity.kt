package com.hrishi.cryptotracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hrishi.cryptotracker.databinding.ActivityMainBinding

const val TAG = "Main Actiivty"
class MainActivity : AppCompatActivity() {

    var asset_list = mutableListOf<asset>()
    val Main_adapter = Main_RecyclerView_Adapter(asset_list)

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)       //View Binding
        setContentView(binding.root)







        binding.mainRecyclerView.adapter = Main_adapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)


        binding.addButton.setOnClickListener {
            add_asset_dialog()

        }





    }

    private fun check_price(cryptoName : String = "BTC") : String{

        val volley_queue = Volley.newRequestQueue(this)     //Api Setup


        var url = "https://rest.coinapi.io/v1/exchangerate/$cryptoName/INR"

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val rate : String = response.getString("rate").toString()
                return rate
            },
            { error ->
                //binding.textView2.text = error.message

            }


        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-CoinAPI-Key"] = "32685D30-226E-4EE3-8AAE-CF6FC3E96124"
                return headers
            }
        }

        volley_queue.add(jsonObjectRequest)

    }


    private fun add_asset_dialog(){

        val view = LayoutInflater.from(this).inflate(R.layout.add_assset_dialog, null)

        val add_dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Add Coin")
            .setView(view)
            .setPositiveButton("Add") { _ , _ ->
                val me = view.findViewById<EditText>(R.id.addAsset_id)
                Log.e(TAG, "add_asset_dialog: ${me.text}", )

            }.setNegativeButton("Cancel"){ _, _ ->
            }
            .setCancelable(true).create()


        add_dialog.show()



    }

    private fun add_asset(){



        val addasset_id = findViewById<TextView>(R.id.addAsset_id)
        val addasset_Quantity = findViewById<TextView>(R.id.addAsset_Quantity)
        val addasset_pricebought = findViewById<TextView>(R.id.addAsset_pricebought)

        val rate = "0"


        asset_list.add(
            asset(
            addasset_id.text.toString(),
                rate, addasset_Quantity.toString(), addasset_pricebought.toString()
            )
        )


        Main_adapter.notifyDataSetChanged()

    }

}