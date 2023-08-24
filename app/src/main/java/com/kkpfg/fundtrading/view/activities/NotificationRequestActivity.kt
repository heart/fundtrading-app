package com.kkpfg.fundtrading.view.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.kkpfg.fundtrading.R
import com.kkpfg.fundtrading.databinding.ActivityNotificationRequestBinding

class NotificationRequestActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                finish()
            } else {
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNotificationRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.okBtn.setOnClickListener{
            requestNotificationPermission()
        }

        binding.cancelBtn.setOnClickListener {
            finish()
        }

        overridePendingTransition(R.anim.slide_up, R.anim.slide_up)
    }

    private fun requestNotificationPermission(){
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}