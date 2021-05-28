package god.treeview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import god.treeview.utils.E
import god.treeview.utils.Node
import god.treeview.utils.TreeHelper
import god.treeview.utils.adapter.TreeAdapter
import god.treeview.utils.showToast

class SimpleTreeAdapter<T>(layoutId: Int, data: List<T>, defaultTreeLevel: Int) :
    TreeAdapter<T>(layoutId, data, defaultTreeLevel) {

    override fun convertView(itemView: View, holder: TreeViewHolder, node: Node, position: Int) {
        val icon = itemView.findViewById<ImageView>(R.id.cell_tree_icon)
        val label = itemView.findViewById<TextView>(R.id.cell_tree_label)

        if (node.icon == -1) {
            icon.visibility = View.INVISIBLE
        } else {
            icon.visibility = View.VISIBLE
//            icon.setImageResource(node.icon)
        }

        // 定制图标
        if (node.isExpand) {
            icon.setImageResource(R.drawable.tree_ex)
        } else {
            icon.setImageResource(R.drawable.tree_ec)
        }

        label.text = node.name

        if (node.extraBean is Organization.FileBean) {
            // 这里是用户传递过来的额外实体
            (node.extraBean as Organization.FileBean).name.showToast(itemView.context)
        }
    }

    /**
     * 增加额外的节点
     * index: 代表增加的id，如果外部调用者需要使用此id，那么需传入对应的id值（比如在数据库中取到的id值），否则为-1
     */
    fun addExtraNode(index: Int = -1, position: Int, label: String) {
        val node = mVisibleNodes?.get(position)
        val indexOf = mAllNodes?.indexOf(node)

        Node(index, node!!.id, label).apply {
            // 设置节点父子关系
            parent = node
            node.children.add(this)

            // 如果是尾节点增加子节点，需要默认展开的话
            if (!node.isExpand) {
                node.isExpand = true
            }

            // 增加数据
            mAllNodes?.add(indexOf!! + 1, this)
        }

        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes!!)
        notifyDataSetChanged()
    }
}