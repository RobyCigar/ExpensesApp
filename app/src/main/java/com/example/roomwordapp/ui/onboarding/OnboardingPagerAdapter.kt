package com.example.roomwordapp.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class OnboardingPagerAdapter(private val context: Context, private val layouts: List<Int>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(layouts[position], container, false)
        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return layouts.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}
