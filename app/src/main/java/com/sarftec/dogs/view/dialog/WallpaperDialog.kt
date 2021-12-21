package com.sarftec.dogs.view.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.sarftec.dogs.databinding.LayoutSetWallpaperDialogBinding

class WallpaperDialog(
    parent: ViewGroup,
    private val onHome: () -> Unit,
    private val onLock: () -> Unit
) : AlertDialog(parent.context) {

    private val layoutBinding = LayoutSetWallpaperDialogBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )

    init {
        layoutBinding.apply {
            lockScreen.setOnClickListener {
                onLock()
                dismiss()
            }
            homeScreen.setOnClickListener {
                onHome()
                dismiss()
            }
        }
        setView(layoutBinding.root)
        setCancelable(true)
    }
}