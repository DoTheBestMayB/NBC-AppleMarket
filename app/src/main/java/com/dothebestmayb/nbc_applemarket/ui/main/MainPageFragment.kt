package com.dothebestmayb.nbc_applemarket.ui.main

import android.Manifest
import android.app.NotificationManager
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
import com.dothebestmayb.nbc_applemarket.util.PRODUCT_NOTIFICATION_CHANNEL_ID
import com.dothebestmayb.nbc_applemarket.util.PRODUCT_NOTIFICATION_ID
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.random.Random

class MainPageFragment : Fragment(), ProductOnClickListener {

    private var _binding: FragmentMainPageBinding? = null
    private val binding: FragmentMainPageBinding
        get() = _binding!!

    private var isDummyDataInserted = false
    private lateinit var productWillBeDeleted: Product

    private val builder by lazy {
        NotificationCompat.Builder(requireContext(), PRODUCT_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.carrot)
            .setContentTitle("Title")
            .setContentText("Content")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(resources.getColor(R.color.primary, requireContext().theme))
    }

    private val notificationPermissionDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.notification_permission_title))
            .setMessage(getString(R.string.notification_permission_message))
            .setNegativeButton(getString(R.string.permission_negative)) { _: DialogInterface, _: Int ->
            }
            .setPositiveButton(getString(R.string.permission_positive)) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:" + requireContext().packageName)
                }
                startActivity(intent)
            }
    }

    private val notificationChannelPermissionDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.product_channel_permission_title))
            .setMessage(getString(R.string.product_channel_permission_message))
            .setNegativeButton(getString(R.string.permission_negative)) { _: DialogInterface, _: Int ->
            }
            .setPositiveButton(getString(R.string.permission_positive)) { _, _ ->
                val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
                    putExtra(Settings.EXTRA_CHANNEL_ID, PRODUCT_NOTIFICATION_CHANNEL_ID)
                }
                startActivity(intent)
            }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                createNotification()
            } else {
                // 사용자에게 권한이 필요한 이유 설명하는 Dialog 띄우기
                notificationPermissionDialog.show()
            }
        }

    private val adapter by lazy { ProductAdapter(this) }
    private val finishDialog by lazy {
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

    private val deleteDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("상품 삭제")
            .setMessage("상품을 정말로 삭제하시겠습니까?")
            .setIcon(R.drawable.conversation)
            .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->

            }
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                ProductManager.removeProduct(productWillBeDeleted)
                updateProductList()
            }
    }

    private fun updateProductList() {
        val products =
            ProductManager.getAllProducts().shuffled(Random(System.currentTimeMillis()))
        adapter.submitList(products)
    }

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

    override fun onLongClick(product: Product) {
        productWillBeDeleted = product
        deleteDialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (isEnabled) {
                finishDialog.show()
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
        binding.rvProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
            updateProductList()
            isDummyDataInserted = true
        }
    }

    private fun setListener() = with(binding) {
        fabUp.setOnClickListener {
            rvProducts.smoothScrollToPosition(0)
        }
        ivAlert.setOnClickListener {
            createNotification()
        }
    }

    private fun createNotification() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            return
        }
        // 게시물 알림을 개별적으로 차단했는지 확인
        val notificationManager = requireContext().getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationManager.getNotificationChannel(PRODUCT_NOTIFICATION_CHANNEL_ID).importance == NotificationManager.IMPORTANCE_NONE) {
            notificationChannelPermissionDialog.show()
            return
        }
        NotificationManagerCompat.from(requireContext()).notify(PRODUCT_NOTIFICATION_ID, builder.build())

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