package com.xnova.digicerto.ui.main

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mAppBarConfiguration: AppBarConfiguration
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(mBinding.appBarMain.toolbar)

        /*binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
        setNavigationMenu()
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(mAppBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setNavigationMenu() {
        val drawerLayout: DrawerLayout = mBinding.drawerLayout
        val navView: NavigationView = mBinding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        mAppBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_settings, R.id.nav_slideshow), drawerLayout
        )
        setupActionBarWithNavController(navController, mAppBarConfiguration)
        navView.setupWithNavController(navController)
    }
}