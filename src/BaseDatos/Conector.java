package BaseDatos;

import Clases.Campeon;
import Clases.CrashReporter;
import Clases.Habilidad;
import Clases.Rutas;
import Clases.Pasiva;
import Clases.Skin;
import Clases.Stats;
import Librerias.Output_Box;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.awt.FlowLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public final class Conector extends JFrame {

    JTextArea progress;

    File baseDatos;
    String lore,
            nombre,
            nombreMote,
            mote,
            sentenciaSql,
            costeIPString,
            tablasCreadas,
            datosMetidos,
            rolCampeon;

    String directorioInstalacion = Rutas.rutaInstalacion;

    String cadenaEnviar;
    final int PORT = 25565;
    Socket sc;

    DataOutputStream out;
    DataInputStream in;
    FileInputStream inArchivo;
    FileOutputStream outArchivo;

    static String claseActual="BaseDatos.Conector";
    static final Logger ARCHIVOLOG = Logger.getLogger(claseActual);

    int costeIP;

    JProgressBar progresoBarra;

    JCheckBox campeonOK,
            pasivaOK,
            habilidadOK,
            skinOK,
            statOK;

    JPanel panelTodo = new JPanel(),
            progresoPanel = new JPanel();

    ResultSet result, result2;
    JsonParser parser;
    FileReader fr;
    JsonElement datos;

    boolean error = false;

    PreparedStatement st;
    Statement stmt;

    Connection conexion;

    float i;

    Properties propiedades;

    public Conector() {

        propiedades = new ArchivoPropiedades().getProperties();

        baseDatos = new File(directorioInstalacion + propiedades.getProperty("baseDatosRuta"));

        String temporada = propiedades.getProperty("temporada");

        String versionActual = propiedades.getProperty("version");

        try {
            sc = new Socket(propiedades.getProperty("ipServer"), PORT);

            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(temporada);
            out.writeUTF(versionActual);

            if (in.readBoolean() != true) {
                Output_Box.Cuadro_Vacio("Nueva Version Disponible", "Hay una nueva version disponible para descargar");
            }

        } catch (IOException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al conectar con el server para saber si el programa esta actualizado"), claseActual);
            
        }

        if (!baseDatos.exists()) {
            definirPanel();
            crearTablaCampeones();
            crearTablaHabilidad();
            crearTablaPasiva();
            crearTablaSkins();
            crearTablaStats();

            guardarObjetoCampeon();
            actualizarBarra(20);

            guardarObjetoHabilidad();
            actualizarBarra(40);

            guardarObjetoPasiva();
            actualizarBarra(60);

            guardarObjetoSkin();
            actualizarBarra(80);

            guardarObjetoStat();
            actualizarBarra(100);

        }
    }

    public void actualizarBarra(int valor) {
        progresoBarra.setValue(valor);

        switch (valor) {
            case 20:
                campeonOK.setSelected(true);
                break;
            case 40:
                habilidadOK.setSelected(true);
                break;
            case 60:
                pasivaOK.setSelected(true);
                break;
            case 80:
                skinOK.setSelected(true);
                break;
            case 100:
                statOK.setSelected(true);
                break;

        }
    }

    public void definirPanel() {

        setSize(400, 400);
        setLocation(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Progreso Completado");

        JPanel campeonPanel = new JPanel(),
                pasivaPanel = new JPanel(),
                habilidadPanel = new JPanel(),
                skinPanel = new JPanel(),
                statPanel = new JPanel(),
                esperarPanel = new JPanel();

        progresoBarra = new JProgressBar(0, 100);

        campeonOK = new JCheckBox();
        pasivaOK = new JCheckBox();
        habilidadOK = new JCheckBox();
        skinOK = new JCheckBox();
        statOK = new JCheckBox();

        JLabel campeonLabel = new JLabel("Campeon:"),
                pasivaLabel = new JLabel("Pasiva:"),
                habilidadLabel = new JLabel("Habilidad:"),
                skinLabel = new JLabel("Skin:"),
                statLabel = new JLabel("Stats:"),
                esperarLabel = new JLabel("Espere Unos Instantes Mientras Las Tablas Se Rellenan");

        progress = new JTextArea();

        // Panel Todo
        panelTodo.setLayout(new BoxLayout(panelTodo, BoxLayout.Y_AXIS));

        // campeon
        campeonPanel.setLayout(new FlowLayout());
        campeonPanel.add(campeonLabel);
        campeonPanel.add(campeonOK);
        panelTodo.add(campeonPanel);
        //Habilidad
        habilidadPanel.setLayout(new FlowLayout());
        habilidadPanel.add(habilidadLabel);
        habilidadPanel.add(habilidadOK);
        panelTodo.add(habilidadPanel);
        //Pasiva
        pasivaPanel.setLayout(new FlowLayout());
        pasivaPanel.add(pasivaLabel);
        pasivaPanel.add(pasivaOK);
        panelTodo.add(pasivaPanel);
        //Skin
        skinPanel.setLayout(new FlowLayout());
        skinPanel.add(skinLabel);
        skinPanel.add(skinOK);
        panelTodo.add(skinPanel);
        // Stats
        statPanel.setLayout(new FlowLayout());
        statPanel.add(statLabel);
        statPanel.add(statOK);
        panelTodo.add(statPanel);
        //esperar
        esperarPanel.setLayout(new FlowLayout());
        esperarPanel.add(esperarLabel);
        panelTodo.add(esperarPanel);
        //progreso
        progresoBarra.setStringPainted(true);
        progresoPanel.setLayout(new FlowLayout());
        progresoPanel.add(progresoBarra);
        panelTodo.add(progresoPanel);

        add(panelTodo);
        pack();
        setVisible(true);
    }

    public Connection conectarBaseDatos() {

        try {
            conexion = DriverManager.getConnection("jdbc:sqlite:" + directorioInstalacion + propiedades.getProperty("baseDatosRuta"));
            if (conexion == null) {
            }
        } catch (SQLException ex) {
            System.out.println("No se ha podido conectar a la base de datos " + ex.getMessage());
        }

        return conexion;
    }

    public void cerrarBaseDatos(Connection conexion) {

        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    // ------------------------- Creacion De Tablas ----------------------------
    public boolean crearTablaCampeones() {

        conexion = conectarBaseDatos();
        error = false;

        try {

            stmt = conexion.createStatement();
            sentenciaSql = "create table campeon "
                    + " (p_campeon       integer primary key          NOT NULL,"
                    + " mote             text                         NOT NULL,"
                    + " nombre           text                         NOT NULL,"
                    + " rol              text                         NOT NULL,"
                    + " lore             text                         NOT NULL,"
                    + " costeIP          integer                      NOT NULL"
                    + ")";
            //FOREIGN KEY(IdtableActual) REFERENCES TABLE(IDTABLE)

            stmt.executeUpdate(sentenciaSql);
            stmt.close();
        } catch (SQLException SQLE) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al crear la tabla Campeon" + SQLE.getMessage()), claseActual);
            error = true;
        } finally {
            cerrarBaseDatos(conexion);
            if (!error) {
                tablasCreadas = "Campeon ";
            }
        }
        return error;
    }

    public boolean crearTablaPasiva() {

        conexion = conectarBaseDatos();
        error = false;

        try {

            stmt = conexion.createStatement();
            sentenciaSql = "create table pasiva "
                    + " (p_pasiva        integer primary key autoincrement      NOT NULL,"
                    + " nombre           text                                   NOT NULL,"
                    + " descripcion      text                                   NOT NULL,"
                    + " enfriamiento     text                                   NULL,"
                    + " a_campeon        integer                                NOT NULL,"
                    + " FOREIGN KEY(a_campeon) REFERENCES campeon(p_campeon)"
                    + ")";
            stmt.executeUpdate(sentenciaSql);
            stmt.close();
        } catch (SQLException e) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al crear la tabla Pasiva " + e.getMessage()), claseActual);
            error = true;
        } finally {
            cerrarBaseDatos(conexion);
            if (!error) {
                tablasCreadas = tablasCreadas.concat("Pasiva ");
            }
        }
        return error;
    }

    public boolean crearTablaHabilidad() {

        conexion = conectarBaseDatos();
        error = false;

        try {
            stmt = conexion.createStatement();
            sentenciaSql = "create table habilidad "
                    + " (p_habilidad        integer primary key autoincrement      NOT NULL,"
                    + " nombre              text                                   NOT NULL,"
                    + " descripcioncorta    text                                   NOT NULL,"
                    + " descripcionlarga    text                                   NOT NULL,"
                    + " tecla               text                                   NOT NULL,"
                    + " coste               text                                   NOT NULL,"
                    + " enfriamiento        text                                   NOT NULL,"
                    + " a_campeon           integer                                NOT NULL,"
                    + " FOREIGN KEY(a_campeon) REFERENCES campeon(p_campeon))";

            stmt.executeUpdate(sentenciaSql);
            stmt.close();

        } catch (SQLException e) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al crear la tabla Habilidad " + e.getMessage()), claseActual);
            error = true;
        } finally {
            cerrarBaseDatos(conexion);
            if (!error) {
                tablasCreadas = tablasCreadas.concat("Habilidad ");

            }
        }
        return error;
    }

    public boolean crearTablaSkins() {
        conexion = conectarBaseDatos();
        error = false;

        try {
            stmt = conexion.createStatement();
            sentenciaSql = "create table skin "
                    + " (p_skin             integer primary key autoincrement      NOT NULL,"
                    + " nombre              text                                   NOT NULL,"
                    + " imagen              text                                   NOT NULL,"
                    + " costeRP             integer                                NOT NULL,"
                    + " a_campeon           integer                                NOT NULL,"
                    + " FOREIGN KEY(a_campeon) REFERENCES campeon(p_campeon))";

            stmt.executeUpdate(sentenciaSql);
            stmt.close();

        } catch (SQLException e) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al crear la tabla Skin " + e.getMessage()), claseActual);
            error = true;
        } finally {
            cerrarBaseDatos(conexion);
            if (!error) {
                tablasCreadas = tablasCreadas.concat("Skins ");
            }
        }
        return error;
    }

    public boolean crearTablaStats() {
        conexion = conectarBaseDatos();
        error = false;

        try {
            stmt = conexion.createStatement();
            sentenciaSql = "create table stat "
                    + " (p_stats        integer primary key autoincrement     NOT NULL,"
                    + " vida            text                                  NOT NULL,"
                    + " regvida         text                                  NOT NULL,"
                    + " mana            text                                  NULL,"
                    + " regmana         text                                  NULL,"
                    + " ataque          text                                  NOT NULL,"
                    + " veloataque      text                                  NOT NULL,"
                    + " armadura        text                                  NOT NULL,"
                    + " resismagica     text                                  NOT NULL,"
                    + " velomov         text                                  NOT NULL,"
                    + " a_campeon       int                                   NOT NULL,"
                    + " FOREIGN KEY(a_campeon) REFERENCES campeon(p_campeon))";

            stmt.executeUpdate(sentenciaSql);
            stmt.close();

        } catch (SQLException e) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al crear la tabla Stat " + e.getMessage()), claseActual);
            error = true;
        } finally {
            cerrarBaseDatos(conexion);
            if (!error) {
                tablasCreadas = tablasCreadas + "Stats";
                Output_Box.Cuadro_Vacio("Tablas Creadas", "Tablas Creadas Con Éxito: ".concat(tablasCreadas));

            }
        }
        return error;
    }

    // --------------------- Fin Creacion De Tablas ----------------------------
    // ----------------------- Guardado De Datos -------------------------------
    public boolean guardarObjetoStat() {

        parser = new JsonParser();
        try {
            fr = new FileReader(directorioInstalacion + propiedades.getProperty("stat"));
        } catch (FileNotFoundException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al leer el archivo Propiedades " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
        }
        datos = parser.parse(fr);

        Gson gson = new Gson();
        Stats[] estadisticas = gson.fromJson(datos, Stats[].class);

        conexion = conectarBaseDatos();

        try {
            st = conexion.prepareStatement(
                    "insert into stat (vida, regvida,mana,regmana,ataque,veloataque,armadura,resismagica,veloMov,a_campeon) values (?,?,?,?,?,?,?,?,?,?)"
            );

            for (Stats stats : estadisticas) {
                st.setString(1, stats.getVida());
                st.setString(2, stats.getRegvida());
                st.setString(3, stats.getMana());
                st.setString(4, stats.getRegmana());
                st.setString(5, stats.getAtaque());
                st.setString(6, stats.getVeloataque());
                st.setString(7, stats.getArmadura());
                st.setString(8, stats.getResismagica());
                st.setString(9, stats.getVelomov());
                st.setInt(10, stats.getA_campeon());
                st.execute();
            }
        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al insertar datos Stats a la base de datos " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
            error = true;
        } finally {
            cerrarBaseDatos(conexion);
            if (!error) {
                Output_Box.Cuadro_Vacio("Tabla Rellenada Con Éxito", "Tabla Stats Rellenada");
                setVisible(false);
            }
        }
        return error;
    }

    public boolean guardarObjetoCampeon() {

        parser = new JsonParser();
        try {
            fr = new FileReader(directorioInstalacion + propiedades.getProperty("campeones"));
        } catch (FileNotFoundException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al leer el archivo Propiedades " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
        }
        datos = parser.parse(fr);

        Gson gson = new Gson();
        Campeon[] heroes = gson.fromJson(datos, Campeon[].class);

        conexion = conectarBaseDatos();

        try {
            st = conexion.prepareStatement(
                    "insert into campeon (p_campeon, mote,nombre,rol,lore,costeIP) values (?,?,?,?,?,?)"
            );

            for (Campeon hero : heroes) {
                st.setInt(1, hero.getId());
                st.setString(2, hero.getMote());
                st.setString(3, hero.getNombre());
                st.setString(4, hero.getRol());
                st.setString(5, hero.getLore());
                st.setInt(6, hero.getCosteIP());
                st.execute();
            }

        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al insertar datos Campeones a la base de datos " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
            error = true;
        } finally {
            cerrarBaseDatos(conexion);
            if (!error) {
                Output_Box.Cuadro_Vacio("Tabla Rellenada Con Éxito", "Tabla Campeon Rellenada");
            }

        }
        return error;
    }

    public boolean guardarObjetoPasiva() {

        parser = new JsonParser();
        try {
            fr = new FileReader(directorioInstalacion + propiedades.getProperty("pasivasJson"));
        } catch (FileNotFoundException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al leer el archivo Propiedades " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
        }
        datos = parser.parse(fr);

        final Gson gson = new Gson();
        Pasiva[] pasivas = gson.fromJson(datos, Pasiva[].class);

        conexion = conectarBaseDatos();

        try {
            st = conexion.prepareStatement(
                    "insert into pasiva ( nombre, descripcion,enfriamiento, a_campeon) values (?,?,?,?)"
            );

            for (Pasiva pasiva : pasivas) {
                st.setString(1, pasiva.getNombre());
                st.setString(2, pasiva.getDescripcion());
                st.setString(3, pasiva.getEnfriamiento());
                st.setInt(4, pasiva.getA_campeon());
                st.execute();

            }

        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al insertar datos Pasiva a la base de datos " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
            error = true;

        } finally {
            cerrarBaseDatos(conexion);
            if (!error) {
                Output_Box.Cuadro_Vacio("Tabla Rellenada Con Éxito", "Tabla Pasiva Rellenada");
            }

        }
        return error;
    }

    public boolean guardarObjetoHabilidad() {

        parser = new JsonParser();
        try {
            fr = new FileReader(directorioInstalacion + propiedades.getProperty("habilidad"));
        } catch (FileNotFoundException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al leer el archivo Propiedades " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
        }
        datos = parser.parse(fr);

        final Gson gson = new Gson();
        Habilidad[] habilidades = gson.fromJson(datos, Habilidad[].class);

        conexion = conectarBaseDatos();

        try {
            st = conexion.prepareStatement(
                    "insert into habilidad (nombre,descripcioncorta,descripcionlarga,tecla,coste,enfriamiento,a_campeon) values (?,?,?,?,?,?,?)"
            );

            for (Habilidad habi : habilidades) {

                st.setString(1, habi.getNombre());
                st.setString(2, habi.getDescripcioncorta());
                st.setString(3, habi.getDescripcionlarga());
                st.setString(4, habi.getTecla());
                st.setString(5, habi.getCoste());
                st.setString(6, habi.getEnfriamiento());
                st.setInt(7, habi.getA_campeon());
                st.execute();

            }

        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al insertar datos Habilidades a la base de datos " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
            error = true;
        } finally {
            cerrarBaseDatos(conexion);
            if (!error) {
                Output_Box.Cuadro_Vacio("Tabla Rellenada Con Éxito", "Tabla Habilidad Rellenada");
            }
        }
        return error;

    }

    public boolean guardarObjetoSkin() {

        parser = new JsonParser();
        try {
            fr = new FileReader(directorioInstalacion + propiedades.getProperty("skinRuta"));
        } catch (FileNotFoundException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al leer el archivo Propiedades " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
        }
        datos = parser.parse(fr);

        final Gson gson = new Gson();
        Skin[] skin = gson.fromJson(datos, Skin[].class);

        conexion = conectarBaseDatos();

        try {
            st = conexion.prepareStatement(
                    "insert into skin (nombre,imagen,costeRP,a_campeon) values (?,?,?,?)"
            );

            for (Skin skines : skin) {

                //System.out.println(skines.toString());
                st.setString(1, skines.getNombre());
                st.setString(2, skines.getImagen());
                st.setInt(3, skines.getCosteRP());
                st.setInt(4, skines.getA_campeon());
                st.execute();
            }

        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al insertar datos Skin a la base de datos " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
            error = true;
        } finally {
            cerrarBaseDatos(conexion);
            if (!error) {
                Output_Box.Cuadro_Vacio("Tabla Rellenada Con Éxito", "Tabla Skins Rellenada");
            }

        }

        return error;
    }

    // ------------------- Fin Guardado De Datos -------------------------------
    // --------------------- Muestra De Datos ----------------------------------
    public String mostrarLore(String nombre) {

        conexion = conectarBaseDatos();

        try {

            st = conexion.prepareStatement(
                    "select lore from campeon where nombre=" + "'" + nombre + "'"
            );

            result = st.executeQuery();

            this.lore = result.getString("lore");

        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al buscar al campeon de nombre " + nombre + " para mostrar la historia" + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
        } finally {
            cerrarBaseDatos(conexion);
        }
        return lore;
    }

    public ArrayList<Habilidad> mostrarHabilidad(String nombreBuscar) {
        conexion = conectarBaseDatos();

        ArrayList<Habilidad> arrayListHabilidades = null;

        try {

            arrayListHabilidades = new ArrayList<>();

            //select h.nombre,h.p_habilidad from habilidad h , campeon c where h.a_campeon=c.p_campeon and c.nombre='Janna' order  by h.p_habilidad
            st = conexion.prepareStatement(
                    "select h.nombre,h.descripcioncorta,h.descripcionlarga,h.coste,h.enfriamiento,h.tecla from habilidad h , campeon c where h.a_campeon=c.p_campeon and c.nombre=" + "'" + nombreBuscar + "'" + "order by h.p_habilidad"
            //"select h.nombre,h.descripcioncorta,h.descripcionlarga,h.coste,h.enfriamiento,h.tecla from habilidad h , campeon c where h.a_campeon=c.p_campeon and c.nombre="+"'"+nombreBuscar+"'"+ "and h.tecla="+"'"+tecla+"'"+ "order by h.p_habilidad"
            //"select h.nombre,h.descripcioncorta,h.descripcionlarga,h.coste,h.enfriamiento from habilidad h , campeon c where h.a_campeon=c.p_campeon and c.nombre="+"'"+nombreBuscar+"'"+ "order  by h.p_habilidad"
            //"select h.nombre,h.p_habilidad from habilidad h , campeon c where h.a_campeon=c.p_campeon and c.nombre="+"'" + nombreBuscar + "'"+ "order  by h.p_habilidad"
            );

            result = st.executeQuery();

            while (result.next()) {
                //String nombre, String descripcioncorta, String descripcionlarga, String tecla, String coste, String enfriamiento, int a_campeon
                Habilidad habi = new Habilidad(
                        result.getString("nombre"),
                        result.getString("descripcioncorta"),
                        result.getString("descripcionlarga"),
                        result.getString("tecla"),
                        result.getString("coste"),
                        result.getString("enfriamiento"),
                        0
                );

                arrayListHabilidades.add(habi);
            }

        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al buscar al campeon de nombre " + nombreBuscar + " para mostrar sus habilidades" + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
        } finally {
            cerrarBaseDatos(conexion);
        }
        return arrayListHabilidades;
    }

    public String mostrarCosteIpCampeon(String nombre) {
        conexion = conectarBaseDatos();

        try {

            st = conexion.prepareStatement(
                    "select costeIP from campeon where nombre=" + "'" + nombre + "'"
            );

            result = st.executeQuery();

            costeIP = result.getInt("costeIP");

            costeIPString = costeIP + "";

        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al buscar al campeon de nombre " + nombre + " para mostrar el coste de PI " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
        } finally {
            cerrarBaseDatos(conexion);
        }
        return costeIPString;
    }

    public String mostrarNombreMote(String nombre) {
        conexion = conectarBaseDatos();

        try {

            st = conexion.prepareStatement(
                    "select nombre,mote from campeon where nombre=" + "'" + nombre + "'"
            );

            result = st.executeQuery();

            this.nombre = result.getString("nombre");
            this.mote = result.getString("mote");

            this.nombreMote = nombre + " , " + mote;

        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al buscar al campeon de nombre " + nombre + " para mostrar el nombre mote" + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
        } finally {
            cerrarBaseDatos(conexion);
        }
        return nombreMote;
    }

    public String mostrarRolCampeon(String nombre) {
        conexion = conectarBaseDatos();

        try {

            st = conexion.prepareStatement(
                    "select rol from campeon where nombre=" + "'" + nombre + "'"
            );
            result = st.executeQuery();

            rolCampeon = result.getString("rol");

        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al buscar al campeon de nombre " + nombre + " para mostrar el rol que desempeña " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
        } finally {
            cerrarBaseDatos(conexion);
        }
        return rolCampeon;
    }

    public ArrayList<Pasiva> mostrarPasiva(String nombreBuscar) {
        conexion = conectarBaseDatos();

        ArrayList<Pasiva> arrayListPasivas = null;

        try {

            arrayListPasivas = new ArrayList<>();

            //select h.nombre,h.p_habilidad from habilidad h , campeon c where h.a_campeon=c.p_campeon and c.nombre='Janna' order  by h.p_habilidad
            st = conexion.prepareStatement(
                    "select p.nombre,p.descripcion,p.enfriamiento from pasiva p,campeon c where p.a_campeon=c.p_campeon and c.nombre=" + "'" + nombreBuscar + "'"
            );

            result = st.executeQuery();

            while (result.next()) {
                //String nombre, String descripcioncorta, String descripcionlarga, String tecla, String coste, String enfriamiento, int a_campeon
                Pasiva pasi = new Pasiva(
                        result.getString("nombre"),
                        result.getString("descripcion"),
                        result.getString("enfriamiento"),
                        0
                );

                arrayListPasivas.add(pasi);
            }

        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al buscar al campeon de nombre " + nombreBuscar + " para mostrar su pasiva " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
        } finally {
            cerrarBaseDatos(conexion);
        }
        return arrayListPasivas;
    }

    public ArrayList<Skin> mostrarSkins(String nombreBuscar) {
        conexion = conectarBaseDatos();

        String tablaNombre = "Skins";

        ArrayList<Skin> arrayListSkins = null;

        try {

            arrayListSkins = new ArrayList<>();

            //select h.nombre,h.p_habilidad from habilidad h , campeon c where h.a_campeon=c.p_campeon and c.nombre='Janna' order  by h.p_habilidad
            st = conexion.prepareStatement(
                    "SELECT s.nombre,s.imagen,s.costeRP FROM skin s , campeon c where s.a_campeon=c.p_campeon and c.nombre=" + "'" + nombreBuscar + "'"
            );

            result = st.executeQuery();

            while (result.next()) {
                Skin skines = new Skin(
                        result.getString("nombre"),
                        result.getString("imagen"),
                        result.getInt("costeRP"),
                        0
                );

                arrayListSkins.add(skines);
            }

        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al buscar al campeon de nombre " + nombreBuscar + " para mostrar sus Skins " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
        } finally {
            cerrarBaseDatos(conexion);
        }
        return arrayListSkins;
    }

    public Stats mostrarStats(String nombreBuscar) {

        String tablaNombre = "Stats";
        conexion = conectarBaseDatos();
        Stats stat = null;

        try {

            //select h.nombre,h.p_habilidad from habilidad h , campeon c where h.a_campeon=c.p_campeon and c.nombre='Janna' order  by h.p_habilidad
            st = conexion.prepareStatement(
                    "SELECT s.vida,s.regvida,s.mana,s.regmana,s.ataque,s.veloataque,s.armadura,s.resismagica,s.velomov FROM stat  s, campeon c where c.p_campeon= s.a_campeon and c.nombre=" + "'" + nombreBuscar + "'"
            );

            result = st.executeQuery();

            while (result.next()) {

                stat = new Stats(
                        result.getString("vida"),
                        result.getString("regvida"),
                        result.getString("mana"),
                        result.getString("regmana"),
                        result.getString("ataque"),
                        result.getString("veloataque"),
                        result.getString("armadura"),
                        result.getString("resismagica"),
                        result.getString("velomov"),
                        0
                );

            }

        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al buscar al campeon de nombre " + nombreBuscar + " para mostrar sus Stats " + ex.getMessage()), claseActual);
            CrashReporter cr = new CrashReporter(claseActual);
        } finally {
            cerrarBaseDatos(conexion);
        }
        return stat;
    }

    public ArrayList<String> nombresCampeones() {
        conexion = conectarBaseDatos();

        ArrayList<String> arrayListNombreCampeones = null;

        try {
            arrayListNombreCampeones = new ArrayList<>();

            st = conexion.prepareStatement("select nombre from campeon order by nombre");

            result = st.executeQuery();

            while (result.next()) {
                arrayListNombreCampeones.add(result.getString("nombre"));
            }

        } catch (SQLException ex) {
            CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al buscar el nombre de todos los campeones para ingresarlos al JComboBox " + ex.getMessage()), claseActual);
            
            CrashReporter cr = new CrashReporter(claseActual);
        } finally {
            cerrarBaseDatos(conexion);
        }

        return arrayListNombreCampeones;
    }
    // ----------------- Fin Muestra De Datos ----------------------------------

}
