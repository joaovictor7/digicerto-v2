package com.xnova.digicerto.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.xnova.digicerto.databinding.ActivitySplashScreenBinding
import com.xnova.digicerto.ui.BaseActivity
import com.xnova.digicerto.ui.main.MainActivity

class SplashScreenActivity : BaseActivity() {

    companion object {
        private const val DELAY_MILLISECONDS = 1000L
    }

    private lateinit var mBinding: ActivitySplashScreenBinding
    private lateinit var mViewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mViewModel = ViewModelProvider(this).get(SplashScreenViewModel::class.java)

        observers()
        startApp()
    }

    private fun startApp() {
        Handler(Looper.getMainLooper()).postDelayed({
            mViewModel.start()
        }, DELAY_MILLISECONDS)
    }

    private fun observers() {
        mViewModel.msgAction.observe(this, {
            if (it.first || mBinding.textAction.isVisible) {
                mBinding.textAction.visibility = View.VISIBLE
                mBinding.textAction.text = it.second
            }
        })

        mViewModel.nextPage.observe(this, {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }
}