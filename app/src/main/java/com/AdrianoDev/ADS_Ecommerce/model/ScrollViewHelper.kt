package com.AdrianoDev.ADS_Ecommerce.model

import android.view.ViewTreeObserver
import android.widget.ScrollView

class ScrollViewHelper {

    private var scrollView: ScrollView? = null
    private var scrollListener: ViewTreeObserver.OnScrollChangedListener? = null

    fun attachScrollView(scrollView: ScrollView) {
        this.scrollView = scrollView
        this.scrollListener = ViewTreeObserver.OnScrollChangedListener {
            scrollView.smoothScrollTo(0, 0)
        }
        scrollView.viewTreeObserver.addOnScrollChangedListener(scrollListener)
    }

    fun detachScrollView() {
        scrollView?.viewTreeObserver?.removeOnScrollChangedListener(scrollListener)
        scrollView = null
        scrollListener = null
    }
}
