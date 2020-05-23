package google.api.test

import com.squareup.moshi.JsonClass

// TODO(): figure out a way to condense this so we don't use this many auxiliary classes
@JsonClass(generateAdapter = true)
data class Distance(val text: String="", val value: Int=0)

@JsonClass(generateAdapter = true)
data class Duration(val text: String="", val value: Int=0)

@JsonClass(generateAdapter = true)
// we need to provide default value for distance/duration in the case that status is NOT_FOUND
data class Element(val distance: Distance=Distance(),
                   val duration: Duration=Duration(),
                   val status: String)

@JsonClass(generateAdapter = true)
data class Row(val elements: List<Element>)

@JsonClass(generateAdapter = true)
data class DistanceMatrix(val origin_addresses: List<String>,
                          val destination_addresses: List<String>,
                          val rows: List<Row>,
                          val status: String)

data class DistanceMatrixItem(val origin: String,
                              val destination: String,
                              val distance: String,
                              val duration: String,
                              val status: String)

