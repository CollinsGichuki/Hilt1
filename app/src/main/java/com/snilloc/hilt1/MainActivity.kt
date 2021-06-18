package com.snilloc.hilt1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

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

        println(someClass.doAThing1())
        println(someClass.doAThing2())
    }
}
//Dependencies that will be supplied by Dagger
//Dagger is creating an instance of this class at run time then making it available at run time
class SomeClass
@Inject
constructor(
    //Use the annotations we have created to distinguish between them
    @Impl1 private val someInterfaceImpl1 : SomeInterface,
    @Impl1 private val someInterfaceImpl2 : SomeInterface
) {
    fun doAThing1() : String {
        return "Look i did ${someInterfaceImpl1.getAThing()}"
    }
    fun doAThing2() : String {
        return "Look i did ${someInterfaceImpl2.getAThing()}"
    }
}
//Same Interface, 2 different implementations with 2 different String dependencies

class SomeInterfaceImpl1
constructor() : SomeInterface{
    override fun getAThing(): String {
        return "A Thing1"
    }

}class SomeInterfaceImpl2
constructor() : SomeInterface{
    override fun getAThing(): String {
        return "A Thing2"
    }

}

interface SomeInterface {
    fun getAThing() : String
}

//Telling Hilt how to build the Interface Implementation object it doesn't know how to
//Install this module in the SingletonComponent(Application lifecycle)

@InstallIn(SingletonComponent::class)
@Module
 class MyModule {
    //Use the annotations we have created to distinguish between them
    @Impl1
    @Singleton
    @Provides
     fun provideSomeDependency1() : SomeInterface {
         return SomeInterfaceImpl1()
     }

    @Impl2
    @Singleton
    @Provides
     fun provideSomeDependency2() : SomeInterface {
         return SomeInterfaceImpl2()
     }

}

//Our new annotations for each implementation
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2