package com.example.recyclerviewhometask.dragdrop

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchDelegate {
    fun startDragging(viewHolder: RecyclerView.ViewHolder)
}