package com.example.moviebooking.authenticationScreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.moviebooking.R
import com.example.moviebooking.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : Fragment() {
    lateinit var binding: FragmentForgotPasswordBinding
    lateinit var navController: NavController
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        binding.progressBarForgotPassword.visibility = View.INVISIBLE
        binding.buttonResetPassword.setOnClickListener {
            binding.progressBarForgotPassword.visibility = View.VISIBLE
            binding.buttonResetPassword.isClickable = false
            val email: String = binding.editTextEmailForgotPassword.editText?.text.toString()
            resetPassword(email)
        }
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful){
                binding.progressBarForgotPassword.visibility = View.INVISIBLE
                binding.buttonResetPassword.isClickable = true
                Toast.makeText(activity?.applicationContext, "password reset mail sent successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_forgotPassword_to_loginFragment)
            }
            else
                Toast.makeText(activity?.applicationContext, task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
}