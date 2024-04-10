package com.dothebestmayb.nbc_applemarket.ui.detail

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dothebestmayb.nbc_applemarket.R
import com.dothebestmayb.nbc_applemarket.data.UserManager
import com.dothebestmayb.nbc_applemarket.databinding.FragmentDetailPageBinding
import com.dothebestmayb.nbc_applemarket.model.Product
import com.dothebestmayb.nbc_applemarket.model.User
import com.dothebestmayb.nbc_applemarket.model.UserTemperature
import com.dothebestmayb.nbc_applemarket.util.toStringWithComma

class DetailPageFragment : Fragment() {

    private var _binding: FragmentDetailPageBinding? = null
    private val binding: FragmentDetailPageBinding
        get() = _binding!!

    private lateinit var product: Product
    private lateinit var seller: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!extractDataFromIntent()) {
            return
        }
        initView()
        setListener()
    }

    private fun extractDataFromIntent(): Boolean {
        product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(BUNDLE_KEY_FOR_PRODUCT, Product::class.java)
        } else {
            arguments?.getParcelable(BUNDLE_KEY_FOR_PRODUCT)
        } ?: run {
            Toast.makeText(
                requireContext(),
                getString(R.string.needed_data_omiited),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        seller = UserManager.getUser(product.sellerNickname) ?: run {
            Toast.makeText(
                requireContext(),
                getString(R.string.seller_does_not_exist), Toast.LENGTH_LONG
            ).show()
            return false
        }

        return true
    }

    private fun initView() = with(binding) {
        tvProfile.clipToOutline = true
        ivThumbnail.setImageURI(product.imageUri)

        tvUserTemperNum.text = getString(R.string.temper_format).format(seller.temper)
        val userTemperature = UserTemperature.get(seller.temper)
        tvUserTemperIcon.text = userTemperature.emoji
        progressTemper.progress = seller.temper.toInt()
        val color = Color.parseColor(userTemperature.colorCode)
        tvUserTemperNum.setTextColor(color)
        progressTemper.setIndicatorColor(color)

        tvSellerName.text = seller.nickname
        tvLocation.text = seller.location

        tvName.text = product.name
        tvIntroduction.text = product.introduction

        tvPrice.text = product.price.toStringWithComma()
    }

    private fun setListener() = with(binding) {
        ibBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }

    companion object {
        const val BUNDLE_KEY_FOR_PRODUCT = "BUNDLE_KEY_FOR_PRODUCT"
    }
}