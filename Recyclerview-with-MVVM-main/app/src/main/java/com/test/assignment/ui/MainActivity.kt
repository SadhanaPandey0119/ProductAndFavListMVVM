package com.test.assignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.test.assignment.R
import com.test.assignment.databinding.ActivityMainBinding
import com.test.assignment.extension.active
import com.test.assignment.extension.switchFragment
import com.test.assignment.helper.BottomNavigationPosition
import com.test.assignment.helper.createFragment
import com.test.assignment.helper.findNavigationPositionById
import com.test.assignment.helper.getTag
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var navPosition: BottomNavigationPosition = BottomNavigationPosition.PRODUCT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreSavedInstanceState(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setProductFragment()

        binding.bottomNavigation.apply {
            // Set a default position
            active(navPosition.position) // Extension function

            // Set a listener for handling selection events on bottom navigation items
            setOnItemSelectedListener { item ->
                navPosition = findNavigationPositionById(item.itemId)
                toolbarTitle(navPosition.position)
                switchFragment(navPosition)
            }
        }
    }

    private fun toolbarTitle(pos: Int) {
        if (pos==0){
            binding.tvHeader.text = "Product List"
        }else{
            binding.tvHeader.text = "Favourite List"
        }
    }

    private fun setProductFragment() {
        navPosition = findNavigationPositionById(0)
        switchFragment(navPosition)
        binding.tvHeader.text = "Product List"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Store the current navigation position.
        outState.putInt(KEY_POSITION, navPosition.id)
        super.onSaveInstanceState(outState)
    }

    private fun restoreSavedInstanceState(savedInstanceState: Bundle?) {
        // Restore the current navigation position.
        savedInstanceState?.getInt(KEY_POSITION, BottomNavigationPosition.PRODUCT.id)?.also {
            navPosition = findNavigationPositionById(it)
        }
    }

    private fun switchFragment(navPosition: BottomNavigationPosition): Boolean {
        return findFragment(navPosition).let {
            supportFragmentManager.switchFragment(it, navPosition.getTag()) // Extension function
        }
    }

    private fun findFragment(position: BottomNavigationPosition): Fragment {
        return supportFragmentManager.findFragmentByTag(position.getTag())
            ?: position.createFragment()
    }


    companion object {
        const val KEY_POSITION = "keyPosition"
    }
}
