package com.kkpfg.fundtrading.view.activities

import TokenManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.kkpfg.fundtrading.data.api.APIClient
import com.kkpfg.fundtrading.data.api.models.UserProfile
import com.kkpfg.fundtrading.databinding.ActivityEditProfileBinding
import com.kkpfg.fundtrading.utils.UtilityFunctions
import com.kkpfg.fundtrading.utils.imageloader.ImageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody


class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private val imagePickerResultActivityLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){ uri ->
        uploadProfilePicture(uri)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.uploadBtn.setOnClickListener{
            selectPhoto()
        }

        binding.saveBtn.setOnClickListener{
            updateProfile()
        }

        loadProfile()
    }

    private fun updateProfile() {
        val profileAPI = APIClient.getProfileAPI()
        val firstname = binding.firstName.text.toString()
        val lastname = binding.lastName.text.toString()

        val profile = UserProfile(firstname = firstname, lastname = lastname)

        CoroutineScope(Dispatchers.IO).launch {
            val accessToken = TokenManager.getTokenManager().getAccessToken()
            accessToken?.let{
                profileAPI.updateProfile(it, profile)
                withContext(Dispatchers.Main){
                    this@EditProfileActivity.finish()
                }
            }
        }
    }

    private fun uploadProfilePicture(uri: Uri?){
        uri?.let{ uri->
            val file = UtilityFunctions.createFileFromUri(this, uri)

            file?.let{file->
                val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("profilePicture", file.name, requestFile)

                val accessToken =  TokenManager.getTokenManager().getAccessToken()
                accessToken?.let{
                    CoroutineScope(Dispatchers.IO).launch {
                        APIClient.getProfileAPI().uploadProfilePicture(it, filePart)
                        withContext(Dispatchers.Main){
                            loadProfile()
                        }
                    }
                }
            }
        }
    }

    private fun selectPhoto(){
        imagePickerResultActivityLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun loadProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            val token = TokenManager.getTokenManager().getAccessToken()

            val profileAPI = APIClient.getProfileAPI()
            val response = profileAPI.getProfile(token ?: "")

            response.body()?.let{ profile->
                withContext(Dispatchers.Main){
                    binding.firstName.setText(profile.firstname )
                    binding.lastName.setText(profile.lastname)
                    profile.picture?.let{
                        ImageLoader.loadImageAsCircle(this@EditProfileActivity, binding.profileImage, it)
                    }
                }
            }
        }
    }
}