package com.ftresearch.cakes.ui.cakes

import com.ftresearch.cakes.rest.cake.Cake

sealed class CakesViewState {

    object Loading : CakesViewState()

    data class Success(val cakes: List<Cake>) : CakesViewState()

    data class Error(val exception: Exception) : CakesViewState()
}
