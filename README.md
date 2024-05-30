# Reclusorio 

## Descripcion 

Reclusorio es un MVC Modelo de vista Controlador. El modelo consiste en el copntrol de datos en un reclusorio mediante una base de datos. En Reclusorio tendremos acceso a el con un ususario y una contrasena la cual al ingresar dichos datos nos enviara a distintas ventanas dependiendo de a quien se trate. Tenemos una vista de Administracion de Datos, de Registro Penal, una de Registro de la Persona. En este diseno tendremos la ventana de poder crear, eliminar, actualizar y leer. 

## Carcateristicas 

- Se guardaran los datos requeridos a la base de datos.
- Se visualizara ventanas dependiendo del Usuario y la Contrasena que se ingrese.
- Se visualizara imagenes de los Delincuentes registrados.
- Creacion de PDF con los datos de los delincuentes que el Usuario seleccione.
- Se enviara un correo electronico.

## Instalacion de MySQL

  Para la instalacion de Netbeans a una base de datos haremos lo siguiente

   - Instalar XAMMP MySQL iniciamos APACHE Y MySQL y haga clic en admin que nos enviara una ventana para poder crear nuestra base de datos. Crearemos una base de datos llamada "datosusuarios" y una tabla llamada "ususarios" que tendremos como datos el Usuario, Nombre, Apellido, Edad, Cargo, Contrasena y ruta. Cada uno sera de tipo varchar a excepcion de "Edad" que es de tipo int.

#### Usuario
| Usuario | Nombre | Apellido | Edad | Carga | Contrasena | Ruta |
|---------|--------|----------|------|-------|------------|------|

De esa forma podemos guardar nuestros datos que se enviaran desde el JFrame.

## Iniciar Sesion 

#### Codigo 

| Nombre | Tipo de dato que retorna | Tipo de dato que recibe | Descripcion |
|--------|--------------------------|-------------------------|-------------|

#### Bloque de Codigo 
```swift
try{
                PreparedStatement ps = (PreparedStatement) cn.prepareStatement("SELECT Cargo FROM usuarios WHERE Usuario='"+usuario+"' AND Contraseña='"+contraseña+"'");
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    String tiponivel = rs.getString("Cargo");
                    
                    if(tiponivel.equalsIgnoreCase("Oficial de Procesamiento de Datos de Criminales")){
                        dispose();
                        Registro_Criminal delincuentes = new Registro_Criminal();
                        delincuentes.setVisible(true);
                        
                    }else if(tiponivel.equalsIgnoreCase("Especialista en Selección de Personal")){
                        dispose();
                        Registro_Personal trabajadores = new Registro_Personal();
                        trabajadores.setVisible(true);
                        
                    }else if(tiponivel.equalsIgnoreCase("Oficial de Inteligencia Criminal")){
                        dispose();
                        Aministración_Datos buscadores = new Aministración_Datos();
                        buscadores.setVisible(true);
                    }
                    
                }else{
                    JOptionPane.showMessageDialog(null,"USUARIO Y/O CONTRASEÑA INCORRECTOS");
                    txtUsuario.setText("");
                    txtContraseña.setText("");
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"ERROR AL INICIAR SESIÓN"+e);
            }
        }else{
            JOptionPane.showMessageDialog(null,"DEBES COMPLETAR LOS CAMPOS");
        }
    }                                                
```

## Conexion JFrame Iniciar Sesion con la Base de Datos 

crearemos una clase java donde tendremos lo siguiente
```swift
public class conexionmysql {
    Connection cn;
    
    public Connection conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost/datosusuario","root","");
            System.out.println("CONECTADO");
        }catch(Exception e){
            System.out.println("ERROR DE CONEXIÓN BD"+e);
        }
        return cn;
    }
}
```
### Metodo 
| Nombre | Que dato retorna | Que dato recibe | Descripcion | 
|--------|------------------|-----------------|-------------|
| conectar() | objeto Conexcion | | Establece una Conexion de la Base de Datos |


## Registro Criminal

En esta ventana tendremos las opciones de ingresar datos que nos soliciten, sera para registrar a un delincuente que sera guardado en la base de datos. 

