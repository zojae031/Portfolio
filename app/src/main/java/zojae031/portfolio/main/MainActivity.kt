package zojae031.portfolio.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_main.*
import zojae031.portfolio.R

class MainActivity : AppCompatActivity(), MainContract.View {


    private val presenter = MainPresenter(this@MainActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onCreate()

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }

        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.menu)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        with(pager) {
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
        indicator.createDotPanel(
            pager.adapter!!.count,
            R.drawable.indicator_off,
            R.drawable.indicator_on,
            0
        )
        loadingAd()
    }

    override fun showToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
    }

    override fun showUserImage() {
        Glide.with(this@MainActivity)
            .load("https://avatars2.githubusercontent.com/u/31091115?s=400&u=6db0ee4cb9b8a82cef9fe3a4196b437dc537339d&v=4")
            .error(R.drawable.picture)
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
            .into(image)
    }

    private fun loadingAd() {
        adView.loadAd(AdRequest.Builder().build())
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    override fun onPause() {
        adView.pause()
        super.onPause()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }
}
