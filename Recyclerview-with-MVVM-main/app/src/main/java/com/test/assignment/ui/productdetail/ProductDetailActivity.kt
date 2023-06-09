package com.test.assignment.ui.productdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.assignment.R
import com.test.assignment.databinding.ProductDetailActivityBinding
import com.test.assignment.ui.favourite.FavoriteDao
import com.test.assignment.ui.favourite.FavoriteDatabase
import com.test.assignment.ui.favourite.FavoriteModel

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ProductDetailActivityBinding
    private var mFavoriteDao: FavoriteDao? = null
    private var id:Int=0
    private var title=""
    private var image=""
    private var rating=0.0
    private var price=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.product_detail_activity)
        val db = FavoriteDatabase.getDatabase(this)
        mFavoriteDao = db!!.dao
        title= intent.getStringExtra("name")!!
        image= intent.getStringExtra("image")!!
        price=intent.getDoubleExtra("price",0.0)
        rating=intent.getDoubleExtra("rating",0.0)
        id= intent.getIntExtra("id",0)
        setDataOnUi(title,image,price,rating)

        //on Click method
        onClick()
        //logic foe set fav product to Favourite fragment
        favLogic()
    }

    private fun favLogic() {
        val isFavorite: Boolean = mFavoriteDao!!.isFavorite(id)
        if (isFavorite) {
            binding.imgFav.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.imgFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        binding.imgFav.setOnClickListener { view: View? ->
            val isCurrentlyFavorite: Boolean = mFavoriteDao!!.isFavorite(id)
            if (isCurrentlyFavorite) {
                mFavoriteDao!!.deleteData(id)
                binding.imgFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            } else {
                mFavoriteDao!!.insertAllData(FavoriteModel(id, title, image,rating,price))
                binding.imgFav.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
        }    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDataOnUi(name: String?, image: String?, price: Double, rating: Double) {

        binding.name.text = "Product Name - $name"
        binding.price.text = "Price - $price"
        binding.rating.text= "Product Rating - $rating"

        Glide.with(binding.root.context)
            .load(image)
            .dontAnimate()
            .error(R.drawable.error)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(binding.image)
    }

}
