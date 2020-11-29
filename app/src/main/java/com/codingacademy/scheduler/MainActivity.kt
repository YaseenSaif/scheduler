package com.codingacademy.scheduler

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var tabViewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.taps)
        tabViewPager = findViewById(R.id.pager)

        tabViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> GeneralFragment.newInstance(position, "")
                    1 -> GeneralFragment.newInstance(position, "")
                    2 -> GeneralFragment.newInstance(position, "")
                    else ->GeneralFragment.newInstance(position, "")
                }
            }

            override fun getItemCount(): Int {
                return 3


            }
        }

        TabLayoutMediator(tabLayout, tabViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "ToDO"
                    tab.setIcon(R.drawable.ic_baseline_schedule_24)
                }
                1 -> {
                    tab.text = "InProgress"
                    tab.setIcon(R.drawable.ic_baseline_autorenew_24)
                }
                2 -> {
                    tab.text = "Done"
                    tab.setIcon(R.drawable.ic_baseline_done_outline_24)
                }
                else -> null
            }


        }.attach()
    }
}