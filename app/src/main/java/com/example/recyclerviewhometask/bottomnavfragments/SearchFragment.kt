package com.example.recyclerviewhometask.bottomnavfragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.Data
import com.example.recyclerviewhometask.R
import com.example.recyclerviewhometask.model.Item
import com.example.recyclerviewhometask.recyclerview.SearchAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    private lateinit var inputCurrencyText: TextInputEditText
    private lateinit var chipGroup: ChipGroup
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chipGroup = view.findViewById(R.id.chipGroup)
        inputCurrencyText = view.findViewById(R.id.search_text_input_ET)
        recyclerView = view.findViewById(R.id.rv_inSearchFragment)

        setupAdapter()
        searchForCurrency()
    }

    private fun setupAdapter() {
        val myLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        searchAdapter = SearchAdapter()
        recyclerView.apply {
            adapter = searchAdapter
            layoutManager = myLayoutManager
        }
        searchAdapter.setData(getLocalData())
    }

    private fun searchForCurrency() {
        inputCurrencyText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotBlank()) {
                    val currency = s.toString()
                    searchAdapter.updateList(currency)

                } else if (s.toString().isEmpty()) {
                    searchAdapter.setData(getLocalData())
                }
            }
        })
    }

    private fun createChipHistory() {
        // TODO: create a chip history,when a currency clicked
        val chip: Chip? = layoutInflater.inflate(R.layout.chip_history, chipGroup, false) as? Chip
        chip?.let {
            it.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Log.i("SearchFragment", "chip clicked")
                }
            }
            chipGroup.addView(it)
        }
    }

    private fun getLocalData(): List<Item.Currency> {
        val allData = Data.elements
        return getCurrencies(allData)
    }

    private fun getCurrencies(data: List<Item>): List<Item.Currency> {
        val onlyCurrencies = mutableListOf<Item.Currency>()
        data.forEach {
            if (it is Item.Currency) {
                onlyCurrencies.add(it)
            }
        }
        return onlyCurrencies
    }

}