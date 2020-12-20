package com.example.project

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RestaurantDetailsFragment : Fragment() {
    lateinit var rdc: RestaurantDataClass
    lateinit var adapter: FavoriteAdapter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            GlobalScope.launch {
                try {
                        rdc.images = rdc.images + "+" + data?.dataString
                    context?.let { RestaurantDatabase.getDatabase(it).restaurantDao().updateById(rdc) }
                    adapter.updateData(rdc.images.split("+"))
                } catch (ex: Exception) {
                    Log.e("MYex", ex.toString())
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FavoriteAdapter()
        arguments?.getLong("Recid")?.let {
            GlobalScope.launch {
                try {
                    rdc = context?.let { it1 -> RestaurantDatabase.getDatabase(it1).restaurantDao().findRestaurantById(it) }!!
                    adapter.updateData(rdc.images.split("+"))
                    adapter.notifyDataSetChanged()
                } catch (ex: Exception) {
                    Log.e("EXXX", ex.toString())
                }
            }

            RestLoad.getByID(it) {
                view?.let { it1 ->
                    Glide.with(this)
                            .load(it.image_url)
                            .centerCrop() //4
                            .placeholder(R.drawable.restimage) //5
                            .error(R.drawable.restimage) //6
                            .fallback(R.drawable.restimage)
                            .into(requireActivity().findViewById<ImageView>(R.id.item_restaurant_image))
                }
                requireActivity().findViewById<TextView>(R.id.RDtitle).text = "Name: " + it.name
                requireActivity().findViewById<TextView>(R.id.RDaddress).text = "Address: " + it.address
                requireActivity().findViewById<TextView>(R.id.RDarea).text = "Area: " + it.area
                requireActivity().findViewById<TextView>(R.id.RDcity).text = "City: " + it.city
                requireActivity().findViewById<TextView>(R.id.RDcoord).text = "Coordinates: " + it.lat + " " + it.lng
                requireActivity().findViewById<TextView>(R.id.RDphone).text = "Phone: " + it.phone
                requireActivity().findViewById<TextView>(R.id.RDprice).text = "Price: " + it.price.toString()
                requireActivity().findViewById<TextView>(R.id.RDstate).text = "Sate: " + it.state
                requireActivity().findViewById<TextView>(R.id.RDweb).text = "Web: " + it.mobile_reserve_url
                requireActivity().findViewById<TextView>(R.id.RDzip).text = "ZipCode: " + it.postal_code
                val url = it.lat + "," + it.lng
                val p = it.phone
                requireActivity().findViewById<Button>(R.id.map).setOnClickListener {
                    startActivity(
                            Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("http://maps.google.com/maps?q=loc:" + url)
                            )
                    )
                }
                requireActivity().findViewById<Button>(R.id.phone).setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("tel:${p}")))
                }
                requireActivity().findViewById<Button>(R.id.addImage).setOnClickListener {
                    //Intent to pick image
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    intent.type = "image/*"
                    startActivityForResult(intent, 1000)

                }
            }
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.list_recycler_view_detail)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        (recyclerView?.adapter as FavoriteAdapter).notifyDataSetChanged()
    }
}
