package google.api.test.places

import google.api.test.places.Places.Place as Place
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URL
import javax.annotation.Nullable
import javax.inject.Singleton

/**
 * A service that makes calls to the Google Maps Places API and
 * parses the response into Place objects
 * (Uses Jackson for the JSON string parsing)
 *
 * @param apiKey my MEGA SECRET Google Maps API key
 */
@Singleton
class PlacesService(private val apiKey: String) {

    private val BASE_URL = "https://maps.googleapis.com/maps/api/place/json"
    private var input: String = ""

    /**
     * Makes a call to Google Maps Places API
     * and returns a PlacesResponse object
     *
     * @param input a place search query
     * @param inputtype the type of input, either textquery or phonenumber
     * @param locationbias an optional parameter to bias the result towards the given location
     */
    fun getResponse(input: String, inputtype: String, @Nullable locationbias: String?): PlacesResponse {
        this.input = input
        val fields = "business_status,formatted_address,geometry,icon," +
                "name,permanently_closed,photos,place_id,plus_code,types"

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
     *
     * @param response the API response, formatted as a JSON string
     */
    class PlacesResponse(response: String) {

        var status: String = ""
        var message: String = ""
        var items: MutableList<Place> = ArrayList()

        init {
            // deserialize the JSON response string into a Places object
            val mapper: ObjectMapper = jacksonObjectMapper()
            val data: Places? = mapper.readValue(response)

            status = data?.status ?: ""
            when (status) {
                "OK"                    -> { items = data!!.candidates as MutableList<Place>; items.forEach { message += getItemMessage(it) } }
                "INVALID_REQUEST"       -> message = "The given request was invalid."
                "MAX_ELEMENTS_EXCEEDED" -> message = "The requests exceed the qer-query limit."
                "OVER_DAILY_LIMIT"      -> message = "There was an issue with the API key."
                "OVER_QUERY_LIMIT"      -> message = "The API has received too many requests from this application."
                "REQUEST_DENIED"        -> message = "This application cannot use the Distance Matrix API."
                "UNKNOWN_ERROR"         -> message = "The request could not be processed due to a server error. Please try again."
                else                    -> message = "Internal server error."
            }
        }

        /**
         * Gets the message to be displayed for the given Place
         *
         * @param item the Place containing the data
         */
        private fun getItemMessage(item: Place): String {
            return  """
                    
                    """.trimIndent()
        }
    }
}
