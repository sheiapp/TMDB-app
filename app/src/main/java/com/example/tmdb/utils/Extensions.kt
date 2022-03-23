package com.example.tmdb.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tmdb.remote.MoviesApi.Companion.SERVER_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
import com.example.tmdb.utils.Result
import com.example.tmdb.remote.MoviesApi.Companion.UNKNOWN_ERROR

/**
 * Created by Shaheer cs on 22/03/2022.
 */

fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

suspend fun <T> apiValidator(call: suspend () -> Response<T>): Result<T> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            response.body()?.let {
                return@let Result.Success(it)
            } ?: Result.Error(UNKNOWN_ERROR)
        } else {
            Result.Error(response.message())
        }
    } catch (e: Exception) {
        Result.Error(SERVER_ERROR)
    }
}