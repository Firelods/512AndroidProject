package fr.seinksansdoozebank.api.androidProject.model;

public class Item {

    int id;
    String title;
    String content;

    public Item(int id,String title, String content)
    {
        this.id=id;
        this.title=title;
        this.content=content;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
