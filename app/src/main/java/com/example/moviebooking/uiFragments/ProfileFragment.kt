package com.example.moviebooking.uiFragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.moviebooking.HomeActivity
import com.example.moviebooking.databinding.FragmentProfileBinding
import com.example.moviebooking.model.User
import com.example.moviebooking.viewModel.ItemViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import java.util.*
import kotlin.concurrent.schedule

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var navController: NavController
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var uri: Uri
    private var isImagePicked = false
    private lateinit var updatedImageUrl: String
    private lateinit var updatedImageName: String
    private val itemViewModel: ItemViewModel by activityViewModels()
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = firebaseStorage.reference
    private var email: String = firebaseAuth.currentUser?.email.toString()
    var user: User? = null
    private var database: DatabaseReference =  Firebase.database.reference.child("users").child(email.replace('.',','))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Profile"
        navController = view.findNavController()
        register()
        val databaseListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                user = dataSnapshot.getValue<User>()
                updatedImageName = user!!.imageName
                updatedImageUrl = user!!.imageUrl
                updateUI(user)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity?.applicationContext ,databaseError.toException().toString(),Toast.LENGTH_SHORT).show() }
        }
        database.addValueEventListener(databaseListener)

        binding.btnEditName.setOnClickListener {
            BottomSheetFragment().show(requireActivity().supportFragmentManager,"Input name")
        }

        binding.btnEditPhone.setOnClickListener {
            BottomSheetPhoneFragment().show(requireActivity().supportFragmentManager,"Input Phone no")
        }

        itemViewModel.name.observe(viewLifecycleOwner, androidx.lifecycle.Observer { item ->
            binding.textViewName.text = item
        })

        itemViewModel.phone.observe(viewLifecycleOwner, androidx.lifecycle.Observer { item ->
            binding.textViewPhone.text = item.toString()
        })

        binding.btnSave.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnSave.isClickable = false
            if(isImagePicked)
                updatePhoto()
            val updatedUser: User? = user?.let { it1 ->
                User(binding.textViewName.text.toString(),
                    binding.textViewEmail.text.toString(),
                    binding.textViewPhone.text.toString(),
                    it1.orders, updatedImageUrl, updatedImageName)
            }
                Log.d("tag", updatedUser?.imageUrl.toString() )
                database.setValue(updatedUser).addOnCompleteListener { task ->
                    if(task.isSuccessful)
                        updateUI(updatedUser)
                    else
                        Toast.makeText(activity?.applicationContext,task.exception?.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        binding.profileImage.setOnClickListener {
            imageChooser()
        }
        }

        private fun imageChooser() {
//                        activity?.applicationContext?.let {
//                            ContextCompat.checkSelfPermission(
//                                it,
//                                android.Manifest.permission.READ_EXTERNAL_STORAGE
//                            )
//                        } != PackageManager.PERMISSION_GRANTED
//
//                    ActivityCompat.requestPermissions(
//                        HomeActivity(),
//                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
//                        1
//                    )
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                activityResultLauncher.launch(intent)
            }

    private fun register() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK && data != null) {
                uri = data.data!!
                Picasso.get().load(uri).into(binding.profileImage).toString()
                isImagePicked = true
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            activityResultLauncher.launch(intent)
        }
    }

    private fun updateUI(user: User?) {
        binding.textViewName.text = user?.name
        binding.textViewEmail.text = user?.email
        binding.textViewPhone.text = user?.phone
        if(isImagePicked)
            Picasso.get().load(uri).into(binding.profileImage, object : Callback {
                override fun onSuccess() {
                    binding.progressBar.visibility = View.INVISIBLE
                }
                override fun onError(e: Exception?) {
                    Toast.makeText(context, "Error in fetching profile image", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        else if(updatedImageUrl != "") {
            Picasso.get().load(updatedImageUrl).into(binding.profileImage, object : Callback {
                override fun onSuccess() {
                    binding.progressBar.visibility = View.INVISIBLE
                }
                override fun onError(e: Exception?) {
                    Toast.makeText(context, "Error in fetching profile image", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave.isClickable = true
    }
    private fun updatePhoto() {
        updatedImageName = if(user!!.imageName == "")
            UUID.randomUUID().toString()
        else user!!.imageName
        Log.d("imageName", updatedImageName)
        val imageReference = storageReference.child("images").child(updatedImageName)
        imageReference.putFile(uri).addOnSuccessListener {
            val downloadReference = storageReference.child("images").child(updatedImageName)
            downloadReference.downloadUrl.addOnSuccessListener { uri ->
                updatedImageUrl = uri.toString()
                Log.d("imageUrl", updatedImageUrl)
            }
        }.addOnFailureListener {
            Toast.makeText(activity?.applicationContext, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
}