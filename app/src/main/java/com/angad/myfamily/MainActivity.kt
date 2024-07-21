package com.angad.myfamily

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val permissinons = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_CONTACTS
    )
    val permissionCode = 70

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        askForPermission()

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
                R.id.nav_dashboard -> {
                    infilateFragment(MapsFragment())
                }
                R.id.nav_guard -> {
                    infilateFragment(GuardFragment.newInstance())
                }
                R.id.nav_profile -> {
                    infilateFragment(ProfileFragment.newInstance())
                }
            }

            true
        }
        bottomBar.selectedItemId = R.id.nav_home
    }

//    Asking various permission from the user

    private fun askForPermission() {
        ActivityCompat.requestPermissions(this,permissinons,permissionCode)
    }

    private fun infilateFragment(newInstance: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newInstance)
        transaction.commit()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if(requestCode == permissionCode){
            if(allPermissionGranted()){
//                openCamera()
            }else{
                
            }
        }
    }

    private fun openCamera() {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        startActivity(intent)
    }

    private fun allPermissionGranted(): Boolean {
        for(item in permissinons){
            if(ContextCompat.checkSelfPermission(this,item) != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

}