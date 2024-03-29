package com.axyz.upasthithshishya.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.fragments.AttendanceFragment
import com.axyz.upasthithshishya.fragments.HomeFragment
import com.axyz.upasthithshishya.fragments.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val homeFragment = HomeFragment();
        val attendanceFragment = AttendanceFragment();
        val settingsFragment = SettingFragment();
        makeCurrentFragment(homeFragment);
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_home ->makeCurrentFragment(homeFragment)
                R.id.ic_attendance -> makeCurrentFragment(attendanceFragment)
                R.id.ic_settings -> makeCurrentFragment(settingsFragment)
            }
            true
        }


    }
    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    override fun onBackPressed() {
        // Do nothing
    }
}