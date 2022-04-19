package com.learning.kids.baseapp.data.repositories

import com.learning.kids.baseapp.data.models.JobsResopnse
import com.learning.kids.baseapp.data.models.PostsResponse

/**
 * Created by dev.mahmoud_ashraf on 12/11/2019.
 */
interface PostsRepository {
    suspend fun getRemoteJobs(): JobsResopnse

    suspend fun getLocalJobs(): JobsResopnse

    suspend fun getPosts(): List<PostsResponse>
}