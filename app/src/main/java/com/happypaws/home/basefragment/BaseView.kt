package com.happypaws.home.basefragment

interface BaseView {

    fun onFailure(errorMessage: String)

    fun showEmptyView(show: Boolean)

    fun showErrorView(show: Boolean)

    fun showLoadingSpinner(show: Boolean)

    fun showResultList(show: Boolean)

    fun stopRefreshing()
}
