package org.opennms.plugins.dbnotifier.test.manual;

import java.util.ArrayList;
import java.util.List;
 
public class Article {
 
    private String title;
    private String body;
    private List<String> tags = new ArrayList<String>();
     
    public Article() {}
     
    public Article(String title, String body) {
        this.title = title;
        this.body = body;
    }
     
    public void addTag(String tag) {
        tags.add(tag);
    }
     
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
 
}