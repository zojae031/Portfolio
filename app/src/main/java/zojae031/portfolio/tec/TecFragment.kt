package zojae031.portfolio.tec

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_tec.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zojae031.portfolio.BR
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseFragment
import zojae031.portfolio.base.BaseRecyclerViewAdapter
import zojae031.portfolio.data.dao.tec.TecEntityOnListener
import zojae031.portfolio.databinding.FragmentTecBinding
import zojae031.portfolio.databinding.TecListBinding

class TecFragment : BaseFragment<FragmentTecBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_tec
    private val tecViewModel by sharedViewModel<TecViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = tecViewModel
        recycler.adapter = object : BaseRecyclerViewAdapter<TecEntityOnListener, TecListBinding>(
            R.layout.tec_list,
            BR.tecEntity
        ) {

        }
        tecViewModel.listData.observe(this, Observer {
            TecDialog().show(fragmentManager!!, "tecDialog")
        })
        tecViewModel.error.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
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