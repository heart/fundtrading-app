package com.kkpfg.fundtrading.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DepositResponse (
    val message: String?,
    val amount: Int?
): Parcelable