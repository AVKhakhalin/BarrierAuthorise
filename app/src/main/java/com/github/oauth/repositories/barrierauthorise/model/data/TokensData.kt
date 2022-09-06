package com.github.oauth.repositories.barrierauthorise.model.data

import com.google.gson.annotations.SerializedName

class TokensData(
    @field:SerializedName("access") val access: String,
    @field:SerializedName("refresh") val refresh: String,
    @field:SerializedName("exp") val exp: Int
)