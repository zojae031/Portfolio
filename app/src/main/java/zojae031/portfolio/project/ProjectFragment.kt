package zojae031.portfolio.project

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_project.view.*
import zojae031.portfolio.R
import zojae031.portfolio.data.RepositoryImpl
import zojae031.portfolio.data.datasource.local.LocalDataSourceImpl
import zojae031.portfolio.data.datasource.remote.RemoteDataSourceImpl

class ProjectFragment : Fragment(), ProjectContract.View {

    private val adapter = ProjectAdapter()
    private val presenter by lazy {
        ProjectPresenter(
            this@ProjectFragment, RepositoryImpl.getInstance(
                LocalDataSourceImpl.getInstance(context!!),
                RemoteDataSourceImpl,
                context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            )
        ).also { it.setAdapter(adapter, adapter) }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
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

    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}