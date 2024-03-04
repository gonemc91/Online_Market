package com.em.catalog.presentation.catalog.utils

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.em.catalog.R
import android.view.LayoutInflater as LayoutInflater1


const val ARG_OBJECT = "object"

class NumberFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater1, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(R.id.textView)
            textView.text = getInt(ARG_OBJECT).toString()
        }
    }

}