package com.example.recipeapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                PagerFragment.newInstance(R.layout.fragment_recommend)
            }
            1 -> {
                PagerFragment.newInstance(R.layout.fragment_favorite)
            }
            else -> {
                PagerFragment.newInstance(R.layout.fragment_recommend)
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0-> {
                "RECOMMENDED"
            }
            else -> {
                "FAVORITE"
            }
        }
    }
}