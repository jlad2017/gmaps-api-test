package google.api.test

import java.net.URL
import javax.inject.Singleton

@Singleton
class DistanceMatrix(private val apiKey: String) {

    private val BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial"

    fun getDistanceMatrix(origin: String, destination: String): DistanceMatrixResponse {
        /**
         * Makes a call to Google Maps Distance Matrix API
         * and returns a DistanceMatrixResponse object
         */
        // convert origin/destination API-friendly strings
        val origin: String = origin.replace(" ", "+")
        val destination: String = destination.replace(" ", "+")

        // build the url for the API call and get response
        val url: String = "$BASE_URL&origins=$origin|los+angeles&destinations=$destination|los+angeles&key=$apiKey"
        val response: String = URL(url).readText()

        return DistanceMatrixResponse(response)
    }

}


