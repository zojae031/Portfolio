package zojae031.portfolio.tec

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.project_dialog.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseFragmentDialog
import zojae031.portfolio.data.dao.tec.TecEntity
import zojae031.portfolio.databinding.TecDialogBinding

class TecDialog :
    BaseFragmentDialog<TecDialogBinding>(R.layout.tec_dialog) {

    private val tecViewModel by sharedViewModel<TecViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = tecViewModel
        binding.dialog = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        JsonParser().parse(data.source).asJsonArray.map { element ->
//            with(element.asJsonObject) {
//                setButton(left, get("data1").asString, get("left").asString)
//                setButton(right, get("data2").asString, get("right").asString)
//            }
//        }

    }


    private fun setButton(button: Button, url: String, buttonName: String) {
        if (url == "" || buttonName == "") {
            button.visibility = View.GONE
        } else {
            button.apply {
                text = buttonName
                setOnClickListener {
                    context.startActivity(
                        TecActivity.getIntent(
                            context,
                            url
                        )
                    )
                }
            }
        }
    }


}

