package com.github.oauth.repositories.barrierauthorise.model.data

import com.google.gson.annotations.SerializedName

class ReceivedUserTokensData(
    @field:SerializedName("token") val tokens: TokensData?
)