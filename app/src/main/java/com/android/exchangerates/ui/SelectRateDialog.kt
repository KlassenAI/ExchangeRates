package com.android.exchangerates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.android.exchangerates.R
import com.android.exchangerates.callback.SelectRateCallback
import com.android.exchangerates.databinding.DialogSelectRateBinding
import com.android.exchangerates.entities.Rate

class SelectRateDialog(
    private val rates: List<Rate>,
    private val listener: SelectRateCallback
) : DialogFragment(R.layout.dialog_select_rate) {

    private lateinit var binding: DialogSelectRateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogSelectRateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rateList.apply {
                adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    rates.map { it.charCode }
                )
                setOnItemClickListener { _, _, position, _ ->
                    val rate = rates[position]
                    listener.setCurrentRate(rate)
                    dismiss()
                }
            }
        }
    }

}