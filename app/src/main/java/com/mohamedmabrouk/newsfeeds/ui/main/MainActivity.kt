package com.mohamedmabrouk.newsfeeds.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mohamedmabrouk.newsfeeds.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting the Toolbar
        val mToolBar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolBar)


        // Setting Navigation Icon
        val actionbar: ActionBar? = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu)

        // Setting the Navigation Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)

        // default checked item
        navigationView.menu.getItem(0).isChecked = true
        navigationView.setNavigationItemSelectedListener {
            // set item as selected to persist highlight
            it.isChecked = true
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()
            Toast.makeText(this@MainActivity, it.title, Toast.LENGTH_SHORT).show()
            true
        }

    }


    // Activate Home Icon to open Drawer
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}