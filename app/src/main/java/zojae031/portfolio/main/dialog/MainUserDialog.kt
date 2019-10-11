package zojae031.portfolio.main.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.user_list_dialog.*
import zojae031.portfolio.Injection
import zojae031.portfolio.R

class MainUserDialog(context: Context) : Dialog(context), MainUserDialogContract.View {
    private val userAdapater = MainUserAdapter()
    private val presenter = MainUserDialogPresenter(
        this,
        Injection.getRepository(context)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list_dialog)
        recyclerView.adapter = userAdapater
        with(presenter) {
            setAdapter(userAdapater, userAdapater)
            onCreate()
        }
    }

    override fun showToast(text: String) {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }
}