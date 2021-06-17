package com.snilloc.hilt1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
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

        println(someClass.doAThing())
    }
}
//Dependencies that will be supplied by Dagger
//Dagger is creating an instance of this class at run time then making it available at run time
class SomeClass
@Inject
constructor(
    private val someInterfaceImpl : SomeInterface
) {
    fun doAThing() : String {
        return "Look i did ${someInterfaceImpl.getAThing()}"
    }
}
class SomeInterfaceImpl
constructor() : SomeInterface{
    override fun getAThing(): String {
        return "A Thing"
    }

}

interface SomeInterface {
    fun getAThing() : String
}

//Telling Hilt how to build the Interface Implementation object it doesn't know how to
//Install this module in the SingletonComponent(Application lifecycle)

@InstallIn(SingletonComponent::class)
@Module
abstract class MyModule {
    @Singleton
    @Binds
    abstract fun bindSomeDependency(
        someImpl : SomeInterfaceImpl
    ) : SomeInterface

}