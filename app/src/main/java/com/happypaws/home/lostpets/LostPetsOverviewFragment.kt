package com.happypaws.home.lostpets

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.happypaws.R
import com.happypaws.adapters.PetsAdapter
import com.happypaws.home.basefragment.BaseFragment
import com.happypaws.petprofile.PetProfileActivity
import com.happypaws.petprofile.PetProfileActivity.Companion.BUNDLE_PET_DTO
import com.happypaws.res.model.PetResponse

class LostPetsOverviewFragment : BaseFragment(), LostPetsOverviewView, SwipeRefreshLayout.OnRefreshListener, PetsAdapter.PetsListListener {

    private var adapter: PetsAdapter? = null
    private var presenter: LostPetsOverviewPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        adapter = PetsAdapter(this)
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener(this)

        presenter = LostPetsOverviewPresenter(service!!, this@LostPetsOverviewFragment)

        showLoadingSpinner(true)
        getLostPetsList()

        return view
    }

    override fun getLostPetsListSuccess(lostPetsListResponse: List<PetResponse>?) {
        Log.d(TAG, getString(R.string.on_success_response))

        adapter!!.setData(lostPetsListResponse!!)
        recyclerView.visibility = VISIBLE
    }

    override fun onItemClicked(petItem: PetResponse) {
        Log.d(TAG, "onItemClicked" + petItem.petId!!)

        val intent = Intent(context, PetProfileActivity::class.java)
        intent.putExtra(BUNDLE_PET_DTO, petItem)
        startActivity(intent)
    }

    override fun onRefresh() {
        getLostPetsList()
    }

    private fun getLostPetsList() {
        presenter!!.getLostPetsList()
    }

    companion object {

        var TAG = "LostPetsOverviewFragment"
    }
}