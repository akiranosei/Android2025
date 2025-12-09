package com.example.studentmind

import android.app.Application
import com.example.studentmind.data.AppDatabase

class StudentMindApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
    }
}
