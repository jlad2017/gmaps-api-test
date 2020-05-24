package google.api.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URL
import javax.annotation.Nullable
import javax.inject.Singleton

/**
 * Makes calls to the Google Maps Places API and
 * parses the response into Place objects
 * (Uses Jackson for the JSON string parsing)
 */
@Singleton
class PlacesService(private val apiKey: String) {

    private val BASE_URL = "https://maps.googleapis.com/maps/api/place/json"
    private var input: String = ""

    /**
     * Makes a call to Google Maps Places API
     * and returns a PlacesResponse object
     */
    fun getResponse(input: String, inputtype: String, @Nullable locationbias: String?): PlacesResponse {
        this.input = input
        val fields = "business_status,formatted_address,geometry,icon,name,permanently_closed,photos,place_id,plus_code,types"

        // convert origin/destination to API-friendly strings
        val input: String = input.replace(" ", "+")

        // build the url for the API call and get response
        var url = "$BASE_URL?fields=$fields&input=$input&inputtype=$inputtype&key=$apiKey"
        if (locationbias != null) {
            url += "&locationbias=$locationbias"
        }
        val response: String = URL(url).readText()

        return PlacesResponse(response)
    }

    /**
     * Parses the JSON response string given by the service
     * and stores the Places data in a list
     */
    class PlacesResponse(response: String) {

        var success: Boolean = false
        var message: String = ""
        var items: MutableList<Places.Place> = ArrayList()

        init {
            // deserialize the JSON response string into a Places object
            val mapper: ObjectMapper = jacksonObjectMapper()
            val data: Places? = mapper.readValue(response)

            if (data?.status == "OK") {
                success = true
                items = data.candidates as MutableList<Places.Place>
                items.forEach { message += getItemMessage(it) }
            } else {
                success = false
                message = "Invalid request."
            }
        }

        private fun getItemMessage(item: Places.Place): String {
            /**
             * Get the message to be displayed
             * for the origin/destination pair
             */
            return  """
                    
                    """.trimIndent()
        }
    }
}
