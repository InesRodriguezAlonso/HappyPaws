package com.happypaws.adoption

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.happypaws.R
import com.happypaws.base.BaseActivity
import com.happypaws.petprofile.PetProfileActivity.Companion.BUNDLE_PET_DTO
import com.happypaws.res.RestApi
import com.happypaws.res.model.AdoptionRequest
import com.happypaws.res.model.PetResponse
import com.happypaws.utils.DialogUtils
import com.happypaws.utils.ValidationUtils
import com.jakewharton.rxbinding.widget.RxTextView
import de.hdodenhof.circleimageview.CircleImageView
import rx.Observable
import javax.inject.Inject

class AdoptionActivity : BaseActivity(), AdoptionView {

    @set:Inject
    internal var service: RestApi? = null

    @BindView(R.id.iv_pet_profile_image)
    lateinit var petImageProfile: CircleImageView

    @BindView(R.id.et_name)
    lateinit var etName: AppCompatEditText

    @BindView(R.id.et_address)
    lateinit var etAddress: AppCompatEditText

    @BindView(R.id.et_email)
    lateinit var etEmail: AppCompatEditText

    @BindView(R.id.et_phone_number)
    lateinit var etPhoneNumber: AppCompatEditText

    @BindView(R.id.et_about_you)
    lateinit var etAboutYou: AppCompatEditText

    private var enableSendForm: Boolean = false

    private var petResponse: PetResponse? = null

    private var presenter: AdoptionPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_adoption)

        ButterKnife.bind(this)

        dependencies!!.inject(adoptionActivity = this)

        petResponse = intent!!.getSerializableExtra(BUNDLE_PET_DTO) as PetResponse

        if (petResponse == null) {
            DialogUtils.showDialogAlert(this, getString(R.string.unexpected_error))
            finish()
        }

        presenter = AdoptionPresenter(service, this)

        setToolbar()
        initView()
        initObservables()
    }

    override fun formCompleted() {
        Log.d(TAG, "form completed")

        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun isAddressValid(): Boolean {
        return etAddress.text.toString().isNotEmpty()
    }

    override fun isAboutYouValid(): Boolean {
        return etAboutYou.text.toString().isNotEmpty()
    }

    override fun isEmailValid(): Boolean {
        return etEmail.text.toString().isNotEmpty() && ValidationUtils.isValidEmail(etEmail.text.toString())
    }

    override fun isNameValid(): Boolean {
        return etName.text.toString().isNotEmpty()
    }

    override fun isPhoneNumberValid(): Boolean {
        return etPhoneNumber.text.toString().isNotEmpty() && ValidationUtils.isValidPhoneNumber(etPhoneNumber.text.toString())
    }

    @OnClick(R.id.bt_send_adoption_form)
    internal fun onSendAdoptionFormClicked() {
        sendAdoptionForm()
    }

    override fun onFailure(errorMessage: String) {
        Log.e(TAG, errorMessage)

        Snackbar.make(findViewById<View>(R.id.main_container), getString(R.string.unexpected_error), Snackbar.LENGTH_LONG).show()
    }

    override fun requestAdoption() {
        val adoptionRequest = AdoptionRequest(etName.text.toString(), etAddress.text.toString(), etEmail.toString(), etPhoneNumber.toString(), etAboutYou.toString(), petResponse!!.petId)
        presenter!!.sendAdoptionRequest(adoptionRequest)
    }

    override fun showAboutYouError() {
        etAboutYou.error = getString(R.string.activity_adoption_about_you_error_hint)
    }

    override fun showAddressError() {
        etAddress.error = getString(R.string.activity_adoption_address_error_hint)
    }

    override fun showEmailError() {
        etEmail.error = getString(R.string.activity_adoption_email_error_hint)
    }

    override fun showNameError() {
        etName.error = getString(R.string.activity_adoption_name_error_hint)
    }

    override fun showPhoneNumberError() {
        etPhoneNumber.error = getString(R.string.activity_adoption_phone_number_error_hint)
    }

    private fun enabledSentForm(enableSendForm: Boolean) {
        this.enableSendForm = enableSendForm
    }

    private fun initObservables() {
        val nameObservable = RxTextView.afterTextChangeEvents(etName).skip(1).map(presenter!!.isNameValid())
        val addressObservable = RxTextView.afterTextChangeEvents(etAddress).skip(1).map(presenter!!.isAddressValid())
        val emailObservable = RxTextView.afterTextChangeEvents(etEmail).skip(1).map(presenter!!.isEmailValid())
        val phoneNumberObservable = RxTextView.afterTextChangeEvents(etPhoneNumber).skip(1).map(presenter!!.isPhoneNumberValid())
        val aboutYouObservable = RxTextView.afterTextChangeEvents(etAboutYou).skip(1).map(presenter!!.isAboutYouValid())

        nameObservable.subscribe(presenter!!.updateNameViewState())
        addressObservable.subscribe(presenter!!.updateAddressViewState())
        emailObservable.subscribe(presenter!!.updateEmailViewState())
        phoneNumberObservable.subscribe(presenter!!.updatePhoneNumberViewState())
        aboutYouObservable.subscribe(presenter!!.updateAboutYouViewState())

        Observable.combineLatest(nameObservable, addressObservable, emailObservable,
                phoneNumberObservable, aboutYouObservable, presenter!!.isFormValid())
                .subscribe { this.enabledSentForm(it) }
    }

    private fun initView() {
        Glide.with(this)
                .load(petResponse!!.petProfileThumbnailUrl)
                .error(
                        Glide.with(this)
                                .load(R.drawable.ic_image_placeholder))
                .into(petImageProfile)
    }

    private fun sendAdoptionForm() {
        if (enableSendForm) {
            Log.d(TAG, "send adoption form: valid form")
            requestAdoption()
        } else {
            Snackbar.make(findViewById<View>(R.id.main_container), getString(R.string.activity_adoption_error_form_not_valid), Snackbar.LENGTH_LONG).show()
            Log.d(TAG, "send adoption form: invalid form")
        }
    }

    private fun setToolbar() {
        supportActionBar!!.title = getString(R.string.activity_adoption_title, petResponse!!.petName)
    }

    companion object {

        var TAG = "AdoptionActivity"

        var REQUEST_CODE_FORM_SENT = 10
    }
}