| Nombre | Descripcion |
|--------|-------------|
| createEmail() | prepara la información necesaria para enviar un correo electrónico, incluyendo el asunto, contenido, y configuración SMTP | 
| limpiar() | encarga de limpiar o restablecer los valores de varios componentes |
|emailTo = emailFrom.trim(); | se refiere a copiar la dirección de correo electrónico almacenada, eliminando cualquier espacio en blanco al principio y al final de la dirección de correo electrónico en el proceso| 
|mProperties.put("mail.smtp.host", "smtp.gmail.com");|establece el servidor SMTP que se utilizará para enviar correos electrónicos|
|mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");| se establece la confianza en el servidor SMTP de Gmail|
|mProperties.put("mail.smtp.starttls.enable", "true");|se utiliza para mejorar la seguridad de la conexión SMTP al cifrar la comunicación entre el cliente y el servidor|
|mProperties.put("mail.smtp.port", "587");|se configura el puerto 587, que es el puerto estándar utilizado para conexiones SMTP con STARTTLS habilitado|
|mProperties.put("mail.smtp.user", emailFrom);|establece el nombre de usuario que se utilizará para autenticarse en el servidor SMTP|
|mProperties.put("mail.smtp.ssl.protocols", "TLS1.2");|especifica los protocolos SSL que se permitirán para la conexión|
|mProperties.put("mail.smtp.auth", "true");| habilita la autenticación en el servidor SMTP| 
|mCorreo.setFrom(new InternetAddress(emailFrom));| indica quién está enviando el correo electrónico|
|mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));| indica a quién se enviará el correo electrónico.|
|mCorreo.setSubject(subject);| Se establece el asunto del correo electrónico utilizando el valor de la variable subject|
|mCorreo.setText(content, "ISO-8859-1", "html");| Se establece el contenido del correo electrónico|

```swift
private void createEmail(){
        emailTo = emailFrom.trim();
        String mensaje = "ADVERTENCIA!! Ha sido ingresado un nuevo delincuente";
        subject = mensaje.trim();
        content ="Estimado Jefe,\n" +
"\n" +
"Quisiera informarle que hemos recibido un nuevo recluso en nuestras instalaciones. Si desea más detalles puede consultar los registros.\n" +
"\n" +
"Atentamente trabajador anónimo" +
"Oficial de Procesamiento de Datos de Criminales".trim();
        
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.put("mail.smtp.starttls.enable", "true");
        mProperties.put("mail.smtp.port", "587");
        mProperties.put("mail.smtp.user", emailFrom);
        mProperties.put("mail.smtp.ssl.protocols", "TLS1.2");
        mProperties.put("mail.smtp.auth", "true");
        
        mSession = Session.getDefaultInstance(mProperties);
        
        
        try {
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(emailFrom));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mCorreo.setSubject(subject);
            mCorreo.setText(content, "ISO-8859-1", "html");
            
        } catch (AddressException ex) {
            Logger.getLogger(Registro_Criminal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Registro_Criminal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void sendEmail(){
        try {
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(emailFrom, passwordFrom);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();
            JOptionPane.showMessageDialog(null, "CORREO ENVIADO");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Registro_Criminal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Registro_Criminal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    void limpiar(){
        txtNuc.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEdad.setText("");
        txtDelito.setText("");
        txtNacimiento.setText("");
        cboTipoSangre.setSelectedItem("Seleccionar");
        lbl1.setIcon(null);
        txtRuta1.setText("");
        lbl2.setIcon(null);
        txtRuta2.setText("");
        lbl3.setIcon(null);
        txtRuta3.setText("");
        lbl4.setIcon(null);
        txtRuta4.setText("");
        lbl5.setIcon(null);
        txtRuta5.setText("");
        lbl6.setIcon(null);
        txtRuta6.setText("");
        lbl7.setIcon(null);
        txtRuta7.setText("");
        lbl8.setIcon(null);
        txtRuta8.setText("");
    }
```
| Nombre | Descripcion |
|--------|-------------|
| sendEmail() | envía un correo electrónico utilizando SMTP |
|Transport mTransport = mSession.getTransport("smtp");|se utiliza para conectar y enviar el correo electrónico a través del protocolo SMTP|
|mTransport.connect(emailFrom, passwordFrom);| autentica al remitente en el servidor SMTP para poder enviar el correo electrónico|
|mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));| Se envía el mensaje de correo electrónico|


