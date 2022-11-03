package com.shahpar.daggerhilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shahpar.daggerhilt.retrofit.GitHubService
import com.shahpar.daggerhilt.retrofit.models.Repo
import com.shahpar.daggerhilt.services.AnalyticsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var analytics: AnalyticsAdapter

// Call directly from Activity
//    @Inject
//    lateinit var gitHubService: GitHubService

    lateinit var viewModel: MViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MViewModel::class.java]

        val txt_response = findViewById<EditText>(R.id.txt_response)

        analytics.callService1()
        analytics.callService2()
        analytics.callService3()

        // Call from ViewModel
        viewModel.fetchRepo("najafloo").observe(this, object : Observer<List<Repo?>?> {
            override fun onChanged(t: List<Repo?>?) {
                txt_response.setText(t.toString())
            }
        })

        // Call directly from Activity
        /*gitHubService.listRepos("najafloo")!!.enqueue(object : Callback<List<Repo?>?> {
            override fun onResponse(call: Call<List<Repo?>?>, response: Response<List<Repo?>?>) {

                val x = "${response.body()}"
                txt_response.setText(x)

                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<List<Repo?>?>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })*/
    }
}