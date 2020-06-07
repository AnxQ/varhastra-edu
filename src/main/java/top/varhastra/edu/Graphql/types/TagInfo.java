package top.varhastra.edu.Graphql.types;

import top.varhastra.edu.Entity.Tag;

public class TagInfo {
    private String tagId;
    private String name;

    public TagInfo(Tag tag) {
        this.tagId = tag.getTagId().toString();
        this.name = tag.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
