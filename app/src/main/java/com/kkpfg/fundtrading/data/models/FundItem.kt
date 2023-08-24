package com.kkpfg.fundtrading.data.api.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FundList(
    @SerializedName("funds")
    var funds: MutableList<FundItem>? = mutableListOf()
): Parcelable

@Parcelize
data class FundItem(
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("expense_ratio")
    val expenseRatio: Double? = null,
    @SerializedName("fact_sheet_link")
    val factSheetLink: String? = null,
    @SerializedName("fund_manager")
    val fundManager: String? = null,
    @SerializedName("fund_manager_background")
    val fundManagerBackground: String? = null,
    @SerializedName("fund_name")
    val fundName: String? = null,
    @SerializedName("minimum_investment")
    val minimumInvestment: Int? = null,
    @SerializedName("objective")
    val objective: String? = null,
    @SerializedName("performance")
    val performance: Performance? = null,
    @SerializedName("reviews")
    val reviews: List<Review?>? = null,
    @SerializedName("risk_level")
    val riskLevel: String? = null,
    @SerializedName("symbol")
    val symbol: String? = null,
    @SerializedName("top_holdings")
    val topHoldings: List<TopHolding?>? = null,
    @SerializedName("user_ratings")
    val userRatings: Double? = null
): Parcelable

@Parcelize
data class Performance(
    @SerializedName("month_1")
    val month1: Double? = null,
    @SerializedName("months_3")
    val months3: Double? = null,
    @SerializedName("since_inception")
    val sinceInception: Double? = null,
    @SerializedName("year_1")
    val year1: Double? = null,
    @SerializedName("years_5")
    val years5: Double? = null
): Parcelable

@Parcelize
data class Review(
    @SerializedName("comment")
    val comment: String? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("username")
    val username: String? = null
): Parcelable

@Parcelize
data class TopHolding(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("percentage")
    val percentage: Double? = null
): Parcelable
