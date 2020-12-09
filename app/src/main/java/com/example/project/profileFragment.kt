package com.example.project

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView


class profileFragment : Fragment() {
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = activity?.getSharedPreferences(R.string.shared_preferences_name.toString(), Context.MODE_PRIVATE)!!


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility =View.VISIBLE
        requireActivity().findViewById<Button>(R.id.button_profile_save).setOnClickListener {
            saveData();
            showData()
        }
        showData()
    }

    fun showData(){
        preferences.getString("name","No Name")?.let { requireActivity().findViewById<TextView>(R.id.userNameBig).text = it }
        preferences.getString("name","No Name")?.let { requireActivity().findViewById<TextView>(R.id.edit_text_text_profile_name).text = it }
        preferences.getString("address","No Address")?.let { requireActivity().findViewById<EditText>(R.id.edit_text_text_profile_address).setText(it) }
        preferences.getString("phone","No Phone number")?.let { requireActivity().findViewById<EditText>(R.id.edit_text_text_profile_phone).setText(it) }
        preferences.getString("email","No Email")?.let { requireActivity().findViewById<EditText>(R.id.edit_text_text_profile_email).setText(it) }
    }

    fun saveData(){
        preferences.let {
            val editor:SharedPreferences.Editor = it.edit()
            editor.putString("name", requireActivity().findViewById<TextView>(R.id.edit_text_text_profile_name).text.toString())
            editor.putString("address", requireActivity().findViewById<EditText>(R.id.edit_text_text_profile_address).text.toString())
            editor.putString("phone", requireActivity().findViewById<EditText>(R.id.edit_text_text_profile_phone).text.toString())
            editor.putString("email", requireActivity().findViewById<EditText>(R.id.edit_text_text_profile_email).text.toString())
            editor.commit()
        }
    }

}
