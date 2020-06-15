package com.forestmouse.samples.streaming.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.forestmouse.samples.streaming.R
import kotlinx.android.synthetic.main.activity_scrolling_new.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling_new)
        if (savedInstanceState != null) return

        if (isConnected(this)) {
            activateScrollingFragment()
        } else {
            noConnectionText.visibility = View.VISIBLE
            scrollingActivityRetryButton.visibility = View.VISIBLE
            scrollingActivityRetryButton.setOnClickListener {
                if (isConnected(this)) {
                    noConnectionText.visibility = View.GONE
                    scrollingActivityRetryButton.visibility = View.GONE
                    activateScrollingFragment()
                }
            }
        }
    }

    private fun activateScrollingFragment() {
        fragmentContainer.removeAllViews()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, ScrollingPostFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    private fun isConnected(context: Context): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }
}