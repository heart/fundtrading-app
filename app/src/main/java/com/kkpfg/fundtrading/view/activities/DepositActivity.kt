package com.kkpfg.fundtrading.view.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kkpfg.fundtrading.R
import com.kkpfg.fundtrading.databinding.ActivityDepositBinding
import com.kkpfg.fundtrading.viewmodels.DepositViewMobel
import okhttp3.HttpUrl

class DepositActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDepositBinding
    private val viewModel = DepositViewMobel()

    private var amount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepositBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.radioGroup.setOnCheckedChangeListener{ _, checkedId ->
             amount = when(checkedId){
                R.id.amount_1000->1000
                R.id.amount_5000->5000
                R.id.amount_10000->10000
                else->0
            }
        }

        viewModel.depositResponse.observe(this){
            val intent = Intent()
            intent.putExtra("deposit", it)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.confirmBtn.setOnClickListener{
            if(amount > 0){
                viewModel.doDeposit(amount)
            }
        }
    }
}