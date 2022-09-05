package com.github.oauth.repositories.barrierauthorise.utils

const val CICERONE_NAME: String = "cicerone"
const val NAME_REMOTE: String = "Remote"
const val LOG_TAG: String = "mylogs"
const val BASE_API_URL: String = "https://dev.barrier.ru/v1"

// Типы сообщений при обмене сообщениями с сервером
enum class ServerResponseStatusCode {
    INFO,
    SUCCESS,
    REDIRECTION,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNDEFINED_ERROR
}

// Пол пользователя
enum class GENDER {
    NOTHING,
    MALE,
    FEMALE
}

// Система-источник регистрации
enum class SYSTEM_SOURCE {
    NOTHING,
    SITE,
    APPLICATION,
    FB
}
