package zojae031.portfolio.presentation.ui.tec

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_tec.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zojae031.portfolio.R
import zojae031.portfolio.databinding.FragmentTecBinding
import zojae031.portfolio.presentation.base.BaseFragment

class TecFragment : BaseFragment<FragmentTecBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_tec

    private val tecViewModel by sharedViewModel<TecViewModel>()

    private val onClickItem: (Int) -> Unit = {
        TecDialog(adapter.getItem(it)).show(fragmentManager!!, "tecDialog")
    }

    private val adapter = TeaRecyclerViewAdapter(onClickItem)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = tecViewModel.apply {
            error.observe(viewLifecycleOwner, Observer {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })
        }
        recycler.adapter = adapter
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