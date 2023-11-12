package edu.jc.csu411.project1


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.Flow


class ColorViewModel(application: Application) : AndroidViewModel(application) {
    private val myAppRepository: MyAppRepository = MyAppRepository.getRepository(application)

    val redIntensityLiveData: Flow<Float> = myAppRepository.redIntensity
    val greenIntensityLiveData: Flow<Float> = myAppRepository.greenIntensity
    val blueIntensityLiveData: Flow<Float> = myAppRepository.blueIntensity

    suspend fun saveIntensities(red: Double, green: Double, blue: Double) {
        myAppRepository.saveIntensities(red, green, blue)
    }
}
