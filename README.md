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


Creamos la instancia con conexionmysql2 (la base de datos), tenemos nuestras variables de cadena que nos almacenaran tanto un correo electronico, una contrasena y el destino del correo. Este tiene la funcion de enviar un correo con la informacion solicitada con la cuenta de procesamientodatoscriminales@gmail.com. 

```swift
public class Registro_Criminal extends javax.swing.JDialog {
    conexion.conexionmysql2 con = new conexionmysql2();
    Connection cn = con.conectar();
    
    String correo = "procesamientodatoscriminales@gmail.com";
    String contra = "oczjlmjzjddrkuhz";
    String correoDestino = "alexin262006@gmail.com";
 ```
En este metodo nos cera un arhcivo adjunto con un texto escrito dentro de el,la envia utilizando javax.mail. Sera enviada con la cuenta que ya fue configurada. 

```swift
private void createEmail() throws MessagingException{
            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
            p.put("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", correo);
            p.put("mail.smtp.ssl.protocols", "TLSv1.2");
            p.setProperty("mail.smtp.auth", "true");
            
            Session s = Session.getDefaultInstance(p);
            BodyPart texto = new MimeBodyPart();
            texto.setText("Espero se encuentre bien, enviamos este correo desde la oficina de procesamiento de datos criminales"
                        + " con la finalidad de que usted lleve a cabo la revisión de los datos del delincuente, cualquier duda"
                        + " no dude en responder este mensaje y nosotros la aclararemos a la brevedad.\n"
                        + "Sin nada más por el momento le mando un cordial saludo.");
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource("C:\\Users\\alexa\\OneDrive\\Escritorio\\REPORTES RECLUSORIO\\ingreso.pdf")));
            adjunto.setFileName("ingreso.pdf");
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(texto);
            m.addBodyPart(adjunto);
            
            
            
            MimeMessage mensaje = new MimeMessage(s);
            
            mensaje.setFrom(new InternetAddress(correo));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(correoDestino));
            mensaje.setSubject("INGRESO DE REO A PRISIÓN");
            mensaje.setContent(m);
            
            Transport t = s.getTransport("smtp");
            t.connect(correo, contra);
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            JOptionPane.showMessageDialog(null, "Mensaje enviado");
    }
```
NUevoIngreso(): fue creado para crear un nuevo documento PDF, donde abre un archvo de salida en la ruta especificada y lo guarda.

```swift
 private void nuevoIngreso(){
        Document documento = new Document();

    try {
        PdfWriter.getInstance(documento, new FileOutputStream("C:\\Users\\alexa\\OneDrive\\Escritorio\\REPORTES RECLUSORIO\\ingreso.pdf"));
        
        documento.open();
```

En este try es para agregar y acomoda el logo dentro del pdf. 

```swift
try {
            com.itextpdf.text.Image logoIzquierdo = com.itextpdf.text.Image.getInstance("src/imagenes/gobierno.jpg");
            logoIzquierdo.scaleToFit(300, 120);
            logoIzquierdo.setAbsolutePosition(50, 750);
            documento.add(logoIzquierdo);
            
            com.itextpdf.text.Image logoDerecho = com.itextpdf.text.Image.getInstance("src/imagenes/prision.png");
            logoDerecho.scaleToFit(90, 80);
            logoDerecho.setAbsolutePosition(450, 750);
            documento.add(logoDerecho);
        } catch (Exception e) {
            System.out.println("ERROR AL CARGAR LOS LOGOS: " + e);
        }
        ```

Nos agrega el titulo del documento PDF y nos lo centra en la pagina sus espacios. y de igual manera nos agrega un mensaje espesifico al PDF. Donde informamos sobre el ingreso de un criminal al reclusorio. 

