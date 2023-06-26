package com.example.okhttp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.okhttp.databinding.FragmentFirstBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.StringBuilder
import java.net.URL
import kotlin.math.log
import android.widget.ArrayAdapter
import android.widget.ListView


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.urlButton.setOnClickListener {
            val URL : String = "https://jsonplaceholder.typicode.com/posts"
            if (URL.isNotEmpty()) {
                val movieDatabaseFetch = OkHttpClient()

                var request = Request.Builder()
                    .url(URL)
                    .build()

                movieDatabaseFetch.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace();
                    }

                    override fun onResponse(call: Call, response: Response) {
                        // log("Response", "Recieved Response from server");
                        response.use {
                            if (!response.isSuccessful){
                           //     log.e("HTTP Error", "Something didnt loat, or wasnt successfull");
                            } else {
                                /*
                                var data = response.toString()
                                var jArray = JSONArray(data)
                                for (i in 0..jArray.length()-1){
                                    var jobject = jArray.getJSONObject(i)
                                    var userid = jobject.getInt("userId")
                                    var id = jobject.getInt("id")
                                    var title = jobject.getString("title")
                                    var body = jobject.getString("body")
                                    Log.e("UserId", userid.toString())
                                    Log.e("id", id.toString())
                                    Log.e("title", title.toString())
                                    Log.e("body", body.toString())
                                } */

                                val body = response?.body?.string()
                                binding.urlDump.text = body
                            }
                        }
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}