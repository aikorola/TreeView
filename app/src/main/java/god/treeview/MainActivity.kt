package god.treeview

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import god.treeview.Organization.FileBean
import god.treeview.utils.E
import god.treeview.utils.Node
import god.treeview.utils.adapter.TreeAdapter
import god.treeview.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val mData = ArrayList<Organization>()
    private var mAdapter: SimpleTreeAdapter<Organization>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()
        try {
            mainTreeView.adapter = getAdapter()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        initEvent()
    }

    private fun initView() {
        mainTreeView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mainTreeView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.HORIZONTAL
            )
        )
    }

    private fun initData() {
        val organization = Organization(8, 7, "shit!")
        organization.fileBean = FileBean("Oh!")
        mData.add(organization)

        mData.add(Organization(1, 0, "根目录1"))
        mData.add(Organization(2, 0, "根目录2"))
        mData.add(Organization(3, 0, "根目录3"))

        mData.add(Organization(4, 1, "根目录1-1"))
        mData.add(Organization(5, 4, "根目录1-1-1"))
        mData.add(Organization(6, 1, "根目录1-2"))
        mData.add(Organization(7, 2, "根目录2-1"))
    }

    private fun initEvent() {
        mAdapter?.setOnEvent(object : TreeAdapter.OnItemClickListener {
            override fun onItemClick(view: View, node: Node?, position: Int) {
                "单击了: ${node!!.name}".E()
                if (node.isLeaf) {
                    node.name.showToast(this@MainActivity)
                }
            }

            override fun onItemLongClick(view: View, node: Node?, position: Int) {
                val editText = EditText(this@MainActivity)
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("增加节点")
                    .setView(editText)
                    .setPositiveButton(
                        "确定"
                    ) { dialog: DialogInterface, which: Int ->
                        val label = editText.text.toString()
                        if (TextUtils.isDigitsOnly(label)) return@setPositiveButton
                        mAdapter?.addExtraNode(-1, position, label)
                        dialog.dismiss()
                    }
                    .setNegativeButton("取消", null)
                    .show()
            }
        })
    }

    private fun getAdapter(): SimpleTreeAdapter<Organization> {
        if (mAdapter == null) {
            mAdapter = SimpleTreeAdapter(R.layout.cell_tree, mData, 2)
        }
        return mAdapter!!
    }
}