package com.github.oauth.repositories.barrierauthorise.model.data

import com.github.oauth.repositories.barrierauthorise.utils.GENDER
import com.github.oauth.repositories.barrierauthorise.utils.SYSTEM_SOURCE

class InputtedUserData {
    /** Исходные данные */ //region
    //region Поля ОБЯЗАТЕЛЬНО должны быть заполнены
    var firstName: String = ""  // не более 50 символов
    var email: String = ""      // не более 254 символов
    var isAgreed: Boolean = false
    var password: String = ""   // строчные и прописные латинские буквы,
                                // цифры, спецсимволы. Минимум 8 символов
    //endregion
    //region Полня НЕ ОБЯЗАТЕЛЬНЫ к заполнению
    var clientId: Int = -1
    var roleId: Int = -1
    var surname: String = ""    // не более 50 символов
    var patronymic: String = "" // не более 50 символов
    var birthDate: String = ""  // ISO 8601
    var gender: GENDER = GENDER.NOTHING
    var welcome: String = ""    // не более 20 символов
    var mobile: String = ""     // не более 15 цифр
    var uuid: String = ""       // не более 30 символов
    var isOffer: Boolean = false
    var checkIn: SYSTEM_SOURCE = SYSTEM_SOURCE.NOTHING
    //endregion
    //endregion
}