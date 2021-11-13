package com.example.salesapp

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_for_recycler_view.view.*

class ItemAdapterControllerForRecyclerView(
    var context: Context,
    var arrayList: ArrayList<ItemModelForRecyclerView>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View =
            LayoutInflater.from(context).inflate(R.layout.item_row_for_recycler_view, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemHolder).bind(
            arrayList[position].name,
            arrayList[position].price,
            arrayList[position].photo,
            arrayList[position].id
        )
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(name: String, price: Double, photoName: String,item_id:Int) {
            itemView.itemName.text = name
            itemView.itemPrice.text = price.toString()
            var web:String="https://sales-app123.000webhostapp.com/SalesWeb/images/" + photoName
            web=web.replace(" ","%20")
            Picasso.with(itemView.context).load(web)
                .into(itemView.itemPhoto)

            itemView.itemAddPhoto.setOnClickListener {
                UserInfo.itemId=item_id

                var obj=QuantityFragment()
                var manager=(itemView.context as Activity).fragmentManager
                obj.show(manager,"Quantity")
            }
        }
    }
}