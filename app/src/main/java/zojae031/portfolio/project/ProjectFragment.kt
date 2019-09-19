package zojae031.portfolio.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.fragment_project.view.*
import zojae031.portfolio.Injection
import zojae031.portfolio.R

class ProjectFragment : Fragment(), ProjectContract.View {

    private val adapter = ProjectAdapter()
    private val presenter by lazy {
        ProjectPresenter(
            this@ProjectFragment, Injection.getRepository(context!!)
        ).also { it.setAdapter(adapter, adapter) }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_project, container, false).apply {
            recycler.adapter = adapter
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
        projectProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        projectProgressBar.visibility = View.GONE
    }

    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}