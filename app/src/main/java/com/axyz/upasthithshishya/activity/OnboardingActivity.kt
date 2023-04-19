package com.axyz.upasthithshishya.activity

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.databinding.ActivityMainBinding
import com.axyz.upasthithshishya.other.OnboardingItem
import com.axyz.upasthithshishya.other.OnboardingItemsAdapter
import com.google.android.material.button.MaterialButton

class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var indicatorsContainer: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)
    }

    private fun setOnboardingItems(){
        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
//                    onBgImage = R.drawable.onbg1,
                    onboardingImage = R.drawable.onclassroom,
                    title = "Easy Attendance Marking System",
                    description = "Mark your attendance via bluetooth within just seconds"
                ),
                OnboardingItem(
//                    onBgImage = R.drawable.onbg2,
                    onboardingImage = R.drawable.onreport,
                    title = "Track Your Attendance",
                    description = "Keep track of your attendance with a trouble-free data system"
                ),
                OnboardingItem(
//                    onBgImage = R.drawable.onbg3,
                    onboardingImage = R.drawable.onschedule,
                    title = "Get Your Lectures' Schedule",
                    description = "Get Schedule of all your lectures effortlessly"
                )

            )
        )
        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingItemsAdapter
        onboardingViewPager.registerOnPageChangeCallback(object:
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        findViewById<ImageView>(R.id.image_next).setOnClickListener {
            if(onboardingViewPager.currentItem+1<onboardingItemsAdapter.itemCount){
                onboardingViewPager.currentItem+=1
            }
            else{
                navigateToHomeActivity()
            }
        }
        findViewById<TextView>(R.id.textSkip).setOnClickListener {
            navigateToHomeActivity()
        }
//        findViewById<MaterialButton>(R.id.btn_get_start).setOnClickListener {
//            navigateToHomeActivity()
//        }
    }
    private fun navigateToHomeActivity(){
        startActivity(Intent(applicationContext, EntryActivity::class.java))
    }

    private fun setupIndicators(){
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8,0,8,0)
        for(i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int){
        val childCount = indicatorsContainer.childCount
        for(i in 0 until childCount){
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if(i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_active_background
                    )
                )
            }
            else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}