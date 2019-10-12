package zojae031.portfolio.main

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_main.*
import zojae031.portfolio.BaseActivity
import zojae031.portfolio.Injection
import zojae031.portfolio.R
import zojae031.portfolio.databinding.ActivityMainBinding
import zojae031.portfolio.main.dialog.MainDialog

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel by lazy {
        ViewModelProviders.of(this@MainActivity, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(Injection.getRepository(applicationContext)) as T
            }
        }).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            viewModel = mainViewModel

            pager.apply {
                offscreenPageLimit = 2
                adapter = MainPagerAdapter(supportFragmentManager)
                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {

                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {

                    }

                    override fun onPageSelected(position: Int) {
                        indicator.selectDot(position)
                    }
                })

            }

            mainViewModel.error.observe(this@MainActivity, Observer {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            })
        }

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }

        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.menu)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        indicator.createDotPanel(
            pager.adapter!!.count,
            R.drawable.indicator_off,
            R.drawable.indicator_on,
            0
        )
        userBtn.setOnClickListener {
            MainDialog(this).show()
        }
        adView.loadAd(AdRequest.Builder().build())

    }


    override fun onResume() {
        super.onResume()
        mainViewModel.onResume()
        adView.resume()
    }

    override fun onPause() {
        mainViewModel.onPause()
        adView.pause()
        super.onPause()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(second)) {
            drawer.closeDrawers()
        } else super.onBackPressed()
    }

}

