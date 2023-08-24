package com.kkpfg.fundtrading.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.kkpfg.fundtrading.data.api.models.FundItem
import com.kkpfg.fundtrading.databinding.ActivityFundDetailBinding
import com.kkpfg.fundtrading.view.adapters.FundDetailViewPagerAdapter
import com.kkpfg.fundtrading.view.fragments.PerformanceChartFragment
import com.kkpfg.fundtrading.view.fragments.TopHoldingFragment
import com.kkpfg.fundtrading.viewmodels.FundDetailViewModel

class FundDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFundDetailBinding

    private var fundItem: FundItem? = null

    private var pagerAdpater: FundDetailViewPagerAdapter? = null

    val viewModel = FundDetailViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFundDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.minimumInvest.text = "${this.fundItem?.minimumInvestment} THB"

        binding.investAmount.setOnFocusChangeListener { _, isFocus ->
            if(isFocus && binding.investAmount.text.toString() == "0"){
                binding.investAmount.setText("")
            }else if( binding.investAmount.text.toString() == ""){
                binding.investAmount.setText("0")
            }
        }


        fundItem = intent.getParcelableExtra("fund", FundItem::class.java)
        fundItem?.let{
            render(it)
        }

        viewModel.fund.observe(this){
            render(it)
        }

        viewModel.userProfile.observe(this){
            binding.balance.text = it.balance
        }
        viewModel.loadProfile()

        checkIsActivityOpenFromScheme()
    }

    private fun checkIsActivityOpenFromScheme() {
        if (Intent.ACTION_VIEW == intent.action) {
            intent.data?.let{  uri->

                if(uri.scheme == "kkpfg"){

                    Log.d("SCHEME", "scheme ${uri.scheme}")
                    Log.d("SCHEME", "host ${uri.host}")
                    Log.d("SCHEME", "path ${uri.path}")

                    uri.path?.replace("/", "")?.let{  id->
                        loadFundFromID(id)
                    }
                }
            }
        }
    }

    private fun loadFundFromID(id: String) {
        viewModel.loadFundFromID(id)
    }

    private fun render(fundItem: FundItem?){
        binding.title.text =  fundItem?.fundName
        binding.symbol.text = fundItem?.symbol
        binding.categoty.text = fundItem?.category

        pagerAdpater = FundDetailViewPagerAdapter(supportFragmentManager, lifecycle)

        fundItem?.let{
            val fragmentList = listOf(
                PerformanceChartFragment.newInstance(fundItem),
                TopHoldingFragment.newInstance(fundItem)
            )
            pagerAdpater?.setFragment(fragmentList)
        }
        binding.viewPager.adapter = pagerAdpater

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> "Performance"
                1 -> "Top Holding"
                else -> ""
            }
        }.attach()

    }

}
