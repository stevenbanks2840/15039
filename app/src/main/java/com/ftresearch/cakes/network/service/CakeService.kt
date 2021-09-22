package com.ftresearch.cakes.network.service

import com.ftresearch.cakes.network.model.CakeDTO
import retrofit2.http.GET

interface CakeService {

    @GET("/stevenbanks2840/de609c37581216267170e6bb81e753d3/raw/99471d5c73ff631f0aa98f0b9d4b2ede91881d36/cakes")
    suspend fun getCakes(): List<CakeDTO>
}
