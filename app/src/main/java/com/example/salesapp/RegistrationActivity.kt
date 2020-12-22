package com.example.salesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registrationSignupButton.setOnClickListener {
            if (registrationPassword.text.toString()
                    .equals(registrationPasswordConfirm.text.toString())
            ) {
                var url =
                    "http://192.168.1.125/SalesWeb/add_user.php?mobile=" +
                            registrationMobile.text.toString() + "&password=" +
                            registrationPassword.text.toString() + "&name=" +
                            registrationName.text.toString() + "&address=" +
                            registrationAddress.text.toString()

                var requestQueue: RequestQueue = Volley.newRequestQueue(this)
                var stringRequest =
                    StringRequest(Request.Method.GET, url, Response.Listener { response ->
                        if (response.equals("0"))
                            Toast.makeText(this, "Mobile already used", Toast.LENGTH_LONG).show()
                        else {
                            UserInfo.mobile = registrationMobile.text.toString()
                            var i = Intent(this, HomeActivity::class.java)
                            startActivity(i)
                        }
                    }, Response.ErrorListener { error ->
                        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                    })

                requestQueue.add(stringRequest)
            } else {
                Toast.makeText(this, "Password not match", Toast.LENGTH_LONG).show()
            }
        }
    }
}