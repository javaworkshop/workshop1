package control;

import model.Klant;

/**
 * Class that contains static methods to generate random klanten. Cannot be instantiated.
 */
public class KlantGenerator {
    
    private KlantGenerator(){}
    
    public static Klant generateKlant() {
        String[] voornaam = {"Hani" , "Gerbrich", "Sander", "Maarten", "Remi" , "Rob" , 
            "Bo" , "Jan" , "Willem" , " Piet"};
        String[] tussenvoegsel = {"van", "de", "el","","van de", "van der" , "ten", 
            "van de", "uit het", "voor den"};
        String[] achternaam = {"de Jong", "Bakker", "Visser", "de Boer", "Peters", "Graaf" + 
                "Jacobs", "Ali", "Hassan", "Beatrix"};
        String[] straatnaam = {"Schoolstraat", "Dorpstraat", "Nieuwstraat", "Kastanjelaan", 
            "Eikenlaan", "Stationsweg","Markt","Beukenlaan", "Industrieweg","Molenstraat"};
        String[] postcode = {"2001aa" , "2002aa", "2003aa","2003ab","2005aa","2006aa", 
            "2007aa","2008aa","2009aa","2010aa"};
        
        Klant klant = new Klant();
        klant.setVoornaam(voornaam[(int)(Math.random()*10)]);
        klant.setTussenvoegsel(tussenvoegsel[(int)(Math.random()*10)]);
        klant.setAchternaam(achternaam[(int)(Math.random()*10)]);
        klant.setStraatnaam(straatnaam[(int)(Math.random()*10)]);
        klant.setHuisnummer((int)(Math.random()*500));
        klant.setPostcode(postcode[(int)(Math.random()*10)]);
        klant.setWoonplaats("Amsterdam");
        
        return klant;
    }
    
    public static Klant[] generateNKlanten(int n) throws IllegalArgumentException {
        if(n <= 0)
            throw new IllegalArgumentException();
        
        Klant[] klanten = new Klant[n];
        for(int i = 0; i < n; i++)
            klanten[i] = generateKlant();
        
        return klanten;
    }
}
