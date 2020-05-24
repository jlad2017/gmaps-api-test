package google.api.test

import io.micronaut.context.annotation.Value
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import javax.inject.Inject

@Controller("/distance-matrix")
class DistanceMatrixController(@Value("\${google-api.key}") apiKey: String) {

    @Inject val service = DistanceMatrixService(apiKey)

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    fun index(): String {
        return "OK"
    }

    @Get("/origin={origin}&destination={destination}")
    @Produces(MediaType.TEXT_PLAIN)
    fun index(origin: String, destination: String): String {
        val response: DistanceMatrixService.DistanceMatrixResponse =
                service.getResponse(origin, destination)
        return response.message
    }

}

