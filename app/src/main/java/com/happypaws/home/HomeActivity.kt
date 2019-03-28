package com.happypaws.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.NonNull
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.happypaws.R
import com.happypaws.base.BaseActivity
import com.happypaws.home.adoption.AdoptionOverviewFragment
import com.happypaws.home.basefragment.BaseFragment
import com.happypaws.home.lostpets.LostPetsOverviewFragment
import com.happypaws.onglocations.OngLocationsActivity

class HomeActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigation)
    lateinit var navigation: BottomNavigationView

    private val fragment1 = LostPetsOverviewFragment()
    private val fragment2 = AdoptionOverviewFragment()
    private val fm = supportFragmentManager
    private var active = fragment1 as BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)

        navigation.setOnNavigationItemSelectedListener(this)

        initFragmentBottomNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onNavigationItemSelected(@NonNull menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.navigation_lost -> {
                fm.beginTransaction().hide(active).show(fragment1).commit()
                active = fragment1
                true
            }
            R.id.navigation_adoption -> {
                fm.beginTransaction().hide(active).show(fragment2).commit()
                active = fragment2
                true
            }
            else -> false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_ong_locations -> {
                Log.d(TAG, "menu ong location clicked")

                startActivity(Intent(this, OngLocationsActivity::class.java))

                return true
            }
        }
        return false
    }

    private fun initFragmentBottomNavigation() {
        fm.beginTransaction().add(R.id.main_container, fragment1, AdoptionOverviewFragment.TAG).hide(fragment2).commit()
        fm.beginTransaction().add(R.id.main_container, fragment2, LostPetsOverviewFragment.TAG).commit()
    }

    companion object {

        var TAG = "HomeActivity"
    }
}