package com.example.lesson_3_5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.lesson_3_5.databinding.ItemImageBinding

class ImageAdapter(val list: MutableList<ImageModel>) : Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addImages(newImages: List<ImageModel>){
        val lastIndex = list.size
        list.addAll(newImages)
        notifyItemRangeInserted(lastIndex, newImages.size)
    }

    fun reloadImages(newImage: List<ImageModel>){
        list.clear()
        list.addAll(newImage)
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) : ViewHolder(binding.root) {
        fun bind(imageModel: ImageModel) {
            binding.imageView.load(imageModel.largeImageURL)
        }

    }
}