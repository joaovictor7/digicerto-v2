package com.xnova.digicerto.ui.splashscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.xnova.digicerto.databinding.ActivitySplashScreenBinding
import java.io.File
import java.io.FileWriter

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySplashScreenBinding
    private lateinit var mViewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mViewModel = ViewModelProvider(this).get(SplashScreenViewModel::class.java)

        observers()

        mViewModel.start()
    }

    private fun observers() {
        mViewModel.action.observe(this, {

        })
    }
}