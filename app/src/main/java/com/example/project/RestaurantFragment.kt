package com.example.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView


class RestaurantFragment : Fragment() {

    private lateinit var dropdown: Spinner
    private lateinit var search:SearchView


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
        search = requireActivity().findViewById<SearchView>(R.id.simpleSearchView)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                RestLoad.getByName(query)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        dropdown = requireActivity().findViewById<Spinner>(R.id.restaurantSpinner)
        val adapter: ArrayAdapter<String>? = context?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_dropdown_item, RestLoad.countryList) }
        dropdown.setAdapter(adapter);
        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                RestLoad.setCountry(position)
            }

        }
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