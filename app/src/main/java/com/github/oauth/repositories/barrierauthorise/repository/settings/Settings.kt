package com.github.oauth.repositories.barrierauthorise.repository.settings

class Settings {
    /** Исходные данные */ //region
    var userId: Int = -1
    var clientId: Int = -1
    var roleId: Int = -1
    var checkIn: String = ""
    var surname: String = ""
    var firstName: String = ""
    var patronymic: String = ""
    var birthDate: String = ""
    var gender: String = ""
    var welcome: String = ""
    var email: String = ""
    var mobile: String = ""
    var uuid: String = ""
    var isOffer: Boolean? = null
    var isEnabled: Boolean? = null
    var isAgreed: Boolean? = null
    var lastActivity: String = ""
    var createdAt: String = ""
    var updateAt: String = ""
    var password: String = ""
    //endregion
}