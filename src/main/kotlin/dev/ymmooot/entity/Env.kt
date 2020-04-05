package dev.ymmooot.entity

class Env {
    val areaCode: String = this.mustGetEnv("AREA_CODE")
    val endpointURL: String = this.mustGetEnv("ENDPOINT_URL")
    val iconURL: String? = System.getenv("ICON_URL")

    private fun mustGetEnv(key: String): String =
            System.getenv(key) ?: throw Exception("environmental variables not found: $key")
}