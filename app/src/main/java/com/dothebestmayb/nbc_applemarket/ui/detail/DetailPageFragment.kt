package com.dothebestmayb.nbc_applemarket.ui.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import com.dothebestmayb.nbc_applemarket.R
import com.dothebestmayb.nbc_applemarket.data.LikeManager
import com.dothebestmayb.nbc_applemarket.data.LoggedUserManager
import com.dothebestmayb.nbc_applemarket.data.ProductManager
import com.dothebestmayb.nbc_applemarket.data.UserManager
import com.dothebestmayb.nbc_applemarket.databinding.FragmentDetailPageBinding
import com.dothebestmayb.nbc_applemarket.model.Product
import com.dothebestmayb.nbc_applemarket.model.User
import com.dothebestmayb.nbc_applemarket.model.UserTemperature
import com.dothebestmayb.nbc_applemarket.ui.main.MainPageFragment
import com.dothebestmayb.nbc_applemarket.util.toStringWithComma
import com.google.android.material.snackbar.Snackbar

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
        progressTemper.clearAnimation()
        progressTemper.progress = seller.temper.toInt()
        val color = Color.parseColor(userTemperature.colorCode)
        tvUserTemperNum.setTextColor(color)
        progressTemper.setIndicatorColor(color)

        tvSellerName.text = seller.nickname
        tvLocation.text = seller.location.name

        tvName.text = product.name
        tvIntroduction.text = product.introduction

        tvPrice.text = product.price.toStringWithComma()

        val drawableId = if (checkLike()) {
            R.drawable.like_fill_big
        } else {
            R.drawable.like_big
        }
        ivLike.setImageResource(drawableId)
    }

    private fun checkLike(): Boolean {
        val user = LoggedUserManager.getUserInfo()
        return LikeManager.checkLike(user, product)
    }

    private fun removeLike() {
        val user = LoggedUserManager.getUserInfo()
        product = LikeManager.remove(user, product)
        ProductManager.updateProduct(product)
    }

    private fun addLike() {
        val user = LoggedUserManager.getUserInfo()
        product = LikeManager.add(user, product)
        ProductManager.updateProduct(product)
        Snackbar.make(binding.root, R.string.liking_product_is_done, Snackbar.LENGTH_SHORT)
            .setAnchorView(binding.ivLike)
            .show()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListener() = with(binding) {
        ibBack.setOnClickListener {
            val mainPageFragment =
                parentFragmentManager.findFragmentByTag(MainPageFragment.MAIN_PAGE_FRAGMENT_TAG)
            if (mainPageFragment != null) {
                parentFragmentManager.commit {
                    show(mainPageFragment)
                    setMaxLifecycle(mainPageFragment, Lifecycle.State.RESUMED)
                }
            }
            parentFragmentManager.popBackStack()
        }
        ivLike.setOnClickListener {
            val drawableId = if (checkLike()) {
                removeLike()
                R.drawable.like_big
            } else {
                addLike()
                R.drawable.like_fill_big
            }
            ivLike.setImageResource(drawableId)
        }
        tvTemperInfo.setOnClickListener {
            vGuidance.root.visibility = View.VISIBLE
        }
        root.setOnClickListener {
            vGuidance.root.visibility = View.GONE
        }

        btnChat.setOnClickListener {
            Toast.makeText(requireContext(), R.string.chat_is_not_supported, Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val guidanceView = binding.vGuidance.root
        if (guidanceView.isVisible && isWithoutViewBounds(guidanceView, event.rawY, event.rawX)) {
            guidanceView.visibility = View.GONE
        }

        return false
    }

    private fun isWithoutViewBounds(view: View, yPoint: Float, xPoint: Float): Boolean {
        val xy = IntArray(2)
        view.getLocationOnScreen(xy)
        val (x, y) = xy
        return xPoint < x || xPoint > x + view.width || yPoint < y || yPoint > y + view.height
    }


    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }

    companion object {
        const val BUNDLE_KEY_FOR_PRODUCT = "BUNDLE_KEY_FOR_PRODUCT"
        const val DETAIL_PAGE_FRAGMENT_TAG = "DETAIL_PAGE_FRAGMENT"
    }
}