package edu.jc.csu411.project1

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyAppRepository private constructor(private val dataStore: DataStore<Preferences>) {

    private val redIntensityKey = floatPreferencesKey("redIntensity")
    private val greenIntensityKey = floatPreferencesKey("greenIntensity")
    private val blueIntensityKey = floatPreferencesKey("blueIntensity")

    val redIntensity: Flow<Float> = this.dataStore.data.map { prefs ->
        prefs[redIntensityKey] ?: INITIAL_INTENSITY
    }

    val greenIntensity: Flow<Float> = this.dataStore.data.map { prefs ->
        prefs[greenIntensityKey] ?: INITIAL_INTENSITY
    }

    val blueIntensity: Flow<Float> = this.dataStore.data.map { prefs ->
        prefs[blueIntensityKey] ?: INITIAL_INTENSITY
    }

    suspend fun saveIntensities(red: Double, green: Double, blue: Double) {
        saveFloatValue(redIntensityKey, red)
        saveFloatValue(greenIntensityKey, green)
        saveFloatValue(blueIntensityKey, blue)
    }

    private suspend fun saveFloatValue(key: Preferences.Key<Float>, value: Double) {
        this.dataStore.edit { preferences ->
            preferences[key] = value.toFloat()
        }
    }

    companion object {
        private const val PREFERENCES_DATA_FILE_NAME = "MyDataStoreFile"
        private const val INITIAL_INTENSITY = 0.0f
        private var singleInstanceOfMyAppRepository: MyAppRepository? = null

        fun getRepository(application: Application): MyAppRepository {
            return singleInstanceOfMyAppRepository
                ?: synchronized(this) {
                    singleInstanceOfMyAppRepository
                        ?: buildRepository(application).also {
                            singleInstanceOfMyAppRepository = it
                        }
                }
        }

        private fun buildRepository(application: Application): MyAppRepository {
            val dataStore = PreferenceDataStoreFactory.create {
                application.preferencesDataStoreFile(PREFERENCES_DATA_FILE_NAME)
            }
            return MyAppRepository(dataStore)
        }
        fun getInstance(context: Context): MyAppRepository {
            return singleInstanceOfMyAppRepository ?: MyAppRepository(
                PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile(PREFERENCES_DATA_FILE_NAME)
                }
            ).also {
                singleInstanceOfMyAppRepository = it
            }
        }
    }
}
