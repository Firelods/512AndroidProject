package fr.seinksansdoozebank.api.androidProject;


import fr.seinksansdoozebank.api.androidProject.model.Item;
import fr.seinksansdoozebank.api.androidProject.model.ListOfItems;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class APIController {


    ListOfItems listOfItems;


    /**
     *
     * @return the ArrayList of items convert into Strings
     */
    @GetMapping("/allItems")
    public ArrayList<String> listOfItems()
    {
         listOfItems= ListOfItems.getInstance();
         return listOfItems.getListInString();
    }


    /**
     *
     * @param id of an item
     * @return An item convert into a string
     */
    @GetMapping("/oneItem/{item-id}")
    public String getOneItem(@PathVariable("item-id") int id)
    {
        listOfItems= ListOfItems.getInstance();
        return listOfItems.get(id).toString();
    }


}
