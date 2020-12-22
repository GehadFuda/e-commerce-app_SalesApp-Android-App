package com.example.salesapp

import android.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuantityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuantityFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var variable = inflater.inflate(R.layout.fragment_quantity, container, false)

        var editText = variable.findViewById<EditText>(R.id.editTextQuantity)
        var button = variable.findViewById<Button>(R.id.buttonQuantity)

        button.setOnClickListener {
            var url = "http://192.168.1.125/SalesWeb/add_temporary_order.php?mobile=" +
                    UserInfo.mobile + "&item_id=" + UserInfo.itemId + "&quantity=" + editText.text.toString()
            var requestQueue: RequestQueue = Volley.newRequestQueue(activity)
            var stringRequest =
                StringRequest(Request.Method.GET, url, Response.Listener { response ->
                    var i = Intent(activity, OrderActivity::class.java)
                    startActivity(i)
                }, Response.ErrorListener { error ->
                    Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
                })

            requestQueue.add(stringRequest)
        }

        return variable
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuantityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuantityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}