package com.greenfox.greenfox

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class ExploreActivity : AppCompatActivity() {

    private lateinit var vpDistricts: ViewPager2
    private lateinit var btnBack: ImageButton
    private lateinit var btnSearch: ImageButton
    private lateinit var btnFavorites: ImageButton
    private lateinit var btnBooking: ImageButton
    private lateinit var btnProfile: ImageButton

    private val districts = listOf(
        District("Толебийский", "Район", listOf(
            Resort("ALMA-TAU", "Зона отдыха", R.drawable.resort_alma_tau_1, listOf(
                ResortOption("Финский домик", R.drawable.finnish_house),
                ResortOption("VIP Коттедж", R.drawable.vip_cottage)
            )),
            Resort("Kaskasu Eco Village", "Эко поселок", R.drawable.kaskasu_village, listOf(
                ResortOption("Семейный дом", R.drawable.family_house),
                ResortOption("Студия", R.drawable.studio)
            ))
        )),
        District("Медеуский", "Район", listOf(
            Resort("Шымбулак", "Горнолыжный курорт", R.drawable.shymbulak, listOf(
                ResortOption("Горный шале", R.drawable.mountain_chalet),
                ResortOption("Апартаменты", R.drawable.apartments)
            ))
        )),
        District("Алатауский", "Район", listOf(
            Resort("Кок-Жайляу", "Природный парк", R.drawable.kok_zhailau, listOf(
                ResortOption("Эко домик", R.drawable.eco_house),
                ResortOption("Глэмпинг", R.drawable.glamping)
            ))
        ))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        initViews()
        setupViewPager()
        setupBottomNavigation()
    }

    private fun initViews() {
        vpDistricts = findViewById(R.id.vp_districts)
        btnBack = findViewById(R.id.btn_back)
        btnSearch = findViewById(R.id.btn_search)
        btnFavorites = findViewById(R.id.btn_favorites)
        btnBooking = findViewById(R.id.btn_booking)
        btnProfile = findViewById(R.id.btn_profile)

        btnBack.setOnClickListener { onBackPressed() }
    }

    private fun setupViewPager() {
        val adapter = DistrictPagerAdapter(this, districts)
        vpDistricts.adapter = adapter
        vpDistricts.offscreenPageLimit = 1
    }

    private fun setupBottomNavigation() {
        btnSearch.setOnClickListener {
            // Current activity - highlight this button
        }

        btnFavorites.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }

        btnBooking.setOnClickListener {
            startActivity(Intent(this, BookingActivity::class.java))
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    fun navigateToResortDetail(resort: Resort) {
        val intent = Intent(this, ResortDetailActivity::class.java).apply {
            putExtra("resort_name", resort.name)
            putExtra("resort_type", resort.type)
            putExtra("resort_image", resort.mainImage)
            putParcelableArrayListExtra("resort_options", ArrayList(resort.options))
        }
        startActivity(intent)
    }

    private inner class DistrictPagerAdapter(
        activity: AppCompatActivity,
        private val districts: List<District>
    ) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = districts.size

        override fun createFragment(position: Int): Fragment {
            return DistrictFragment.newInstance(districts[position])
        }
    }
}