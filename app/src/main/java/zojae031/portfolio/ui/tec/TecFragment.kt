package zojae031.portfolio.ui.tec

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_tec.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zojae031.portfolio.BR
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseFragment
import zojae031.portfolio.base.SimpleRecyclerViewAdapter
import zojae031.portfolio.data.dao.tec.TecEntityOnListener
import zojae031.portfolio.databinding.FragmentTecBinding
import zojae031.portfolio.databinding.TecListBinding

class TecFragment : BaseFragment<FragmentTecBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_tec
    private val tecViewModel by sharedViewModel<TecViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = tecViewModel.apply {
            listData.observe(this@TecFragment, Observer {
                TecDialog().show(fragmentManager!!, "tecDialog")
            })
            error.observe(this@TecFragment, Observer {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })
        }
        recycler.adapter = object : SimpleRecyclerViewAdapter<TecEntityOnListener, TecListBinding>(
            R.layout.tec_list,
            BR.tecEntity
        ) {

        }
    }

    override fun onPause() {
        tecViewModel.clearDisposable()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        tecViewModel.onResume()
    }

}