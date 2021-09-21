package com.ftresearch.cakes.ui.cakes

sealed class CakesViewState {

    object Loading : CakesViewState()

    object PrePopulated : CakesViewState()

    data class Error(val exception: Exception) : CakesViewState()
}
