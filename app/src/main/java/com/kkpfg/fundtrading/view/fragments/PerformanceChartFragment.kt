package com.kkpfg.fundtrading.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.kkpfg.fundtrading.data.api.models.FundItem
import com.kkpfg.fundtrading.databinding.FragmentPerformanceChartBinding


class PerformanceChartFragment : Fragment() {

    private var fund: FundItem? = null

    private lateinit var binding:FragmentPerformanceChartBinding

    companion object {
        fun newInstance(fund: FundItem) =
            PerformanceChartFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("fund", fund)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fund = it.getParcelable("fund", FundItem::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerformanceChartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setChartData(fund)
    }

    private fun setChartData(fundItem: FundItem?) {

        val performanceData = mapOf(
            "1 month" to (fundItem?.performance?.month1 ?: 0),
            "3 months" to (fundItem?.performance?.months3 ?: 0),
            "1 year" to (fundItem?.performance?.year1 ?: 0),
            "5 years" to (fundItem?.performance?.years5 ?: 0),
            "since inception" to (fundItem?.performance?.sinceInception ?: 0),
        )

        val aaChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .title("Fund Performance")
            .categories(performanceData.keys.toTypedArray())
            .yAxisTitle("")
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Performance")
                        .data(performanceData.values.toTypedArray()),
                    AASeriesElement()
                        .name("Performance")
                        .type(AAChartType.Line) // Set the chart type to Line
                        .data(performanceData.values.toTypedArray())
                )
            )


        WebView.setWebContentsDebuggingEnabled(false)

        binding.aaChartView.aa_drawChartWithChartModel(aaChartModel)
    }

}