Ingreso de datos donde se ira almacenando en la variable correspondiente y comparar que ningun campo de encuentre vacio. nos prepara la consulta para almacenarla. 

```swift
String nuc = txtNuc.getText();
        String nacimiento = txtNacimiento.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String edad = txtEdad.getText();
        String delito = txtDelito.getText();
        String tiposangre = (String) cboTipoSangre.getSelectedItem();

        if(nuc.isEmpty()||nacimiento.isEmpty()||nombre.isEmpty()||apellido.isEmpty()||edad.isEmpty()||tiposangre.isEmpty()){
            JOptionPane.showMessageDialog(null,"DEBES RELLENAR TODOS LOS CAMPOS");
        }else{
            try{
                String consulta = "INSERT INTO presos (rnd,nombre,apellido,edad,delito,lugarNacimiento,tipoSangre,ruta1,ruta2,ruta3,ruta4,ruta5,ruta6,ruta7,ruta8)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = (PreparedStatement) cn.prepareStatement(consulta);

                ps.setString(1, txtNuc.getText());
                ps.setString(2, txtNombre.getText());
                ps.setString(3, txtApellido.getText());
                ps.setString(4, txtEdad.getText());
                ps.setString(5, txtDelito.getText());
                ps.setString(6, txtNacimiento.getText());
                ps.setString(7, tiposangre);
                ps.setString(8, txtRuta1.getText());
                ps.setString(9, txtRuta2.getText());
                ps.setString(10, txtRuta3.getText());
                ps.setString(11, txtRuta4.getText());
                ps.setString(12, txtRuta5.getText());
                ps.setString(13, txtRuta6.getText());
                ps.setString(14, txtRuta7.getText());
                ps.setString(15, txtRuta8.getText());

                int i = ps.executeUpdate();
                if(i>0){
                    JOptionPane.showMessageDialog(null,"EL DELINCUENTE HA SIDO INGRESADO CORRECTAMENTE");
                    limpiar();
                }

            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"NO SE PUDO INGRESAR EL DELINCUENTE"+e);
            }
        }
    }                                           

```

```swift
FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de Archivos JPEG(*.JPG;*.JPEG;*.PNG)","jpg","jpeg","png");
        JFileChooser archivo = new JFileChooser();
        archivo.addChoosableFileFilter(filtro);
        archivo.setDialogTitle("Abrir Archivo");
        File ruta = new File("C:\\Users\\alexa\\OneDrive\\Imágenes\\Imagenes presos");
        archivo.setCurrentDirectory(ruta);
        int ventana = archivo.showOpenDialog(null);
        if(ventana == JFileChooser.APPROVE_OPTION){
            File file = archivo.getSelectedFile();
            txtRuta5.setText(String.valueOf(file));
            Image foto = getToolkit().getImage(txtRuta5.getText());
            foto = foto.getScaledInstance(lbl5.getWidth(), lbl5.getHeight(), Image.SCALE_SMOOTH);
            lbl5.setIcon(new ImageIcon(foto));
        }
```
Se crea un filtro de extensión de archivo para permitir la selección de archivos con extensiones .jpg, .jpeg o .png. Este filtro se utilizará para restringir los tipos de archivos que el usuario puede seleccionar en el cuadro de diálogo de selección de archivo. Este bloque de código permite al usuario seleccionar un archivo de imagen y mostrarlo en un JLabel en la interfaz de usuario. Además, limita la selección de archivos a formatos específicos utilizando un filtro de extensión de archivo.


## Registro Personal 
Este código se encarga de validar y registrar los datos de un nuevo trabajador en una base de datos, mostrando mensajes de éxito o error según sea necesario.
|Nombre | Descripcion |
|-------|-------------|
|PreparedStatement ps = (PreparedStatement) cn.prepareStatement(consulta);| Esto se hace para evitar la inyección SQL y permitir la inserción de valores parametrizados |

