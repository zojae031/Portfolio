package zojae031.portfolio.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zojae031.portfolio.R
import zojae031.portfolio.databinding.UserListDialogBinding
import zojae031.portfolio.presentation.base.BaseFragmentDialog

class MainDialog :
    BaseFragmentDialog<UserListDialogBinding>() {

    private val mainViewModel by sharedViewModel<MainViewModel>()

    override val layoutId: Int
        get() = R.layout.user_list_dialog

    private val onClickItem: (Int) -> Unit = { position ->
        adapter.getItem(position).name.run {
            mainViewModel.setUrl(this)
            Toast.makeText(
                context,
                "$this 님의 포트폴리오를 확인합니다.",
                Toast.LENGTH_SHORT
            ).show()
        }
        startActivity(
            Intent(
                requireContext().applicationContext,
                MainActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
        dismiss()
    }

    private val adapter = MainRecyclerViewAdapter(onClickItem)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            vm = mainViewModel
            recyclerView.adapter = adapter
        }
    }


    override fun onPause() {
        mainViewModel.clearDisposable()
        super.onPause()
    }

}