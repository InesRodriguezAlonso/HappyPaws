package com.happypaws.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.happypaws.R
import com.happypaws.res.model.PetResponse
import java.util.*

class PetsAdapter(private val listener: PetsListListener?) : RecyclerView.Adapter<PetsAdapter.AdoptionListHolder>() {

    private var data: List<PetResponse>? = ArrayList()

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): PetsAdapter.AdoptionListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.adoption_list_item, parent, false)

        return AdoptionListHolder(v)
    }

    override fun onBindViewHolder(@NonNull holder: AdoptionListHolder, position: Int) {
        val petItem = data!![position]

        holder.tvPetName.text = petItem.petName
        holder.ivPetType.setBackgroundResource(petItem.petTypeIcon)
        holder.ivPetGender.setBackgroundResource(petItem.petGenderIcon)

        Glide.with(holder.itemView)
                .load(petItem.petProfileThumbnailUrl)
                .error(
                        Glide.with(holder.itemView.context)
                                .load(R.drawable.ic_image_placeholder))
                .into(holder.tvPetImage)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(petItem)
        }
    }

    override fun getItemCount(): Int {
        return if (data != null) data!!.size else 0
    }

    fun setData(data: List<PetResponse>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class AdoptionListHolder(v: View) : RecyclerView.ViewHolder(v) {
        @BindView(R.id.tv_pet_image)
        lateinit var tvPetImage: AppCompatImageView

        @BindView(R.id.iv_pet_gender)
        lateinit var ivPetGender: AppCompatImageView

        @BindView(R.id.iv_pet_type)
        lateinit var ivPetType: AppCompatImageView

        @BindView(R.id.tv_pet_name)
        lateinit var tvPetName: AppCompatTextView

        init {
            ButterKnife.bind(this, v)
        }
    }

    interface PetsListListener {
        fun onItemClicked(petItem: PetResponse)
    }
}