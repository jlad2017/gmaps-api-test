package google.api.test

import io.micronaut.context.annotation.Value
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import javax.inject.Inject

@Controller("/distance-matrix")
class DistanceMatrixController(@Value("\${micronaut.google-api.key}") apiKey: String) {

    @Inject val service = DistanceMatrixService(apiKey)

    @Get("/")
    fun index(): String {
        return "OK"
    }

    @Get("/origin={origin}&destination={destination}")
    fun index(origin: String, destination: String): String {
        val response: DistanceMatrixService.DistanceMatrixResponse = service.getDistanceMatrix(origin, destination)
        return response.message
    }

}

