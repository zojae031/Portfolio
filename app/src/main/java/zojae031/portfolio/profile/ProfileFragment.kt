package zojae031.portfolio.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_profile.*
import zojae031.portfolio.R
import zojae031.portfolio.data.dao.profile.ProfileEntity


class ProfileFragment : Fragment(), ProfileContract.View {
    private val presenter by lazy {
        ProfilePresenter(
            this@ProfileFragment, Injection.getRepository(context!!)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun showProgress() {
        profileProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        profileProgressBar.visibility = View.GONE
    }

    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun showBasicInformation(data: ProfileEntity) {
        name.text = "이름 : ${data.name}"
        age.text = "나이 : ${data.age}"
        university.text = "학력 : ${data.university}"
        major.text = "전공 : ${data.major}"
        military.text = "병역 : ${data.military}"
        hobby.text = "취미 : ${data.hobby}"
        additionalOption(data.additional)
    }

    private fun additionalOption(data: String) {
        if (data != "") {
            additional.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data)))
                }
            }
        } else {
            additional.visibility = View.GONE
        }
    }
}