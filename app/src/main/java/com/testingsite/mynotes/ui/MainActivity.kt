package com.testingsite.mynotes.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.testingsite.mynotes.R
import com.testingsite.mynotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val UPDATE_REQ_CODE = 1612
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
        //navController.popBackStack(0, false)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.archiveFragment,
                R.id.tasksListFragment,
                R.id.upcomingAlarmsFragment,
                R.id.settingsFragment
            ), binding.drawerLayout
        )
        binding.navigationView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        checkForUpdates()


        /*val actionBar = supportActionBar
        actionBar?.title = "Hello Toolbar"*/


        // Initialize the action bar drawer toggle instance
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                //toast("Drawer closed")
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                //toast("Drawer opened")
            }
        }


        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()


        // Set navigation view navigation item selected listener
        binding.navigationView.setNavigationItemSelectedListener {
            if (it.itemId != binding.navigationView.checkedItem?.itemId) {
                NavigationUI.onNavDestinationSelected(it, navController)
            } else if (it.itemId == R.id.fragment) {
                NavigationUI.onNavDestinationSelected(it, navController)
            }
            /*when (it.itemId) {
                R.id.my_notes -> toast("Cut my_notes")
                R.id.personal_notes -> toast("Cut personal_notes")
                R.id.task_list -> toast("Cut task_list")
                R.id.upcoming_alarms -> toast("Copy upcoming_alarms")
                R.id.settings -> toast("settings")
            }*/
            // Close the drawer
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        /*val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        //val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)*/

        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        //val navController = navHostFragment.navController

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment), null
        )
    }

    private fun checkForUpdates() {
        val appUpdateManager = AppUpdateManagerFactory.create(applicationContext!!)
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            try {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ) {
                    // Request the update.`
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        UPDATE_REQ_CODE
                    )
                }
            } catch (e: Exception) {
                Log.i("TAG", "checkForUpdates: ${e.message}")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UPDATE_REQ_CODE) {
            if (resultCode != RESULT_OK) {
                Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val appUpdateManager = AppUpdateManagerFactory.create(applicationContext!!)
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->

                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    // If an in-app update is already running, resume the update.
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        UPDATE_REQ_CODE
                    )
                }
            }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}