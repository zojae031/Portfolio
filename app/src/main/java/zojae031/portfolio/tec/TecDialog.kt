package zojae031.portfolio.tec

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.dialog.*
import zojae031.portfolio.R
import zojae031.portfolio.data.dao.tec.TecEntity

class TecDialog(context: Context, private val data: TecEntity) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog)

        Glide
            .with(context)
            .load(data.image)
            .error(R.drawable.ic_launcher_foreground)
            .override(200, 200)
            .into(project_image)

        title.text = data.name
        outer.apply {
            removeView(text)
            removeView(skill_text)
        }
        JsonParser().parse(data.source).asJsonArray.map { element ->
            left.apply {
                text = element.asJsonObject.get("left").asString
                setOnClickListener {
                    context.startActivity(
                        TecActivity.getIntent(
                            context,
                            element.asJsonObject.get("data1").asString
                        )
                    )
                }
            }

            right.apply {
                text = element.asJsonObject.get("right").asString
                setOnClickListener {
                    context.startActivity(
                        TecActivity.getIntent(
                            context,
                            element.asJsonObject.get("data2").asString
                        )
                    )
                }
            }

        }
    }


}

