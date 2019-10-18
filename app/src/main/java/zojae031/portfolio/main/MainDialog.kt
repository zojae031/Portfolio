package zojae031.portfolio.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.user_list_dialog.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zojae031.portfolio.BR
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseFragmentDialog
import zojae031.portfolio.base.BaseRecyclerViewAdapter
import zojae031.portfolio.data.dao.main.MainUserEntity
import zojae031.portfolio.databinding.UserListBinding
import zojae031.portfolio.databinding.UserListDialogBinding

class MainDialog :
    BaseFragmentDialog<UserListDialogBinding>(R.layout.user_list_dialog) {

    private val mainViewModel by sharedViewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.apply {
            error.observe(this@MainDialog, Observer {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })

            userName.observe(this@MainDialog, Observer {
                //다시 셋 되면서 동작이됨
                Toast.makeText(
                    context,
                    "$it 님의 포트폴리오를 확인합니다.",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(
                        context?.applicationContext,
                        MainActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                dismiss()
            })

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            vm = mainViewModel

        }

        recyclerView.adapter =
            object : BaseRecyclerViewAdapter<MainUserEntity, UserListBinding>(
                R.layout.user_list,
                BR.userData
            ) {

            }
    }


    override fun onPause() {
        mainViewModel.onPause()
        super.onPause()
    }

}