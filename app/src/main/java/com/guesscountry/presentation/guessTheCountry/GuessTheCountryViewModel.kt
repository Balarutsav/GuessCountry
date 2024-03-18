package com.guesscountry.presentation.guessTheCountry

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuessTheCountryViewModel @Inject constructor(val countryDao: CountryDao) : ViewModel() {
    var selectedCountryName by mutableStateOf("")
    val countryList = mutableStateOf<List<CountryEntity>>(emptyList())

    private val selectedCountryIds = mutableListOf<Int>()
    private var randomCountry by mutableStateOf<CountryEntity?>(null)

    init {

        getRandomCountry()


    }

    fun getCountryList() {
        viewModelScope.launch(Dispatchers.IO) {
            // Fetch the country list from the repository
            val countries = countryDao.getAllCountries()
            Log.e("GuessTheCountry", "getCountryList:${countries.size} ")
            countryList.value = countries
        }
    }

    fun getSavedRandomCountry(): CountryEntity? {
        return randomCountry
    }

    fun getRandomCountry() {
        viewModelScope.launch(Dispatchers.IO) {
            val excludedIds = selectedCountryIds.toList()
            var country = countryDao.getRandomCountry(excludedIds)
            country.let {
                randomCountry = it
                selectedCountryIds.add(it.id)
                Log.e("RandomCountryName", "getRandomCountry: ${country.name}")
            }
        }
    }

    fun reset() {
        getRandomCountry()
        selectedCountryName = ""
    }
}