```swift
 Paragraph title = new Paragraph("\n\nNuevo Delincuente Ingresado al Penal\n\n", 
                FontFactory.getFont("Tahoma", 22, Font.BOLD, BaseColor.DARK_GRAY));
        title.setAlignment(Element.ALIGN_CENTER);
        documento.add(title);

Paragraph mensaje2 = new Paragraph(
            "Se informa que ha habido un nuevo ingreso de un reo al penal. " +
            "El sistema penitenciario se encarga de la seguridad y custodia de los internos, " +
            "proporcionando un ambiente seguro tanto para los reclusos como para el personal. " +
            "Este reporte contiene los datos personales y las imágenes del nuevo recluso, " +
            "así como la información relevante para su seguimiento y control. " +
            "El objetivo es asegurar la correcta identificación y registro de cada interno, " +
            "manteniendo un control riguroso y preciso dentro del penal. " +
            "Es importante que todos los datos sean revisados y actualizados conforme a los protocolos establecidos.\n\n" +
            "Para más información, consulte las siguientes páginas del documento donde se detallan " +
            "los datos del nuevo recluso y se proporcionan imágenes para su correcta identificación.\n" +
            "Agradecemos su atención y colaboración en este proceso."
          + "\n\n\n\n\n\n\n\n\n\n\n\n Atentamente\n Oficial de Procesamiento de Datos de Criminales."
            , FontFactory.getFont("Tahoma", 14, Font.NORMAL, BaseColor.BLACK));
        mensaje2.setAlignment(Element.ALIGN_JUSTIFIED);
        documento.add(mensaje2);
```

Nos va a crear una tabla con los datos del criminal dentro del PDF. 

```swift
PdfPTable tablaDatos = new PdfPTable(2);
        tablaDatos.setWidthPercentage(100);//ancho a ocupar en la tabla
        tablaDatos.setSpacingBefore(20f);//espacio antes de la tabla
        tablaDatos.setSpacingAfter(20f);//espacio despues de la tabla

        // Definir fuentes para encabezados y datos
        Font fontHeader = FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.WHITE);
        Font fontData = FontFactory.getFont("Arial", 14, Font.NORMAL, BaseColor.BLACK);
        
        // Encabezados de los datos
        String[] headers = {"NUC", "Nombre(s)", "Apellidos", "Edad", "Delito", "Lugar de Nacimiento", "Tipo de Sangre"};
        String[] data = {
            txtNuc.getText(),
            txtNombre.getText(),
            txtApellido.getText(),
            txtEdad.getText(),
            txtDelito.getText(),
            txtNacimiento.getText(),
            cboTipoSangre.getSelectedItem().toString()
        };
        ```

Tennemos un arreglo que se ira recorriendo para crear datos y agregar celdas a la tabla. 
```swift
        for (int i = 0; i < headers.length; i++) {
            PdfPCell headerCell = new PdfPCell(new Phrase(headers[i], fontHeader));
            headerCell.setBackgroundColor(BaseColor.ORANGE);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(10f);
            tablaDatos.addCell(headerCell);

            PdfPCell dataCell = new PdfPCell(new Phrase(data[i], fontData));
            dataCell.setPadding(10f);
            tablaDatos.addCell(dataCell);
        }
        
        documento.add(tablaDatos);
```

