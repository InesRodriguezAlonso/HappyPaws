package com.happypaws.home.basefragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.happypaws.R
import com.happypaws.home.HomeActivity
import com.happypaws.res.RestApi
import com.happypaws.utils.DialogUtils
import java.util.*
import javax.inject.Inject

open class BaseFragment : Fragment(), BaseView {

    @set:Inject
    internal var service: RestApi? = null

    @BindView(R.id.swipe_refresh_layout)
    lateinit var refreshLayout: SwipeRefreshLayout

    @BindView(R.id.loading_spinner)
    lateinit var loadingSpinner: LinearLayout

    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.empty_view)
    lateinit var emptyView: View

    @BindView(R.id.error_view)
    lateinit var errorView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_base_overview, container, false)

        ButterKnife.bind(this, view)

        (Objects.requireNonNull<FragmentActivity>(activity) as HomeActivity).dependencies!!.inject(this)

        showLoadingSpinner(true)
        recyclerView.visibility = View.GONE

        return view
    }

    override fun onFailure(errorMessage: String) {
        Log.e(TAG, getString(R.string.on_error_response, errorMessage))

        DialogUtils.showDialogAlert(this.context!!, errorMessage)
    }

    override fun showEmptyView(show: Boolean) {
        Log.d(TAG, "showEmptyView: $show")

        emptyView.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showErrorView(show: Boolean) {
        Log.d(TAG, "showErrorView: $show")

        errorView.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showLoadingSpinner(show: Boolean) {
        Log.d(TAG, "showLoadingSpinner: $show")

        loadingSpinner.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showResultList(show: Boolean) {
        Log.d(TAG, "showResultList: $show")

        recyclerView.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun stopRefreshing() {
        refreshLayout.isRefreshing = false
    }

    companion object {

        private const val TAG = "BaseFragment"
    }
}