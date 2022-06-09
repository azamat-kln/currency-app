package com.example.recyclerviewhometask.dragdrop

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.recyclerview.ButtonViewHolder
import com.example.recyclerviewhometask.recyclerview.MyAdapter

class DragDropHelper : ItemTouchHelper.SimpleCallback(UP or DOWN or START or END, 0) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val adapter = recyclerView.adapter as MyAdapter
        val from = viewHolder.adapterPosition
        val to = target.adapterPosition

        if (viewHolder is ButtonViewHolder) return false

        adapter.moveItem(from, to)
        adapter.notifyItemMoved(from, to)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)

        if (actionState == ACTION_STATE_DRAG) {
            viewHolder?.itemView?.alpha = 0.5f
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.alpha = 1.0f
    }
}