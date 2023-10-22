package com.sliit.docportal

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class PatientViewHolder(view:View):ViewHolder(view) {
    val cbName: CheckBox = view.findViewById(R.id.cbName)
    val tvCondition:TextView = view.findViewById(R.id.tvCondition)
    val tvMobile:TextView = view.findViewById(R.id.tvMobile)
    val ivDelete:ImageView = view.findViewById(R.id.ivDelete)

}