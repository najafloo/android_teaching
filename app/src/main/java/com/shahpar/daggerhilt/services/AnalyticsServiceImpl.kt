package com.shahpar.daggerhilt.services

import android.util.Log
import com.shahpar.daggerhilt.Const.TAG
import javax.inject.Inject

class AnalyticsServiceImpl @Inject constructor() : AnalyticsService {

    override fun analyticsMethods1() {
        Log.d(TAG, "analyticsMethods1: ")
    }

    override fun analyticsMethods2() {
        Log.d(TAG, "analyticsMethods2: ")
    }

    override fun analyticsMethods3() {
        Log.d(TAG, "analyticsMethods3: ")
    }
}