```swift
String usuario = txtUsuario.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String edad = txtEdad.getText();
        String cargo = (String)cboCargo.getSelectedItem();
        String contraseña = txtContraseña.getText();

        if(usuario.isEmpty()||nombre.isEmpty()||apellido.isEmpty()||edad.isEmpty()||contraseña.isEmpty()){
            JOptionPane.showMessageDialog(null,"DEBES RELLENAR TODOS LOS CAMPOS");
        }else{
            if(cargo.equalsIgnoreCase("Seleccionar")){
                JOptionPane.showMessageDialog(null,"DEBES DARLE UN CARGO AL TRABAJADOR");
            }else{
                try{
                    String consulta = "INSERT INTO usuarios (Usuario,Nombre,Apellido,Edad,Cargo,Contraseña,ruta)VALUES(?,?,?,?,?,?,?)";
                    PreparedStatement ps = (PreparedStatement) cn.prepareStatement(consulta);

                    ps.setString(1, txtUsuario.getText());
                    ps.setString(2, txtNombre.getText());
                    ps.setString(3, txtApellido.getText());
                    ps.setString(4, txtEdad.getText());
                    ps.setString(5, cargo);
                    ps.setString(6, contraseña);
                    ps.setString(7, lbl1.getText());

                    int i = ps.executeUpdate();
                    if(i>0){
                        JOptionPane.showMessageDialog(null,"EL TRABAJADOR HA SIDO REGISTRADO CORRECTAMENTE");
                        limpiar();
                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,"NO SE PUDO INGRESAR EL TRABAJADOR"+e);
                }
            }
        }
    }
```

Este bloque de código se encarga de preparar y ejecutar una consulta SQL para insertar un nuevo registro en una tabla de base de datos. Si la inserción es exitosa, muestra un mensaje de éxito; de lo contrario, muestra un mensaje de error con información sobre la excepción que ocurrió.

```swift
FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de Archivos JPEG(*.JPG;*.JPEG;*.PNG)","jpg","jpeg","png");
        JFileChooser archivo = new JFileChooser();
        archivo.addChoosableFileFilter(filtro);
        archivo.setDialogTitle("Abrir Archivo");
        File ruta = new File("C:\\Users\\alexa\\OneDrive\\Imágenes\\Imagenes trabajadores");
        archivo.setCurrentDirectory(ruta);
        int ventana = archivo.showOpenDialog(null);
        if(ventana == JFileChooser.APPROVE_OPTION){
            File file = archivo.getSelectedFile();
            lbl1.setText(String.valueOf(file));
            Image foto = getToolkit().getImage(lbl1.getText());
            foto = foto.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(foto));
        }
```
Se crea un filtro de extensión de archivo para permitir la selección de archivos con extensiones .jpg, .jpeg o .png. Este filtro se utilizará para restringir los tipos de archivos que el usuario puede seleccionar en el cuadro de diálogo de selección de archivo. Este bloque de código permite al usuario seleccionar un archivo de imagen y mostrarlo en un JLabel en la interfaz de usuario. Además, limita la selección de archivos a formatos específicos utilizando un filtro de extensión de archivo.


## Administrador de Datos

Esta clase proporciona métodos para cargar datos en una tabla, filtrar datos según ciertos criterios y generar gráficos de barras basados en los datos de la tabla. 

|Nombre | Descripcion |
|-------|-------------|
|DefaultTableModel model;| se utilizará para manejar los datos en la tabla |
|barras();| genera un gráfico de barras que muestra la cantidad de diferentes tipos de delitos cometidos. Utiliza la biblioteca JFreeChart para crear el gráfico |
|cargarTabla(String cad)| se encarga de cargar datos en la tabla desde algún origen de datos, como una base de datos |
|filtrarDatos(int posicion, String valor)| Este método se utiliza para filtrar los datos en la tabla presos según algún criterio|