Tedremos nuestra tabla de Imgenes, diciendole que tamano se requiere y en donde sera implementado.
```swift
 PdfPTable tablaImagenes = new PdfPTable(4);
        tablaImagenes.setWidthPercentage(100);
        tablaImagenes.setSpacingBefore(10f);
        tablaImagenes.setSpacingAfter(10f);

        try {
            ImageIcon[] imageIcons = new ImageIcon[] {
                (ImageIcon) lbl1.getIcon(),
                (ImageIcon) lbl2.getIcon(),
                (ImageIcon) lbl3.getIcon(),
                (ImageIcon) lbl4.getIcon(),
                (ImageIcon) lbl5.getIcon(),
                (ImageIcon) lbl6.getIcon(),
                (ImageIcon) lbl7.getIcon(),
                (ImageIcon) lbl8.getIcon()
            };

            for (ImageIcon icon : imageIcons) {
                com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(((ImageIcon) icon).getImage(), null);
                img.scaleToFit(120, 120);
                PdfPCell cell = new PdfPCell(img, true);
                cell.setPadding(10f); // Añadir espacio dentro de la celda
                cell.setBorderWidth(1); // Añadir borde a la celda
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tablaImagenes.addCell(cell);
            }

            // Añadir tabla de imágenes al documento
            documento.add(tablaImagenes);
```
    
 ```swift   
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
                    ps.setString(8, sexo);
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

Crea un documento PDF y se agregara un contenido:

```swift
Document documento = new Document();

    try {
        PdfWriter.getInstance(documento, new FileOutputStream("C:\\Users\\alexa\\OneDrive\\Escritorio\\REPORTES RECLUSORIO\\reporte.pdf"));
        
        documento.open();
```

Carga la imagen para poder agregarla al documento PDF como encabezado.
        
      ```swift
        try {
            com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/imagenes/header.png");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);
            documento.add(header);
        } catch (Exception e) {
            System.out.println("ERROR AL CARGAR LA IMAGEN DEL HEADER: " + e);
        }
        ```

Nos carga la imagen que seria el logo del Reclusorio y la agregara al documento PDF. con la yuada de un try para capturar alguna excepcion. 
        
        ```swift
        try {
            com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance("src/imagenes/prision.png");
            logo.scaleToFit(100, 100);
            logo.setAlignment(Chunk.ALIGN_CENTER);
            documento.add(logo);
        } catch (Exception e) {
            System.out.println("ERROR AL CARGAR LA IMAGEN DEL LOGO: " + e);
        }
        ```
    Nos crea 2 parrafos para agregarlo al PDF. El cual tendra un titulo "REPORTE DE DELINCUENTES ACTUALIZADO", con el tipo de letra "TAHOMA" con una fuente de 22 y color gris oscuro. Y el subtitutlo como "DELINCUENTES REGISTRADOS".

```swift
        Paragraph title = new Paragraph("REPORTE DE DELINCUENTES ACTUALIZADO\n\n", 
                FontFactory.getFont("Tahoma", 22, Font.BOLD, BaseColor.DARK_GRAY));
        title.setAlignment(Element.ALIGN_CENTER);
        documento.add(title);
        
        Paragraph subtitle = new Paragraph("Delincuentes Registrados\n\n", 
                FontFactory.getFont("Arial", 18, Font.ITALIC, BaseColor.GRAY));
        subtitle.setAlignment(Element.ALIGN_CENTER);
        documento.add(subtitle);
        
        // Añadir un párrafo con detalles
        Paragraph details = new Paragraph("Este reporte contiene información detallada sobre los delincuentes ingresados en el reclusorio.\n"
                                        + " A continuación, se presentan los datos más relevantes recopilados hasta la fecha:\n\n",
                FontFactory.getFont("Times New Roman", 14, Font.NORMAL, BaseColor.BLACK));
        details.setAlignment(Element.ALIGN_JUSTIFIED);
        documento.add(details);
```

Creamos una tabla en nuestro documento PDF y se llena con los datos obtenidos de nuetsra base de datos. Tendremos una tabla de 7 columnas y se define las celdas de encabezado opara cada clumna de igual forma se le establece un estilo de fuente y color. Ahora vamos a obtener los datos que se encuentran en nuestra base de datos para llenar la tabla asi que tendremos que tener conectada nuestra base de datos ya una ves llenado se agrega al documento. 

        ```swift
        PdfPTable tabla = new PdfPTable(7);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(10f);
        tabla.setSpacingAfter(10f);
        
        PdfPCell[] celdas = new PdfPCell[] {
            new PdfPCell(new Phrase("NUC", FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.WHITE))),
            new PdfPCell(new Phrase("Nombre(s)", FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.WHITE))),
            new PdfPCell(new Phrase("Apellidos", FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.WHITE))),
            new PdfPCell(new Phrase("Edad", FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.WHITE))),
            new PdfPCell(new Phrase("Delito", FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.WHITE))),
            new PdfPCell(new Phrase("Lugar de nacimiento", FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.WHITE))),
            new PdfPCell(new Phrase("Tipo de sangre", FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.WHITE)))
        };
        
        for (PdfPCell celda : celdas) {
            celda.setBackgroundColor(BaseColor.ORANGE);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(celda);
        }
        
        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/datosdelincuentes", "root", "");
            PreparedStatement pst = (PreparedStatement) cn.prepareStatement("SELECT * FROM presos");
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                tabla.addCell(rs.getString(1));
                tabla.addCell(rs.getString(2));
                tabla.addCell(rs.getString(3));
                tabla.addCell(rs.getString(4));
                tabla.addCell(rs.getString(5));
                tabla.addCell(rs.getString(6));
                tabla.addCell(rs.getString(7));
            }
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER DATOS DE LA BASE DE DATOS: " + e);
        }
        
        documento.add(tabla);
        
        documento.close();
        
        String mensaje = "REPORTE CREADO EXITOSAMENTE";
        JOptionPane.showMessageDialog(null, mensaje);
        ```

        Tendremos una forma correcta de abrir nuestro archivo PDF generado. Tomando en cuneta si nuestra Desktop soporta la funcionalidad de escritorio. Ya una ves hecho nos crea un obejto File como un archivo PDF y si el PDF existe lo abre sin rpoblemas.  
        
        ```swift
        if (Desktop.isDesktopSupported()) {
            File pdfFile = new File("C:\\Users\\alexa\\OneDrive\\Escritorio\\REPORTES RECLUSORIO\\reporte.pdf");
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            }
        }
        
    } catch (Exception e) {
        System.out.println("ERROR EN PDF " + e);
    }
    }                     
      ```         


 ## Explicacion de librerias
 
 - import com.mysql.jdbc.Connection;

Esta clase se usa para establecer una conexión con una base de datos MySQL. Proporciona métodos para conectar, manejar y cerrar conexiones con la base de datos.
- import com.mysql.jdbc.PreparedStatement;

Esta clase permite crear declaraciones SQL precompiladas y parametrizadas. Es útil para ejecutar consultas de manera segura, evitando problemas como la inyección SQL.
- import conexion.conexionmysql;

Este parece ser un import personalizado que se refiere a una clase dentro de tu proyecto. Generalmente, esta clase se usa para manejar la conexión a la base de datos MySQL, encapsulando los detalles de conexión.
- import java.awt.Graphics;

Esta clase proporciona un conjunto de métodos para dibujar gráficos primitivos como líneas, rectángulos y figuras. Es esencial para cualquier operación de dibujo en componentes de AWT o Swing.
- import java.awt.Image;

La clase Image es una clase abstracta que representa imágenes gráficas. Puede ser usada para manejar imágenes en aplicaciones Java.
- import javax.swing.JOptionPane;

Esta clase proporciona métodos estáticos para mostrar cuadros de diálogo estándar como mensajes de información, advertencia, error, confirmación y entrada de datos.
- import java.sql.ResultSet;

Esta interfaz proporciona métodos para acceder y manipular los datos recuperados de una base de datos como resultado de una consulta SQL. Permite iterar sobre las filas de datos.
- import javax.swing.ImageIcon;

Esta clase encapsula una imagen y puede ser usada en componentes Swing, como etiquetas (JLabel) o botones (JButton), para mostrar imágenes.
- import javax.swing.JPanel;

Es un contenedor genérico que se puede usar para agrupar otros componentes de la interfaz de usuario y administrar su disposición.
- import com.itextpdf.text.BaseColor;
  
Esta clase se usa para definir colores en los documentos PDF. Puedes especificar colores usando valores RGB.
- import com.itextpdf.text.Chunk;

Es la parte más pequeña de un texto en un documento PDF. Representa una cadena de texto con un formato específico.
- import com.itextpdf.text.Document;

Esta clase representa un documento PDF. Contiene métodos para añadir contenido, abrir y cerrar el documento.
- import com.itextpdf.text.DocumentException;

Esta excepción se lanza cuando ocurre un error al manipular un documento PDF.
- import com.itextpdf.text.Font;

Esta clase se usa para definir fuentes (tipo, tamaño, estilo) en los documentos PDF.
- import com.itextpdf.text.FontFactory;

Esta clase proporciona métodos para crear y manejar fuentes en los documentos PDF.
- import com.itextpdf.text.pdf.PdfPTable;

Esta clase se usa para crear tablas en un documento PDF.
  - import java.io.File;
 
Esta clase representa archivos y directorios en el sistema de archivos.
- import java.io.FileInputStream;

Esta clase permite leer bytes desde un archivo.
- import java.io.FileOutputStream;

 Esta clase permite escribir bytes a un archivo
 - import java.util.Properties;

Esta clase representa un conjunto de propiedades, que son pares clave-valor. Se usa comúnmente para manejar configuraciones.
- import java.util.logging.Level;

Esta clase define los niveles de registro para el Logger.
 - import java.util.logging.Logger;

Esta clase se usa para registrar mensajes en aplicaciones Java. Permite registrar mensajes con diferentes niveles de severidad.
- import javax.activation.DataHandler;

 Esta clase se usa para manejar datos enviados o recibidos en correos electrónicos, especialmente para archivos adjuntos.
 - import javax.activation.FileDataSource;

Esta clase se usa para encapsular archivos como fuentes de datos para correos electrónicos.
- import javax.mail.BodyPart;

Representa una parte del cuerpo de un mensaje de correo electrónico.
- import javax.mail.Message;

Esta clase representa un mensaje de correo electrónico.
- import javax.mail.MessagingException;

Se lanza cuando ocurre un error en el manejo de mensajes de correo electrónico.
- import javax.mail.NoSuchProviderException;

Se lanza cuando no se encuentra un proveedor de servicios de correo.
- import javax.mail.Session;

 Representa una sesión de correo electrónico. Se usa para configurar y obtener las propiedades y el autenticador.
 - import javax.mail.Transport;

Esta clase se usa para enviar mensajes de correo electrónico.
- import javax.mail.internet.AddressException;

Se lanza cuando se encuentra una dirección de correo electrónico con un formato incorrecto.
- import javax.mail.internet.InternetAddress;

Representa una dirección de correo electrónico.
- import javax.mail.internet.MimeBodyPart;

Representa una parte del cuerpo de un mensaje MIME.
- import javax.mail.internet.MimeMessage;

Esta clase representa un mensaje de correo electrónico en formato MIME.
- import javax.mail.internet.MimeMultipart;

Esta clase representa un mensaje que contiene varias partes en formato MIME.
- import javax.swing.JFileChooser;

Esta clase proporciona una ventana de diálogo estándar para seleccionar archivos o directorios.
- import javax.swing.filechooser.FileNameExtensionFilter;

Esta clase se usa para filtrar los archivos visibles en un JFileChooser basado en sus extensiones.
- import java.awt.Desktop;

Esta clase se usa para interactuar con el escritorio del sistema, como abrir archivos con la aplicación predeterminada.
- import java.awt.Toolkit;

Esta clase proporciona métodos para interactuar con las herramientas gráficas del sistema.
- import java.sql.Connection;

Esta interfaz se usa para establecer una conexión con la base de datos.
- import java.sql.DriverManager;

Esta clase se usa para gestionar un conjunto de controladores JDBC. Facilita la creación de conexiones a bases de datos.
- import java.sql.ResultSet;

Esta interfaz proporciona métodos para acceder y manipular los datos recuperados de una base de datos como resultado de una consulta SQL.
- import Clase.Cliente2;

Este parece ser un import personalizado que se refiere a una clase llamada Cliente2 dentro del paquete Clase. Generalmente, esta clase se usaría para representar la lógica o los datos de un cliente en tu aplicación.
- import java.awt.Color;

La clase Color se usa para encapsular colores en el espacio de color sRGB. Puede ser usada para establecer colores en gráficos, componentes de interfaz de usuario, y más.
- import java.util.regex.*;

Este import incluye todas las clases del paquete java.util.regex, que proporciona clases para trabajar con expresiones regulares en Java, como Pattern y Matcher.
- import javax.swing.table.DefaultTableModel;

Esta clase es una implementación de la interfaz TableModel que utiliza un Vector de Vector de Object para almacenar los datos del modelo de tabla. Facilita la creación y manipulación de tablas en JTable.
- import org.jfree.chart.ChartFactory;

Esta clase proporciona métodos estáticos para crear una variedad de gráficos (charts) como barras, líneas, pasteles, etc., usando la biblioteca JFreeChart.
- import org.jfree.chart.ChartFrame;

Esta clase se usa para mostrar un gráfico en una ventana de marco (frame). Facilita la visualización de gráficos generados por JFreeChart.
- import org.jfree.chart.JFreeChart;

Esta clase representa un gráfico completo en JFreeChart. Se utiliza para personalizar y renderizar el gráfico.
- import org.jfree.chart.plot.PlotOrientation;

Esta clase define las constantes utilizadas para especificar la orientación de un gráfico (vertical u horizontal).
- import org.jfree.data.category.DefaultCategoryDataset;

Esta clase se utiliza para crear y manipular conjuntos de datos categóricos, que son necesarios para generar gráficos de barras, líneas, etc., en JFreeChart.
- import com.itextpdf.text.Element;

 Esta clase define constantes y métodos para alinear y posicionar elementos en un documento PDF.
 - import com.itextpdf.text.Phrase;

Una Phrase es un grupo de Chunks y es el elemento más pequeño de texto que puede ser añadido a un ColumnText en iText. Sirve para contener múltiples fragmentos de texto.
- import com.itextpdf.text.pdf.PdfPCell;

Esta clase representa una celda en una tabla PDF (PdfPTable). Puedes usarla para controlar el contenido y el formato de cada celda en la tabla.
- import Clase.Cliente;

Este es un import personalizado que se refiere a una clase Cliente dentro del paquete Clase. Generalmente, esta clase se usaría para representar la lógica o los datos de un cliente en tu aplicación.
- import conexion.conexionmysql2;

Este es un import personalizado que se refiere a una clase dentro de tu proyecto que maneja la conexión a la base de datos MySQL, similar a conexionmysql.
- import java.lang.System.Logger;

Esta clase proporciona un sistema de registro de eventos de bajo nivel. Es utilizado para registrar mensajes con diferentes niveles de severidad.
- import com.mysql.jdbc.Statement;

 Esta clase permite ejecutar sentencias SQL sin parámetros en una base de datos MySQL. Se usa comúnmente para consultas y actualizaciones simples.
 - import java.sql.SQLException;

Esta excepción se lanza cuando ocurre un error al acceder a la base de datos.
- import org.jfree.chart.plot.PlotOrientation;

Esta clase define las constantes utilizadas para especificar la orientación de un gráfico (vertical u horizontal).
- import java.sql.*;

Este import incluye todas las clases e interfaces del paquete java.sql. Se usa para interactuar con bases de datos en Java y contiene clases e interfaces como Connection, Statement, ResultSet, y SQLException.


  Se realizo una redaccion de una forma sencilla y entendible para el manejo de los datos. 
Reclusorio fue hecho para poder tener un control con los datos dentro de el. Hay que tener almacenados los datos que se podran requerir en algun futuro, entonces fue importante saber como hacer la conexion con la base de datos. Este proyecto fue hecho con la finalidad de saber mejor el manejo de la interfaz entre otros temas que se fue aprendiendo en el camino.
Hecho por:
Torres Cortes Alexander Jassiel
Cruz Alonso Kelly Adanari 
   

