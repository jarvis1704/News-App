package com.biprangshu.newsapp

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.newsapp.domain.usecases.AppEntryUseCases
import com.biprangshu.newsapp.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
): ViewModel() {
    val splashCondition =  mutableStateOf(true)


    var startDestination by mutableStateOf(Route.AppStartNavigation.Route)
        private set

    init {
        appEntryUseCases.readAppEntry().onEach {
            shouldStartFromHomeScreen->
            if(shouldStartFromHomeScreen){
                startDestination=Route.NewsNavigation.Route
            }else{
                startDestination=Route.AppStartNavigation.Route
            }
            delay(300)
            splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}