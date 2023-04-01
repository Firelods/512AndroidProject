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
        add(new Voiture(0,"A1","Audi",35000.0,"https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Audi_A1_1.2_TFSI_Ambition_S-line_%E2%80%93_Frontansicht%2C_14._April_2011%2C_Velbert.jpg/1920px-Audi_A1_1.2_TFSI_Ambition_S-line_%E2%80%93_Frontansicht%2C_14._April_2011%2C_Velbert.jpg","Entrée de Gamme audi Moteur Essence de 4.5L , 250ch et 350nm de couple"));
        add(new Voiture(1,"A4","Audi",55000.0,"https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/2018_Audi_A4_Sport_TDi_Quattro_S-A_2.0.jpg/1024px-2018_Audi_A4_Sport_TDi_Quattro_S-A_2.0.jpg","Alliant confort de conduite et puissance l'audi A4 du haut de ses 350ch et ses 500nm de couple saura vous conquérir"));
        add(new Voiture(2,"RS6","Audi",75000.0,"https://upload.wikimedia.org/wikipedia/commons/thumb/0/0f/Blue_Audi_RS6_C5_sedan_fl.jpg/1024px-Blue_Audi_RS6_C5_sedan_fl.jpg","La RS6 est un incontournable de la marque Audi, voiture aggressive et puissante(425ch et 450nm) avec un longue histoire"));
        add(new Voiture(3,"R8","Audi",125000.0,"https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/2007_Audi_R8.jpg/1024px-2007_Audi_R8.jpg","L'audi R8 est actuellement la voiture de série la plus puissante d'Audi avec 530ch et 485nm de couple, un V8 en ligne, un moteur en position centrale arriere"));
        add(new Voiture(4,"350Z","Nissan",27504.76,"https://upload.wikimedia.org/wikipedia/commons/thumb/d/d8/Nissan350Z-01.jpg/1024px-Nissan350Z-01.jpg","V6 atmospherique avec 300ch et 350nm de couple 3.5cl et 6 vitesses"));
        add(new Voiture(5,"250Z","Nissan",20132.12,"https://upload.wikimedia.org/wikipedia/commons/thumb/d/d8/Nissan350Z-01.jpg/1024px-Nissan350Z-01.jpg","V6 atmospherique avec 258ch et 325nm de couple"));
        add(new Voiture(6,"370Z","Nissan",52322.09,"https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/2020_Nissan_370Z_50th_Anniversary_Edition_front_NYIAS_2019.jpg/1024px-2020_Nissan_370Z_50th_Anniversary_Edition_front_NYIAS_2019.jpg","Voiture la plus puissante de la série Z de Nissan avec plus de 400ch, 380nm de couple et le V6 atmosphérique symbolique de la série Z"));
        add(new Voiture(7,"Model X ","Tesla",42320.10,"https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/2017_Tesla_Model_X_100D_Front.jpg/1024px-2017_Tesla_Model_X_100D_Front.jpg","Moteur électrique qui accélère vite mais sans sensations, sans bruit, sans vibrations"));
        add(new Voiture(8,"Model S","Tesla",33042.98,"https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Tesla_Model_S_%28Facelift_ab_04-2016%29_trimmed.jpg/1024px-Tesla_Model_S_%28Facelift_ab_04-2016%29_trimmed.jpg","Moteur électrique avec une voiture légere mais pas très puissante, mais une bonne accélération 0-100km/h en 5.5s"));
        add(new Voiture(9,"Yaris","Toyota",5024.99,"https://upload.wikimedia.org/wikipedia/commons/thumb/2/29/Toyota_Yaris_1.0_VVT-i_Cool_%28XP130%29_%E2%80%93_Frontansicht%2C_18._Mai_2012%2C_Ratingen.jpg/1024px-Toyota_Yaris_1.0_VVT-i_Cool_%28XP130%29_%E2%80%93_Frontansicht%2C_18._Mai_2012%2C_Ratingen.jpg","Voiture d'exception qui n'a pour limite que son pneu avant gauche"));
        add(new Voiture(10,"206","Peugeot",6320.54,"https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Peugeot_206_Quicksilver_90.jpg/1024px-Peugeot_206_Quicksilver_90.jpg","Voiture utile permettant de transporter jusqu'a 5 personnes dans des conditions de confort ne dependant que de l'humeur du conducteur"));
        add(new Voiture(11,"3008","Peugeot",12450.98,"https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/2014_Peugeot_3008_%28T8_MY13%29_Allure_HDi_wagon_%282015-07-16%29_01.jpg/1024px-2014_Peugeot_3008_%28T8_MY13%29_Allure_HDi_wagon_%282015-07-16%29_01.jpg","Voiture familiale avec une motorisation correcte"));
        add(new Voiture(12,"Megane","Renault",35850.71,"https://upload.wikimedia.org/wikipedia/commons/thumb/9/9c/2017_Renault_Megane_Dynamique_S_NAV_DC_1.5_Front.jpg/1024px-2017_Renault_Megane_Dynamique_S_NAV_DC_1.5_Front.jpg","Voiture familiale avec des nouvelle génération electrique, une bonne fiabilité et une batterie correcte"));
        add(new Voiture(13,"Clio2","Renault",35850.71,"https://upload.wikimedia.org/wikipedia/commons/thumb/2/29/1998_Renault_Clio_Grande_1.2_Front.jpg/1024px-1998_Renault_Clio_Grande_1.2_Front.jpg","Voiture agée mais qui a fait ses preuves, a la fiabilité sans limite tant que le frein a main est bien relevé"));
        add(new Voiture(14,"M2","BMW",45120.07,"https://upload.wikimedia.org/wikipedia/commons/thumb/7/76/BMW_M3_E30_front_20090514.jpg/1024px-BMW_M3_E30_front_20090514.jpg","Voiture sportive avec un moteur 6 cylindres en ligne de 3.0L, 370ch et 500nm de couple, embleme de la marque BMW"));
        add(new Voiture(15,"M4","BMW",65120.07,"https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/2014_Canadian_International_AutoShow_0148_%2812646102484%29.jpg/1024px-2014_Canadian_International_AutoShow_0148_%2812646102484%29.jp","Voiture sportive et puissante symbole du savoir faire de la marque BMW avec un moteur 6 cylindres en ligne de 3.0L, 450ch et 550nm de couple"));
        add(new Voiture(16,"Cayenne","Porsche",87220.85,"https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Porsche_Cayenne_S_2004_Seite.jpg/1024px-Porsche_Cayenne_S_2004_Seite.jpg","Enorme SUV avec un moteur V8 de 4.8L, 500ch et 600nm de couple de 2.5tonnes "));
        add(new Voiture(17,"Carrera","Porsche",75412.66,"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/Porsche_Carrera_GT_-_Goodwood_Breakfast_Club_%28July_2008%29.jpg/1024px-Porsche_Carrera_GT_-_Goodwood_Breakfast_Club_%28July_2008%29.jpg","Voiture petite, sportive et elegante avec un look aggressif et un moteur V6 de 3.0L, 400ch et 500nm de couple"));
        add(new Voiture(18,"911","Porsche",41231.32,"https://upload.wikimedia.org/wikipedia/commons/thumb/c/c8/DSC06513-Porsche_911_Speedster_Concept_Mondial_Paris_2018.jpg/1024px-DSC06513-Porsche_911_Speedster_Concept_Mondial_Paris_2018.jpg","Voiture embleme de Porsche qui reflete le savoir faire de la marque"));

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
