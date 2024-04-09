package com.dothebestmayb.nbc_applemarket.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.dothebestmayb.nbc_applemarket.R
import com.dothebestmayb.nbc_applemarket.data.ProductManager
import com.dothebestmayb.nbc_applemarket.databinding.FragmentMainPageBinding
import com.dothebestmayb.nbc_applemarket.model.Product
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.random.Random

class MainPageFragment : Fragment() {

    private var _binding: FragmentMainPageBinding? = null
    private val binding: FragmentMainPageBinding
        get() = _binding!!

    private val adapter by lazy { ProductAdapter() }
    private val dialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("종료")
            .setMessage("정말 종료하시겠습니까?")
            .setIcon(R.drawable.conversation)
            .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->

            }
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                requireActivity().finish()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            dialog.show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        insertDummyData()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.rvProducts.adapter = adapter
        binding.rvProducts.addItemDecoration(SimpleDividerItemDecoration(requireContext(), R.drawable.line_divider))

        val products = ProductManager.getAllProducts().shuffled(Random(System.currentTimeMillis()))
        adapter.submitList(products)
    }

    private fun insertDummyData() {
        ProductManager.addProduct(Product.getDummyData())
    }

    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }

}