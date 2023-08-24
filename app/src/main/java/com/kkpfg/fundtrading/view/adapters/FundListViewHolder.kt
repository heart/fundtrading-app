package com.kkpfg.fundtrading.view.adapters

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.kkpfg.fundtrading.data.api.models.FundItem
import com.kkpfg.fundtrading.databinding.FundListItemBinding

typealias OnClickFundItem = (model: FundItem)->Unit

class FundListViewHolder(private val binding: FundListItemBinding): RecyclerView.ViewHolder(binding.root) {

    companion object{
        fun create(context: Context): FundListViewHolder {
            val binding = FundListItemBinding.inflate(LayoutInflater.from(context))
            return FundListViewHolder(binding)
        }
    }

    private var onClickFundItem: OnClickFundItem? = null

    fun setOnClickFundItem(callback: OnClickFundItem){
        onClickFundItem = callback
    }

    fun setModel(model: FundItem) {
        binding.fundName.text =  model.fundName
        binding.fundManager.text = model.fundManager
        binding.category.text = model.category
        binding.symbol.text = model.symbol
        binding.performance.text = "${model.performance?.sinceInception?.toString()} %"
        binding.riskLevel.text = model.riskLevel
        binding.userRating.text = model.userRatings?.toString()

        binding.root.setOnClickListener {
            this.onClickFundItem?.invoke(model)
        }
    }


}