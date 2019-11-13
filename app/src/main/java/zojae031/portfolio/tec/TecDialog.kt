package zojae031.portfolio.tec

import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseFragmentDialog
import zojae031.portfolio.databinding.TecDialogBinding

class TecDialog :
    BaseFragmentDialog<TecDialogBinding>() {
    private val tecViewModel by sharedViewModel<TecViewModel>()

    override val layoutId: Int
        get() = R.layout.tec_dialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = tecViewModel
        binding.dialog = this
    }

}

