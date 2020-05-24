package google.api.test.places

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class Places(val candidates: List<Place>, val status: String) {

    class Place(val business_status: String,
                val formatted_address: String,
                val geometry: Geometry,
                val icon: String,
                val name: String,
                val photos: List<Photo>,
                val place_id: String,
                val plus_code: PlusCode,
                val types: List<String>) {

        @JsonIgnoreProperties(value = ["viewport"])
        class Geometry(val location: Location) {
            data class Location(val lat: Float, val lng: Float)
        }

        @JsonIgnoreProperties(value = ["height", "width", "photo_reference"])
        data class Photo(@JsonAlias("html_attributions") val htmlAttributes: List<String>)

        data class PlusCode(@JsonAlias("compound_code") val compoundCode: String,
                            @JsonAlias("global_code") val globalCode: String
        )

    }

}

