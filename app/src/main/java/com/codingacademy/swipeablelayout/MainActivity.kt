package com.codingacademy.swipeablelayout

import android.os.Bundle
import android.widget.FrameLayout
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
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Toast.makeText(this@MainActivity, tab?.text.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

        tabViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> FirstFragment.newInstance(position, "")
                    1 -> FirstFragment.newInstance(position, "")
                    2 -> FirstFragment.newInstance(position, "")
                    else -> ThirdFragment.newInstance("", "")
                }
            }

            override fun getItemCount(): Int {
                return 3


            }
        }

        TabLayoutMediator(tabLayout, tabViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Tab 1"
                    tab.setIcon(R.drawable.ic_baseline_keyboard_voice_24)
                }
                1 -> {
                    tab.text = "Tab 2"
                    tab.setIcon(R.drawable.ic_baseline_camera_alt_24)
                }
                2 -> {
                    tab.text = "Tab 3"
                    tab.setIcon(R.drawable.ic_baseline_videocam_24)
                }
                else -> null
            }


        }.attach()
    }
}