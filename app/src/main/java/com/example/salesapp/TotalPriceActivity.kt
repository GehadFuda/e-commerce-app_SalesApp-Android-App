package com.example.salesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_total_price.*
import java.math.BigDecimal

class TotalPriceActivity : AppCompatActivity() {

    var config: PayPalConfiguration? = null
    var amount: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_price)

        var url =
            "http://192.168.1.125/SalesWeb/get_total_price.php?bill_no=" + intent.getStringExtra("bill_no")
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)
        var stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener { response ->
                textViewTotalPrice.text = response
            }, Response.ErrorListener { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(stringRequest)

        config = PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(UserInfo.clientID)
        var i = Intent(this, PayPalService::class.java)
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        startService(i)

        paypalButton.setOnClickListener {
            amount = textViewTotalPrice.text.toString().toDouble()
            var payment = PayPalPayment(
                BigDecimal.valueOf(amount),
                "USD",
                "Sales App",
                PayPalPayment.PAYMENT_INTENT_SALE
            )

            var intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
            startActivityForResult(intent, 123)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                var obj = Intent(this, PaypalConfirmActivity::class.java)
                startActivity(obj)
            }
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }
}