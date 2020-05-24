package google.api.test.distancematrix

import com.squareup.moshi.JsonClass

/**
 * DTOs that enable Moshi to deserialize the JSON response string
 * into usable DistanceMatrix objects-- an example JSON string is
 * {
 *      destination_addresses: [ Gardena, CA, USA" ],
 *      origin_addresses: [ "Seal Beach, CA, USA" ],
 *      rows: [{ elements: [{ distance: { text: "19.8 mi", value: 31788 },
 *                            duration: { text: "30 mins", value: 1829 },
 *                            status: "OK" }]}],
 *      status: "OK"
 * }
 */
@JsonClass(generateAdapter = true)
data class DistanceMatrix(val origin_addresses: List<String>,
                          val destination_addresses: List<String>,
                          val rows: List<Row>,
                          val status: String) {

    data class Row(val elements: List<Element>) {

        data class Element(val distance: Distance = Distance(),
                           val duration: Duration = Duration(),
                           val status: String) {

            data class Distance(val text: String="", val value: Int=0)
            data class Duration(val text: String="", val value: Int=0)

        }
    }
}

/** Contains distance information for one item in the Distance Matrix */
data class DistanceMatrixItem(val origin: String,
                              val destination: String,
                              val distance: String,
                              val duration: String,
                              val status: String
)

