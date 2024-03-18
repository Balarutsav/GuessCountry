package com.guesscountry.presentation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guesscountry.data.CountryEntity
import com.guesscountry.data.CountryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
 val   countryDao: CountryDao,
 @ApplicationContext private val context: Context
) : ViewModel() {
    companion object{
        val TAG="HomeViewModel"
    }

    init {
        addCountryInDB()
    }

   private fun addCountryInDB() {
        viewModelScope.launch {
            val countryObject = readJsonData()
            countryObject?.let {
              val countryList=  getCountryListFromJson(it)
                if (countryDao.getCountOfCountries()==0){
                    countryDao.insertCountries(countryList)

                }
                    }
        }

    }

    private fun getCountryListFromJson(countryObject: JSONObject): List<CountryEntity> {
        val countryEntityList = mutableListOf<CountryEntity>()

        val keys = countryObject.keys()

        keys.forEach { code ->
            val name = countryObject.getString(code)
            val countryEntity = CountryEntity(code = code, name = name)
            countryEntityList.add(countryEntity)
        }

        return countryEntityList
    }


    suspend fun readJsonData(): JSONObject? {
        var jsonObject: JSONObject? = null

        withContext(Dispatchers.IO) {
            try {
                val inputStream = context.assets.open("countries.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()

                val jsonString = String(buffer, Charsets.UTF_8)
                jsonObject = JSONObject(jsonString)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return jsonObject
    }
}