package zojae031.portfolio.main

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseActivity
import zojae031.portfolio.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            viewModel = mainViewModel.apply {
                error.observe(this@MainActivity, Observer {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                })
            }

            activity = this@MainActivity

            pager.apply {
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

        adView.loadAd(AdRequest.Builder().build())

    }

    fun showDialog() {
        MainDialog().show(supportFragmentManager, "mainDialog")
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getUserList()
        mainViewModel.getDataList()
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

