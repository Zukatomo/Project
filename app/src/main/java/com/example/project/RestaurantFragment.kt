package com.example.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RestrictTo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RestaurantFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        GlobalScope.launch{
//            RestaurantDatabase.getDatabase(requireContext()).restaurantDao().insertRestaurant()
//        }

        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility =
            View.VISIBLE
        val recyclerView=view?.findViewById<RecyclerView>(R.id.list_recycler_view)
        recyclerView?.adapter = AwesomeRecAdapter(requireContext())
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.initPagination(){
            RestLoad.getCP("US"){}
        }
        RestLoad.setEvent {
            (recyclerView?.adapter as AwesomeRecAdapter).notifyDataSetChanged()
        }

    }

    private fun RecyclerView.initPagination(paginationCallback: () -> Unit = {}) {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    paginationCallback()
                }
            }
        })
    }
}