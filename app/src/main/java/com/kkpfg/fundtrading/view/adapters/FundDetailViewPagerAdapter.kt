package com.kkpfg.fundtrading.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class FundDetailViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: androidx.lifecycle.Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {

    private var fragmentList: List<Fragment> = listOf()

    fun setFragment(fragmentList: List<Fragment>){
        this.fragmentList = fragmentList
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}