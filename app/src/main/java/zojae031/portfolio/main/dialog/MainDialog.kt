package zojae031.portfolio.main.dialog

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.user_list_dialog.*
import zojae031.portfolio.Injection
import zojae031.portfolio.R

@RequiresApi(Build.VERSION_CODES.N)
class MainDialog(context: Context) : Dialog(context), MainDialogContract.View {

    private val userAdapter = MainDialogAdapter(Injection.getUrlUtil(context))
    private val presenter = MainDialogPresenter(
        this,
        Injection.getRepository(context)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list_dialog)
        recyclerView.adapter = userAdapter

        with(presenter) {
            setAdapter(userAdapter, userAdapter)
            onCreate()
        }

    }

    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }
}