package com.test.assignment.ui.favourite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.assignment.R

class FavoriteAdapter(private val listener: ProductItemListener,val context: Context, private val mFavoriteDao: FavoriteDao,
    private val list: List<FavoriteModel?>,
    private val deleteItemClickListner: DeleteItemClickListner
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>()
{
    interface ProductItemListener {
        fun onClickedProduct(product: FavoriteModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_fav, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.title.text=model!!.title
        holder.price.text= "Price:"+" "+model.price.toString()

        Glide.with(context)
            .load(model.imageURL)
            .dontAnimate()
            .error(R.drawable.error)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(holder.image)

        val isFavorite = mFavoriteDao.isFavorite(model.id)
        if (isFavorite) {
            holder.ivfavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            holder.ivfavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        holder.ivfavorite.setOnClickListener { view: View? ->
            deleteItemClickListner.onItemDelete(
                position,
                list[position]!!.id
            )
        }

        holder.itemView.setOnClickListener {
            listener.onClickedProduct(list[position]!!)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface DeleteItemClickListner {
        fun onItemDelete(position: Int, id: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivfavorite: ImageView
        var title:TextView
        var price:TextView
        var image:ImageView

        init {
            ivfavorite = view.findViewById(R.id.imgFav)
            title=view.findViewById(R.id.name)
            price=view.findViewById(R.id.price)
            image=view.findViewById(R.id.image)

        }
    }
}