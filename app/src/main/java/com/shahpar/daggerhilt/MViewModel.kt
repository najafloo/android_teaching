package com.shahpar.daggerhilt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shahpar.daggerhilt.retrofit.GitHubService
import com.shahpar.daggerhilt.retrofit.models.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MViewModel @Inject constructor(private val gitHubService: GitHubService) : ViewModel() {

    fun fetchRepo(username : String): LiveData<List<Repo?>?> {
        val result = MutableLiveData<List<Repo?>?>()

        gitHubService.listRepos(username)!!.enqueue(object : Callback<List<Repo?>?> {
            override fun onResponse(call: Call<List<Repo?>?>, response: Response<List<Repo?>?>) {
                if(response.isSuccessful) result.value = response.body()
            }

            override fun onFailure(call: Call<List<Repo?>?>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return result
    }
}