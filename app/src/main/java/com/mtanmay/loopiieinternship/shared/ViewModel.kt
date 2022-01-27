package com.mtanmay.loopiieinternship.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mtanmay.loopiieinternship.api.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var loadAgain = true

    fun getRecentPhotos(): LiveData<PagingData<Photo>> {
        loadAgain = false
        return repository.getRecentPhotos().cachedIn(viewModelScope)
    }

    fun getSearchPhotos(query: String): LiveData<PagingData<Photo>> =
        repository.getSearchPhotos(query).cachedIn(viewModelScope)

}