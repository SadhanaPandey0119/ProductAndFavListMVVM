package com.test.assignment.ui.favourite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.test.assignment.databinding.FavouriteFragmentBinding
import com.test.assignment.ui.productdetail.ProductDetailActivity
import com.test.assignment.utils.autoCleared

class FavouriteFragment : Fragment(),FavoriteAdapter.DeleteItemClickListner,FavoriteAdapter.ProductItemListener {
    private var binding: FavouriteFragmentBinding by autoCleared()
    private var mFavoriteDao: FavoriteDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavouriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getData() {
        val list: List<FavoriteModel?>? = FavoriteDatabase.getDatabase(requireContext())!!.dao!!.allData
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerview.adapter =
            list?.let {
                FavoriteAdapter(this,requireContext(), mFavoriteDao!!, it, this)
            }

        if (list!!.isEmpty()) {
            binding.emptyImage.visibility = View.VISIBLE
        } else {
            binding.emptyImage.visibility = View.GONE
        }
    }

    companion object {
        val TAG: String = FavouriteFragment::class.java.simpleName
        fun newInstance() = FavouriteFragment()
    }

    override fun onItemDelete(position: Int, id: Int) {
        FavoriteDatabase.getDatabase(requireContext())!!.dao!!.deleteData(id)
        getData()
    }

    override fun onResume() {
        super.onResume()
        val db = FavoriteDatabase.getDatabase(requireContext())!!
        mFavoriteDao = db.dao
        getData()
    }

    override fun onClickedProduct(item: FavoriteModel) {
        val i= Intent(requireActivity(), ProductDetailActivity::class.java)
        i.putExtra("name",item.title)
        i.putExtra("image",item.imageURL)
        i.putExtra("rating",item.ratingCount)
        i.putExtra("price",item.price)
        i.putExtra("id",item.id)
        startActivity(i)
    }
}