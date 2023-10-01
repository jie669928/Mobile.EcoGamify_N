//package com.example.mobileecogamify
//
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
//
///**
// * A simple [Fragment] subclass.
// * Use the [CommunityFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
//class CommunityFragment : Fragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_community, container, false)
//    }
//
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment CommunityFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            CommunityFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
//}
package com.example.mobileecogamify
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.Toast
//import androidx.cardview.widget.CardView
//import androidx.fragment.app.Fragment
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.core.view.ViewCompat
//import androidx.core.view.updatePadding
//import androidx.core.widget.NestedScrollView
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.firestore.FirebaseFirestore
//
//class CommunityFragment : Fragment() {
//    private var currentUser: FirebaseUser? = null
//    private lateinit var mAuth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Initialize Firebase Authentication
//        mAuth = FirebaseAuth.getInstance()
//        currentUser = mAuth.currentUser
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val rootView = inflater.inflate(R.layout.fragment_community, container, false)
//
//        // Discover and Following Buttons
//        val btnDiscover = rootView.findViewById<Button>(R.id.btnDiscover)
//        val btnFollowing = rootView.findViewById<Button>(R.id.btnPost)
//        val db = FirebaseFirestore.getInstance()
//
//        // Set an OnClickListener for the Discover button
//        btnDiscover.setOnClickListener {
//            // Navigate to DiscoverActivity (replace with the correct activity)
//            val intent = Intent(requireContext(), DiscoverActivity::class.java)
//            startActivity(intent)
//        }
//
//        // Other UI and functionality specific to CommunityFragment
//        // ...
//
//        // Image Click Listeners (replace with your logic)
//        val hotCommunity1 = rootView.findViewById<ImageView>(R.id.hot_community1)
//        val hotCommunity2 = rootView.findViewById<ImageView>(R.id.hot_community2)
//        val hotCommunity3 = rootView.findViewById<ImageView>(R.id.hot_community3)
//
//        hotCommunity1.setOnClickListener {
//            // Handle the click for hotCommunity1
//            // You can start a new activity here
//            val intent = Intent(requireContext(), HotCommunity1Activity::class.java)
//            currentUser?.uid?.let { userId ->
//                intent.putExtra("userId", userId)
//            }
//            startActivity(intent)
//        }
//
//        // Other click listeners
//        // ...
//
//        return rootView
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//    }
//}


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class CommunityFragment : Fragment() {
    private var currentUser: FirebaseUser? = null
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        // Initialize Firebase Authentication
        currentUser = FirebaseAuth.getInstance().currentUser

        // Discover and Following Buttons
        val btnDiscover = view.findViewById<Button>(R.id.btnDiscover)

        val db = FirebaseFirestore.getInstance()

        btnDiscover.setOnClickListener {
            // Navigate to DiscoverActivity
            val intent = Intent(requireContext(), CommunityFragment::class.java)
            startActivity(intent)
        }



        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance()

        // Check if a user is currently signed in
        if (mAuth.currentUser != null) {
            // currentUser is not null, it's safe to perform operations
            val user = mAuth.currentUser as FirebaseUser // Smart cast to FirebaseUser
            // Now you can use 'user' to access the user's information
            val userId = user.uid
            // Perform other operations with the user
        } else {
            // Handle the case when there is no signed-in user
            // You can display a message or navigate to the login screen
        }

        if (currentUser != null) {
            val userId = currentUser!!.uid
            val postsCollection = db.collection("posts")

            // Query Firestore for posts of the current user
            postsCollection.whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    // Iterate through the querySnapshot to display user-specific posts
                    for (document in querySnapshot) {
                        val content = document.getString("content")
                        val imageUrl = document.getString("imageUrl")
                        // Display or process posts as needed
                    }
                }
                .addOnFailureListener { e ->
                    // Handle any errors
                    showToast("Failed to fetch user-specific posts.")
                }
        } else {
            // Handle the case where there's no authenticated user
            showToast("User not authenticated. Please log in.")
        }

        // Hot Topic Card
        val cardHotTopic = view.findViewById<CardView>(R.id.cardHotTopic)

        cardHotTopic.setOnClickListener {
            // Navigate to HotTopicActivity
            val intent = Intent(requireContext(), HotTopicActivity::class.java)
            startActivity(intent)
        }



        val btnPost = view.findViewById<Button>(R.id.btnPost)

        btnPost.setOnClickListener {
            val intent = Intent(requireContext(), PostActivity::class.java)
            startActivity(intent)
        }


        // Community Card
        val cardCommunity = view.findViewById<CardView>(R.id.cardCommunity)

        cardCommunity.setOnClickListener {
            // Navigate to HotCommunityActivity
            val intent = Intent(requireContext(), HotCommunityActivity::class.java)
            startActivity(intent)
        }

        // Write Something to Share
        val editTextShare = view.findViewById<View>(R.id.editTextShare)

        editTextShare.setOnClickListener {
            // Navigate to CreatePostActivity (or the relevant activity to create a post)
            val intent = Intent(requireContext(), CreatePostActivity::class.java)
            startActivity(intent)
        }

        // Bottom Navigation Bar (Your navigation logic can be added here)
        // ...



        // Image Click Listeners
        val hotCommunity1 = view.findViewById<ImageView>(R.id.hot_community1)
        val hotCommunity2 = view.findViewById<ImageView>(R.id.hot_community2)
        val hotCommunity3 = view.findViewById<ImageView>(R.id.hot_community3)

        hotCommunity1.setOnClickListener {
            // Handle the click for hotTopic1
            // You can start a new activity here
            val intent = Intent(requireContext(), HotCommunity1Activity::class.java)
            currentUser?.uid?.let { userId ->
                intent.putExtra("userId", userId)
            }
            startActivity(intent)
        }

        hotCommunity2.setOnClickListener {
            // Handle the click for hotTopic2
            // You can start a new activity here
            val intent = Intent(requireContext(), HotCommunity2Activity::class.java)
            currentUser?.uid?.let { userId ->
                intent.putExtra("userId", userId)
            }
            startActivity(intent)
        }

        hotCommunity3.setOnClickListener {
            // Handle the click for hotTopic3
            // You can start a new activity here
            val intent = Intent(requireContext(), HotCommunity3Activity::class.java)
            currentUser?.uid?.let { userId ->
                intent.putExtra("userId", userId)
            }
            startActivity(intent)
        }

        return view
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}