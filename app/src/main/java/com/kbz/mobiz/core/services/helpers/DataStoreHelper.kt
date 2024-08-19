package com.kbz.mobiz.core.services.helpers
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "setting")
class DataStoreHelper @Inject
constructor(@ApplicationContext private val context: Context) {

    companion object PreferencesKey {
        val name = booleanPreferencesKey("WELCOME")
    }




    suspend fun saveToLocal(name: Boolean) {
        context.dataStore.edit { preference->
            preference[PreferencesKey.name] = name
        }
    }


    val readFromLocal : Flow<Boolean> = context.dataStore.data
        .catch {
            if(this is Exception){
                emit(emptyPreferences())
            }
        }.map { preference->
            val isWelcome = preference[PreferencesKey.name] ?: false
            isWelcome
        }

}