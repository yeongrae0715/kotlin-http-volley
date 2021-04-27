package com.example.myhttprequest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sendButton = findViewById<Button>(R.id.btSend).setOnClickListener { request() }


    }
    fun request(){
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.0.196:8080"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(GET, url,
            { response -> Log.d("myResponse", "$response")},
            { error -> Log.d("myResponse", "$error") })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
        Log.d("myQueue", queue.toString())
    }
}