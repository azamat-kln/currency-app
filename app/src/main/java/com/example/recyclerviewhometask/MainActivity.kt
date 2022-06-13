package com.example.recyclerviewhometask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerviewhometask.bottomnavfragments.*
import com.example.recyclerviewhometask.bottomnavfragments.news.NewsFragment
import com.example.recyclerviewhometask.bottomnavfragments.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView = findViewById(R.id.bottom_nav_view)

        addConverterFragmentAsLauncher()
        onBottomNavViewItemsClicked()
    }

    private fun onBottomNavViewItemsClicked() {
        bottomNavView.setOnItemSelectedListener {
            val transaction = supportFragmentManager.beginTransaction()
            when (it.itemId) {
                R.id.item_1 -> {
                    transaction.replace(R.id.container_for_fragment, TranslateFragment()).commit()
                    true
                }
                R.id.item_2 -> {
                    transaction.replace(R.id.container_for_fragment, NewsFragment()).commit()
                    true
                }
                R.id.item_3 -> {
                    transaction.replace(R.id.container_for_fragment, ConverterFragment()).commit()
                    true
                }
                R.id.item_4 -> {
                    transaction.replace(R.id.container_for_fragment, SearchFragment()).commit()
                    true
                }
                R.id.item_5 -> {
                    transaction.replace(R.id.container_for_fragment, ProfileFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun addConverterFragmentAsLauncher() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_for_fragment, ConverterFragment()).commit()
    }

}