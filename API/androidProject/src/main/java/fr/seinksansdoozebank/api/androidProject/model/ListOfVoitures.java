package fr.seinksansdoozebank.api.androidProject.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class ListOfVoitures extends ArrayList<Voiture> {

    private static ListOfVoitures list=null;
    public static ListOfVoitures getInstance()
    {
        if(list==null){
            list = new ListOfVoitures();
        }
        return list;
    }

    private ListOfVoitures()
    {
        add(new Voiture(0,"A1","Audi",35000.0,null,"Entrée de Gamme audi Moteur Essence de 4.5L , 250ch et 350nm de couple"));
        add(new Voiture(1,"A4","Audi",55000.0,null,"Alliant confort de conduite et puissance l'audi A4 du haut de ses 350ch et ses 500nm de couple saura vous conquérir"));
        add(new Voiture(2,"RS6","Audi",75000.0,null,"La RS6 est un incontournable de la marque Audi, voiture aggressive et puissante(425ch et 450nm) avec un longue histoire"));
        add(new Voiture(3,"R8","Audi",125000.0,null,"L'audi R8 est actuellement la voiture de série la plus puissante d'Audi avec 530ch et 485nm de couple, un V8 en ligne, un moteur en position centrale arriere"));
        add(new Voiture(4,"350Z","Nissan",27504.76,null,"V6 atmospherique avec 300ch et 350nm de couple 3.5cl et 6 vitesses"));
        add(new Voiture(5,"250Z","Nissan",20132.12,null,"V6 atmospherique avec 258ch et 325nm de couple"));
        add(new Voiture(6,"370Z","Nissan",52322.09,null,"Voiture la plus puissante de la série Z de Nissan avec plus de 400ch, 380nm de couple et le V6 atmosphérique symbolique de la série Z"));
        add(new Voiture(7,"Model X ","Tesla",42320.10,null,"Moteur électrique qui accélère vite mais sans sensations, sans bruit, sans vibrations"));
        add(new Voiture(8,"Model S","Tesla",33042.98,null,"Moteur électrique avec une voiture légere mais pas très puissante, mais une bonne accélération 0-100km/h en 5.5s"));
        add(new Voiture(9,"Yaris","Toyota",5024.99,null,"Voiture d'exception qui n'a pour limite que son pneu avant gauche"));
        add(new Voiture(10,"206","Peugeot",6320.54,null,"Voiture utile permettant de transporter jusqu'a 5 personnes dans des conditions de confort ne dependant que de l'humeur du conducteur"));
        add(new Voiture(11,"3008","Peugeot",12450.98,null,"Voiture familiale avec une motorisation correcte"));
        add(new Voiture(12,"Megane","Renault",35850.71,null,"Voiture familiale avec des nouvelle génération electrique, une bonne fiabilité et une batterie correcte"));
        add(new Voiture(13,"Clio2","Renault",35850.71,null,"Voiture agée mais qui a fait ses preuves, a la fiabilité sans limite tant que le frein a main est bien relevé"));
        add(new Voiture(14,"M2","BMW",45120.07,null,"Voiture sportive avec un moteur 6 cylindres en ligne de 3.0L, 370ch et 500nm de couple, embleme de la marque BMW"));
        add(new Voiture(15,"M4","BMW",65120.07,null,"Voiture sportive et puissante symbole du savoir faire de la marque BMW avec un moteur 6 cylindres en ligne de 3.0L, 450ch et 550nm de couple"));
        add(new Voiture(16,"Cayenne","Porsche",87220.85,null,"Enorme SUV avec un moteur V8 de 4.8L, 500ch et 600nm de couple de 2.5tonnes "));
        add(new Voiture(17,"Carrera","Porsche",75412.66,null,"Voiture petite, sportive et elegante avec un look aggressif et un moteur V6 de 3.0L, 400ch et 500nm de couple"));
        add(new Voiture(18,"911","Porsche",41231.32,null,"Voiture embleme de Porsche qui reflete le savoir faire de la marque"));

    }
    public String getListInString() {
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            System.out.println(list);
            json = mapper.writeValueAsString(list);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return json;
    }
    public String getId(int id) {
        boolean founded=false;
        while (!founded && id<list.size())
        {
            if(list.get(id).getId()==id)
            {
                founded=true;
            }else{
                id++;
            }
        }
        if(founded)
        {
            return list.get(id).toString();
        }
        return null;
    }


}
