package com.example.myhttprequest

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request.Method.GET
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

@Suppress("ClassName")
class MainActivity : AppCompatActivity() {

    var flagRequest:Boolean = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sendButton = findViewById<Button>(R.id.btSend).setOnClickListener {
            flagRequest = !flagRequest
            Log.d("myResponse", "$flagRequest")

        }

        val myThread = requestAsync()
        myThread.start()
    }

    inner class requestAsync : Thread() {
        override fun run() {
            Log.d("myResponse", "thread started")
            while (true){
                if (flagRequest){
                    try {
                        request()
                        sleep(100)
                    }
                    catch (e: Exception){
                        Log.d("myError", "$e")
                    }
                }
            }

        }
    }

    var volleyCount:Int = 0
    lateinit var resp:String
    var queue:RequestQueue? = null

    fun request(){
        // Instantiate the RequestQueue.
        if (queue == null){
            queue = Volley.newRequestQueue(this)
            Log.d("create", "I created a queue")
        }

        val url = "http://192.168.0.196:8080"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(GET, url,
            { response ->
                resp = response
                Log.d("myResponse", "$resp / count : $volleyCount")

            },
            { error -> Log.d("myError", "$error / count : $volleyCount") })

        stringRequest.setShouldCache(false) // no caching url...

        stringRequest.retryPolicy = DefaultRetryPolicy(
            20000,  //time to wait for it in this case 20s
            20,  //tries in case of error
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )


        // Add the request to the RequestQueue.
        try {
//            if (volleyCount > 500) {
////                queue.cancelAll(true)
//                volleyCount = 0
//            }
            queue?.add(stringRequest)
            Log.d("myQueue", "$queue // $volleyCount")
            volleyCount++
        }
        catch (error: Exception){
            Log.d("myError", "$error // $volleyCount")
        }
    }
}