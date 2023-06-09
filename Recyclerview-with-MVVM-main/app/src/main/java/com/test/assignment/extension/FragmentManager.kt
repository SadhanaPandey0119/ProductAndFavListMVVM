package com.test.assignment.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.test.assignment.R

fun FragmentManager.switchFragment(fragment: Fragment, tag: String): Boolean {
    if (fragment.isAdded) return false
    commit {
        // Detach a fragment
        findFragmentById(R.id.nav_fragment)?.also {
            detach(it)
        }
        // Attach or add a fragment
        if (fragment.isDetached) {
            attach(fragment)
        } else {
            add(R.id.nav_fragment, fragment, tag)
        }
        // Set the animation for this transaction
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    }
    // Immediately execute transactions
    return executePendingTransactions()
}