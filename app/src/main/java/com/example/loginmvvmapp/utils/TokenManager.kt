package com.example.loginmvvmapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.Provides
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class TokenManager @Inject constructor (@ApplicationContext val context: Context) {



    companion object{
        val TOKEN = stringPreferencesKey("TOKEN")
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("TokenDataStore")
    }



    suspend fun saveToken(token: String) {

        context.dataStore.edit {
            it[TOKEN] = token
        }

    }


    suspend fun getToken() = context.dataStore.data.catch { exception ->

        // dataStore.data throws an IOException when an error is encountered when reading data
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }

    }.map {
        it[TOKEN]?: null


        // or use


//        getToken()= context.dataStore.data.map{
//            it[TOKEN]?: ""
//        }
    }



    }


