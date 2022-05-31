package com.learning.kids.baseapp.data.models

data class UserResponse(
    var result: UserResult,
    var code: Int,
    var message: String
) {
}