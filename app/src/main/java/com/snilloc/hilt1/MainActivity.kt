package com.snilloc.hilt1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

//Using this annotation we don't have to inject MainActivity to the dagger graph
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //FieldInjection
    //Dagger makes the instance of the class(dependency) available
    @Inject
    lateinit var someClass: SomeClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.doAThing())
        println(someClass.doSomeOtherThing())

    }
}

//Dependencies that will be supplied by Dagger
//Dagger is creating an instance of this class at run time then making it available at run time
//Singleton Scope makes the dependency live as long as the Application
@ActivityScoped
class SomeClass
@Inject
constructor(
    //Constructor injection
    @Inject
    private val someOtherClass: SomeOtherClass
) {
    fun doAThing(): String {
        return "Look i did a thing!"
    }

    fun doSomeOtherThing(): String {
        return someOtherClass.doSomeOtherThing()
    }
}

class SomeOtherClass
@Inject
constructor() {
    fun doSomeOtherThing(): String {
        return "Look I did some other thing!"
    }
}