package com.kkpfg.fundtrading.view.activities

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kkpfg.fundtrading.data.api.models.ErrorResponse
import com.kkpfg.fundtrading.databinding.ActivityRegisterBinding
import com.kkpfg.fundtrading.viewmodels.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel = RegisterViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener{
            viewModel.doRegister(binding.email.text.toString(),
                binding.password.text.toString(),
                binding.firstname.text.toString(),
                binding.lastname.text.toString()
                )
        }

        initViewModelObserver()
    }

    private fun initViewModelObserver(){
        viewModel.registerResponse.observe(this){response->
            if (response.isSuccessful) {
                val response = response.body()
                alertDialog("Success", response?.message){
                    this@RegisterActivity.finish()
                }
            } else {
                val error = ErrorResponse.parseError(response.errorBody())
                alertDialog("Error", error.error)
            }
        }
    }

    private fun alertDialog(title: String?, message: String?, clickOk: (()->Unit)? = null){
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                clickOk?.invoke()
            }
            .create()
            .show()
    }
}