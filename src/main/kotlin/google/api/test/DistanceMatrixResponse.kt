package google.api.test

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class DistanceMatrixResponse(response: String) {

    var success: Boolean = false
    var message: String = ""
    var items: MutableList<DistanceMatrixItem> = ArrayList()

    init {
        val moshi: Moshi = Moshi.Builder()
                                .add(KotlinJsonAdapterFactory())
                                .build()
        val jsonAdapter: JsonAdapter<DistanceMatrixData> = moshi.adapter(DistanceMatrixData::class.java)
        val data: DistanceMatrixData? = jsonAdapter.fromJson(response)

        if (data?.status == "OK") {
            success = true
            items = getItems(data)
            items.forEach { message += getItemMessage(it) }
        } else {
            success = false
            message = "Invalid request."
        }
    }

    private fun getItems(data: DistanceMatrixData): MutableList<DistanceMatrixItem> {
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
