package com.greenfox.greenfox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ResortDetailActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnFavorite: ImageButton
    private lateinit var ivResortImage: ImageView
    private lateinit var tvResortName: TextView
    private lateinit var tvResortType: TextView
    private lateinit var tvDescription: TextView
    private lateinit var rvOptions: RecyclerView
    private lateinit var btnBookNow: Button

    private var resortOptions: List<ResortOption> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resort_detail)

        initViews()
        setupData()
        setupListeners()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btn_back)
        btnFavorite = findViewById(R.id.btn_favorite)
        ivResortImage = findViewById(R.id.iv_resort_image)
        tvResortName = findViewById(R.id.tv_resort_name)
        tvResortType = findViewById(R.id.tv_resort_type)
        tvDescription = findViewById(R.id.tv_description)
        rvOptions = findViewById(R.id.rv_options)
        btnBookNow = findViewById(R.id.btn_book_now)
    }

    private fun setupData() {
        val resortName = intent.getStringExtra("resort_name") ?: ""
        val resortType = intent.getStringExtra("resort_type") ?: ""
        val resortImage = intent.getIntExtra("resort_image", 0)
        resortOptions = intent.getParcelableArrayListExtra<ResortOption>("resort_options") ?: emptyList()

        tvResortName.text = resortName
        tvResortType.text = resortType
        ivResortImage.setImageResource(resortImage)

        // Set description based on resort name
        tvDescription.text = getDescriptionForResort(resortName)

        setupOptionsRecyclerView()
    }

    private fun getDescriptionForResort(resortName: String): String {
        return when (resortName) {
            "ALMA-TAU" -> "Прекрасная зона отдыха в живописном месте с современными удобствами и комфортными условиями для семейного отдыха."
            "Kaskasu Eco Village" -> "Экологически чистый поселок с уникальной архитектурой и близостью к природе."
            "Шымбулак" -> "Современный горнолыжный курорт с отличными трассами и захватывающими видами на горы."
            "Кок-Жайляу" -> "Природный парк с чистым воздухом и возможностями для активного отдыха на природе."
            else -> "Отличное место для отдыха с комфортными условиями и красивой природой."
        }
    }

    private fun setupOptionsRecyclerView() {
        val adapter = ResortOptionsDetailAdapter(resortOptions) { option ->
            proceedToBooking(option)
        }
        rvOptions.layoutManager = LinearLayoutManager(this)
        rvOptions.adapter = adapter
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            onBackPressed()
        }

        btnFavorite.setOnClickListener {
            // Toggle favorite status
            // TODO: Implement favorite functionality
        }

        btnBookNow.setOnClickListener {
            if (resortOptions.isNotEmpty()) {
                proceedToBooking(resortOptions.first())
            }
        }
    }

    private fun proceedToBooking(option: ResortOption) {
        val intent = Intent(this, BookingActivity::class.java).apply {
            putExtra("resort_name", tvResortName.text.toString())
            putExtra("resort_type", tvResortType.text.toString())
            putExtra("option_name", option.name)
            putExtra("option_image", option.image)
        }
        startActivity(intent)
    }
}