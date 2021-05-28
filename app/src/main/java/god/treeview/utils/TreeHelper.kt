package god.treeview.utils

import god.treeview.R

class TreeHelper {
    companion object {
        /**
         * 将用户的数据转换为树形的数据
         */
        @JvmStatic
        @Throws(IllegalArgumentException::class, IllegalAccessException::class)
        fun <T> convertDatas2Nodes(data: List<T>): ArrayList<Node>? {
//            val nodes = ArrayList<Node>()
//            var node: Node? = null
//            for (t in data) {
//                var id = -1
//                var pid = -1
//                var label = ""
//                if (t == null) {
//                    continue
//                }
//                // 通过反射 + 注解拿到完成转换Bean需要的一些值
//                val clazz = t.javaClass
//                for (field in clazz.declaredFields) {
//                    if (field.getAnnotation(TreeNodeId::class.java) != null) {
//                        field.isAccessible = true
//                        id = field.getInt(t)
//                    }
//                    if (field.getAnnotation(TreeNodePid::class.java) != null) {
//                        field.isAccessible = true
//                        pid = field.getInt(t)
//                    }
//                    if (field.getAnnotation(TreeNodeLabel::class.java) != null) {
//                        field.isAccessible = true
//                        label = field.get(t) as String
//                    }
//                }
//
//                node = Node(id, pid, label)
//                nodes.add(node)
//            }

            val nodes = TreeHelperSupport.getFields(data)

            /**
             * 设置Node间的节点关系
             */
            for (i in nodes.indices) {
                val n = nodes[i]
                for (j in i + 1 until nodes.size) {
                    val m = nodes[j]

                    if (m.pid == n.id) {    // m问一下它的爸爸是不是n
                        n.children.add(m)   // m是n的儿子
                        m.parent = n        // 将n作为m的爸爸
                    } else if (m.id == n.pid) {  // m问一下它的儿子是不是n
                        m.children.add(n)
                        n.parent = m
                    }
                }
            }

            for (node in nodes) {
                setNodeIcon(node)
            }

            return nodes
        }

        /**
         * 将用户的数据转换为树形的数据，并进行排序
         */
        @JvmStatic
        @Throws(IllegalArgumentException::class, IllegalAccessException::class)
        fun <T> getSortedNodes(data: List<T>, defaultExpandLevel: Int): ArrayList<Node>? {
            val result = ArrayList<Node>()
            val nodes = convertDatas2Nodes(data)

            // 获得树的所有根节点
            val rootNodes = getRootNodes(nodes)

            for (node in rootNodes) {
                addNode(result, node, defaultExpandLevel, 1)
            }

            return result
        }

        /**
         * 过滤出可见的节点
         */
        @JvmStatic
        fun filterVisibleNodes(nodes: List<Node>): List<Node> {
            val result = ArrayList<Node>()
            for (node in nodes) {
                if (node.isRoot || node.isParentExpand) {
                    setNodeIcon(node)
                    result.add(node)
                }
            }
            return result
        }

        /**
         * 设置展开/收缩的图片
         */
        private fun setNodeIcon(n: Node) {
            if (n.children.size > 0 && n.isExpand) {        // 有孩子并且展开了
                n.icon = R.drawable.tree_ex
            } else if (n.children.size > 0 && !n.isExpand) {// 有孩子但是没展开
                n.icon = R.drawable.tree_ec
            } else {    // 孤
                n.icon = -1
            }
        }

        /**
         * 把一个节点的所有孩子节点都放入result
         */
        private fun addNode(
            result: java.util.ArrayList<Node>,
            node: Node,
            defaultExpandLevel: Int,    // 默认展开几级
            currentLevel: Int
        ) {
            result.add(node)
            if (defaultExpandLevel >= currentLevel) {
                node.isExpand = true
            }
            if (node.isLeaf) return

            for (childNode in node.children) {
                addNode(result, childNode, defaultExpandLevel, currentLevel + 1)
            }
        }

        /**
         * 从所有节点中过滤根节点
         */
        private fun getRootNodes(nodes: ArrayList<Node>?): List<Node> {
            val roots = ArrayList<Node>()
            for (node in nodes!!) {
                if (node.isRoot) {
                    roots.add(node)
                }
            }
            return roots
        }
    }
}