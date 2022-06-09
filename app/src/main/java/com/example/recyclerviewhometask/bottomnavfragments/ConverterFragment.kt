package com.example.recyclerviewhometask.bottomnavfragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.example.recyclerviewhometask.*
import com.example.recyclerviewhometask.bottomsheets.AddNewCurrency
import com.example.recyclerviewhometask.bottomsheets.FlagChoiceBottomSheet
import com.example.recyclerviewhometask.bottomsheets.OnAddNewCurrency
import com.example.recyclerviewhometask.dialogremove.DialogCallBack
import com.example.recyclerviewhometask.dialogremove.RemoveDialogFragment
import com.example.recyclerviewhometask.dragdrop.DragDropHelper
import com.example.recyclerviewhometask.dragdrop.ItemTouchDelegate
import com.example.recyclerviewhometask.model.Item
import com.example.recyclerviewhometask.recyclerview.MyAdapter
import com.example.recyclerviewhometask.recyclerview.OnAddClicked
import com.example.recyclerviewhometask.recyclerview.OnLongClicked
import com.example.recyclerviewhometask.swipe.LeftSwiped
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class ConverterFragment : Fragment(R.layout.items_layout), ItemTouchDelegate, OnAddClicked,
    DialogCallBack, OnLongClicked, OnAddNewCurrency {

    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var myRecyclerView: RecyclerView
    private lateinit var adapterItems: MyAdapter

    private lateinit var tengeInput: TextInputEditText
    private lateinit var myToolbar: Toolbar
    private var chosenIndex: Int? = null

    private lateinit var dialog: RemoveDialogFragment

    private var dragDropHelper: ItemTouchHelper? = null
    private var snapPosition = RecyclerView.NO_POSITION

    private lateinit var itemToDelete: Item.Currency
    private var indexOfRemovedItem = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tengeInput = view.findViewById(R.id.text_input_ET)
        myToolbar = view.findViewById(R.id.myToolBar)
        myToolbar.inflateMenu(R.menu.menu_currency)

        setupAdapter()

        onMenuItemsClicked()

        onTengeChanged()
    }

    private fun setupAdapter() {
        myLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapterItems = MyAdapter(this, this, this)
        myRecyclerView = requireView().findViewById(R.id.recycler_view)
        myRecyclerView.layoutManager = myLayoutManager
        myRecyclerView.adapter = adapterItems

        adapterItems.setItems(Data.elements)

        setupSwipeAndDragDrop()
        setupSnapping()
    }

    private fun onMenuItemsClicked() {
        myToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sort_by_alphabet -> {
                    adapterItems.sortItemsBy(SortBy.ALPHABET)
                    chosenIndex = 0
                    true
                }
                R.id.sort_by_cost -> {
                    adapterItems.sortItemsBy(SortBy.COST)
                    chosenIndex = 1
                    true
                }
                R.id.reset_sorting -> {
                    adapterItems.resetSorting()
                    true
                }
                R.id.delete_item -> {
                    dialog = RemoveDialogFragment()
                    dialog.show(childFragmentManager, null)
                    true
                    // childFragmentManager: return a fragmentManager for placing Fragments(like RemoveDialogFragment) inside of a Fragment(MainFragment)
                }
                else -> false
            }
        }
    }

    private fun onTengeChanged() {
        tengeInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val tenge = s.toString()
                if (tenge.isNotBlank()) {
                    adapterItems.convertCurrencies(tenge)
                }
            }

        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val chosenItemId = when (chosenIndex) {
            0 -> R.id.sort_by_alphabet
            1 -> R.id.sort_by_cost
            else -> 0
        }
        menu.findItem(chosenItemId).isChecked = true
    }

    private fun setupSwipeAndDragDrop() {
        // set left swipe
        ItemTouchHelper(LeftSwiped(adapterItems)).attachToRecyclerView(myRecyclerView)
        // set drag and drop with MyDragDrop class
        dragDropHelper = ItemTouchHelper(DragDropHelper())
        dragDropHelper?.attachToRecyclerView(myRecyclerView)
    }

    private fun setupSnapping() {
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(myRecyclerView)
        myRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                snapHelper.findSnapView(myLayoutManager)?.let {
                    val position = myLayoutManager.getPosition(it)
                    if (snapPosition != position) {
                        snapPosition = position
                        // invoke some callback
                    }
                }

            }
        })
    }

    override fun startDragging(viewHolder: RecyclerView.ViewHolder) {
        dragDropHelper?.startDrag(viewHolder)
    }

    override fun onAddClick() {
        AddNewCurrency().show(childFragmentManager, null)
    }

    override fun scrollToEnd() {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int = SNAP_TO_START
        }
        smoothScroller.targetPosition = adapterItems.itemCount
        myLayoutManager.startSmoothScroll(smoothScroller)
    }

    override fun onRemoveConfirmed() {
        indexOfRemovedItem = adapterItems.deleteItem(itemToDelete)
        dialog.dismiss()
    }

    override fun showSnackBar() {
        val mySnackBar = Snackbar.make(
            requireView().findViewById(R.id.coordinatorLayout),
            "Валюта удалена",
            Snackbar.LENGTH_SHORT
        )
        mySnackBar.setAction("Вернуть") {
            adapterItems.addByIndex(itemToDelete, indexOfRemovedItem)
        }
        mySnackBar.show()
    }

    override fun setDefaultToolbarState() {
        myToolbar.title = "Converter"
        myToolbar.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white_toolbar_background
            )
        )
        myToolbar.menu.findItem(R.id.delete_item).isVisible = false
        myToolbar.menu.findItem(R.id.sort_by).isVisible = true
        myToolbar.menu.findItem(R.id.reset_sorting).isVisible = true
    }

    override fun changeToolbarState() {
        myToolbar.title = "item selected"
        myToolbar.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.grey_background
            )
        )
        myToolbar.menu.findItem(R.id.delete_item).isVisible = true
        myToolbar.menu.findItem(R.id.sort_by).isVisible = false
        myToolbar.menu.findItem(R.id.reset_sorting).isVisible = false
    }

    override fun itemToDelete(currency: Item.Currency) {
        itemToDelete = currency
    }

    override fun addNewCurrency(newCurrency: Item.Currency) {
        adapterItems.add(newCurrency, chosenIndex)
    }

}