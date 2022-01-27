package com.mtanmay.loopiieinternship.ui.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mtanmay.loopiieinternship.BuildConfig
import com.mtanmay.loopiieinternship.api.API
import com.mtanmay.loopiieinternship.api.Photo

private const val TAG = "SearchPagingSource"

class SearchPagingSource(
    private val query: String,
    private val api: API
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {

        val position = params.key ?: 1

        return try {

            val response = api.getSearchPhotos(query = query)
            val photos = response.photos.photo

            LoadResult.Page(
                data = photos,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            if (BuildConfig.DEBUG)
                exception.printStackTrace()

            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? = null
}