package com.happypaws.home.adoption

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.happypaws.R
import com.happypaws.adapters.PetsAdapter
import com.happypaws.home.basefragment.BaseFragment
import com.happypaws.petprofile.PetProfileActivity
import com.happypaws.petprofile.PetProfileActivity.Companion.BUNDLE_ADOPTION
import com.happypaws.petprofile.PetProfileActivity.Companion.BUNDLE_PET_DTO
import com.happypaws.res.model.PetResponse

class AdoptionOverviewFragment : BaseFragment(), AdoptionOverviewView, PetsAdapter.PetsListListener, SwipeRefreshLayout.OnRefreshListener {

    private var adapter: PetsAdapter? = null
    private var presenter: AdoptionOverviewPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        adapter = PetsAdapter(this)
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener(this)

        presenter = AdoptionOverviewPresenter(service!!, this@AdoptionOverviewFragment)

        showLoadingSpinner(true)
        getAdoptionList()

        return view
    }

    override fun getPetsForAdoptionListSuccess(response: List<PetResponse>) {
        Log.d(TAG, getString(R.string.on_success_response))

        adapter!!.setData(response)
        recyclerView.visibility = View.VISIBLE
    }

    override fun onItemClicked(petItem: PetResponse) {
        Log.d(TAG, "onItemClicked" + petItem.petId!!)

        val intent = Intent(context, PetProfileActivity::class.java)
        intent.putExtra(BUNDLE_PET_DTO, petItem)
        intent.putExtra(BUNDLE_ADOPTION, true)
        startActivity(intent)
    }

    override fun onRefresh() {
        getAdoptionList()
    }

    private fun getAdoptionList() {
        presenter!!.getPetsForAdoptionList()
    }

    companion object {

        var TAG = "AdoptionOverviewFragment"
    }
}