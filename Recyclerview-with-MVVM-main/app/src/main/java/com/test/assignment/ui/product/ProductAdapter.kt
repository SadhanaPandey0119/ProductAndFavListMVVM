package com.test.assignment.ui.product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.assignment.R
import com.test.assignment.data.entities.Product
import com.test.assignment.databinding.ItemProductBinding
import com.test.assignment.ui.favourite.FavoriteDao
import com.test.assignment.ui.favourite.FavoriteModel

class ProductAdapter(private val listener: ProductItemListener,
                     val favoriteDao: FavoriteDao?) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface ProductItemListener {
        fun onClickedProduct(product: Product)
    }

    private val items = ArrayList<Product>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<Product>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding: ItemProductBinding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(items[position])

    inner class ProductViewHolder(private val itemBinding: ItemProductBinding,
                                private val listener: ProductItemListener) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var user: Product

        init {
            itemBinding.root.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Product) {
            this.user = item
            itemBinding.name.text = item.title
            itemBinding.price.text = "Price:"+" "+item.price[0].value.toString()
            itemBinding.btnAddCart.text=item.addToCartButtonText

            Glide.with(itemBinding.root.context)
                .load(item.imageURL)
                .dontAnimate()
                .error(R.drawable.error)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(itemBinding.image)

            val isFavorite: Boolean = favoriteDao!!.isFavorite(item.id!!)
            if (isFavorite) {
                itemBinding.imgFav.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                itemBinding.imgFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            itemBinding.imgFav.setOnClickListener { view: View? ->
                val isCurrentlyFavorite: Boolean = favoriteDao.isFavorite(item.id)
                if (isCurrentlyFavorite) {
                    favoriteDao.deleteData(item.id)
                    itemBinding.imgFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                } else {
                    favoriteDao.insertAllData(FavoriteModel(item.id, item.title, item.imageURL,item.ratingCount,item.price[0].value))
                    itemBinding.imgFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                }
            }
        }

        override fun onClick(v: View?) {
            listener.onClickedProduct(user)
        }
    }
}
