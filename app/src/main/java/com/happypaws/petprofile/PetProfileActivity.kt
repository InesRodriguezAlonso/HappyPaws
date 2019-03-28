package com.happypaws.petprofile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import com.happypaws.R
import com.happypaws.adoption.AdoptionActivity
import com.happypaws.adoption.AdoptionActivity.Companion.REQUEST_CODE_FORM_SENT
import com.happypaws.res.model.PetResponse
import com.happypaws.utils.DialogUtils
import com.happypaws.utils.TimeUtils

class PetProfileActivity : AppCompatActivity() {

    @BindView(R.id.iv_pet_profile_image)
    lateinit var petImageProfile: AppCompatImageView

    @BindView(R.id.iv_pet_type)
    lateinit var petType: AppCompatImageView

    @BindView(R.id.iv_pet_gender)
    lateinit var petGender: AppCompatImageView

    @BindView(R.id.tv_pet_breed)
    lateinit var petBreed: AppCompatTextView

    @BindView(R.id.bt_adopt_me)
    lateinit var btAdoptMe: AppCompatButton

    @BindView(R.id.tv_pet_age)
    lateinit var petAge: AppCompatTextView

    @BindView(R.id.tv_pet_description)
    lateinit var petDescription: AppCompatTextView

    @BindView(R.id.tv_pet_registration_date)
    lateinit var petRegistrationDate: AppCompatTextView

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.collapsing_toolbar_layout)
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout

    private var petResponse: PetResponse? = null

    /*show adoption menu only if pet is available to adopt*/
    private var petForAdoption: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pet_profile)

        ButterKnife.bind(this)

        if (intent != null) {
            petResponse = intent.getSerializableExtra(BUNDLE_PET_DTO) as PetResponse
            petForAdoption = intent.getBooleanExtra(BUNDLE_ADOPTION, false)
        }

        if (petResponse == null) {
            DialogUtils.showDialogAlert(this, getString(R.string.unexpected_error))
            finish()
        }

        setToolbar()
        initProfile()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_FORM_SENT && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "Form Sent")
            Snackbar.make(findViewById(R.id.coordinatorLayout), getString(R.string.activity_profile_alert_form_sent), Snackbar.LENGTH_LONG).show()
        }
    }

    @OnClick(R.id.bt_adopt_me)
    internal fun onAdoptMeClicked() {
        val intent = Intent(this, AdoptionActivity::class.java)
        intent.putExtra(BUNDLE_PET_DTO, petResponse)
        startActivityForResult(intent, REQUEST_CODE_FORM_SENT)
    }

    private fun initProfile() {
        if (petForAdoption) {
            btAdoptMe.visibility = View.VISIBLE
        }

        Glide.with(this)
                .load(petResponse!!.petProfileThumbnailUrl)
                .error(
                        Glide.with(this)
                                .load(R.drawable.ic_image_placeholder))
                .into(petImageProfile)

        petType.setBackgroundResource(petResponse!!.petTypeIcon)
        petGender.setBackgroundResource(petResponse!!.petGenderIcon)
        petBreed.text = petResponse!!.petBreed
        petAge.text = petResponse!!.petAge
        petDescription.text = petResponse!!.petDescription
        petRegistrationDate.text = TimeUtils.getDateFormat(petResponse!!.petRegistrationDateMs, this)
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)

        toolbar.title = petResponse!!.petName
        collapsingToolbarLayout.title = petResponse!!.petName

        toolbar.setNavigationOnClickListener { finish() }
    }

    companion object {

        var TAG = "PetProfileActivity"

        var BUNDLE_PET_DTO = "bundle::pet:dto"
        var BUNDLE_ADOPTION = "bundle::adoption"
    }
}