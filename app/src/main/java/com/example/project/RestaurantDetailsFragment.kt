package com.example.project

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_restaurant.view.*


class RestaurantDetailsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("sdsa",arguments?.get("Recid").toString())
        arguments?.getInt("Recid")?.let {
            RestLoad.getByID(it){
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
                requireActivity().findViewById<TextView>(R.id.RDaddress).text ="Address: " + it.address
                requireActivity().findViewById<TextView>(R.id.RDarea).text ="Area: " + it.area
                requireActivity().findViewById<TextView>(R.id.RDcity).text ="City: " + it.city
                requireActivity().findViewById<TextView>(R.id.RDcoord).text ="Coordinates: " + it.lat +" "+  it.lng
                requireActivity().findViewById<TextView>(R.id.RDphone).text ="Phone: " + it.phone
                requireActivity().findViewById<TextView>(R.id.RDprice).text ="Price: " + it.price.toString()
                requireActivity().findViewById<TextView>(R.id.RDstate).text ="Sate: " + it.state
                requireActivity().findViewById<TextView>(R.id.RDweb).text ="Web: " + it.mobile_reserve_url
                requireActivity().findViewById<TextView>(R.id.RDzip).text ="ZipCode: " + it.postal_code
                val url = it.lat +"," + it.lng
                val p = it.phone
                requireActivity().findViewById<Button>(R.id.map).setOnClickListener{
                    startActivity(
                            Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("http://maps.google.com/maps?q=loc:"+url)
                                    )
                            )
                }
                requireActivity().findViewById<Button>(R.id.phone).setOnClickListener{
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("tel:${p}")))
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
    }
}
