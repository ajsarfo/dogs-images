package com.sarftec.dogs.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.dogs.domain.model.Dog
import com.sarftec.dogs.view.adapter.viewholder.DogViewHolder
import com.sarftec.dogs.view.viewmodel.DetailViewModel

class DogAdapter(viewModel: DetailViewModel) : RecyclerView.Adapter<DogViewHolder>() {

    private var items: List<Dog> = emptyList()

    private val viewHolderDependency = DogViewHolder.ViewHolderDependency(
        viewModel
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
       return DogViewHolder.getInstance(parent, viewHolderDependency)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitItems(items: List<Dog>) {
        this.items = items
        notifyDataSetChanged()
    }
}