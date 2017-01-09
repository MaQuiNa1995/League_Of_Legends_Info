package Json;

import Clases.Campeon;
import Clases.Skin;
import java.io.FileNotFoundException;
import java.io.FileReader;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.Gson;

public class Ver_Json {

    public Ver_Json() {
    }

    public void verArchivo() throws FileNotFoundException {
        String archivo = "src/skins.json";

        JsonParser parser = new JsonParser();
        FileReader fr = new FileReader(archivo);
        JsonElement datos = parser.parse(fr);

        final Gson gson = new Gson();
        Skin[] skins = gson.fromJson(datos, Skin[].class);

        int i = 0;
        for (Skin skines : skins) {
            i = i + 1;
            System.out.println("---------------------------------------");
            skines.toString();
            System.out.println(i);
        }
        System.out.println("---------------------------------------");
        System.out.println("Hay " + i + " Skins");
    }

    public void verHabilidades() throws FileNotFoundException {

        String archivo = "src/habilidades_original.json";

        JsonParser parser = new JsonParser();
        FileReader fr = new FileReader(archivo);
        JsonElement datos = parser.parse(fr);

        final Gson gson = new Gson();
        Campeon[] heroes = gson.fromJson(datos, Campeon[].class
        );

        int i = 0;
        for (Campeon hero : heroes) {
            i = i + 1;
            System.out.println("---------------------------------------");
            System.out.println(hero.getId());
            System.out.println(hero.getMote());
            System.out.println(hero.getNombre());
            System.out.println(hero.getRol());
            System.out.println(hero.getLore());
        }
        System.out.println("---------------------------------------");
        System.out.println("Hay " + i + " Campeones");
    }
}
