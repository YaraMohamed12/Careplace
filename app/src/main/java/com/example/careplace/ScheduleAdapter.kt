package com.example.careplace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ScheduleAdapter(context: Context, private val scheduleList: List<Schedule>) : ArrayAdapter<Schedule>(context, 0, scheduleList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val schedule = scheduleList[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView = convertView ?: inflater.inflate(R.layout.appointment_card, parent, false)
        itemView.findViewById<TextView>(R.id.date_of_appointment).text = schedule.date
        itemView.findViewById<TextView>(R.id.time_of_appointment).text = schedule.time

        return itemView
    }

}
