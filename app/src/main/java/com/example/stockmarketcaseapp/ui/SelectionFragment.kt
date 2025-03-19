package com.example.stockmarketcaseapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmarketcaseapp.databinding.DialogFilterSelectionBinding
import com.example.stockmarketcaseapp.model.ColumnItem
import com.example.stockmarketcaseapp.ui.adapter.SelectionAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectionDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DialogFilterSelectionBinding

    private var items: List<ColumnItem>? = null
    private var onItemSelected: ((ColumnItem) -> Unit)? = null

    companion object {
        fun newInstance(
            items: List<ColumnItem>,
            onItemSelected: (ColumnItem) -> Unit
        ): SelectionDialogFragment {
            val fragment = SelectionDialogFragment()
            val args = Bundle().apply {
                putParcelableArrayList("items", ArrayList(items))
            }
            fragment.arguments = args
            fragment.onItemSelected = onItemSelected
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFilterSelectionBinding.inflate(inflater, container, false)

        items = arguments?.getParcelableArrayList("items")

        val adapter = SelectionAdapter { selected ->
            onItemSelected?.invoke(selected)
            dismiss()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        adapter.submitList(items)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Dialog boyutlarını ayarlamak
        val dialog = dialog as BottomSheetDialog
        val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val layoutParams = bottomSheet?.layoutParams
        layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT // Ekranın tamamını kaplayacak şekilde ayarlıyoruz
        bottomSheet?.layoutParams = layoutParams
    }
}