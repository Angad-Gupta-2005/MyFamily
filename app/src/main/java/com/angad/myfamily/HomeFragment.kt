package com.angad.myfamily

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameU:String
        val listMembers = listOf(
            MemberModel(
                nameU = "Angad",
                address = "Mahatma Joytiba Phule nagar IIT market, Powai-400076",
                battery = "80%",
                distance = "210"
            ),
            MemberModel(
                nameU = "Ram",
                address = "17 street gandhi nagar near Dhramshala, Gorakhpur",
                battery = "55%",
                distance = "580"
            ),
            MemberModel(
                nameU = "Shyam",
                address = "Jai bhim office near tilak chok, Sant Nagar",
                battery = "75%",
                distance = "430"
            ),
            MemberModel(
                nameU = "Rajan",
                address = "Dream the mall near Bhandup Station",
                battery = "83%",
                distance = "500"
            ),
        )

        val adapter = MemberAdapter(listMembers)

        val recycler = requireView().findViewById<RecyclerView>(R.id.recycler_member)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}