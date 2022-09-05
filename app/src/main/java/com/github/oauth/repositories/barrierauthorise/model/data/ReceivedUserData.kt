package com.github.oauth.repositories.barrierauthorise.model.data

import com.google.gson.annotations.SerializedName

class ReceivedUserData(
    @field:SerializedName("user_id") val userId: Int,
    @field:SerializedName("client_id") val clientId: Int,
    @field:SerializedName("role_id") val roleId: Int,
    @field:SerializedName("check_in") val checkIn: String?,
    @field:SerializedName("surname") val surname: String?,
    @field:SerializedName("first_name") val firstName: String,
    @field:SerializedName("patronymic") val patronymic: String?,
    @field:SerializedName("birth_date") val birthDate: String?,
    @field:SerializedName("gender") val gender: String?,
    @field:SerializedName("welcome") val welcome: String?,
    @field:SerializedName("email") val email: String,
    @field:SerializedName("mobile") val mobile: String?,
    @field:SerializedName("uuid") val uuid: String?,
    @field:SerializedName("is_offer") val isOffer: Boolean?,
    @field:SerializedName("is_enabled") val isEnabled: Boolean?,
    @field:SerializedName("is_agreed") val isAgreed: Boolean,
    @field:SerializedName("last_activity") val lastActivity: String?,
    @field:SerializedName("created_at") val createdAt: String?,
    @field:SerializedName("update_at") val updateAt: String?
)