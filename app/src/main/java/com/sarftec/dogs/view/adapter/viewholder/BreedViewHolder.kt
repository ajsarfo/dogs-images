package com.sarftec.dogs.view.adapter.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sarftec.dogs.databinding.LayoutBreedBinding
import com.sarftec.dogs.domain.model.Breed
import com.sarftec.dogs.domain.model.Dog
import com.sarftec.dogs.tools.extra.Resource
import com.sarftec.dogs.view.task.Task
import com.sarftec.dogs.view.task.TaskManager
import com.sarftec.dogs.view.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import java.util.*

class BreedViewHolder(
    private val layoutBinding: LayoutBreedBinding,
    private val dependency: ViewHolderDependency
) : RecyclerView.ViewHolder(layoutBinding.root) {

    private val uuid = UUID.randomUUID().toString()

    private fun clearLayout(breed: Breed) {
        layoutBinding.breedCard.setOnClickListener {
            dependency.onClick(breed)
        }
        layoutBinding.image.setImageBitmap(null)
    }

    private fun setLayout(resource: Resource<Dog>) {
        if (resource.isSuccess()) {
            Glide.with(itemView)
                .load(resource.data!!.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(layoutBinding.image)

            //layoutBinding.image.setImageURI(resource.data!!.image)
        }
        if (resource.isError()) Log.v("TAG", "${resource.message}")
        dependency.taskManager.removeTask(uuid)
    }

    fun bind(breed: Breed) {
        layoutBinding.breed.text = breed.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString()
        }
        clearLayout(breed)
        val task = Task.createTask<Breed, Resource<Dog>>(
            dependency.coroutineScope,
            breed
        )
        task.addExecution { input -> dependency.viewModel.getRandomDog(input) }
        task.addCallback { setLayout(it) }
        dependency.taskManager.addTask(uuid, task.build())
    }

    companion object {
        fun getInstance(parent: ViewGroup, dependency: ViewHolderDependency): BreedViewHolder {
            return BreedViewHolder(
                LayoutBreedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                dependency
            )
        }
    }

    class ViewHolderDependency(
        val coroutineScope: CoroutineScope,
        val viewModel: MainViewModel,
        val taskManager: TaskManager<Breed, Resource<Dog>>,
        val onClick: (Breed) -> Unit
    )
}