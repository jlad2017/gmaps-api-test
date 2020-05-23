package google.api.test

import io.micronaut.context.annotation.Value
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus
import javax.inject.Inject

@Controller("/places")
class PlacesController(@Value("\${micronaut.google-api.key}") apiKey: String) {

    @Inject val service = PlacesService(apiKey)

    @Get("/")
    fun index(): String {
        return "OK"
    }

    @Get("/input={input}&inputtype={inputtype}")
    fun index(input: String, inputtype: String): String {
        return ""
    }

}