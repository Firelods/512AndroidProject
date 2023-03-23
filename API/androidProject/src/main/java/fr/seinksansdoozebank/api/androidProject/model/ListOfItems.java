package fr.seinksansdoozebank.api.androidProject.model;

import javax.swing.text.html.ListView;
import java.util.ArrayList;
import java.util.List;

public class ListOfItems extends ArrayList<Item> {

    private static ListOfItems list=null;
    public static ListOfItems getInstance()
    {
        if(list==null){
            list = new ListOfItems();
        }
        return list;
    }

    private ListOfItems()
    {
        //TODO Generate a list of items
    }
    public ArrayList<String> getListInString() {
        ArrayList<String> res = new ArrayList<>();
        for(Item i : list)
        {
            res.add(i.toString());
        }
        return res;
    }
}
