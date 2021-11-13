package com.example.salesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        var url = "https://sales-app123.000webhostapp.com/SalesWeb/get_temporary_order.php?mobile=" + UserInfo.mobile

        var arrayList = ArrayList<String>()
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)
        var jsonArrayRequest =
            JsonArrayRequest(Request.Method.GET, url, null, Response.Listener { response ->
                for (x in 0..response.length() - 1)
                    arrayList.add(
                        "Name: " + response.getJSONObject(x).getString("name") + "\n"
                                + "Price: " + response.getJSONObject(x).getString("price") + "\n"
                                + "Quantity: " + response.getJSONObject(x).getString("quantity")
                    )

                var arrayAdapter =
                    ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, arrayList)
                orderListView.adapter = arrayAdapter
            }, Response.ErrorListener { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            })
        requestQueue.add(jsonArrayRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.ItemBackToMenu) {
            var i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        }

        if (item.itemId == R.id.ItemCancel) {
            var url = "https://sales-app123.000webhostapp.com/SalesWeb/cancel_order.php?mobile=" + UserInfo.mobile
            var requestQueue: RequestQueue = Volley.newRequestQueue(this)
            var stringRequest =
                StringRequest(Request.Method.GET, url, Response.Listener { response ->
                    var i = Intent(this, HomeActivity::class.java)
                    startActivity(i)
                }, Response.ErrorListener { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                })

            requestQueue.add(stringRequest)
        }

        if (item.itemId == R.id.ItemConfirm) {
            var url = "https://sales-app123.000webhostapp.com/SalesWeb/confirm_order.php?mobile=" + UserInfo.mobile
            var requestQueue: RequestQueue = Volley.newRequestQueue(this)
            var stringRequest =
                StringRequest(Request.Method.GET, url, Response.Listener { response ->
                    var i = Intent(this, TotalPriceActivity::class.java)
                    i.putExtra("bill_no",response)
                    startActivity(i)
                }, Response.ErrorListener { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                })

            requestQueue.add(stringRequest)
        }

        return super.onOptionsItemSelected(item)
    }
}