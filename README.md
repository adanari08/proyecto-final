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

