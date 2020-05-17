package com.example.top10

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ViewModel(v: View) {
    val tvName: TextView = v.findViewById(R.id.tvName)
    val tvArtist: TextView = v.findViewById(R.id.tvArtist)
    val tvSummary: TextView = v.findViewById(R.id.tvSummary)
}

class FeedAdapter(
    context: Context,
    private val resource: Int,
    private val applications: List<FeedEntry>
) : ArrayAdapter<FeedEntry>(context, resource) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewModel: ViewModel

        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewModel = ViewModel(view)
            view.tag = viewModel
        } else {
            view = convertView
            viewModel = view.tag as ViewModel
        }

        val currentApp = applications[position]

        viewModel.tvName.text = currentApp.name
        viewModel.tvArtist.text = currentApp.artist
        viewModel.tvSummary.text = currentApp.summary
        return view
    }

    override fun getCount(): Int {
        return applications.size
    }
}