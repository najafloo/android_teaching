package com.shahpar.rxjava

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.shahpar.rxjava.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    val TAG = "SHAHPAR"
    val address = "https://randomuser.me/api/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            try {
                binding.progressBar.visibility = View.VISIBLE
                Download().execute(address)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
//        readApi()
    }

    private fun readApi() {
        Thread {
            val url = URL(address)
            val connection = url.openConnection()
            val inputStream = connection.getInputStream()
            val inpuStreamReader = InputStreamReader(inputStream)
            var data = inpuStreamReader.read()
            var result = ""

            while (data > 0) {
                result += data.toChar()
                data = inpuStreamReader.read()
            }

            runOnUiThread {
                Log.d(TAG, "readApi: $result")
            }
        }.start()
    }

    inner class Download : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg p0: String?): String {
            var result = ""

            val url: URL
            val httpURLConnection: HttpsURLConnection

            try {
                url = URL(p0[0])
                httpURLConnection = url.openConnection() as HttpsURLConnection
                val inputStream = httpURLConnection.inputStream
                val inputStreamRead = InputStreamReader(inputStream)
                var data = inputStreamRead.read()

                while (data > 0) {
                    val ch = data.toChar()
                    result += ch
                    data = inputStreamRead.read()
                }

                binding.progressBar.visibility = View.GONE
                return result

            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                e.printStackTrace()
                return result
            }
        }

        override fun onPostExecute(result: String?) {
            try {
                val jsonObject = result?.let { JSONObject(it) } ?: return

                val resultsArray = jsonObject.getJSONArray("results")

                if(resultsArray.length() != 0) {
                    val firstResult = resultsArray[0] as JSONObject

                    // name object
                    val nameObject = firstResult.getJSONObject("name")
                    val title = nameObject.getString("title")
                    val name = nameObject.getString("first")
                    val lastname = nameObject.getString("last")

                    // birthday object
                    val dobObject = firstResult.getJSONObject("dob")
                    val age = dobObject.getInt("age")
                    val bdate = dobObject.getString("date")
                    val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:MM:ss", Locale.ENGLISH)
                    val sdf1 = SimpleDateFormat("yyyy-MM-dd EEEE", Locale.ENGLISH)
                    val ddate = sdf.parse(bdate)
                    val day = sdf1.format(ddate)

                    // email
                    val email = firstResult.getString("email")

                    // picture pictureObject
                    val pictureObject = firstResult.getJSONObject("picture")
                    val largeFile = pictureObject.getString("large")

                    Glide.with(applicationContext).load(largeFile).into(binding.imgPicture)
                    binding.txtAge.text = age.toString()
                    val personName = "$title $name $lastname"
                    binding.txtName.text = personName
                    binding.txtBod.text = day
                    binding.txtEmail.text = email
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            super.onPostExecute(result)
        }
    }
}
/*

Sample json:

"""
{
   "results":[
      {
         "gender":"female",
         "name":{
            "title":"Miss",
            "first":"Julia",
            "last":"Paavola"
         },
         "location":{
            "street":{
               "number":5136,
               "name":"Hämeentie"
            },
            "city":"Kustavi",
            "state":"Southern Savonia",
            "country":"Finland",
            "postcode":73830,
            "coordinates":{
               "latitude":"-27.0399",
               "longitude":"43.7593"
            },
            "timezone":{
               "offset":"-3:30",
               "description":"Newfoundland"
            }
         },
         "email":"julia.paavola@example.com",
         "login":{
            "uuid":"6a25846d-badc-46d7-87cf-4ac21dac367a",
            "username":"yellowelephant674",
            "password":"robin",
            "salt":"N2XyYDXE",
            "md5":"745d7723ee5d07d49976bee9bb9e55df",
            "sha1":"5ca20f72fbee6d3c3c839c922719f804e7efca3f",
            "sha256":"07d7f26145c843be8fbe39fabe628024342945db42101b1e4a1fd3639a169678"
         },
         "dob":{
            "date":"1989-12-04T07:16:58.865Z",
            "age":32
         },
         "registered":{
            "date":"2005-08-20T11:53:59.475Z",
            "age":17
         },
         "phone":"08-671-446",
         "cell":"043-585-85-46",
         "id":{
            "name":"HETU",
            "value":"NaNNA078undefined"
         },
         "picture":{
            "large":"https:\/\/randomuser.me\/api\/portraits\/women\/6.jpg",
            "medium":"https:\/\/randomuser.me\/api\/portraits\/med\/women\/6.jpg",
            "thumbnail":"https:\/\/randomuser.me\/api\/portraits\/thumb\/women\/6.jpg"
         },
         "nat":"FI"
      }
   ],
   "info":{
      "seed":"24e140925404aa11",
      "results":1,
      "page":1,
      "version":"1.4"
   }
}
"""
*/