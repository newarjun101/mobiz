package com.kbz.mobiz.core.services.helpers
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences
import javax.inject.Inject
class DataStoreHelper @Inject
constructor(@ApplicationContext private val context: Context) {

    object PreferencesKey {
        val name = booleanPreferencesKey("WELCOME")
    }

    private val Context.dataStore by preferencesDataStore(name = "setting")


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