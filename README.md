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
| sendEmail() | envía un correo electrónico utilizando SMTP | 
| limpiar() | encarga de limpiar o restablecer los valores de varios componentes |
|emailTo = emailFrom.trim(); | se refiere a copiar la dirección de correo electrónico almacenada, eliminando cualquier espacio en blanco al principio y al final de la dirección de correo electrónico en el proceso| 
|mProperties.put("mail.smtp.host", "smtp.gmail.com");|establece el servidor SMTP que se utilizará para enviar correos electrónicos|
|mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");| se establece la confianza en el servidor SMTP de Gmail|
|mProperties.put("mail.smtp.starttls.enable", "true");|se utiliza para mejorar la seguridad de la conexión SMTP al cifrar la comunicación entre el cliente y el servidor|
|mProperties.put("mail.smtp.port", "587");|se configura el puerto 587, que es el puerto estándar utilizado para conexiones SMTP con STARTTLS habilitado|
|mProperties.put("mail.smtp.user", emailFrom);|establece el nombre de usuario que se utilizará para autenticarse en el servidor SMTP|
|mProperties.put("mail.smtp.ssl.protocols", "TLS1.2");|especifica los protocolos SSL que se permitirán para la conexión|
|mProperties.put("mail.smtp.auth", "true");| habilita la autenticación en el servidor SMTP| 

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


