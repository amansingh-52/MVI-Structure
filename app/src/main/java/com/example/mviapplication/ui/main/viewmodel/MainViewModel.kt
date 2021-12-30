package com.example.mviapplication.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviapplication.data.repository.MainRepository
import com.example.mviapplication.ui.main.intent.MainIntent
import com.example.mviapplication.ui.main.viewstate.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

@ExperimentalCoroutinesApi
class MainViewModel(
    private val repository : MainRepository
) : ViewModel(){

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent(){
        viewModelScope.launch(Dispatchers.Main) {
            userIntent.consumeAsFlow().collect {
                when(it){
                    is MainIntent.FetchUser -> fetchUserData()
                }
            }
        }
    }

    private fun fetchUserData(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Users(repository.getUsers())
            }catch (e : Exception){
                MainState.Error(e.localizedMessage)
            }
        }
    }

}