package com.learning.kids.baseapp.data.daos

import com.learning.kids.baseapp.data.models.JobsResopnse
import com.learning.kids.baseapp.data.models.PostsResponse
import retrofit2.http.GET

/**
 * Created by dev.mahmoud_ashraf on 12/11/2019.
 */
interface RemoteServiceDao {

    @GET("jobs")
    suspend fun getAllJobs(): JobsResopnse

    @GET("/posts" )
    suspend fun posts(): List<PostsResponse>

}