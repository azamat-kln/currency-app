package com.example.recyclerviewhometask.swipe

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.recyclerview.ButtonViewHolder
import com.example.recyclerviewhometask.recyclerview.MyAdapter

class LeftSwiped(private val adapter: MyAdapter?) : ItemTouchHelper.SimpleCallback(
    0, ItemTouchHelper.LEFT
) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter?.deleteItemAt(viewHolder.adapterPosition)
    }

    override fun getSwipeDirs(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        if (viewHolder is ButtonViewHolder) return 0
        return super.getSwipeDirs(recyclerView, viewHolder)
    }
}