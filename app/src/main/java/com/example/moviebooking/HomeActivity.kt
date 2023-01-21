package com.example.moviebooking

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.example.moviebooking.databinding.ActivityHomeBinding
import com.example.moviebooking.model.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var email: String = firebaseAuth.currentUser?.email.toString()
    private var database: DatabaseReference =
        Firebase.database.reference.child("users").child(email.replace('.', ','))
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_second) as NavHostFragment
        val navController = navHostFragment.navController
        setSupportActionBar(binding.appBarMain.toolbar)
        drawerLayout = binding.myDrawerLayout
        navView = binding.navigationView

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.profileFragment, R.id.orderFragment, R.id.homeFragment, R.id.buzzFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            true
        }

        val databaseListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue<User>()
                updateUI(user)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    applicationContext, "error in fetching details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        database.addValueEventListener(databaseListener)
    }


    private fun updateUI(user: User?) {
        val view = binding.navigationView.getHeaderView(0)
        val imageView = view.findViewById<ImageView>(R.id.imageViewHeader)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBarHeader)
        if (user?.imageUrl != "")
            imageView.load(user?.imageUrl)
        else
            imageView.setImageResource(R.drawable.ic_profile_image)
        val name: TextView = view.findViewById(R.id.nameHeader)
        val email: TextView = view.findViewById(R.id.emailHeader)
        name.text = user?.name
        email.text = user?.email
        progressBar.visibility = View.INVISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_second)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}