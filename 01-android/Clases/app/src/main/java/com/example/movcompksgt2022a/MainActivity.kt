package com.example.movcompksgt2022a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import io.sentry.Sentry

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Sentry.captureMessage("testing SDK setup")
    }
}