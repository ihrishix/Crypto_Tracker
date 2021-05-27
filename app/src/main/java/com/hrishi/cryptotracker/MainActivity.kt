package com.hrishi.cryptotracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hrishi.cryptotracker.databinding.ActivityMainBinding

const val TAG = "Main Activity"

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

    //Updates price of all the added coins
    fun update_price(){

        Log.d(TAG, "update_price: update price entered")

        val volley_queue = Volley.newRequestQueue(this) //Api Setup

        if(asset_list.isNotEmpty()) {

            Log.e(TAG, "update_price: Assert list not empty", )

            for(asset in asset_list){
                var cryptoName = asset.id
                var url = "https://rest.coinapi.io/v1/exchangerate/$cryptoName/INR"

                val jsonObjectRequest = object : JsonObjectRequest(

                    Method.GET, url, null,
                    { response ->
                        var price = response.getString("rate").toString()
                        asset.rate = price
                        Main_adapter.notifyDataSetChanged()
                        Log.d(TAG, "update_price: Got a response $price")

                    },
                    { error ->
                        Log.e(TAG, "check_price: ${error.message}",)
                        Toast.makeText(this, "Something went wrong ${error.message}", Toast.LENGTH_LONG)
                            .show()

                        Log.e(TAG, "update_price: Error rrrrrrrr", )

                    }
                
                


                ) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["X-CoinAPI-Key"] = "32685D30-226E-4EE3-8AAE-CF6FC3E96124"
                        return headers
                    }
                }

                volley_queue.add(jsonObjectRequest)
                Log.e(TAG, "update_price: nofity dataset ", )


            }
        }
    }

    //Creates a dialog to add asset(coin).
    private fun add_asset_dialog(){

        val view = LayoutInflater.from(this).inflate(R.layout.add_assset_dialog, null)

        val add_dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Add Coin")
            .setView(view)
            .setPositiveButton("Add") { _ , _ ->
                add_asset(view)

            }.setNegativeButton("Cancel"){ _, _ ->

            }
            .setCancelable(true).create()

        add_dialog.show()
    }

    //Adds coin to the list
    fun add_asset(view : View){

        val addasset_reference = view.findViewById<Spinner>(R.id.asset_spinner)
        val asset_text = addasset_reference.selectedItem.toString()
        val asset_text_array = asset_text.split("-")

        val asset_id = asset_text_array[1].trimStart()
        val asset_name = asset_text_array[0].trimEnd()
        val addasset_Quantity = view.findViewById<TextView>(R.id.addAsset_Quantity)
        val addasset_pricebought = view.findViewById<TextView>(R.id.addAsset_pricebought)

        asset_list.add(
            asset(asset_name, asset_id, "" , addasset_Quantity.text.toString(), addasset_pricebought.text.toString()
            )
        )

        update_price()
    }
}