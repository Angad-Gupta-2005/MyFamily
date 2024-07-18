package com.angad.myfamily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemberAdapter(private val listMembers: List<MemberModel>) :RecyclerView.Adapter<MemberAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.ViewHolder {
        val infater = LayoutInflater.from(parent.context)
        val item = infater.inflate(R.layout.member_item, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: MemberAdapter.ViewHolder, position: Int) {
        val item = listMembers[position]
        holder.name.text = item.nameU
        holder.address.text = item.address
        holder.battery.text = item.battery
        holder.distance.text = item.distance
    }

    override fun getItemCount(): Int {
        return listMembers.size
    }

    class ViewHolder(private val item: View): RecyclerView.ViewHolder(item) {
        val imageUser = item.findViewById<ImageView>(R.id.ic_man)
        val name = item.findViewById<TextView>(R.id.name)
        val address = item.findViewById<TextView>(R.id.address)
        val battery = item.findViewById<TextView>(R.id.batteryP)
        val distance = item.findViewById<TextView>(R.id.msg_txt)

    }
}