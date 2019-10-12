package zojae031.portfolio.main.dialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.user_list_dialog.*
import zojae031.portfolio.*
import zojae031.portfolio.data.dao.main.MainUserEntity
import zojae031.portfolio.databinding.UserListBinding
import zojae031.portfolio.databinding.UserListDialogBinding


class MainDialog :
    BaseActivity<UserListDialogBinding>(R.layout.user_list_dialog) {

    private val mainDialogViewModel by lazy {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainDialogViewModel(Injection.getRepository(this@MainDialog)) as T
            }
        }).get(MainDialogViewModel::class.java)
    }

    // 2. Adpater를 이용하여 onClick 구현하기
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            vm = mainDialogViewModel.apply {
                error.observe(this@MainDialog, Observer {
                    Toast.makeText(this@MainDialog, it, Toast.LENGTH_SHORT).show()
                })
            }.also { it.onCreate() }
        }

        recyclerView.adapter =
            object : BaseRecyclerViewAdapter<MainUserEntity, UserListBinding>(
                R.layout.user_list,
                BR.userData
            ) {

            }
    }


    companion object {
        fun getIntent(context: Context) =
            Intent(context, MainDialog::class.java)
    }
}