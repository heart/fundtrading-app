package com.kkpfg.fundtrading.view.adapters

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kkpfg.fundtrading.data.api.models.BannerItem
import com.kkpfg.fundtrading.data.api.models.FundItem
import com.kkpfg.fundtrading.data.api.models.FundList

typealias OnScrollToBottom = ()->Unit

sealed class ListItem{
    class TypeFund(val fund: FundItem): ListItem()
    class TypeBanner(val banner: BannerItem): ListItem()
}

class FundListingAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<ListItem> = mutableListOf()

    private var dataset: FundList? = FundList()
    private var banners: List<BannerItem>? = null

    private var onScrollToBottom: OnScrollToBottom? = null

    private var setOnClickFundItem: OnClickFundItem? = null

    fun removeAll() {
        this.dataset?.funds = mutableListOf()
        orderList()

        notifyDataSetChanged()
    }

    fun updateBanner(banners: List<BannerItem>?) {
        this.banners = banners
        orderList()
    }

    private fun orderList(){
        list = mutableListOf()

        dataset?.funds?.forEach{
            val fundListItem = ListItem.TypeFund(it)
            list.add(fundListItem)
        }

        var index = 0
        banners?.forEach{
            val banner = ListItem.TypeBanner(it)
            if(index <= list.size-1){
                list.add( index, banner)
                index += 3
            }
        }
    }

    fun setOnClickViewItem(callback: OnClickFundItem){
        setOnClickFundItem = callback
    }

    fun setOnScrollToBottom(callback: OnScrollToBottom){
        this.onScrollToBottom = callback
    }

    fun updateDataset(dataset: FundList){
        this.dataset = dataset
        orderList()
        notifyDataSetChanged()
    }

    fun appendDataset(dataset: FundList){
        dataset.funds?.let{
            this.dataset?.funds?.addAll(it)
        }
        orderList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            0 -> BannerViewHolder.create(parent.context)
            1-> FundListViewHolder.create(parent.context)
            else-> FundListViewHolder.create(parent.context)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when( list[position] ){
            is ListItem.TypeBanner -> 0
            is ListItem.TypeFund -> 1
        }
    }

    override fun getItemCount(): Int {

        return  this.list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        when (holder) {
            is FundListViewHolder -> {
                val m = model as ListItem.TypeFund
                holder.setModel(m.fund)
                holder.setOnClickFundItem {
                    this.setOnClickFundItem?.invoke(it)
                }
            }
            is BannerViewHolder ->{
                val m = model as ListItem.TypeBanner
                holder.setModel(m.banner)
            }
        }

        val size = dataset?.funds?.size ?: 0

        if(position == size-1) {
            this.onScrollToBottom?.invoke()
        }
    }

}
