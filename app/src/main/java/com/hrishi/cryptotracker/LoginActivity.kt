package com.hrishi.cryptotracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.hrishi.cryptotracker.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding:ActivityLoginBinding

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            goto_Main_Activity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener {
            create_user(binding.loginEmail.text.toString(), binding.loginPass.text.toString())
        }

        binding.btnLogin.setOnClickListener {
            login(binding.loginEmail.text.toString(), binding.loginPass.text.toString())
        }






    }


    private fun create_user(email:String, password:String){

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    create_error_dialog("Please verify email by clicking on the link sent to your Mail ID.")
                    auth.currentUser!!.sendEmailVerification()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun login(email: String, password: String){

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    if(auth.currentUser!!.isEmailVerified){
                        goto_Main_Activity()
                    }else{
                        create_error_dialog("Verification Mail sent. Please Verify Email")
                        auth.currentUser!!.sendEmailVerification()
                        auth.signOut()
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }

    //Creates a error dialog
    private fun create_error_dialog(title : String){


        val add_dialog = android.app.AlertDialog.Builder(this)
            .setTitle(title)
            .setPositiveButton("Ok") { _ , _ -> }
            .setCancelable(true).create()

        add_dialog.show()
    }


    //Closes this activity, Opens Main Activity
    private fun goto_Main_Activity() {
        val MainActivity_intent = Intent(this, MainActivity::class.java)
        startActivity(MainActivity_intent)
        finish()
    }

}