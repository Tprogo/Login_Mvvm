package com.example.loginmvvmapp

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.loginmvvmapp.databinding.ActivityMainBinding
import com.example.loginmvvmapp.utils.NetworkConnection
import com.example.loginmvvmapp.utils.Network_Check

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController


//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        navController = navHostFragment.navController





        val networkCheck = Network_Check(applicationContext)


//        if (networkCheck.checkForInternet()){
//            setVisibility(true)
//            Log.d("Check TAG","Check Internet ${networkCheck.checkForInternet()}")
//        }else{
//            setVisibility(false)
//        }


        val networkConnection = NetworkConnection(application)
//
        networkConnection.observe(this, Observer { connection ->
            if (connection) {
                Log.d("TAG Network", "Connection $connection")
                setVisibility(true)
            } else {
                setVisibility(false)
            }
        })

        setVisibility(false)
    }


    private fun setVisibility(flag: Boolean){
        if (flag){
            binding.fragmentContainerView.visibility = View.VISIBLE
            binding.noInternet.root.visibility = View.GONE
        } else{
            binding.noInternet.root.visibility = View.VISIBLE
            binding.fragmentContainerView.visibility = View.GONE

        }

    }

    // exit app if you press back button at the home fragment
    // also check navController.addOnDestinationChangedListener for destination change listener

//    override fun onBackPressed() {
//
//
//        if(navController.currentDestination?.id == R.id.homeFragment){
//            finish()
//        }
//    }

}