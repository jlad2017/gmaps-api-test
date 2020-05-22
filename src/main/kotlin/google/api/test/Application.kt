package google.api.test

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("google.api.test")
                .mainClass(Application.javaClass)
                .start()
    }
}