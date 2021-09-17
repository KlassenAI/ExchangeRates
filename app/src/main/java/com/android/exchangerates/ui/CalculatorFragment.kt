package com.android.exchangerates.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.exchangerates.R
import com.android.exchangerates.callback.SelectRateCallback
import com.android.exchangerates.databinding.FragmentCalculatorBinding
import com.android.exchangerates.entities.Rate
import com.android.exchangerates.viewmodel.BaseViewModel

class CalculatorFragment : Fragment(R.layout.fragment_calculator), SelectRateCallback {

    private lateinit var binding: FragmentCalculatorBinding
    private lateinit var viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        initCalculatorRateCode()
        initEditTextRubles()
        initChangeCurrentRateObserver()
    }

    private fun initCalculatorRateCode() {
        binding.calculatorRateCode.setOnClickListener {
            if (viewModel.dailyRates.value != null) {
                startSelectRateDialog()
            } else {
                showToast("Загрузите список валют")
            }
        }
    }

    private fun startSelectRateDialog() {
        val dialog = SelectRateDialog(viewModel.dailyRates.value!!, this)
        dialog.show(childFragmentManager, "dialog")
    }

    private fun showToast(text: String) = Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

    private fun initChangeCurrentRateObserver() {
        binding.apply {
            viewModel.currentRate.observe(viewLifecycleOwner, {
                calculatorRateCode.text = it.charCode
                calculatorRateName.text = it.name
                calculatorRateValue.text = it.getValueInRubles(
                    editTextRubles.text.toString().toDouble()
                )
            })
        }
    }

    private fun initEditTextRubles() {
        binding.apply {
            editTextRubles.apply {

                if (viewModel.currentRubles.value != null) {
                    editTextRubles.setRublesString(viewModel.currentRubles.value!!)
                }

                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {}
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        val string = s.toString()
                        val stringWithNoZeros = string.removeNonSignificantZeros()
                        if (string.isEmpty()) {
                            editTextRubles.setRublesString(string)
                        } else if (string != stringWithNoZeros) {
                            editTextRubles.setRublesString(stringWithNoZeros)
                            viewModel.currentRubles.postValue(string)
                        }
                        calculatorRateValue.text = viewModel.currentRate.value?.getValueInRubles(string.toDouble())
                    }
                })
            }
        }
    }

    private fun String.removeNonSignificantZeros(): String {
        return when {
            this == "0" -> this
            this.contains(".") -> this.replace("""^0+[.]""".toRegex(), "0.")
            else -> this.replace("""^0+""".toRegex(), "")
        }
    }

    private fun EditText.setRublesString(string: String) {
        setText(string, TextView.BufferType.EDITABLE)
        setSelection(string.length)
    }

    override fun setCurrentRate(rate: Rate) {
        viewModel.currentRate.postValue(rate)
    }
}

