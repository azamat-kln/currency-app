package com.example.recyclerviewhometask.bottomsheets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.recyclerviewhometask.R
import com.example.recyclerviewhometask.model.Item
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout

class AddNewCurrency : BottomSheetDialogFragment(), OnRowClick {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.add_new_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val choiceFlagBtn = view.findViewById<Button>(R.id.button_choose_flag)
        val addNewCurrencyBtn = view.findViewById<Button>(R.id.button_add)
        val currencyNameEditText: TextInputLayout = view.findViewById(R.id.textIL_currency_name)
        val currencyAmountEditText: TextInputLayout = view.findViewById(R.id.textIL_amount)

        choiceFlagBtn.setOnClickListener {
            FlagChoiceBottomSheet().show(childFragmentManager, null)
        }

        addNewCurrencyBtn.setOnClickListener {
            val currencyName = currencyNameEditText.editText?.text.toString()
            val currencyAmount = currencyAmountEditText.editText?.text.toString().toFloat()

            val newCurrency = Item.Currency(currencyAmount, currencyName, R.drawable.usa_flag)

            (parentFragment as? OnAddNewCurrency)?.addNewCurrency(newCurrency)
            dismiss()
        }
    }

    override fun onRowClicked(imageId: Int) {
        Toast.makeText(requireContext(), "$imageId", Toast.LENGTH_SHORT).show()
    }

}

interface OnAddNewCurrency {

    fun addNewCurrency(newCurrency: Item.Currency)

}