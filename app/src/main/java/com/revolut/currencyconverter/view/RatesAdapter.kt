package com.revolut.currencyconverter.view

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.revolut.currencyconverter.model.RatesModel
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.currency_item.view.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class RatesAdapter(
    var ratesList: MutableList<RatesModel> = ArrayList(),
    val itemListener: ItemListener
) : RecyclerView.Adapter<RatesAdapter.RatesViewHolder>() {

    private val ratesSubject: PublishSubject<Pair<String, Double>> = PublishSubject.create()
    private val ratesSubjectDisposable: Disposable? = ratesSubject.debounce(2, TimeUnit.SECONDS)
        .subscribe {
            itemListener.onTextChange(it.first, it.second)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RatesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.revolut.currencyconverter.R.layout.currency_item, parent, false)
        return RatesViewHolder(view)
    }


    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        val currency: RatesModel = ratesList.get(position)
        holder.containerView.title.text = currency.currencySymbol
        holder.containerView.description.text = currency.currencyName
        holder.containerView.amount.setText(currency.rate.toString())
        holder.containerView.amount.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                if (holder.adapterPosition != 0) {
                    handleItemClick(holder, currency)
                } else {
                    itemListener.beforeTextChange()
                    ratesSubject.onNext(
                        Pair(
                            currency.currencySymbol,
                            holder.containerView.amount.text.toString().toDoubleOrNull() ?: 0.0
                        )
                    )
                }

            }
            return@OnTouchListener false
        })



        Glide.with(holder.containerView.context)
            .load(currency.url)
            .override(128, 128)
            .into(holder.containerView.imageView)



        holder.containerView.setOnClickListener {
            if (holder.adapterPosition != 0) {
                handleItemClick(holder, currency)

            }
        }

        holder.containerView.amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                if (holder.containerView.hasFocus() && holder.adapterPosition == 0) {
                    ratesSubject.onNext(
                        Pair(
                            currency.currencySymbol,
                            editable?.toString()?.toDoubleOrNull() ?: 0.0
                        )
                    )
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // No implantation needed here
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // No implantation needed here
            }
        })


    }


    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        ratesSubject.onComplete()
        ratesSubjectDisposable?.dispose()
    }

    private fun handleItemClick(
        holder: RatesViewHolder,
        currency: RatesModel
    ) {
        holder.containerView.amount.requestFocus()
        swapItem(holder.adapterPosition, 0)
        itemListener.onTextChange(
            currency.currencySymbol,
            holder.containerView.amount.text.toString().toDoubleOrNull() ?: 0.0
        )

    }

    private fun swapItem(fromPosition: Int, toPosition: Int) {
        Collections.swap(ratesList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }


    override fun getItemCount(): Int {
        return ratesList.size
    }


    class RatesViewHolder(override var containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer

    interface ItemListener {
        fun onTextChange(currencySymbol: String, rate: Double)
        fun beforeTextChange()
    }
}