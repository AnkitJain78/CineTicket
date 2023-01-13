package com.example.moviebooking.uiFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviebooking.networking.BuzzService
import com.example.moviebooking.networking.model.Article
import com.example.moviebooking.networking.model.Buzz
import com.example.moviebooking.adapter.BuzzAdapter
import com.example.moviebooking.databinding.FragmentBuzzBinding
import com.example.moviebooking.networking.cache
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuzzFragment : Fragment() {
    private lateinit var binding: FragmentBuzzBinding
    var buzzList = ArrayList<Article>()
    var buzzList2 = ArrayList<Article>()
    var buzzList3 = ArrayList<Article>()
    var buzzList4 = ArrayList<Article>()
    private lateinit var adapter1: BuzzAdapter
    private lateinit var adapter2: BuzzAdapter
    private lateinit var adapter3: BuzzAdapter
    private lateinit var adapter4: BuzzAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBuzzBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Buzz"
        binding.recyclerView1.layoutManager =
            LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView2.layoutManager =
            LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView3.layoutManager =
            LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView4.layoutManager =
            LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        val adapter1 = activity?.let { BuzzAdapter(buzzList, it.applicationContext, binding.recyclerView1) }
        val adapter2 =
            activity?.let { BuzzAdapter(buzzList2, it.applicationContext, binding.recyclerView2) }
        val adapter3 =
            activity?.let { BuzzAdapter(buzzList3, it.applicationContext, binding.recyclerView3) }
        val adapter4 =
            activity?.let { BuzzAdapter(buzzList4, it.applicationContext, binding.recyclerView4) }
        binding.recyclerView1.adapter = adapter1
        binding.recyclerView2.adapter = adapter2
        binding.recyclerView3.adapter = adapter3
        binding.recyclerView4.adapter = adapter4
        activity?.let { cache(it.applicationContext) }
        getBuzz("", 1, 1)
        getBuzz("entertainment", 1,  2)
        getBuzz("technology", 1, 3)
        getBuzz("sports", 1, 4)
    }

    private fun getBuzz(category: String, page: Int, section: Int) {
        val buzz = BuzzService.buzzInstance.getGetHeadlines(category,page)
        buzz.enqueue(object : Callback<Buzz> {
            override fun onResponse(call: Call<Buzz>, response: Response<Buzz>) {
                when (section) {
                    1 -> {
                        response.body()?.let { buzzList.addAll(it.articles) }
                        binding.recyclerView1.adapter?.notifyDataSetChanged()
                    }
                    2 -> {
                        response.body()?.let { buzzList2.addAll(it.articles) }
                        binding.recyclerView2.adapter?.notifyDataSetChanged()
                    }
                    3 -> {
                        response.body()?.let { buzzList3.addAll(it.articles) }
                        binding.recyclerView3.adapter?.notifyDataSetChanged()
                    }
                    4 -> {
                        response.body()?.let { buzzList4.addAll(it.articles) }
                        binding.recyclerView4.adapter?.notifyDataSetChanged()
                    }
                }

            }

            override fun onFailure(call: Call<Buzz>, t: Throwable) {
                Log.e("error", "error in fetching")
            }
        })
    }
}