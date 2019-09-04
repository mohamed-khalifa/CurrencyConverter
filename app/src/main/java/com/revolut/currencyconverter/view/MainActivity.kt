package com.revolut.currencyconverter.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.revolut.currencyconverter.model.ErrorModel
import com.revolut.currencyconverter.R
import com.revolut.currencyconverter.model.RatesModel
import com.revolut.currencyconverter.viewmodel.RatesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), RatesAdapter.ItemListener {

    override fun beforeTextChange() {
        viewModel.dispose()
    }

    override fun onTextChange(currencySymbol: String, rate: Double) {
        viewModel.getRates(currencySymbol, rate)

    }

    private lateinit var viewModel: RatesViewModel
    private lateinit var ratesAdapter: RatesAdapter
    private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(RatesViewModel::class.java)
        viewModel.initObservers()
        ratesAdapter = RatesAdapter(itemListener = this)
        ratesRecyclerView.adapter = ratesAdapter

        val ratesObserver = Observer<MutableList<RatesModel>> {
            ratesErrorView.visibility = View.GONE
            snackBar?.dismiss()
            ratesAdapter.ratesList = it
            ratesAdapter.notifyDataSetChanged()


        }
        val errorObserver = Observer<ErrorModel> {
            ratesErrorView.visibility = View.VISIBLE
            showSnackBar(it.errorMessage)
        }

        viewModel.rates.observe(this, ratesObserver)
        viewModel.errorModel.observe(this, errorObserver)
        viewModel.getRates()

    }

    private fun showSnackBar(errorMessage: String?) {
        errorMessage?.let {
            snackBar = Snackbar
                .make(constraintLayout, errorMessage, Snackbar.LENGTH_INDEFINITE)
            snackBar?.show()
        }
    }
}