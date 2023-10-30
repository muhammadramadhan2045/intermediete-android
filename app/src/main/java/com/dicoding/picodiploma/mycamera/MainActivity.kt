package com.dicoding.picodiploma.mycamera

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.mycamera.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.cameraXButton.setOnClickListener { startCameraX() }
        binding.uploadButton.setOnClickListener { uploadImage() }
    }

    private val launcherGallery=registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){uri:Uri?->
        if(uri!=null){
            currentImageUri=uri
            showImage()
        }else{
            Log.d("Photo Picker","Image not found")
            Toast.makeText(this,"Image not found",Toast.LENGTH_SHORT).show()
        }
    }

    private fun startGallery() {
//        Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
    }

    private fun startCameraX() {
        Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
    }

    private fun uploadImage() {
        Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
    }

    private fun showImage(){
        currentImageUri?.let{
            Log.d("Image URI","Show image from URI : $it")
            binding.previewImageView.setImageURI(it)
        }
    }

}
