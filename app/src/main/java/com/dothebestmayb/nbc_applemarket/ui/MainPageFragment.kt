package com.dothebestmayb.nbc_applemarket.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dothebestmayb.nbc_applemarket.data.ProductManager
import com.dothebestmayb.nbc_applemarket.databinding.FragmentMainPageBinding
import com.dothebestmayb.nbc_applemarket.model.Product
import kotlin.random.Random

class MainPageFragment : Fragment() {

    private var _binding: FragmentMainPageBinding? = null
    private val binding: FragmentMainPageBinding
        get() = _binding!!

    private val adapter by lazy { ProductAdapter() }

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