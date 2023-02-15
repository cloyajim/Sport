package com.example.sports

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.sports.databinding.ItemSportBinding

class SportListAdapter(private val listener: OnClickListener):
    ListAdapter<Sport, RecyclerView.ViewHolder>(sportDiffCallBack()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_sport, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sport = getItem(position)
        with(holder as ViewHolder){
            setListener(sport)

            binding.tvName.text = sport.name

            Glide.with(context)
                .asBitmap()
                .load(sport.imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop() //centrar y ajustar imagen
                .into(object: CustomTarget<Bitmap>(1280,720){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        binding.prgBar.visibility = View.GONE
                        binding.ivPhoto.scaleType = ImageView.ScaleType.CENTER_CROP //ajustar al tamaño
                        binding.ivPhoto.setImageBitmap(resource)
                    }

                    override fun onLoadStarted(placeholder: Drawable?) {
                        super.onLoadStarted(placeholder)
                        binding.ivPhoto.setImageResource(R.drawable.ic_access_time)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        binding.prgBar.visibility = View.GONE
                        binding.ivPhoto.setImageResource(R.drawable.ic_error)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        TODO("Not yet implemented")
                    }
                })
        }

    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemSportBinding.bind(view)
        fun setListener(sport: Sport){
            binding.root.setOnClickListener { listener.OnCLick(sport) }
        }
    }

    class sportDiffCallBack: DiffUtil.ItemCallback<Sport>() {
        override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean = oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean = oldItem == newItem
    }
}