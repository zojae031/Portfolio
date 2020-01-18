package zojae031.portfolio.presentation.ui.main


import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.setPadding

class Indicator @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var defaultCircle: Int = 0
    private var selectedCircle: Int = 0
    private var imageDot: MutableList<ImageView> = mutableListOf()
    private val padding = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 4.5f, resources.displayMetrics
    )

    fun createDotPanel(count: Int, defaultCircle: Int, selectCircle: Int, position: Int) {
        this.removeAllViews()
        this.defaultCircle = defaultCircle
        this.selectedCircle = selectCircle

        for (i in 0 until count) {
            imageDot.add(ImageView(context, attrs).apply {
                setPadding(padding.toInt())
            })
            this.addView(imageDot[i])
        }
        selectDot(position)
    }

    fun selectDot(position: Int) {
        for (i in imageDot.indices) {
            if (i == position) {
                imageDot[i].setImageResource(selectedCircle)

            } else {
                imageDot[i].setImageResource(defaultCircle)
            }
        }
    }

}
