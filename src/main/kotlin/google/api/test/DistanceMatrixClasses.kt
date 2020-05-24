package google.api.test

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DistanceMatrix(val origin_addresses: List<String>,
                          val destination_addresses: List<String>,
                          val rows: List<Row>,
                          val status: String) {

    data class Row(val elements: List<Element>) {

        data class Element(val distance: Distance=Distance(),
                           val duration: Duration=Duration(),
                           val status: String) {

            data class Distance(val text: String="", val value: Int=0)
            data class Duration(val text: String="", val value: Int=0)

        }

    }

}

data class DistanceMatrixItem(val origin: String,
                              val destination: String,
                              val distance: String,
                              val duration: String,
                              val status: String
)

