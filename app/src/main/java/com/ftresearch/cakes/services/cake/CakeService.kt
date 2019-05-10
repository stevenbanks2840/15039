package com.ftresearch.cakes.services.cake

import com.ftresearch.cakes.rest.cake.Cake
import com.ftresearch.cakes.rest.cake.CakeInterface
import javax.inject.Inject

class CakeService @Inject constructor(private val cakeInterface: CakeInterface) {
    suspend fun getCakes(onSuccess: (List<Cake>) -> Unit,
                         onError: (String) -> Unit) { // TODO: Provide richer error information
        val request = cakeInterface.getCakesAsync()

        try {
            val response = request.await()
            val cakes = response.body()

            if (cakes != null) {
                val uniqueCakes = cakes.distinctBy { it.title }.sortedBy { it.title }

                onSuccess(uniqueCakes)
            } else {
                onError("Failed to retrieve cakes") // TODO: Inject resource provider for strings
            }
        } catch (exception: Exception) { // TODO: Catch more specific exceptions
            onError("Failed to retrieve cakes")
        }
    }
}
