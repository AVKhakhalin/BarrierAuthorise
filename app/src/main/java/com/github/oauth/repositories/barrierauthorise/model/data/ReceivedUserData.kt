package com.github.oauth.repositories.barrierauthorise.model.data

import com.google.gson.annotations.SerializedName

class ReceivedUserData(
    @field:SerializedName("user") val user: UserData?
)