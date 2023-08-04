package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Viewのセット
        setPagerFragment()

        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.main_drawer)
        setToggle(this, drawerLayout, toolbar)

        val navigationDrawer = findViewById<NavigationView>(R.id.main_drawer_navigation)
        onNavigation(applicationContext, navigationDrawer, drawerLayout)
    }

    override fun onRestart() {
        super.onRestart()
        // Activityのリロード
        reload()
        Log.d("MainActivity", "onRestart")
    }

    /**
     * Activityのリロード
     */
    private fun reload() {
        val intent = intent
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()

        overridePendingTransition(0, 0)
        startActivity(intent)
    }

    /**
     * フラグメントのセット
     */
    private fun setPagerFragment() {
        val fm: FragmentManager = supportFragmentManager
        val viewPager = findViewById<ViewPager>(R.id.main_viewPager)
        val adapter = MainPagerAdapter(fm)
        viewPager.adapter = adapter
        val tabLayout: TabLayout = findViewById(R.id.main_tab)
        tabLayout.setupWithViewPager(viewPager)
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }
}