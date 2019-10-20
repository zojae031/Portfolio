package zojae031.portfolio.tec

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tec.*
import kotlinx.android.synthetic.main.fragment_tec.view.*
import org.koin.android.ext.android.inject
import zojae031.portfolio.R
import zojae031.portfolio.data.Repository

class TecFragment : Fragment(), TecContract.View {
    private val repository: Repository by inject()
    private val adapter = TecAdapter()
    private val presenter by lazy {
        TecViewModel(
            this,
            repository
        ).also { it.setAdapter(adapter, adapter) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_tec, container, false).apply {
            recycler.adapter = adapter
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun showProgress() {
        tecProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        tecProgressBar.visibility = View.GONE
    }

    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}