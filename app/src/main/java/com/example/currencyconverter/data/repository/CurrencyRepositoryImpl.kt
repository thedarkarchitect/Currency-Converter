package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.local.CurrencyRateDao
import com.example.currencyconverter.data.local.entity.toCurrencyRate
import com.example.currencyconverter.data.local.entity.toCurrencyRateEntity
import com.example.currencyconverter.data.remote.CurrencyApi
import com.example.currencyconverter.data.remote.dto.toCurrencyRates
import com.example.currencyconverter.domain.model.CurrencyRate
import com.example.currencyconverter.domain.model.Resource
import com.example.currencyconverter.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CurrencyRepositoryImpl(
    private val api: CurrencyApi,
    private val dao: CurrencyRateDao
): CurrencyRepository {
    override fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>> = flow {
        val localCurrencyRates = getLocalCurrencyRates()
        emit(Resource.Success(localCurrencyRates))

        try {
            val newRates = getRemoteCurrencyRates()
            updateLocalCurrencyRates(newRates)//this updates the currency that is already selected
            emit(Resource.Success(newRates))//this gets the list of all the rates
        } catch (e: IOException){
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection",
                    data = localCurrencyRates
                )
            )
        } catch (e: Exception) { //incase any other exception is found
            emit(
                Resource.Error(
                    message = "Oops, something went wrong! ${e.message}",
                    data = localCurrencyRates
                )
            )
        }
    }

    private suspend fun getLocalCurrencyRates(): List<CurrencyRate> {
        //currency convert list rate converted from currency entity to currency rate data class
        return dao.getAllCurrencyRates().map{ it.toCurrencyRate() }
    }

    private suspend fun getRemoteCurrencyRates(): List<CurrencyRate> {
        //this is returning rates from the api
        val response = api.getLatestRates()
        return response.toCurrencyRates()
    }

    private suspend fun updateLocalCurrencyRates(currencyRates: List<CurrencyRate>) {
        dao.upsertAll(currencyRates.map{ it.toCurrencyRateEntity() })
    }

}