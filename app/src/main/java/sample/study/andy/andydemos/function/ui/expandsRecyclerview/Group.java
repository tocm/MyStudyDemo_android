package sample.study.andy.andydemos.function.ui.expandsRecyclerview;

import java.util.List;

/**
 * Created by Andy.chen on 2016/11/30.
 */

public class Group {
    //分组名称
    private String mGroupName;
    //分组项目
    private List<GroupItem> mGroupItems;

    public Group(String GroupName, List<GroupItem> GroupItems) {
        this.mGroupName = GroupName;
        this.mGroupItems = GroupItems;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String mGroupName) {
        this.mGroupName = mGroupName;
    }

    public List<GroupItem> getGroupItems() {
        return mGroupItems;
    }

    public void setGroupItems(List<GroupItem> mItems) {
        this.mGroupItems = mItems;
    }

    @Override
    public String toString() {
        String string = "GroupName=" + mGroupName + ";GroupItem=" + mGroupItems.toString();
        return string;
    }
}
