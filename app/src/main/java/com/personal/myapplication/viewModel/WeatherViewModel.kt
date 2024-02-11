package com.personal.myapplication.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.personal.myapplication.R
import com.personal.myapplication.model.WeatherRepository
import com.personal.myapplication.model.WeatherResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class WeatherViewModel(application: Application):AndroidViewModel(application) {

    private val repository = WeatherRepository()
    private val key = application.getString(R.string.apiKey)
    val weatherResult = MutableLiveData<WeatherResult>()
    val isSuccessful = MutableLiveData<Int>()

    fun weatherDetails(cityName: String){

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("viewModelActivity", "Data Started generating")
            try{
                val details = repository.getWeatherDetails(key, cityName)
                weatherResult.postValue(details)
                Log.d("ViewModelActivity", "Data generation successful")
                isSuccessful.postValue(1)
            }
            catch(err: HttpException){
                Log.d("ViewModelActivity", "User inputed a wrong city name")
                isSuccessful.postValue(2)
            }
            catch (err: Exception){
                Log.d("ViewModelActivity", "Bad Internet Connection")
                isSuccessful.postValue(0)
            }
        }
    }
}