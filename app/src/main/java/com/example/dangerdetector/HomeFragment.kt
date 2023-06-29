package com.example.dangerdetector

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.example.dangerdetector.adapter.NotificationAdapter
import com.example.dangerdetector.configs.APIClient
import com.example.dangerdetector.databinding.FragmentHomeBinding
import com.example.dangerdetector.model.NotificationInfo
import com.example.dangerdetector.model.NotificationModel
import com.example.dangerdetector.service.DangerDetectorService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var notificationListView : ListView
    lateinit var refreshBtn : ImageButton
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var dangerService: DangerDetectorService
    lateinit var activity: MainActivity
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        notificationListView = binding.notificationListView
        refreshBtn = binding.refreshBtn

        val userToken: String? = activity.getToken()
        getNotifications(userToken!!)

        refreshBtn.setOnClickListener {
            getNotifications(userToken)
        }
        return root
    }

    fun getNotifications(userToken : String){
        dangerService.getNotifications("Bearer "+userToken).enqueue(object : Callback<NotificationInfo>{
            override fun onResponse(
                call: Call<NotificationInfo>, response: Response<NotificationInfo>) {
                val notResponse = response.body()
                if(notResponse!=null){
                    val notificationList = notResponse.result
                    val notificationAdapter = NotificationAdapter(requireActivity(),notificationList)
                    notificationListView.adapter = notificationAdapter
                    notificationListView.setOnItemClickListener { adapterView, view, position, l ->
                        val intent = Intent(requireActivity(),NotificationDetail::class.java)
                        val replacedDate = notificationList.get(position).createdAt.replace('T',' ')
                        val dateText = replacedDate.subSequence(0,19)
                        intent.putExtra("name",notificationList.get(position).name)
                        intent.putExtra("description",notificationList.get(position).description)
                        intent.putExtra("imgUrl",notificationList.get(position).imageUrl)
                        intent.putExtra("date",dateText)
                        startActivity(intent)
                    }
                }
            }
            override fun onFailure(call: Call<NotificationInfo>, t: Throwable) {
                Log.d("FAILLL","Error")
            }
        })
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}