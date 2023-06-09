package fr.seinksansdoozebank.api.androidProject;


import fr.seinksansdoozebank.api.androidProject.model.Achat;
import fr.seinksansdoozebank.api.androidProject.model.ListOfVoitures;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class APIController {

    private ArrayList<Achat> listeAchat=new ArrayList<>();
    private ListOfVoitures listOfVoitures;




    /**
     *
     * @return the ArrayList of items convert into Strings
     */
    @GetMapping("/allItems")
    public String listOfItems()
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
        return listOfVoitures.getId(id);
    }
    @PostMapping("/ajouter")
    public boolean ajouterItem(@RequestBody Achat item)
    {
        this.listeAchat.add(item);
        return true;
    }
    @GetMapping("/display")
    public ArrayList<Achat> displayPanier()
    {
        return this.listeAchat;
    }


}
