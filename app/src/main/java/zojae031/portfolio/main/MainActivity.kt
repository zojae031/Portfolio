package zojae031.portfolio.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdRequest
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseActivity
import zojae031.portfolio.databinding.ActivityMainBinding
import zojae031.portfolio.util.NetworkUtil

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by viewModel<MainViewModel>()
    private val networkUtil by inject<NetworkUtil>()
    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            viewModel = mainViewModel.apply {

                error.observe(this@MainActivity, Observer {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                })
                finishState.observe(this@MainActivity, Observer {
                    if (it) {
                        finish()
                    } else {
                        Toast.makeText(this@MainActivity, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }

            activity = this@MainActivity

            pager.run {
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

        supportActionBar?.run {
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

    override fun onStart() {
        super.onStart()
        networkUtil.checkNetworkInfo()?.subscribe {
            applicationContext.startActivity(
                Intent(
                    applicationContext,
                    MainActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }.also { disposable = it!! }
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
        mainViewModel.clearDisposable()
        adView.pause()
        super.onPause()
    }

    override fun onStop() {
        networkUtil.destroyNetwork()
        disposable.dispose()
        super.onStop()
    }

    override fun onDestroy() {
        mainViewModel.clearBackPressDisposable()
        adView.destroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(second)) {
            drawer.closeDrawers()
        } else {
            mainViewModel.onBackPressed()
        }
    }

}

