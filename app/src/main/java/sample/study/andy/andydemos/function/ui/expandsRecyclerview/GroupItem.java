package sample.study.andy.andydemos.function.ui.expandsRecyclerview;

/**
 * Created by Andy.chen on 2016/11/30.
 */

public class GroupItem {

    private String Title;
    private String Content;

    public GroupItem(String mTitle, String mContent) {
        this.Title = mTitle;
        this.Content = mContent;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        String string = "Title=" + Title + ";Content=" + Content;
        return string;
    }
}
