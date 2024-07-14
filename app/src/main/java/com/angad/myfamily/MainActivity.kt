package com.angad.myfamily

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Toast.makeText(this, "Hello Angad", Toast.LENGTH_LONG).show()

        val bottomBar = findViewById<BottomNavigationView>(R.id.bottom_bar)

        bottomBar.setOnItemSelectedListener { menuItem->

            when (menuItem.itemId) {
                R.id.nav_home -> {
                    infilateFragment(HomeFragment.newInstance())
                }
                R.id.nav_guard -> {
                    infilateFragment(GuardFragment.newInstance())
                }
                R.id.nav_dashboard -> {
                    infilateFragment(DashboardFragment.newInstance())
                }
                R.id.nav_profile -> {
                    infilateFragment(ProfileFragment.newInstance())
                }
            }

            true
        }
        bottomBar.selectedItemId = R.id.nav_home
    }

    private fun infilateFragment(newInstance: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newInstance)
        transaction.commit()
    }
}