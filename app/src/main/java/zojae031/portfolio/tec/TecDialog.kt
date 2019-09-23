package zojae031.portfolio.tec

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.bumptech.glide.Glide
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.dialog.*
import zojae031.portfolio.R
import zojae031.portfolio.data.dao.tec.TecEntity

class TecDialog(context: Context, private val data: TecEntity) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog)

        setUi()

        JsonParser().parse(data.source).asJsonArray.map { element ->
            with(element.asJsonObject) {
                setButton(left, get("data1").asString, get("left").asString)
                setButton(right, get("data2").asString, get("right").asString)
            }
        }

    }

    private fun setUi() {
        Glide
            .with(context)
            .load(data.image)
            .error(R.drawable.ic_launcher_foreground)
            .override(200, 200)
            .into(project_image)

        title.text = data.name
        text.visibility = View.GONE
        skill_text.visibility = View.GONE
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

