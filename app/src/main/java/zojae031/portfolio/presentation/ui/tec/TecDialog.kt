package zojae031.portfolio.presentation.ui.tec

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.tec_dialog.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zojae031.portfolio.R
import zojae031.portfolio.presentation.base.BaseFragmentDialog
import zojae031.portfolio.databinding.TecDialogBinding

class TecDialog :
    BaseFragmentDialog<TecDialogBinding>() {
    private val tecViewModel by sharedViewModel<TecViewModel>()

    override val layoutId: Int
        get() = R.layout.tec_dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            vm = tecViewModel.apply {
                listData.observe(this@TecDialog, Observer {
                    BUTTON_STATE.map { map ->
                        buttonSetting(it.source.toString(), map)
                    }
                })
            }
        }
    }

    private fun buttonSetting(json: String, state: Pair<String, String>) {
        val buttonName = if (state.first == "left") tec_left
        else tec_right

        JsonParser().parse(json).asJsonArray.map { element ->
            with(element.asJsonObject) {
                buttonName.text = get(state.first).asString.also { buttonText ->
                    if (buttonText.isEmpty()) {
                        buttonName.visibility = View.GONE
                    } else {
                        buttonName.setOnClickListener {
                            requireContext().startActivity(
                                TecActivity.getIntent(
                                    requireContext(),
                                    get(state.second).asString
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        private val BUTTON_STATE =
            arrayOf("left" to "data1", "right" to "data2")
    }
}

