package com.example.recyclerviewhometask.dialogremove

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.recyclerviewhometask.R

class RemoveDialogFragment : DialogFragment(R.layout.custom_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            findViewById<Button>(R.id.cancel_btn).setOnClickListener {
                (parentFragment as? DialogCallBack)?.setDefaultToolbarState()
                dismiss()
            }

            findViewById<Button>(R.id.remove_tn).setOnClickListener {
                (parentFragment as? DialogCallBack)?.onRemoveConfirmed()
                (parentFragment as? DialogCallBack)?.showSnackBar()
                (parentFragment as? DialogCallBack)?.setDefaultToolbarState()
            }

        }
    }

}

interface DialogCallBack {
    fun onRemoveConfirmed()
    fun showSnackBar()
    fun setDefaultToolbarState()
}