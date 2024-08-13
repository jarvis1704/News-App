package com.biprangshu.newsapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.biprangshu.newsapp.Util.Constants
import com.biprangshu.newsapp.Util.Constants.USER_SETTINGS
import com.biprangshu.newsapp.domain.LocalUserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImplementation(
    private val context: Context
): LocalUserManager {
    override suspend fun SaveAppEntry() {
        context.dataStore.edit {
            settings->
            settings[PreferencesKeys.APP_ENTRY] = true
        }
    }
    override fun ReadAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map{
            preferences->
            preferences[PreferencesKeys.APP_ENTRY]?: false
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

private object PreferencesKeys{
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)
}