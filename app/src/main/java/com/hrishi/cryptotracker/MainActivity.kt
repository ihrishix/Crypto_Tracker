package com.hrishi.cryptotracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hrishi.cryptotracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)       //View Binding
        setContentView(binding.root)


        var temp_list = mutableListOf<asset>(
            asset("Bitcoin", "BTC", "2344543"),
            asset("Dogecoin", "DOGE", "290.433"),
            asset("WRXcoin", "WRX", "54.32"),
            asset("coinBase", "CBS", "0.0024"),
            asset("Mera COin", "MCO", "234.343"),
            asset("Bitcoin", "BTC", "2344543"),
        asset("Dogecoin", "DOGE", "290.433"),
        asset("WRXcoin", "WRX", "54.32"),
        asset("coinBase", "CBS", "0.0024"),
        asset("Mera COin", "MCO", "234.343")
        )

        val Main_adapter = Main_RecyclerView_Adapter(temp_list)
        binding.mainRecyclerView.adapter = Main_adapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)







    }

    private fun check_price(cryptoName : String = "BTC"){

        val volley_queue = Volley.newRequestQueue(this)     //Api Setup


        var url = "https://rest.coinapi.io/v1/exchangerate/$cryptoName/INR"

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                //binding.textView2.text = response.getString("rate")
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
}