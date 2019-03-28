package com.happypaws.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.happypaws.dependencies.DaggerDependencies.builder
import com.happypaws.dependencies.Dependencies
import com.happypaws.res.RestClientModule
import java.io.File

open class BaseActivity : AppCompatActivity() {
    var dependencies: Dependencies? = null
        private set

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cacheFile = File(applicationContext!!.cacheDir, "responses")
        dependencies = builder().restClientModule(RestClientModule(cacheFile)).build()
    }
}