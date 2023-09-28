# ArredondoGarridoLaura
Ejercicio Funko, Laura Garrido Arredondo
![image](https://github.com/Vanelota/ArredondoGarridoLaura/assets/132077920/b691bf13-bfaa-49a5-9f63-259d66a8915f)
<br>
<p>Primero esto es la base de mi respositorio.</p>
<h1>Tenemos en la carpeta src/main:</h1>
<b>-CSV:</b>
<p>Dentro de esta carpeta esta el fichero de funkos csv</p>
<b>-->Carpeta java (Donde se encuentra la codificación)</b>
<br>
<b>-Carpeta adaptador:</b>
<p>Dentro de esta carpeta esta el fichero encargado de que no de problemas el LocalDate a la hora de leer o crear el fichero json de Funkos.</p>

![image](https://github.com/Vanelota/ArredondoGarridoLaura/assets/132077920/80d1d8e7-c6e0-440d-9bef-d7e8c6c91abf)
<ul>
  <p>Formato de Fecha y Hora:</p>
  <li>La clase utiliza `DateTimeFormatter` para especificar el formato en el que se deben representar las fechas `LocalDate` cuando se conviertan a JSON. El formato utilizado es "yyyy-MM-dd" para mayor entendimiento.</li>
  <p>Método write</p>
<li>El método `write` se encarga de convertir un objeto `LocalDate` en una cadena JSON y escribirlo en un `JsonWriter`.</li> 
<li>Si el valor del `LocalDate` es nulo, se llama a `out.nullValue()` para representarlo como un valor nulo en JSON y asi evitar problemas.</li>
<li> Si el valor de `LocalDate` no es nulo, se utiliza el formateador para convertirlo en una cadena con el formato especificado y se escribe en el `JsonWriter`.</li>
  <p>Método read</p>
<li>El método `read` se encarga de convertir una cadena JSON en un objeto `LocalDate` al leer desde un `JsonReader`.</li>
   <li>Comienza comprobando si el próximo token en el `JsonReader` es un valor nulo y Si es así, significa que el valor de `LocalDate` en JSON es nulo.</li>
  <li>Si el próximo no es un valor nulo, se lee la cadena JSON que representa la fecha utilizando `in.nextString()`, y luego se utiliza el formateador para convertirla en un objeto `LocalDate` utilizando `LocalDate.parse(dateStr, formatter)`.</li>
</ul>

<b>-Carpeta Crud: </b>
<p>Dentro de esta carpeta se encuentran los ficheros para poder hacer el crud de Funko, con una interfaz y las clases Repositorio y Servicio que se encargaran de manejar los crud y find by nombre y id.</p>

![image](https://github.com/Vanelota/ArredondoGarridoLaura/assets/132077920/dbc71bbf-16eb-4b9c-8603-b1efe71d54d8)
<ul>
  <p>Aqui se muestra la interfaz del cual las dos clases de java se toman de referencia. Como se puede ver se muestra:</p>
<li>save: Guarda un objeto y devuelve el mismo objeto.</li>
<li>update: Actualiza un objeto y devuelve el mismo objeto.</li>
<li>findById: Busca un objeto por su ID y devuelve un Optional.</li>
<li>findAll: Busca y devuelve todos los objetos.</li>
<li>deleteById: Borra un objeto por su ID y devuelve true si todo funciona correctamente.</li>
<li>deleteAll: Borra todos los objetos en la base de datos.</li>

</ul>
<b>-Carpeta database:</b>
<p>Esta carpeta esta encargada de crear la base de datos, con sus propiedades, configuracion y su manejador de problemas.</p>

![image](https://github.com/Vanelota/ArredondoGarridoLaura/assets/132077920/6a8d18f3-4ba5-4410-a255-f4f7a5839729)
<ul>
  <li>Aqui se muestra el fichero databaseConfig, donde como se puede ver solo se encarga de las configuraciones para poder entrar a la base de datos siguiendo su url, poniendo exactamente usuario y contraseña bien. Y asi poder entrar a la base de datos de Funko.</li>
</ul>
<b>-Carpeta model:</b>
<p>En esta carpeta se encontrara el fichero Funko del cual sera la base para funko y sus propiedades establecidas. Junto con el Main de la clase, del cual inicializara las busquedas.</p>

![image](https://github.com/Vanelota/ArredondoGarridoLaura/assets/132077920/ad257492-f4f0-41cf-bbdf-49f3764a4a68)

<ul>
<li>Aqui se muestra la estructura de Funko.java donde se tienen en cuenta las propiedades de Funko al leer los datos del csv. (Y aunque no se ve en la imagen completamente, los getter y setters de cada uno de las propiedades del Funko.</li>
  
</ul>
<b>-Fichero Leercsv:</b>
<p>Se encargará de leer el fichero csv de funkos.</p>

<ul>
<li>Primero se crea una lista juguetes para almacenar objetos de la clase Funko.</li>
<li>Luego se define una ruta path que apunta al archivo "funkos.csv". La ruta se construye utilizando la ubicación absoluta del directorio actual del programa.</li>
<b>Se utiliza la biblioteca opencsv para leer el archivo CSV.</b>
<br>
<li>Se omite la primera línea del archivo,por tener encabezados de las columnas dentro del csv.</li>
<li>Luego, se lee cada línea del archivo CSV, se procesa y se crea un objeto Funko a partir de los datos de cada línea. Los datos se extraen y se asignan a las propiedades del objeto Funko.</li>
<li>Cada objeto Funko se agrega a la lista juguetes.</li>
<li>Si la lista juguetes no está vacía, se imprime información sobre cada objeto Funko en la lista, incluyendo su código, nombre, modelo, precio y fecha de lanzamiento.</li>

</ul>
<b>-Fichero write Json:</b>
<p>Se encargará de escribir el json de funkos.</p>
<ul>
  <li>Primero se Importa las bibliotecas Gson y opencsv.</li>
<li>En el método writeJSON, crea un archivo JSON en una ubicación específica y escribe una lista de objetos Funko en él usando Gson para la serialización.</li>
<li>Y por ultimo en el método main, se utiliza Leercsv para con la clase writeJSON  escribir esos datos en un archivo JSON llamado "Funkos.json".</li>
</ul>
<b>-Carpeta Json donde estara el json de Funkos.</b>
<b>-Carpeta resources</b>
