package zojae031.portfolio.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_main.*
import zojae031.portfolio.Injection
import zojae031.portfolio.R

class MainActivity : AppCompatActivity(), MainContract.View {


    private val presenter by lazy {
        MainPresenter(
            this,
            Injection.getRepository(applicationContext),
            Injection.getNetworkUtil(applicationContext)
        )
    }

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
        indicator.createDotPanel(
            pager.adapter!!.count,
            R.drawable.indicator_off,
            R.drawable.indicator_on,
            0
        )
        addBtn.setOnClickListener {
            MainDialog(this,Injection.getUrlUtil()).show()
        }
        adView.loadAd(AdRequest.Builder().build())

    }

    override fun setNotice(notice: String) {
        this.notice.text = notice
    }

    override fun showProgress() {
        imgProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        imgProgressBar.visibility = View.GONE
    }

    override fun showToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
    }

    override fun showUserImage(url: String) {
        Glide.with(this@MainActivity)
            .load(url)
            .error(R.drawable.picture)
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
            .into(image)
    }


    override fun onResume() {
        super.onResume()
        presenter.onResume()
        adView.resume()
    }

    override fun onPause() {
        presenter.onPause()
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
