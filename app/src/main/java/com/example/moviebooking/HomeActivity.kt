package com.example.moviebooking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moviebooking.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_second) as NavHostFragment
        navController = findNavController(R.id.nav_host_fragment_second)
        setSupportActionBar(binding.appBarSecond.toolbar)
//        binding.appBarSecond.toolbar.title = "View Movies"

        val drawerLayout: DrawerLayout = binding.myDrawerLayout
        val navView: NavigationView = binding.navigationView
        val navController = findNavController(R.id.nav_host_fragment_second)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.profileFragment, R.id.orderFragment, R.id.homeFragment, R.id.buzzFragment
            ), drawerLayout
        )
        //navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener {
        //    FirebaseAuth.getInstance().signOut()
        //    startActivity(Intent(this, LoginActivity::class.java))
        //    finish()
        //    true
        //}
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> {
                firebaseAuth.signOut()
               // navController.navigate()
            }
            R.id.orderFragment -> {
               // navController.navigate()
            }
            R.id.buzzFragment -> {

            }
            R.id.nav_changePassword -> {

            }
            R.id.profileFragment -> {
               // navController.navigate()
            }
        }
       // binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_second)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}