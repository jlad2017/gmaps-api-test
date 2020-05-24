package google.api.test.places

import io.micronaut.context.annotation.Value
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import javax.annotation.Nullable
import javax.inject.Inject

@Controller("/places")
class PlacesController(@Value("\${google-api.key}") apiKey: String) {

    @Inject val service = PlacesService(apiKey)

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    fun index(): String {
        return "OK"
    }

    @Get("/findplacefromtext/input={input}&inputtype={inputtype}{?locationbias}")
    @Produces(MediaType.TEXT_PLAIN)
    fun index(input: String, inputtype: String, @Nullable locationbias: String?): String {
        val response: PlacesService.PlacesResponse = service.getResponse(input, inputtype, locationbias)
        return response.message
    }
}