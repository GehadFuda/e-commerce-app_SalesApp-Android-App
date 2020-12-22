package com.example.salesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signupButton.setOnClickListener {
            var i = Intent(this, RegistrationActivity::class.java)
            startActivity(i)
        }

        loginButton.setOnClickListener {

            /*for using GET method*/
//            var url =
//                "http://192.168.1.125/SalesWeb/login.php?mobile=" +
//                        loginMobile.text.toString() + "&password=" +
//                        loginPassword.text.toString()
//            var requestQueue: RequestQueue = Volley.newRequestQueue(this)

            /*for using POST method*/
            var url = "http://192.168.1.125/SalesWeb/login.php"

            var requestQueue: RequestQueue = Volley.newRequestQueue(this)

            /*for using GET method*/
//            var stringRequest =
//                StringRequest(Request.Method.GET, url, Response.Listener { response ->
//                    if (response.equals("0"))
//                        Toast.makeText(this, "Login faill", Toast.LENGTH_LONG).show()
//                    else {
//                        UserInfo.mobile = loginMobile.text.toString()
//                        var i = Intent(this, HomeActivity::class.java)
//                        startActivity(i)
//                    }
//                }, Response.ErrorListener { error ->
//                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
//                })

            /*for using POST method*/
            var stringRequest =
                object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
                    if (response.equals("0"))
                        Toast.makeText(this, "Login faill", Toast.LENGTH_LONG).show()
                    else {
                        UserInfo.mobile = loginMobile.text.toString()
                        UserInfo.accessToken=response
                        var i = Intent(this, HomeActivity::class.java)
                        startActivity(i)
                    }
                }, Response.ErrorListener { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                }) {
                    override fun getParams(): MutableMap<String, String> {
                        var hashMap = HashMap<String, String>()
                        hashMap.put("mobile", loginMobile.text.toString())
                        hashMap.put(("password"), loginPassword.text.toString())

                        return hashMap
                    }
                }

            requestQueue.add(stringRequest)
        }
    }
}