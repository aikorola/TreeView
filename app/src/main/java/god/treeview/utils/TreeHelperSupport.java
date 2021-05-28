package god.treeview.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import god.treeview.utils.annotation.TreeNodeExtra;
import god.treeview.utils.annotation.TreeNodeId;
import god.treeview.utils.annotation.TreeNodeLabel;
import god.treeview.utils.annotation.TreeNodePid;

public class TreeHelperSupport {
    public static <T> ArrayList<Node> getFields(List<T> data) throws IllegalAccessException {
        ArrayList<Node> nodes = new ArrayList<>();
        Node node = null;
        for (T t : data) {
            int id = -1;
            String idStr = "";
            int pid = -1;
            String label = "";
            Object extraBean = null;

            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                TreeNodeId treeNodeIdAnnotation = field.getAnnotation(TreeNodeId.class);
                if (treeNodeIdAnnotation != null) {
                    Class type = treeNodeIdAnnotation.type();
                    if (type.equals(String.class)) {
                        // 传的id是String类型...
                        idStr = (String) field.get(t);
                    } else {
                        id = field.getInt(t);
                    }
                }
                if (field.getAnnotation(TreeNodePid.class) != null) {
                    pid = field.getInt(t);
                }
                if (field.getAnnotation(TreeNodeLabel.class) != null) {
                    label = (String) field.get(t);
                }

                /**
                 * 添加额外的实体，以解决用户有过多数据的情况
                 */
                if (field.getAnnotation(TreeNodeExtra.class) != null) {
                    extraBean = field.get(t);
                }
            }

            node = new Node(id, pid, label);
            node.setExtraBean(extraBean);
            nodes.add(node);
        }

        return nodes;
    }
}
