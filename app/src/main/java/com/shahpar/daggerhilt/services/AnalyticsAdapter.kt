package com.shahpar.daggerhilt.services

import com.shahpar.daggerhilt.services.AnalyticsService
import javax.inject.Inject

class AnalyticsAdapter @Inject constructor(private val service: AnalyticsService) {

    fun callService1() {
        service.analyticsMethods1()
    }

    fun callService2() {
        service.analyticsMethods2()
    }

    fun callService3() {
        service.analyticsMethods3()
    }

}