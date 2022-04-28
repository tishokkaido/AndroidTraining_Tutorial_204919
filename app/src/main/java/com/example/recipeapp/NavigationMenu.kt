package com.example.recipeapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

fun setToggle(activity: AppCompatActivity, drawerLayout: DrawerLayout, toolbar: Toolbar) {
    val toggle = ActionBarDrawerToggle(
        activity,
        drawerLayout,
        toolbar,
        R.string.app_name,
        R.string.app_name
    )
    drawerLayout.setDrawerListener(toggle)
    toggle.syncState()
}

fun onNavigation(applicationContext: Context, navigationDrawer: NavigationView, drawerLayout: DrawerLayout) {
    navigationDrawer.setNavigationItemSelectedListener {
        when(it.itemId) {
            R.id.main_activity -> {
                val intent = Intent(applicationContext, MainActivity::class.java)
                ContextCompat.startActivity(applicationContext, intent, null)
            }
            R.id.favorite_activity -> {
                val intent = Intent(applicationContext, FavoriteActivity::class.java)
                ContextCompat.startActivity(applicationContext, intent, null)
            }
        }
        drawerLayout.closeDrawer(navigationDrawer)
        true
    }
}