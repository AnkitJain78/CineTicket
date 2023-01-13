package com.example.moviebooking.authenticationScreens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.moviebooking.HomeActivity
import com.example.moviebooking.R
import com.example.moviebooking.databinding.FragmentSignUpBinding
import com.example.moviebooking.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    lateinit var navController: NavController
    private val auth = FirebaseAuth.getInstance()
    private var database = FirebaseDatabase.getInstance()
    private lateinit var user: User
    private val ref = database.reference.child("users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = view.findNavController()
        binding.progressBarSignUp.visibility = View.INVISIBLE
        navController = view.findNavController()
        binding.textViewGoToLogin.setOnClickListener{
            navController.navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        binding.buttonSignUp.setOnClickListener{
            if (!isAllDetailsNotFilled()) {
                val email = binding.editTextEmailSignUp.text.toString()
                val password = binding.editTextPasswordSignUp.editText?.text.toString()
                signUp(email, password)
            }
            else
                Toast.makeText(activity?.applicationContext, "Please fill all details", Toast.LENGTH_SHORT).show()

        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun isAllDetailsNotFilled(): Boolean {
        val email = binding.editTextEmailSignUp.text.toString()
        val password = binding.editTextPasswordSignUp.editText?.text.toString()
        val phone = binding.editTextPhone.editText?.text.toString()
        val name = binding.editTextName.editText?.text.toString()
        user = User(name,email,phone)
        return name.isEmpty() || password.isEmpty() || phone.isEmpty() || email.isEmpty()
    }

    private fun signUp(email: String, password: String){
        binding.progressBarSignUp.visibility = View.VISIBLE
        binding.buttonSignUp.isClickable = false
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                binding.progressBarSignUp.visibility = View.INVISIBLE
                binding.buttonSignUp.isClickable = true
                ref.child(email.replace('.',',')).setValue(user)
                Toast.makeText(activity?.applicationContext, "Account Created successfully.", Toast.LENGTH_SHORT).show()
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            else {
                Toast.makeText(
                    activity?.applicationContext,
                    task.exception?.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
                binding.progressBarSignUp.visibility = View.INVISIBLE
                binding.buttonSignUp.isClickable = true
            }
        }
    }
}