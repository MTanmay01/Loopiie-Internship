package com.mtanmay.loopiieinternship.shared

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mtanmay.loopiieinternship.api.API
import com.mtanmay.loopiieinternship.ui.feed.FeedPagingSource
import com.mtanmay.loopiieinternship.ui.search.SearchPagingSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: API
) {

    fun getRecentPhotos() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                FeedPagingSource(api)
            }
        ).liveData

    fun getSearchPhotos(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchPagingSource(query, api)
            }
        ).liveData

}