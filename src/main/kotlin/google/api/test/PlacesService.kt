package google.api.test

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.net.URL
import javax.inject.Singleton

@Singleton
class PlacesService(private val apiKey: String) {

    private val BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial"

    fun getPlaces(origin: String, destination: String): DistanceMatrixResponse {
        /**
         * Makes a call to Google Maps Distance Matrix API
         * and returns a DistanceMatrixResponse object
         */
        // convert origin/destination API-friendly strings
        val origin: String = origin.replace(" ", "+")
        val destination: String = destination.replace(" ", "+")

        // build the url for the API call and get response
        val url: String = "$BASE_URL&origins=$origin&destinations=$destination&key=$apiKey"
        val response: String = URL(url).readText()

        return DistanceMatrixResponse(response)
    }

    class DistanceMatrixResponse(response: String) {
        /**
         * Parses the JSON response string given by the service
         * and stores the Distance Matrix data in a list
         */

        var success: Boolean = false
        var message: String = ""
        var items: MutableList<DistanceMatrixItem> = ArrayList()

        init {
            val moshi: Moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            val jsonAdapter: JsonAdapter<DistanceMatrix> = moshi.adapter(DistanceMatrix::class.java)
            val data: DistanceMatrix? = jsonAdapter.fromJson(response)

            if (data?.status == "OK") {
                success = true
                items = getItems(data)
                items.forEach { message += getItemMessage(it) }
            } else {
                success = false
                message = "Invalid request."
            }
        }

        private fun getItems(data: DistanceMatrix): MutableList<DistanceMatrixItem> {
            /**
             * Return a list of DistanceMatrixItems
             * for each origin/destination pair
             */
            // flatten rows (list of list of elements) into one list of elements
            val elements: MutableList<Element> = ArrayList()
            data.rows.forEach { list -> elements.addAll(list.elements) }

            // TODO(): get rid of the nested for-loop
            // add a DistanceMatrixItem to item list for each origin/destination pair
            var i = 0
            for (origin: String in data.origin_addresses) {
                for (destination: String in data.destination_addresses) {
                    val element: Element = elements[i]
                    items.add(DistanceMatrixItem(origin, destination, element.distance.text, element.duration.text, element.status))
                    i++
                }
            }
            return items
        }

        private fun getItemMessage(item: DistanceMatrixItem): String {
            /**
             * Get the message to be displayed
             * for the origin/destination pair
             */
            if (item.status == "OK") {
                return  """
                    The distance from ${item.origin} to ${item.destination} is ${item.distance}.
                    The drive will take ${item.duration}. 
                    ${System.lineSeparator()} ${System.lineSeparator()}
                    """.trimIndent()
            } else if (item.status == "NOT_FOUND") {
                return  """
                    The distance from ${item.origin} to ${item.destination} could not be found.
                    """.trimIndent()
            }
            return "Undefined error."
        }
    }
}
