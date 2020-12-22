package com.example.salesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_item.*

class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        var category: String = intent.getStringExtra("category")
        var url = "http://192.168.1.125/SalesWeb/get_items.php?category=" + category
        var arrayList = ArrayList<ItemModelForRecyclerView>()

        var requestQueue: RequestQueue = Volley.newRequestQueue(this)
        var jsonArrayRequest =
            JsonArrayRequest(Request.Method.GET, url, null, Response.Listener { response ->
                for (x in 0..response.length() - 1)
                    arrayList.add(
                        ItemModelForRecyclerView(
                            response.getJSONObject(x).getInt(
                                "id"
                            ),
                            response.getJSONObject(x).getString("name"),
                            response.getJSONObject(x).getDouble("price"),
                            response.getJSONObject(x).getString("photo")
                        )
                    )

                var adapter = ItemAdapterControllerForRecyclerView(this, arrayList)
                itemRecyclerView.layoutManager = LinearLayoutManager(this)
                itemRecyclerView.adapter = adapter
            }, Response.ErrorListener { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            })
        requestQueue.add(jsonArrayRequest)
    }
}