```swift
public class Administracion_Datos extends javax.swing.JDialog {
    DefaultTableModel model;

    /**
     * Creates new form Administracion_Datos
     */
    public Administracion_Datos(javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        cargarTabla("");
    }
    
     public Administracion_Datos() {
        initComponents();
        this.setLocationRelativeTo(null);
        cargarTabla("");
    }
     
     public void Barras(){
         int asalto = 0;
         int robo = 0;
         int asesinato = 0;
         int estafa = 0;
         
         try{
             DefaultCategoryDataset cd = new DefaultCategoryDataset();
             //Recorrido por la tabla
             for (int i=0; i<tDatos.getRowCount(); i++){
                 if(tDatos.getValueAt(i,5).equals("Asalto")){
                     asalto++;
                 }else if(tDatos.getValueAt(i,5).equals("Robo")){
                     robo++;
                 }else if(tDatos.getValueAt(i,5).equals("Asesinato")){
                     asesinato++;
                 }
                 else if(tDatos.getValueAt(i,5).equals("Estafa")){
                     estafa++;
                 }
             }
             cd.addValue(asalto, "Asalto", "");
             cd.addValue(robo, "Robo", "");
             cd.addValue(asesinato, "Asesinato", "");
             cd.addValue(estafa, "Estafa", "");
             JFreeChart fc = ChartFactory.createBarChart3D("", "Tipo de delito cometido", "Cantidad", cd, PlotOrientation.HORIZONTAL, true, true, true);
             ChartFrame cf = new ChartFrame("Tipo de delito cometido", fc);
             cf.setSize(600, 600);
             cf.setLocationRelativeTo(null);
             cf.setVisible(true);
         }catch(Exception e){
         }
     }
     
    
    void cargarTabla(String cad){
        Cliente c = new Cliente();
        c.CargarTabla(tDatos, cad);
    }
    
    void filtrarDatos(int posicion, String valor){
        String sql;
        if(posicion==0){
            sql = "SELECT * FROM presos";
        }
    }
```
Este fragmento de código en Java crea un documento PDF que contiene un informe de delincuentes registrados.
Importaos las siguientes librerias. 
```swift
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.sql.*;
import javax.swing.JOptionPane;
```
|Nombre | Descripcion|
|-------|------------|
|header.setAlignment(Chunk.ALIGN_CENTER);|se usa para centrar un texto dentro de un Chunk|
|PdfPTable tabla = new PdfPTable(7);| Creacion de una Tabla|

Se establece una conexión con la base de datos y se ejecuta una consulta para obtener todos los registros de la tabla presos. Luego, se recorren los resultados y se añaden a la tabla en el documento. 

```swift
Document documento = new Document();
        
        try{
            PdfWriter.getInstance(documento, new FileOutputStream("C:\\Users\\alexa\\OneDrive\\Documentos\\NetBeansProjects\\PROYECTO RECLUSORIO\\RECLUSORIO2\\PDF's REPORTES\\Reporte_Delincuentes.pdf"));
            
            Image header = Image.getInstance("src/imagenes/header.png");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);
            
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("REPORTE DE DELINCUENTES ACTUALIZADO \n\n");
            parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));
            parrafo.add("Delincuentes Registrados \n\n");
            
                    
            documento.open();
            
            documento.add(header);
            documento.add(parrafo);
            
            PdfPTable tabla = new PdfPTable(7);
            tabla.addCell("NUC");
            tabla.addCell("Nombre(s)");
            tabla.addCell("Apellidos");
            tabla.addCell("Edad");
            tabla.addCell("Delito");
            tabla.addCell("Lugar de nacimiento");
            tabla.addCell("Tipo de sangre");
            
            try{
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/datosdelincuentes","root","");
                PreparedStatement pst = (PreparedStatement) cn.prepareStatement("select * from presos");
                
                ResultSet rs = pst.executeQuery();
                
                if(rs.next()){
                    
                    do{
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                        tabla.addCell(rs.getString(6));
                        tabla.addCell(rs.getString(7));

                    }while(rs.next());
                    documento.add(tabla);
                }
            }catch(DocumentException | SQLException e){
            }
            documento.close();
            String mensaje = "REPORTE CREADO EXITOSAMENTE EN \n\nC:\\Users\\alexa\\OneDrive\\Documentos\\NetBeansProjects\\PROYECTO RECLUSORIO\\RECLUSORIO2\\PDF's REPORTES";
            JOptionPane.showMessageDialog(null, mensaje);
        }catch (Exception e){
            System.out.println("ERROR EN PDF "+e);
        }
    }           
```
Este fragmento de código se encarga de extraer datos de un ResultSet de una base de datos y añadirlos a una tabla en un documento PDF.

## Envio de Correo Electronico 

