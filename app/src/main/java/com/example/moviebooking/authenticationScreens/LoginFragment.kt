package com.example.moviebooking.authenticationScreens

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.moviebooking.HomeActivity
import com.example.moviebooking.R
import com.example.moviebooking.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    lateinit var navController: NavController
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private val auth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
        binding.progressBarLogin.visibility = View.INVISIBLE
        val googleSignIn: TextView = binding.buttonSignInWithGoogle.getChildAt(0) as TextView
        googleSignIn.textSize = 18F
        googleSignIn.setTextColor(Color.BLACK)

        registerActivity()
        binding.buttonSignInWithGoogle.setOnClickListener {
            signInWithGoogle()
        }

        navController = view.findNavController()                              // find nav controller
        binding.textViewGoToSignUp.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        binding.textViewGoToForgot.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_forgotPassword)
        }
        binding.buttonSignIn.setOnClickListener {
            val email = binding.editTextEmailLogin.editText?.text.toString()
            val password = binding.editTextPasswordLogin.editText?.text.toString()
            login(email, password)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun registerActivity() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                val resultCode = result.resultCode
                val data = result.data
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(data)
                    firebaseSignInWithGoogle(task)
                }
            })
    }

    private fun firebaseSignInWithGoogle(task: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            if (account != null) {
                updateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
                Toast.makeText(activity?.applicationContext, "Welcome back !!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(activity?.applicationContext,
                    task.exception?.localizedMessage?.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(resources.getString(R.string.default_web_client_id))
            .requestEmail().build()

        googleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) }!!
        signInIntent()
    }

    private fun signInIntent() {
        val intent: Intent = googleSignInClient.signInIntent
        activityResultLauncher.launch(intent)
    }

    private fun login(email: String, password: String) {
        binding.progressBarLogin.visibility = View.VISIBLE
        binding.buttonSignIn.isClickable = false
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    activity?.applicationContext,
                    "Welcome back !!",
                    Toast.LENGTH_SHORT
                ).show()
                binding.progressBarLogin.visibility = View.INVISIBLE
                binding.buttonSignIn.isClickable = true
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()

            } else {
                Toast.makeText(
                    activity?.applicationContext, task.exception?.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
                binding.progressBarLogin.visibility = View.INVISIBLE
                binding.buttonSignIn.isClickable = true
            }
        }
    }
    fun getEmail(): String{
        return binding.editTextEmailLogin.editText?.text.toString().replace('.',',')
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user!=null) {
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}