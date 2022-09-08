package com.github.oauth.repositories.barrierauthorise.model.data

import com.github.oauth.repositories.barrierauthorise.utils.GENDER
import com.github.oauth.repositories.barrierauthorise.utils.SYSTEM_SOURCE

class InputtedUserData(
    /** Исходные данные */ //region
    //region Поля ОБЯЗАТЕЛЬНО должны быть заполнены
    var first_name: String? = null,  // не более 50 символов
    var email: String? = null,       // не более 254 символов
    var is_agreed: Boolean? = null,
    var password: String? = null,    // строчные и прописные латинские буквы,
                                     // цифры, спецсимволы. Минимум 8 символов
    //endregion
    //region Поля НЕ ОБЯЗАТЕЛЬНЫ к заполнению
    var clientId: Int? = null,
    var roleId: Int? = null,
    var surname: String? = null,    // не более 50 символов
    var patronymic: String? = null, // не более 50 символов
    var birthDate: String? = null,  // ISO 8601
    var gender: GENDER? = null,
    var welcome: String? = null,    // не более 20 символов
    var mobile: String? = null,     // не более 15 цифр
    var uuid: String? = null,       // не более 30 символов
    var isOffer: Boolean? = null,
    var checkIn: SYSTEM_SOURCE? = null
    //endregion
    //endregion
)