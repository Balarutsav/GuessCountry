package com.guesscountry.presentation.guessTheFlagScreen

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guesscountry.data.CountryDao
import com.guesscountry.data.CountryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuessTheFlagViewModel @Inject constructor(val countryDao: CountryDao) : ViewModel() {
    var selectedCountryName by mutableStateOf("")
    val countryList = mutableStateOf<List<CountryEntity>>(emptyList())

    private val selectedCountryIds = mutableListOf<Int>()
    private var randomCountry by mutableStateOf<CountryEntity?>(null)

    var remainingTime by   mutableStateOf(10)
    private var timer: CountDownTimer? = null
    private val _timerCompleted = MutableStateFlow(false) // Initialize with false
    val timerCompleted: StateFlow<Boolean> = _timerCompleted

    fun getCountryList() {
        viewModelScope.launch(Dispatchers.IO) {
            // Fetch the country list from the repository
            val countries =async {  countryDao.getRandomThreeCountry(selectedCountryIds) }.await()
            Log.e("GuessTheCountry", "getCountryList:${countries.size} ")
            randomCountry = countries.random()
            selectedCountryIds.addAll(countries.map { it.id })
            countryList.value = countries
        }
    }

    fun getSavedRandomCountry(): CountryEntity? {
        return randomCountry
    }

    fun startTimer() {

        timer = object : CountDownTimer(10 * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                _timerCompleted.value = true
                remainingTime = 0
            }
        }.start()

    }
    fun resetTimerCompleted() {
        _timerCompleted.value = false // Reset to false when needed
    }


    fun reset() {
        getCountryList()
        selectedCountryName = ""
    }
}