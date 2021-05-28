package god.treeview;

import god.treeview.utils.annotation.TreeNodeExtra;
import god.treeview.utils.annotation.TreeNodeId;
import god.treeview.utils.annotation.TreeNodeLabel;
import god.treeview.utils.annotation.TreeNodePid;

public class Organization {
    @TreeNodeId(type = int.class)
    private int id;

    @TreeNodePid
    private int pid;

    @TreeNodeLabel
    private String label;

    /**
     * 用户可传递其他数据（除上面三个必备的），后台已将数据封装在Node.class中了，用户可在获取
     */
    @TreeNodeExtra
    private FileBean fileBean;

    public Organization(int id, int pid, String label) {
        this.id = id;
        this.pid = pid;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public FileBean getFileBean() {
        return fileBean;
    }

    public void setFileBean(FileBean fileBean) {
        this.fileBean = fileBean;
    }

    public static class FileBean {
        private String name;

        public FileBean(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