|Nombre | Descripcion|
|-------|------------|
|createEmail() |configura las propiedades del correo y prepara el mensaje con el contenido y los adjuntos |
|Session.getDefaultInstance(mProperties)| Crea una sesión con las propiedades configuradas |
|sendEmail() | maneja la conexión al servidor SMTP y envía el mensaje |


Configuración de las propiedades del correo: Define las propiedades necesarias para conectarse al servidor SMTP de Gmail.

- mail.smtp.host: Servidor SMTP de Gmail.
- mail.smtp.ssl.trust: Permite confiar en el servidor SMTP especificado.
- mail.smtp.starttls.enable: Habilita STARTTLS para la conexión segura.
- mail.smtp.port: Puerto SMTP.
- mail.smtp.user: Usuario del correo.
- mail.smtp.ssl.protocols: Protocolos SSL a usar.
- mail.smtp.auth: Habilita la autenticación SMTP.


```swift
public Envio_Correo(javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        mProperties = new Properties();
        nombres_archivos = "";
    }

    
        private void createEmail(){
        emailTo = txtReceptor.getText().trim();
        subject = txtAsunto.getText().trim();
        content =txtContenido.getText().trim();
        
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.put("mail.smtp.starttls.enable", "true");
        mProperties.put("mail.smtp.port", "587");
        mProperties.put("mail.smtp.user", emailFrom);
        mProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.put("mail.smtp.auth", "true");
        
        mSession = Session.getDefaultInstance(mProperties);
        
        
        try {
            MimeMultipart mElementosCorreo = new MimeMultipart();
            //Contenido del correo
            MimeBodyPart mContenido = new MimeBodyPart();
            mContenido.setContent(content, "text/html; charset=utf-8");
            mElementosCorreo.addBodyPart(mContenido);
            
            //Agregar archivos adjuntos
            MimeBodyPart mAdjuntos = null;
            for(int i=0; i<mArchivosAdjuntos.length; i++){
                mAdjuntos = new MimeBodyPart();
                mAdjuntos.setDataHandler(new DataHandler(new FileDataSource(mArchivosAdjuntos[i].getAbsolutePath())));
                mAdjuntos.setFileName(mArchivosAdjuntos[i].getName());
                mElementosCorreo.addBodyPart(mAdjuntos);
            }
            
            
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(emailFrom));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mCorreo.setSubject(subject);
            mCorreo.setContent(mElementosCorreo);
            //mCorreo.setText(content, "ISO-8859-1", "html");
            
        } catch (AddressException ex) {
            Logger.getLogger(Registro_Criminal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Registro_Criminal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void sendEmail(){
        try {
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(emailFrom, passwordFrom);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();
            JOptionPane.showMessageDialog(null, "CORREO ENVIADO");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Registro_Criminal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Registro_Criminal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
```

|Nombre | Descripcion|
|-------|------------|
|chooser.setMultiSelectionEnabled(true);| Permite seleccionar múltiples archivos al mismo tiempo|
|chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); | Configura el JFileChooser para que solo permita la selección de archivos, no de directorios|

 El código permite al usuario seleccionar múltiples archivos y luego muestra los nombres de esos archivos en una etiqueta. Usa JFileChooser para la selección de archivos y construye una cadena de texto en formato HTML para mostrar los nombres de los archivos en la etiqueta. 
```swift
JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        if(chooser.showOpenDialog(this) != JFileChooser.CANCEL_OPTION){
            mArchivosAdjuntos = chooser.getSelectedFiles();
            
            for(File archivo : mArchivosAdjuntos){
                nombres_archivos += archivo.getName() + "<br>";
            }
            
            lblArchivos.setText("<html><p>" + nombres_archivos + "</p></html>");
        }

```
Se realizo una redaccion de una forma sencilla y entendible para el manejo de los datos. 
Reclusorio fue hecho para poder tener un control con los datos dentro de el. Hay que tener almacenados los datos que se podran requerir en algun futuro, entonces fue importante saber como hacer la conexion con la base de datos. Este proyecto fue hecho con la finalidad de saber mejor el manejo de la interfaz entre otros temas que se fue aprendiendo en el camino.
Hecho por:
Torres Cortes Alexander Jassiel
Cruz Alonso Kelly Adanari 
