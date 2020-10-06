package com.byjus.headlines.headlines.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.byjus.headlines.core.application.ByjusApplications
import com.byjus.headlines.core.constants.Constant
import com.byjus.headlines.headlines.dao.HeadLineDatabase
import com.byjus.headlines.headlines.models.request.HeadLineRequest
import com.byjus.headlines.headlines.models.response.HeadlineResponse
import com.byjus.headlines.headlines.networkUtils.NetworkApis
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Inject


class MainViewModel @Inject constructor(var pApplication: Application) :
    AndroidViewModel(pApplication) {

    private var mHeadlineResponse = MutableLiveData<HeadlineResponse>()

    fun getHeadLineList() = mHeadlineResponse

    @Inject
    lateinit var mRetrofit: Retrofit

    fun downloadHeadLines(pHeadLineRequest: HeadLineRequest) {
        when ((pApplication as ByjusApplications).checkConnection()) {
            false -> {
                getHeadLineList().postValue(HeadlineResponse())
                return
            }
        }
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val type: Type = object : TypeToken<Map<String?, String?>?>() {}.type
                val requestMap: Map<String, String> =
                    Gson().fromJson(Gson().toJson(pHeadLineRequest), type)
                val response =
                    mRetrofit.create(NetworkApis::class.java).getHeadLine(requestMap)
                when (response.status) {
                    Constant.mSTATUS_OK -> {
                        getHeadLineList().postValue(response)
                        cacheToDB(response)
                    }
                    else -> {
                        getHeadLineList().postValue(HeadlineResponse())
                    }
                }

            } catch (e: Exception) {
                getHeadLineList().postValue(HeadlineResponse())
                Log.e(MainViewModel::class.java.simpleName, e.message)
            }
        }
    }

    private fun cacheToDB(pHeadLineRequest: HeadlineResponse) {
        val daoHeadLineDatabase = HeadLineDatabase.getDatabase(getApplication())?.DAOHeadline()!!
        daoHeadLineDatabase.deleteAll()
        pHeadLineRequest.articles?.forEach { it ->
            HeadLineDatabase.databaseWriteExecutor.execute { daoHeadLineDatabase.insert(it) }
        }
    }
}