package com.example.recyclerviewhometask.bottomnavfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.recyclerviewhometask.R
import com.example.recyclerviewhometask.bottomnavfragments.viewpager.ProfileAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewpager = view.findViewById<ViewPager2>(R.id.view_pager)
        val adapter = ProfileAdapter(childFragmentManager, lifecycle)
        viewpager.adapter = adapter

        val tablayout = view.findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "Основные"
                1 -> tab.text = "Статистика"
                2 -> tab.text = "Последний таб"
            }
        }.attach()

    }

}