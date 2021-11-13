package com.example.salesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var url = "https://sales-app123.000webhostapp.com/SalesWeb/get_categories.php"
        var arrayList = ArrayList<String>()
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)
        var jsonArrayRequest =
            JsonArrayRequest(Request.Method.GET, url, null, Response.Listener { response ->
                for (x in 0..response.length() - 1)
                    arrayList.add(response.getJSONObject(x).getString("category"))

                var arrayAdapter =
                    ArrayAdapter(this, R.layout.my_textview_for_listview, arrayList)
                homeCategories.adapter = arrayAdapter
            }, Response.ErrorListener { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            })
        requestQueue.add(jsonArrayRequest)

        homeCategories.setOnItemClickListener { adapterView, view, position, id ->
            var category: String = arrayList[position]
            var i = Intent(this, ItemActivity::class.java)
            i.putExtra("category", category)
            startActivity(i)
        }
    }
}