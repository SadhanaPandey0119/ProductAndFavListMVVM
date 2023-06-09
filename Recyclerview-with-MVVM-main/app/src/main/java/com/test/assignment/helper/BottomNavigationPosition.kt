package com.test.assignment.helper

import androidx.fragment.app.Fragment
import com.test.assignment.R
import com.test.assignment.ui.product.ProductFragment
import com.test.assignment.ui.favourite.FavouriteFragment


enum class BottomNavigationPosition(val position: Int, val id: Int) {
    PRODUCT(0, R.id.product),
    FAVORITE(1, R.id.fav),
}

fun findNavigationPositionById(id: Int): BottomNavigationPosition = when (id) {
    BottomNavigationPosition.PRODUCT.id -> BottomNavigationPosition.PRODUCT
    BottomNavigationPosition.FAVORITE.id -> BottomNavigationPosition.FAVORITE
    else -> BottomNavigationPosition.PRODUCT
}

fun BottomNavigationPosition.createFragment(): Fragment = when (this) {
    BottomNavigationPosition.PRODUCT -> ProductFragment.newInstance()
    BottomNavigationPosition.FAVORITE -> FavouriteFragment.newInstance()
}

fun BottomNavigationPosition.getTag(): String = when (this) {
    BottomNavigationPosition.PRODUCT -> ProductFragment.TAG
    BottomNavigationPosition.FAVORITE -> FavouriteFragment.TAG
}

