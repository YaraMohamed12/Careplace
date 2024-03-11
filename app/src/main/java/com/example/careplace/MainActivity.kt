package com.example.careplace

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    private lateinit var gestureDetector: GestureDetector
    private lateinit var fragmentManager: FragmentManager
    private var currentFragmentIndex = 0
    private val fragments = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize fragments
        fragments.add(one_fragment())
        fragments.add(second_fragment())
        fragments.add(thrid_fragment())
        fragments.add(forth_fragment())
        fragments.add(fiveith_fragment())

        // Initialize gesture detector
        gestureDetector = GestureDetector(this, GestureListener())

        // Initialize fragment manager
        fragmentManager = supportFragmentManager
        showFragment(currentFragmentIndex)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            gestureDetector.onTouchEvent(event)
        }
        return super.onTouchEvent(event)
    }

    private fun showFragment(index: Int) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_continer, fragments[index])
        transaction.commit()
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            val diffX = e2?.x?.minus(e1!!.x) ?: 0f
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    // Swipe right
                    if (currentFragmentIndex > 0) {
                        currentFragmentIndex--
                        showFragment(currentFragmentIndex)
                    }
                } else {
                    // Swipe left
                    if (currentFragmentIndex < fragments.size - 1) {
                        currentFragmentIndex++
                        showFragment(currentFragmentIndex)
                    }
                }
                return true
            }
            return false
        }
    }
}