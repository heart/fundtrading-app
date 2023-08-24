package com.kkpfg.fundtrading.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kkpfg.fundtrading.data.api.models.FundItem
import com.kkpfg.fundtrading.data.api.models.TopHolding
import com.kkpfg.fundtrading.databinding.FragmentTopHoldingBinding
import com.kkpfg.fundtrading.databinding.FundHoldingCellLayoutBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TopHoldingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopHoldingFragment : Fragment() {

    private var fundItem: FundItem? = null

    lateinit var binding: FragmentTopHoldingBinding

    private var adapter: TopHoldingAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance(fundItem: FundItem) =
            TopHoldingFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("fund", fundItem)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fundItem = it.getParcelable("fund", FundItem::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTopHoldingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TopHoldingAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerView.adapter = adapter

        fundItem?.topHoldings?.let{
            adapter?.setModel(it)
        }

    }

}


class TopHoldingViewHolder(val binding: FundHoldingCellLayoutBinding): RecyclerView.ViewHolder(binding.root){

}

class TopHoldingAdapter: RecyclerView.Adapter<TopHoldingViewHolder>(){

    private var list: List<TopHolding?> = listOf()

    fun setModel(list: List<TopHolding?>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHoldingViewHolder {
        val binding = FundHoldingCellLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return TopHoldingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TopHoldingViewHolder, position: Int) {
        val item = list[position]

        holder.binding.holderName.text = item?.name
        holder.binding.percent.text = item?.percentage?.toString()
    }

}