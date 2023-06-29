package com.example.dangerdetector

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.dangerdetector.configs.APIClient
import com.example.dangerdetector.databinding.FragmentHomeBinding
import com.example.dangerdetector.databinding.FragmentProfileBinding
import com.example.dangerdetector.service.DangerDetectorService

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var dangerService: DangerDetectorService
    lateinit var activity: MainActivity
    lateinit var userNameText : TextView
    lateinit var logOutBtn : Button
    lateinit var changePasswordBtn : Button
    lateinit var changeUsernameBtn : Button

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        activity = requireActivity() as MainActivity
        dangerService = APIClient.getClient().create(DangerDetectorService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        userNameText = binding.profileScreenUserEmail
        logOutBtn = binding.logOutButton
        changePasswordBtn = binding.changePasswordButton
        changeUsernameBtn = binding.changeUsernameButton
        userNameText.text = activity.userName

        logOutBtn.setOnClickListener {
            val intent = Intent(requireContext(),LoginActivity::class.java)
            startActivity(intent)
        }

        changePasswordBtn.setOnClickListener {
            val intent = Intent(requireContext(),ChangePasswordActivity::class.java)
            val userToken = activity.userToken
            intent.putExtra("token",userToken)
            startActivity(intent)
        }

        changeUsernameBtn.setOnClickListener {
            val intent = Intent(requireContext(),ChangeActivity::class.java)
            val userToken = activity.userToken
            intent.putExtra("token",userToken)
            startActivity(intent)
        }
        return root
    }

    override fun onStart() {

        super.onStart()
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}