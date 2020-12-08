package com.example.project

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionInflater
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout

class profile : Fragment() {

    var settings: SharedPreferences? = null
        set(settings) {
            settings?.let {
                field = settings
                user = User(
                    settings.getString("user_name", "") ?: "",
                    settings.getString("user_address", "") ?: "",
                    settings.getString("user_phone", "") ?: "",
                    settings.getString("user_email", "") ?: ""
                )
            }
        }
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view?.findViewById<AppCompatTextView>(R.id.profile_name)?.text  = user.name
        val btn_click_me = view?.findViewById<Button>(R.id.button_profile_save)
        btn_click_me?.setOnClickListener {
            saveProfile()
        }
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        settings = activity?.getSharedPreferences(getString(R.string.shared_preferences_name),
            Context.MODE_PRIVATE
        )

    }

    fun saveProfile() {
        Log.e("Button", "clicked.")
        settings?.let {

            val editor: SharedPreferences.Editor = it.edit()
            editor.putString("user_name",
                view?.findViewById<TextInputLayout>(R.id.text_input_profile_name).toString()
            )
            editor.putString("user_address", user?.address)
            editor.putString("user_phone", user?.phone)
            editor.putString("user_email", user?.email)
            editor.commit()
        }
    }

}