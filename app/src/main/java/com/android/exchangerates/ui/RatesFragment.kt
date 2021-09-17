package com.android.exchangerates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.android.exchangerates.R
import com.android.exchangerates.adapter.RateItemAdapter
import com.android.exchangerates.databinding.FragmentRatesBinding
import com.android.exchangerates.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RatesFragment : Fragment(R.layout.fragment_rates) {

    private lateinit var binding: FragmentRatesBinding
    private lateinit var viewModel: BaseViewModel
    private val itemAdapter = RateItemAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecycler()
        initSwipeRefresh()
    }

    private fun initViewModel() {
        viewModel = (activity as MainActivity).viewModel
        if (isNoDataLoad()) {
            viewModel.getDailyRates()
        }
        viewModel.dailyRates.observe(viewLifecycleOwner, {
            itemAdapter.setList(it)
        })
    }

    private fun isNoDataLoad() = viewModel.dailyRates.value == null

    private fun initRecycler() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            adapter = itemAdapter
        }
    }

    private fun initSwipeRefresh() {
        binding.swipeRefresh.apply {
            setOnRefreshListener {
                viewModel.getDailyRates()
                isRefreshing = false
            }
        }
    }
}
