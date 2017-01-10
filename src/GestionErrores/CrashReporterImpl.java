/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionErrores;

import Clases.Rutas;
import Clases.VentanaPopup;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public final class CrashReporterImpl extends JFrame implements ActionListener, CrashReporter {

    JPanel panelTodo;

    String claseActual;

    JTextArea descProblemaText;

    public CrashReporterImpl(String claseActual) {
        this.claseActual = claseActual;
        definirVentana();
        definirPanelTodo();
        definirPanelTodoDentro();
    }

    public void definirVentana() {

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setTitle("Crash Reporter");

    }

    public void definirPanelTodo() {
        panelTodo = new JPanel();
        panelTodo.setLayout(new BorderLayout());

    }

    public void definirPanelTodoDentro() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.PAGE_AXIS));

        JPanel descProblemaPanel = new JPanel();
        descProblemaPanel.setLayout(new BoxLayout(descProblemaPanel, BoxLayout.PAGE_AXIS));

        JLabel descProblemaEtiqueta = new JLabel("Descripción Del Problema");
        descProblemaText = new JTextArea(300, 200);
        descProblemaText.setLineWrap(true);

        descProblemaPanel.add(descProblemaEtiqueta);
        descProblemaPanel.add(descProblemaText);

        JPanel botonPanel = new JPanel();
        botonPanel.setLayout(new BoxLayout(botonPanel, BoxLayout.PAGE_AXIS));

        JButton botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(this);

        botonPanel.add(botonEnviar);

        panelTitulo.add(descProblemaPanel);
        panelTitulo.add(botonPanel);

        panelTodo.add(panelTitulo, BorderLayout.CENTER);

        add(panelTodo);
        setVisible(true);
    }

    static public String fechaActual() {
        Date date = new Date();
        String stringFormatoDate = "d M yyyy hh:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(stringFormatoDate);
        String dateFormato = dateFormat.format(date);
        return dateFormato;
    }

    // TODO aqui falta excepcion 
    public static void meterCadena(String cadenaMeter, String NombreFichero) {

        try (FileWriter Escribir_Fichero = new FileWriter(Rutas.rutaInstalacion + "/InfoLol/Logs/" + NombreFichero + ".txt", false)) {
            Escribir_Fichero.write(cadenaMeter + "\n");
        } catch (IOException error) {
        }
    }

    
    @Override
    public void envioCorreo(String asunto, String mensaje) {

        String adjunto = Rutas.rutaInstalacion + "/InfoLol/Logs/" + claseActual + ".txt";
        String miCorreo = "maquina1995prueba@gmail.com";
        String destinatario = "maquina1995@gmail.com";
        String miContra = "1995prueba1995";
        String servidorSMTP = "smtp.gmail.com";
        String puertoEnvio = "587";

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puertoEnvio);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(miCorreo, miContra);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(miCorreo));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destinatario));
            message.setSubject(asunto);

            MimeMultipart multiParte = new MimeMultipart();

            BodyPart texto = new MimeBodyPart();
            texto.setText(mensaje);

            

            DataSource fuente = new FileDataSource(adjunto);
            texto.setDataHandler(new DataHandler(fuente));
            texto.setFileName(adjunto);

            multiParte.addBodyPart(texto);
            message.setContent(multiParte);

            Transport.send(message);

        } catch (MessagingException ex) {
            VentanaPopup.mostrarVentana("Error Al Enviar El Mail","Error: Revise los permisos de su firewall",2);
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equalsIgnoreCase("Enviar")) {
            envioCorreo("Crasheo En La Clase: ".concat(claseActual), descProblemaText.getText());
            System.exit(1);
        }
    }

}

