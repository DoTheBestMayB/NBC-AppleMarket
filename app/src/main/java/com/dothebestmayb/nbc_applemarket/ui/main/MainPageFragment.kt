package com.dothebestmayb.nbc_applemarket.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dothebestmayb.nbc_applemarket.R
import com.dothebestmayb.nbc_applemarket.data.ProductManager
import com.dothebestmayb.nbc_applemarket.data.UserManager
import com.dothebestmayb.nbc_applemarket.databinding.FragmentMainPageBinding
import com.dothebestmayb.nbc_applemarket.model.Product
import com.dothebestmayb.nbc_applemarket.model.User
import com.dothebestmayb.nbc_applemarket.ui.detail.DetailPageFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.random.Random

class MainPageFragment : Fragment(), ProductOnClickListener {

    private var _binding: FragmentMainPageBinding? = null
    private val binding: FragmentMainPageBinding
        get() = _binding!!

    private val adapter by lazy { ProductAdapter(this) }
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
    private var isDummyDataInserted = false

    override fun onClick(product: Product) {
        parentFragmentManager.commit {

            val bundle = Bundle().apply {
                putParcelable(DetailPageFragment.BUNDLE_KEY_FOR_PRODUCT, product)
            }
            val fragment = DetailPageFragment().apply {
                arguments = bundle
            }
            replace(R.id.fragment_container_view, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (isEnabled) {
                dialog.show()
            }
        }
        insertDummyData()
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

        setRecyclerView()
        setListener()
    }

    private fun setRecyclerView() {
        binding.rvProducts.adapter = adapter
        binding.rvProducts.addItemDecoration(
            SimpleDividerItemDecoration(
                requireContext(),
                R.drawable.line_divider
            )
        )
        binding.rvProducts.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findFirstVisibleItemPosition() == 0) {
                    binding.fabUp.hide()
                } else {
                    binding.fabUp.show()
                }
            }
        })

        if (isDummyDataInserted.not()) {
            val products =
                ProductManager.getAllProducts().shuffled(Random(System.currentTimeMillis()))
            adapter.submitList(products)
            isDummyDataInserted = true
        }
    }

    private fun setListener() = with(binding) {
        fabUp.setOnClickListener {
            rvProducts.smoothScrollToPosition(0)
        }
    }

    private fun insertDummyData() {
        ProductManager.addProduct(Product.getDummyData())
        UserManager.addUser(User.getDummyData())
    }

    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }

}