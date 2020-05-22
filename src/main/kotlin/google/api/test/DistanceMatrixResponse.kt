package google.api.test

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser

class DistanceMatrixResponse(val response: String) {

    var success: Boolean = false
    var message: String = ""
    var json: JsonObject? = null

    var origin: String? = ""
    var destination: String? = ""
    var distance: String? = ""
    var duration: String? = ""

    private val parser: Parser = Parser()

    init {
        // parse the API response into a JsonObject
        val stringBuilder: StringBuilder = StringBuilder(response)
        json = parser.parse(stringBuilder) as JsonObject

        // check if API call was successful
        val elements: JsonObject? = json!!.array<JsonObject>("rows")?.get(0)?.
        array<JsonObject>("elements")?.get(0)

        // if successful, extract data from JsonObject
        if (elements?.string("status") == "OK") {
            success = true

            origin = json!!.array<String>("origin_addresses")?.get(0)
            destination = json!!.array<String>("destination_addresses")?.get(0)

            val distanceObject: JsonObject = elements?.get("distance") as JsonObject
            distance = distanceObject["text"] as String?

            val durationObject: JsonObject = elements?.get("duration") as JsonObject
            duration = durationObject["text"] as String?

            message =  """
                       The distance from ${origin} to ${destination} is ${distance}.
                       The drive will take ${duration}.
                       """.trimIndent()
        } else {
            message = "Invalid input."
        }
    }
}

