package com.sarftec.dogs.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.dogs.domain.model.Breed
import com.sarftec.dogs.view.adapter.viewholder.BreedViewHolder
import com.sarftec.dogs.view.task.TaskManager
import com.sarftec.dogs.view.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope

class BreedAdapter(
    coroutineScope: CoroutineScope,
    viewModel: MainViewModel,
    onClick: (Breed) -> Unit
) : RecyclerView.Adapter<BreedViewHolder>() {

    private var items = listOf<Breed>()

    private val dependency = BreedViewHolder.ViewHolderDependency(
        coroutineScope,
        viewModel,
        TaskManager(),
        onClick
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
       return BreedViewHolder.getInstance(parent, dependency)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
       holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitItems(items: List<Breed>) {
        this.items = items
        notifyDataSetChanged()
    }
}