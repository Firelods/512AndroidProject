package fr.seinksansdoozebank.api.androidProject;


import fr.seinksansdoozebank.api.androidProject.model.ListOfVoitures;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class APIController {


    ListOfVoitures listOfVoitures;


    /**
     *
     * @return the ArrayList of items convert into Strings
     */
    @GetMapping("/allItems")
    public ArrayList<String> listOfItems()
    {
         listOfVoitures = ListOfVoitures.getInstance();
         return listOfVoitures.getListInString();
    }


    /**
     *
     * @param id of an item
     * @return An item convert into a string
     */
    @GetMapping("/oneItem/{item-id}")
    public String getOneItem(@PathVariable("item-id") int id)
    {
        listOfVoitures = ListOfVoitures.getInstance();
        return listOfVoitures.get(id).toString();
    }


}
