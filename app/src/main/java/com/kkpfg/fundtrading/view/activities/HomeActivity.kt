package com.kkpfg.fundtrading.view.activities

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkpfg.fundtrading.R
import com.kkpfg.fundtrading.data.models.DepositResponse
import com.kkpfg.fundtrading.databinding.ActivityHomeBinding
import com.kkpfg.fundtrading.utils.imageloader.ImageLoader
import com.kkpfg.fundtrading.view.adapters.FundListingAdapter
import com.kkpfg.fundtrading.viewmodels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private var fundListingAdapter: FundListingAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    private var page = 1
    private var endOfPage = false

    private var viewModel = HomeViewModel()


    private val depositActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let{intent->
                val result = intent.getParcelableExtra("deposit",DepositResponse::class.java)
                Toast.makeText(this@HomeActivity, "Deposit Completed ${result?.amount}", Toast.LENGTH_LONG).show()
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editBtn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        fundListingAdapter = FundListingAdapter()
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.fundListingTable.layoutManager = layoutManager
        binding.fundListingTable.adapter = fundListingAdapter

        fundListingAdapter?.setOnClickViewItem {
            val intent = Intent(this, FundDetailActivity::class.java)
            intent.putExtra("fund", it)
            startActivity(intent)
        }

        fundListingAdapter?.setOnScrollToBottom {
            if (!endOfPage) {
                page++
                viewModel.loadFundListing(page)
            }
        }

        binding.swipeRefresher.setOnRefreshListener {
            page = 1
            endOfPage = false
            fundListingAdapter?.removeAll()
            viewModel.loadFundListing(page)
        }

        binding.depositBtn.setOnClickListener{
            val intent = Intent(this, DepositActivity::class.java)
            depositActivityResult.launch(intent)
        }

        binding.fireNoti.setOnClickListener {
            val CHANNEL_ID = "ch1"

            val title = "My notification"

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line...")
                )
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notiManager = NotificationManagerCompat.from(this)

            val intent = Intent(this, NotificationReaderActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            builder.setContentIntent(pendingIntent)

            val granted = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (granted) {
                notiManager.notify(title.hashCode() , builder.build())
            }

        }

        initViewModelObserver()

        viewModel.loadProfile()
        viewModel.loadFundListing(page)
        viewModel.loadBanner()

        CoroutineScope(Dispatchers.Default).launch {
            delay(1200)
            requestNotiPermission()
        }


        //-----------
//        val crashButton = Button(this)
//        crashButton.text = "Test Crash"
//
//        crashButton.setOnClickListener {
//            throw RuntimeException("Test Crash") // Force a crash
//        }
//
//        addContentView(crashButton, ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT))
        //----------

    }

    private fun requestNotiPermission() {
        val isGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        if (!isGranted) {
            val intent = Intent(this, NotificationRequestActivity::class.java)
            startActivity(intent)
        }
    }


    private fun initViewModelObserver() {
        viewModel.userProfile.observe(this) {
            binding.fullName.text = "${it.firstname} ${it.lastname}"
            binding.balance.text = it.balance
            it.picture?.let {
                ImageLoader.loadImageAsCircle(this@HomeActivity, binding.profileImage, it)
            }
        }

        viewModel.fundList.observe(this) {
            binding.swipeRefresher.isRefreshing = false
            fundListingAdapter?.appendDataset(it)
        }

        viewModel.bannerList.observe(this) {
            fundListingAdapter?.updateBanner(it.banners)
        }
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.loadProfile()
    }

}