package com.example.careplace


import android.widget.Filter
import android.widget.Filterable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LIstDoctorAdapter(private val originalDoctorsList: ArrayList<DUsers>) :
    RecyclerView.Adapter<LIstDoctorAdapter.DoctorsViewHolder>(), Filterable {

    private var filteredDoctorsList: ArrayList<DUsers> = originalDoctorsList

    inner class DoctorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val docNameTextView: TextView = itemView.findViewById(R.id.crd_name1)
        private val docspecTextView: TextView = itemView.findViewById(R.id.crd_spec1)


        fun bind(user: DUsers) {
            docNameTextView.text = user.DName
            docspecTextView.text = user.Specialization

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.doc_card, parent, false)
        return DoctorsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredDoctorsList.size
    }

    override fun onBindViewHolder(holder: DoctorsViewHolder, position: Int) {
        val currentUser = filteredDoctorsList[position]
        holder.bind(currentUser)
    }

    // Custom filter logic for search functionality
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults = ArrayList<DUsers>()

                constraint?.let { query ->
                    if (query.isEmpty()) {
                        filteredResults.addAll(originalDoctorsList)
                    } else {
                        val searchQuery = query.toString().toLowerCase().trim()
                        for (user in originalDoctorsList) {
                            if (user.DName!!.toLowerCase().contains(searchQuery)) {
                                filteredResults.add(user)
                            }
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredResults
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredDoctorsList = results?.values as ArrayList<DUsers>
                notifyDataSetChanged()
            }
        }
    }
}

