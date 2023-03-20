package com.vungn.mytlumdc.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vungn.mytlumdc.ui.home.views.HomeFragment
import com.vungn.mytlumdc.ui.home.views.NotificationFragment

class ViewpagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = TABS_SIZE

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            HOME_TAB -> HomeFragment()
            NOTIFICATION_TAB -> NotificationFragment()
            else -> HomeFragment()
        }
    }

    companion object {
        const val HOME_TAB = 0
        const val NOTIFICATION_TAB = 1
        const val TABS_SIZE = 2
    }
}