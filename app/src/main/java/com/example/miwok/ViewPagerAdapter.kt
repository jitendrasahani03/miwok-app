package com.example.miwok

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return NumbersFragment()
            1 -> return FamilyFragment()
            2 -> return ColorsFragment()
        }
        return PhrasesFragment()
    }

    override fun getItemCount(): Int {
        return 4
    }
}