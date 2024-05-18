package com.example.careplace


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LIstDoctorAdapter(private val originalDoctorsList: ArrayList<DUsers>) :
    RecyclerView.Adapter<LIstDoctorAdapter.DoctorsViewHolder>(), Filterable {

    private var filteredDoctorsList: ArrayList<DUsers> = originalDoctorsList

    inner class DoctorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val docNameTextView: TextView = itemView.findViewById(R.id.crd_name1)
        private val docspecTextView: TextView = itemView.findViewById(R.id.DocSpeclia)
        private val docprofilebtn: TextView = itemView.findViewById(R.id.view_prof_btn1)
        private val doctorRate: RatingBar = itemView.findViewById(R.id.ratingBar_incard1)
        private val Doctor_location: TextView = itemView.findViewById(R.id.DocLocation)
        lateinit var DoctorId: String
        lateinit var DoctorName: String
        lateinit var Doctor_spec: String
        lateinit var Doctor_loc: String

        init {
            // Set click listener in the ViewHolder constructor
            docprofilebtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val context: Context = itemView.context
                    // Navigate to DoctorProfileSchedule activity
                    val intent = Intent(context, DoctorProfileSchedule::class.java)
                    intent.putExtra("DoctorId", DoctorId)
                    intent.putExtra("DoctorName", DoctorName)
                    intent.putExtra("Doctor_spec", Doctor_spec)
                    if (::Doctor_loc.isInitialized) {  // Check if Doctor_loc is initialized
                        intent.putExtra("Doctor_loc", Doctor_loc)
                    }
                    context.startActivity(intent)
                }
            }
        }

        fun bind(user: DUsers) {
            docNameTextView.text = user.DName
            docspecTextView.text = user.Specialization
            DoctorId = user.DUID!!
            DoctorName = user.DName!!
            Doctor_spec = user.Specialization!!
            val database = FirebaseDatabase.getInstance()
            val ratingsRef = database.getReference("doctor_ratings").child(DoctorId)
            ratingsRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val ratingValue = snapshot.getValue(Float::class.java)
                        if (ratingValue != null) {
                            doctorRate.rating = ratingValue
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error if necessary
                }
            })

            val locatRef = database.getReference("/DUser/$DoctorId/Location")
            locatRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val location = snapshot.getValue(String::class.java)
                        if (location != null) {
                            Doctor_location.text = location
                            Doctor_loc = location
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error if necessary
                }
            })
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
                            if (user.DName?.toLowerCase()?.contains(searchQuery) == true) {
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



