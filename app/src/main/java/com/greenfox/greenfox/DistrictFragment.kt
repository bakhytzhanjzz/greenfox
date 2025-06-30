package com.greenfox.greenfox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DistrictFragment : Fragment() {

    private lateinit var tvDistrictName: TextView
    private lateinit var tvDistrictType: TextView
    private lateinit var rvResorts: RecyclerView

    private var district: District? = null

    companion object {
        private const val ARG_DISTRICT = "district"

        fun newInstance(district: District): DistrictFragment {
            val fragment = DistrictFragment()
            val args = Bundle().apply {
                putParcelable(ARG_DISTRICT, district)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            district = args.getParcelable(ARG_DISTRICT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_district, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupData()
    }

    private fun initViews(view: View) {
        tvDistrictName = view.findViewById(R.id.tv_district_name)
        tvDistrictType = view.findViewById(R.id.tv_district_type)
        rvResorts = view.findViewById(R.id.rv_resorts)
    }

    private fun setupData() {
        district?.let { dist ->
            tvDistrictName.text = dist.name
            tvDistrictType.text = dist.type

            val adapter = ResortsAdapter(dist.resorts) { resort ->
                (activity as? ExploreActivity)?.navigateToResortDetail(resort)
            }

            rvResorts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvResorts.adapter = adapter
        }
    }
}