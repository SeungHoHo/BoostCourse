package com.seungho.android.myapplication.movieapp.View.Activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.seungho.android.myapplication.movieapp.Adapter.FragmentAdapter
import com.seungho.android.myapplication.movieapp.R
import com.seungho.android.myapplication.movieapp.View.Fragment.Movie.*
import com.seungho.android.myapplication.movieapp.View.Fragment.MovieList.*
import com.seungho.android.myapplication.movieapp.databinding.ActivityViewpagerBinding

class ViewPagerActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val binding by lazy { ActivityViewpagerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnNavi.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START) // 햄버거 메뉴를 누르면 왼쪽에 메뉴 생성
       }

        binding.naviView.setNavigationItemSelectedListener(this)

        /* 프래그먼트 어댑터 */
        val fragmentList = listOf(FragmentA(), FragmentB(), FragmentC(), FragmentD(), FragmentE() )

        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList
        binding.viewPager.adapter = adapter

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuList -> Toast.makeText(applicationContext, "영화 목록", Toast.LENGTH_SHORT).show()
            R.id.menuApi -> Toast.makeText(applicationContext, "영화 API", Toast.LENGTH_SHORT).show()
            R.id.menuBook -> Toast.makeText(applicationContext, "예매하기", Toast.LENGTH_SHORT).show()
            R.id.menuSetting -> Toast.makeText(applicationContext, "사용자 설정", Toast.LENGTH_SHORT).show()
        }
        binding.drawerLayout.closeDrawers()
        return false
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            binding.drawerLayout.closeDrawers()
        }
        else
        {
            super.onBackPressed()
        }
    }

    fun goMovie_A_Api() {
        val Api_A_Fragment = MovieAActivity()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.viewpagerFrameLayout, Api_A_Fragment)
        transaction.commit()

        transaction.addToBackStack(null)
    }

    fun goMovie_B_Api() {
        val Api_B_Fragment = MovieBActivity()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.viewpagerFrameLayout, Api_B_Fragment)
        transaction.commit()

        transaction.addToBackStack(null)
    }

    fun goMovie_C_Api() {
        val Api_C_Fragment = MovieCActivity()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.viewpagerFrameLayout, Api_C_Fragment)
        transaction.commit()

        transaction.addToBackStack(null)
    }

    fun goMovie_D_Api() {
        val Api_D_Fragment = MovieDActivity()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.viewpagerFrameLayout, Api_D_Fragment)
        transaction.commit()

        transaction.addToBackStack(null)
    }

    fun goMovie_E_Api() {
        val Api_E_Fragment = MovieEActivity()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.viewpagerFrameLayout, Api_E_Fragment)
        transaction.commit()

        transaction.addToBackStack(null)
    }
}