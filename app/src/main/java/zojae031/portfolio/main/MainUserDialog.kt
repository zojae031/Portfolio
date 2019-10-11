package zojae031.portfolio.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.user_list_dialog.*
import zojae031.portfolio.Injection
import zojae031.portfolio.R
import zojae031.portfolio.data.dao.main.MainUserEntity

class MainUserDialog(context: Context) : Dialog(context) {
    private val userAdapater = MainUserAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list_dialog)
        recyclerView.adapter = userAdapater
        Injection.getRepository(context).getUserList()
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->
                data.map {
                    Gson().fromJson(it, MainUserEntity::class.java)
                }.toTypedArray()
            }
            .subscribe { data ->
                userAdapater.clearList()
                userAdapater.updateList(data)
                userAdapater.notifyData()
            }
    }
}