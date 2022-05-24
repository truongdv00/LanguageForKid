package com.learning.kids.baseapp.data.models

data class BigListResponse(
    var result: BigLisResult,
    var code: Int,
    var message: String
) {
}