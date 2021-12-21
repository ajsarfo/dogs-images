package com.sarftec.dogs.view.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sarftec.dogs.databinding.LayoutDogBinding
import com.sarftec.dogs.domain.model.Dog
import com.sarftec.dogs.view.viewmodel.DetailViewModel

class DogViewHolder private constructor(
    private val layoutBinding: LayoutDogBinding,
    private val dependency: ViewHolderDependency
) : RecyclerView.ViewHolder(layoutBinding.root) {

    fun bind(dog: Dog) {
        Glide.with(itemView)
            .load(dog.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(layoutBinding.image)
    }

    companion object {
        fun getInstance(parent: ViewGroup, dependency: ViewHolderDependency) : DogViewHolder {
            return DogViewHolder(
                LayoutDogBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                dependency
            )
        }
    }

    class ViewHolderDependency(
        val viewModel: DetailViewModel
    )
}
