package com.snilloc.hilt1

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Hilt will generate the app component and prepare everything we need to use Hilt in our application
@HiltAndroidApp
class MyApplication: Application()