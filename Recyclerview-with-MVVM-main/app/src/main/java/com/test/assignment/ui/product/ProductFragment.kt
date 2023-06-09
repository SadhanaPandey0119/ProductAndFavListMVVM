package com.test.assignment.ui.product

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.test.assignment.data.entities.Product
import com.test.assignment.databinding.ProductFragmentBinding
import com.test.assignment.ui.favourite.FavoriteDao
import com.test.assignment.ui.favourite.FavoriteDatabase
import com.test.assignment.ui.productdetail.ProductDetailActivity
import com.test.assignment.utils.Resource
import com.test.assignment.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductFragment : Fragment(), ProductAdapter.ProductItemListener {
    private var binding: ProductFragmentBinding by autoCleared()
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var adapter: ProductAdapter
    private var mFavoriteDao: FavoriteDao? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductFragmentBinding.inflate(inflater, container, false)
        val db = FavoriteDatabase.getDatabase(requireContext())
        mFavoriteDao = db!!.dao
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(this,mFavoriteDao)
        binding.rvProductList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProductList.adapter = adapter
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setupObservers() {
        viewModel.productList.observe(
            viewLifecycleOwner
        ) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    if (!it.data?.products.isNullOrEmpty()) adapter.setItems(ArrayList(it.data!!.products))
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun onClickedProduct(item: Product) {
        val i=Intent(requireActivity(),ProductDetailActivity::class.java)
        i.putExtra("name",item.title)
        i.putExtra("image",item.imageURL)
        i.putExtra("price",item.price[0].value)
        i.putExtra("rating",item.ratingCount)
        i.putExtra("id",item.id)
        startActivity(i)
    }

    companion object {
        val TAG: String = ProductFragment::class.java.simpleName
        fun newInstance() = ProductFragment()
    }

}
