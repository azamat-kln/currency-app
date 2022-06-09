package com.example.recyclerviewhometask.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.recyclerviewhometask.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FlagChoiceBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.choice_flag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val row1 = view.findViewById<TextView>(R.id.text_view_1)
        val row2 = view.findViewById<TextView>(R.id.text_view_2)
        val row3 = view.findViewById<TextView>(R.id.text_view_3)
        val row4 = view.findViewById<TextView>(R.id.text_view_4)

        row1.setOnClickListener {
            val image1 = view.findViewById<ImageView>(R.id.flag_image1)
            (parentFragment as? OnRowClick)?.onRowClicked(imageId = image1.id)
            dismiss()
        }
        row2.setOnClickListener {
            val image2 = view.findViewById<ImageView>(R.id.flag_image2)
            (parentFragment as? OnRowClick)?.onRowClicked(imageId = image2.id)
            dismiss()
        }
        row3.setOnClickListener {
            val image3 = view.findViewById<ImageView>(R.id.flag_image3)
            (parentFragment as? OnRowClick)?.onRowClicked(imageId = image3.id)
            dismiss()
        }
        row4.setOnClickListener {
            val image4 = view.findViewById<ImageView>(R.id.flag_image4)
            (parentFragment as? OnRowClick)?.onRowClicked(imageId = image4.id)
            dismiss()
        }

    }

}

interface OnRowClick {
    fun onRowClicked(imageId: Int)
}


