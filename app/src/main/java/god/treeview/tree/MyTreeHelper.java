package god.treeview.tree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import god.treeview.R;

public class MyTreeHelper {

    /**
     * 获取到排序后的List
     */
    public static <T> List<MyNode> getSortedList(List<T> data, int defaultExpandLevel) throws IllegalAccessException {
        // 将用户数据转化为List<Node>以及设置Node间关系
        List<MyNode> nodes = convertData2Node(data);
        // 拿到根节点
        List<MyNode> rootNodes = getRootNodes(nodes);
        // 排序
        List<MyNode> result = new ArrayList<>();
        for (MyNode node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    /**
     * 过滤真正显示的节点
     */
    public static List<MyNode> filterVisibleNode(List<MyNode> data) {
        List<MyNode> result = new ArrayList<>();
        for (MyNode node : data) {
            if (node.isRoot() || node.isParentExpand()) {
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }

    // ============================================================================================================================

    /**
     * 通过注解和反射获取到运行中实际的属性值
     * input: List<T> -> List<MyNode>
     */
    private static <T> List<MyNode> convertData2Node(List<T> data) throws IllegalAccessException {
        List<MyNode> nodes = new ArrayList<>();
        MyNode node = null;
        for (T t : data) {
            String id = "", pid = "", label = "";
            Class<?> clazz = t.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field f : declaredFields) {
                f.setAccessible(true);
                if (f.getAnnotation(MyTreeId.class) != null) {
                    id = (String) f.get(t);
                }
                if (f.getAnnotation(MyTreePid.class) != null) {
                    pid = (String) f.get(t);
                }
                if (f.getAnnotation(MyTreeLabel.class) != null) {
                    label = (String) f.get(t);
                }
            }
            node = new MyNode(id, pid, label);
            nodes.add(node);
        }

        /**
         * 设置父子关系
         */
        for (int i = 0; i < nodes.size(); i++) {
            MyNode n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                MyNode m = nodes.get(j);
                if (n.getId().equals(m.getPid())) {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (m.getId().equals(n.getPid())) {
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }

        for (MyNode n : nodes) {
            setNodeIcon(n);
        }
        return nodes;
    }

    /**
     * 获取根节点
     */
    private static List<MyNode> getRootNodes(List<MyNode> data) {
        List<MyNode> result = new ArrayList<>();
        for (MyNode node : data) {
            if (node.isRoot()) {
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 把对应的节点挂到List上
     */
    private static void addNode(List<MyNode> data, MyNode node, int defaultExpandLevel, int currentLevel) {
        data.add(node);
        if (defaultExpandLevel >= currentLevel) {
            node.setExpand(true);
        }
        if (node.isLeaf()) {
            return;
        }
        for (MyNode child : node.getChildren()) {
            addNode(data, child, defaultExpandLevel, currentLevel + 1);
        }
    }

    /**
     * icon仅对父节点有效
     */
    private static void setNodeIcon(MyNode node) {
        // 有孩子并且展开了
        if (node.getChildren().size() > 0 && node.isExpand()) {
            // 展开的icon
            node.setIcon(R.drawable.tree_ex);
        }
        // 有孩子但没展开
        else if (node.getChildren().size() > 0 && !node.isExpand()) {
            // 关闭的icon
            node.setIcon(R.drawable.tree_ec);
        } else {
            node.setIcon(-1);
        }
    }
}
