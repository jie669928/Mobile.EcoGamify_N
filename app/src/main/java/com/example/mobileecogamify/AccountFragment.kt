package com.example.mobileecogamify
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {

    private lateinit var logoutButton: Button
    private lateinit var updateButton: Button
    private lateinit var showProfileButton: Button
    private lateinit var showHistoryButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        // Initialize the log-out button
        logoutButton = view.findViewById(R.id.btn_logout)

        // Set a click listener for the log-out button
        logoutButton.setOnClickListener {
            // Handle log-out here
            val firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()

            // After signing out, you can navigate to the login or registration screen
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Optional: Finish the current activity
        }

            // Initialize the log-out button
        updateButton = view.findViewById(R.id.updateProfileBtn)

            // Set a click listener for the log-out button
        updateButton.setOnClickListener {
                // Handle log-out here

            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        showProfileButton = view.findViewById(R.id.showProfileBtn)
        showProfileButton.setOnClickListener {
                // Handle log-out here

             val intent = Intent(requireContext(), ProfileHistoryActivity::class.java)
             startActivity(intent)

        }
        showHistoryButton = view.findViewById(R.id.historyBtn)
        showHistoryButton.setOnClickListener {
            // Handle log-out here

            val intent = Intent(requireContext(), History::class.java)
            startActivity(intent)

        }

        return view
    }
}
