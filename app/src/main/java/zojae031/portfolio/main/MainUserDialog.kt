package zojae031.portfolio.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.user_list_dialog.*
import zojae031.portfolio.R

class MainUserDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list_dialog)
        recyclerView.adapter = MainUserAdapter()
    }
}