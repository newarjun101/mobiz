package com.kbz.mobiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kbz.mobiz.core.services.helpers.DataStoreHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject  constructor(private val datastoreSetting: DataStoreHelper) : ViewModel() {


    fun saveToLocal(name: Boolean) = viewModelScope.launch {
        datastoreSetting.saveToLocal(name)
    }

    val readFromLocal = datastoreSetting.readFromLocal

}