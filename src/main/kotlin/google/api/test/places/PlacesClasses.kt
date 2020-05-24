package google.api.test.places

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * DTOs that enable Jackson to deserialize the JSON response string
 * into usable Place objects-- an example JSON string is
 * {
 *      candidates: [{ business_status: "OPERATIONAL",
 *                     formatted_address: "4348 Katella Ave, Los Alamitos, CA 90720, United States",
 *                     geometry: { location: { lat: 33.8025817, lng: -118.0568463 },
 *                     viewport: { northeast: { lat: 33.80405757989273, lng: -118.0555937201073 },
 *                                 southwest: { lat: 33.80135792010728, lng: -118.0582933798927 } } },
 *                     icon: "https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png",
 *                     name: "Xtreme Sushi Restaurant",
 *                     photos: [{ height: 3024,
 *                                html_attributions: [ "<a href="https://maps.google.com/maps/contrib/104363920225425131838">chris.villaflor</a>" ],
 *                                photo_reference: "(omitted)",
 *                                width: 4032 }],
 *                     place_id: "ChIJ4weBtvMu3YARCHZ6BmwyGtw",
 *                     plus_code: { compound_code: "RW3V+27 Los Alamitos, California", global_code: "8553RW3V+27" },
 *                     types: [ "restaurant", "food", "point_of_interest", "establishment"] }],
 *      status: "OK"
 * }
 */
data class Places(val candidates: List<Place>, val status: String) {

    data class Place(val business_status: String,
                val formatted_address: String,
                val geometry: Geometry,
                val icon: String,
                val name: String,
                val photos: List<Photo>,
                val place_id: String,
                val plus_code: PlusCode,
                val types: List<String>) {

        @JsonIgnoreProperties(value = ["viewport"])
        data class Geometry(val location: Location) {
            data class Location(val lat: Float, val lng: Float)
        }

        @JsonIgnoreProperties(value = ["height", "width", "photo_reference"])
        data class Photo(@JsonAlias("html_attributions") val htmlAttributes: List<String>)

        data class PlusCode(@JsonAlias("compound_code") val compoundCode: String,
                            @JsonAlias("global_code") val globalCode: String
        )

    }
}

