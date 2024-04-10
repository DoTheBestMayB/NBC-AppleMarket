package com.dothebestmayb.nbc_applemarket.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dothebestmayb.nbc_applemarket.R
import com.dothebestmayb.nbc_applemarket.databinding.FragmentDetailPageBinding
import com.dothebestmayb.nbc_applemarket.model.Product

class DetailPageFragment : Fragment() {

    private var _binding: FragmentDetailPageBinding? = null
    private val binding: FragmentDetailPageBinding
        get() = _binding!!

    private lateinit var receivedProduct: Product

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
    }

    private fun extractDataFromIntent(): Boolean {
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(BUNDLE_KEY_FOR_PRODUCT, Product::class.java)
        } else {
            arguments?.getParcelable(BUNDLE_KEY_FOR_PRODUCT)
        } ?: run {
            Toast.makeText(requireContext(), getString(R.string.needed_data_omiited), Toast.LENGTH_LONG).show()
            return false
        }
        receivedProduct = data
        return true
    }

    private fun initView() = with(binding) {
        tvProfile.clipToOutline = true
    }

    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }

    companion object {
        const val BUNDLE_KEY_FOR_PRODUCT = "BUNDLE_KEY_FOR_PRODUCT"
    }
}