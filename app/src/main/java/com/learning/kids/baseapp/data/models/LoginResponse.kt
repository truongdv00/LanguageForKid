package com.learning.kids.baseapp.data.models

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    var result: ResultLogin,
    var code: Int,
    var message: String
)