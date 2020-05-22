package google.api.test

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class DistanceMatrixResponse(val response: String) {

    var success: Boolean = false
    var message: String = ""
//    var json: JsonObject? = null

    var origin: String? = ""
    var destination: String? = ""
    var distance: String? = ""
    var duration: String? = ""


    init {
        val moshi: Moshi = Moshi.Builder()
                                .add(KotlinJsonAdapterFactory())
                                .build()
        val jsonAdapter: JsonAdapter<DistanceMatrixData> = moshi.adapter(DistanceMatrixData::class.java)

        val data: DistanceMatrixData? = jsonAdapter.fromJson(response)
        message = data?.rows?.get(0)?.elements.toString()
    }
}
@JsonClass(generateAdapter = true)
data class Data(val text: String, val value: Int)

@JsonClass(generateAdapter = true)
data class Item(val distance: Data, val duration: Data, val status: String)

@JsonClass(generateAdapter = true)
data class Row(val elements: List<Item>)

@JsonClass(generateAdapter = true)
data class DistanceMatrixData(val origin_addresses: List<String>,
                              val destination_addresses: List<String>,
                              val rows: List<Row>,
                              val status: String)

@JsonClass(generateAdapter = true)
data class DistanceMatrixJson(val origin_addresses: List<String>,
                            val destination_addresses: List<String>,
                            val rows: List<Any>,
                            val status: String)

//@JsonClass(generateAdapter = true)
//class DistanceMatrixDataAdapter {
//    @FromJson
//    fun dataFromJson(json: DistanceMatrixJson): DistanceMatrixData {
//
//
//        return DistanceMatrixData(json.origin_addresses, json.destination_addresses, json.rows, json.status)
//    }
//}
