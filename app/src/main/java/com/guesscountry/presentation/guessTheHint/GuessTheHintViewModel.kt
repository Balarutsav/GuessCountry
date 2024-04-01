package com.guesscountry.presentation.guessTheHint

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuessTheHintViewModel @Inject constructor(val countryDao: CountryDao) : ViewModel() {

    private var randomCountry by mutableStateOf<CountryEntity?>(null)
    private val selectedCountryIds = mutableListOf<Int>()

    val guessedCountryName = lazy {
        MutableList(getSavedRandomCountry()?.name?.toList()?.size ?: 0) { "" }
    }
    init {
        getRandomCountry()
    }

    private fun getRandomCountry() {
        viewModelScope.launch(Dispatchers.IO) {
            val excludesIds = selectedCountryIds.toList()
            val country = async { countryDao.getRandomCountry(excludesIds) }.await()
            country.let {
                delay(500)
                randomCountry = it
                selectedCountryIds.add(it.id)
            }
        }

    }

    fun getSavedRandomCountry(): CountryEntity? {
        return randomCountry
    }

}

