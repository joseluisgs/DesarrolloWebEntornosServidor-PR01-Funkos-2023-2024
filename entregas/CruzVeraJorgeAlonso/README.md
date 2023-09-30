# Proyecto Funkos

## Funko.java - CLASE POJO

Implementacion de atributos, Constructor y Getters & Setters a traves de la libreria Lombok.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/45d02456-e280-4f10-9c0b-0842f8fb474f)

## Modelo.java - ENUM MODELOS

Clase ENUM de Modelos para regular el paso de datos de este atributo. 
Tiene 4 definidos: MARVEL, DISNEY, ANIME, OTROS.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/01f5860d-0a7c-4b6d-87bc-3adf81cdad52)


## Funcionalidad Lectura de Archivo CSV

Se ha desarrollado la funcionalidad de la lectura de Archivo CSV en la cual se encuentran los datos separados por ','.
En la clase ReadCSVFunkos hemos creado un unico metodo llamado ***readFileFunko()*** el cual devuelve un Arraylist del
Objeto "Funko".
```

    public ArrayList<Funko> readFileFunko() {
        String path = Paths.get("").toAbsolutePath().toString() + File.separator + "data" + File.separator + "funkos.csv";
        ArrayList<Funko> funks = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try(CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))){
            String[] line;
            reader.readNext();
        while ((line = reader.readNext()) != null){
            Funko fk = new Funko();
            fk.setUuid(UUID.fromString(line[0].length()>36?line[0].substring(0,35):line[0]));
            fk.setName(line[1]);
            fk.setModelo(Modelo.valueOf(line[2]));
            fk.setPrecio(Double.parseDouble(line[3]));
            fk.setFecha_lanzamiento(LocalDate.parse(line[4],formatter));
            funks.add(fk);
        }
        }catch (Exception e){
            e.printStackTrace();
        }

        return funks;
    }

```
Empezamos guardando la Ruta Absoluta de donde leeremos el archivo .csv.

Continuamos creando un ArrayList del objeto funko que mas adelante devolveremos con los Objetos leidos del fichero.

Para la lectura de las fechas he utilizado un DateTimeFormatter con el patron *("yyyy-MM-dd")* que luego sera utilizado
para parsear la Fecha_Lanzamiento.

Como podemos apreciar hacemos uso de la dependencia ***opencsv*** y de su clase ***CSVReader*** para realizar la lectura 
del Fichero. Todo esto se realiza utilizando *try-with-resources* para los cierres automaticos.
Al Objeto CSVReader se le pasa un lector de ficheros el cual busca en el **path** declarado antes.

Creamos un Array de Strings el cual guardara cada linea del fcihero.
Saltamos una linea por que es el **Nombre de los datos** y creamos un bucle que creara un Objeto **Funko**, le dara los datos leidos
a traves de los metodo Set del objeto, luego, procede a añadirlos a nuestro ArrayList creado anteriormente.

En caso todo salga correctamente retornamos la lista con los Funkos.


## Funcionalidad Escribir en un archivo JSON

Se desarrollo la funcionalidad de la escritura en un archivo JSON utilizando la dependencia **GSON** en una clase llamada **WriteFUNKOSJson"
implementando un unico metodo llamado "writeJSON" con 2 parametros:
-routePath: String que refiere el nombre que tendra el fichero creado.
-funks: Lista que contiene los Funkos a escribir.

```

public boolean writeJSON(String routePath, List<Funko> funks) {
    String path = Paths.get("").toAbsolutePath().toString() + File.separator + "data" + File.separator + routePath;
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();
    boolean success = false;
    try (FileWriter writer = new FileWriter(path)) {
        gson.toJson(funks, writer);
        success = true;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return success;
}


```
Empezamos guardando la ruta donde se guardara el archvio .JSON que se creara.
Creamos un objeto GsonBuilder, le registramos un adaptador para la fecha llamado LocalDateAdapter 
(Clase que hereda de TypeAdapter y sobreescribe el metodo **write**).
Utilizamos "PrettyPrinting" para que la salida en fichero sea mas limpia y legible y lo creamos.

Tambien tenemos una variable success que nos dira si se escribi correctamente.

En un try-with-resources abrimos un FileWriter y le pasamos la ruta ya guardada en "path".

Por ultimo, usando el metodo toJson de GSON le pasamos la lista y writer, si todo sale bien otorga
a success true y lo devuelve.

## Class LocalDateAdapter

Clase utilizada como adaptador para el formato del LocalDate.

```

public class LocalDateAdapter extends TypeAdapter<LocalDate> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(formatter.format(value));
        }
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        return null;
    }
}

```

En esta clase extendemos de TypeAdapter pasandole lo que queremos convertir, en este caso un LocalDate.
Finalmente, sobreescribimos los metodos **write** y **read**

## Excepciones

Creamos las Excepciones especifias.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/438da35c-77a5-4f1b-adc7-2cfbfb9f0fcd)

## Locale

Clase Locale para la conversion de tipos como la moneda y las fechas segun locacion.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/a9e13cc1-9cd4-41d4-be99-df7feb3f66b5)

## config.properties

Fichero de properties con los datos de conexion a la Base de Datos. (Properties con # comentandos).

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/cf07c2ba-79f8-4ebc-b40e-4576d6d4c2a9)

## init.sql

Fichero SQL que contiene el Create Table de la clase Pojo Funko.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/53c733fa-d667-4ce0-bb21-dc5b5401a8c4)


## DatabaseManager

Clase manejadora de la Base de Datos que contiene

### ATRIBUTOS

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/f7d073fe-57cb-47fc-939a-699e7d51f7c1)

Tiene en sus atributos algunos que se utilizaran luego para la conexion a la Base de Datos.
Algunos de los principales son:
-instance: Instancia de la propia base de datos //Importante para utilizar un Singleton de la clase.
-connection: Objeto Connection de SQL para la generar la sesion a la Base de Datos especificada.
-preparedStatement: Objeto representante de la PreCompilacion de un Query SQL.

### INSTANCION Y SINGLETON

Aqui vemos el Constructor de la clase junto a 2 metodos esenciales de la clase para volverla un Singleton.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/05bf47b8-6339-4d24-9efb-3a2c4637c6fe)


El Constructor es privado para que no pueda ser llamado exteriormente.
Este al ser llamado ejecuta metodos que veremos mas adelante, por ejemplo:
-configFromProperties(): Trae las configuraciones del Properties y los define en cada atributo.
-openConnection():Inicializa la Conexion dandoinicio a una nueva sesion.
-executeScript(): Executa el script "init.sql".

Tambien se pregunta si el atributo ***chargeInit*** es true para cargar nuestro fichero "init.sql" y crear las tablas.

El metodo getInstance() pregunta si la instancia no esta iniciada, si esta cerrada la inicia.
Luego la devuelve.

### Manejo de conexiones

Los 3 metodos que utilizan la conexion, Inicializandola, Obteniendola y Cerrandola.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/205faa28-7c11-41cc-a107-35da8b0f86e2)

### Configuracion desde Properties

En este metodo traemos de config.propertie las propiedades y las guardamos en los atributos de la clase. 

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/6ac42332-43fd-4823-be58-c0d488a22f39)

### Ejecucion de Script

En este metodo pedimos el path del script y un boolean que nos confirme si se quiere escribir el log de ejecucion.
Usamos la clase ScriptRunner de SQL.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/a6c18d99-6704-42d2-b2e2-b970cf3729ff)

## Repositorios de clase

### CRUDRepository.java - Repositorio Generico.

Este repositorio tiene las operaciones basicas de un CRUD.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/7a13c054-468e-4e8e-8683-b69e4646351b)

### FunkoRepository.java - Repositorio de metodos propios de **Funko**

Este repositorio hereda de CRUDRepository y aparte implementa metodos adicionales.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/2878f44d-22fa-4da4-8881-5dcdd2ea6f53)

### FunkoRepositoryImpl.java - Repositorio de Implementacion

Este repositorio implementa los metodos de Funko y Crud Repository.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/260baf39-6025-4e6f-80d1-ac9f260d74a4)

Es un Singleton, asi que tiene los atributos necesarios para ello.
Tambien implementa la libreria Logger, para mostrar mensajes por consola en ejecucion.


### Implementacion del Singleton.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/9269cd0a-3db1-40ab-9240-ce757aecc2e4)

### Implementacion del CRUD

**save()**

Metodo que guarda un Funko recibido por parametro en la Base de Datos.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/a7c9d9d4-db4b-4723-9b3b-07ba314be63c)

Si todo sale correctamente devuelve el Funko guardado, si no salta una Excepcion Personalizada.
Utilizamos el metodo **commit()** de Connection para la persistencia de los datos en la Base de datos siempre.

**update()**

Metodo de actualiza un Funko ya existente en la Base de Datos con los datos de uno recibido por parametro.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/a24b63f6-b62a-4410-8ae1-5a6ff5177f9f)

Retorna el Funko en caso todo salio correctamente, si no lanza una Excepion Personalizada.

**findById()**

Metodo para obtener un Objeto de la Base de Datos segun el ID pasado por parametro.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/5edae6e2-6f42-4cf1-bbcb-de40f9104479)

Retorna un Optional del Funko en caso correcto o un Optionas Empty en caso de no encontrarlo.

**findAll()**

Metodo que obtiene todos los Objetos de la Base de Datos.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/68f4e751-bd71-4fd3-be10-6f8efc24b8cb)

Retorna una Lista de Funkos con los Objetos obtenidos de la Base de Datos.

**deletedById()**

Metodo que elimina un Objeto de la Base de Datos segun el ID pasado por Parametro.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/d2489e60-41eb-427f-86d6-695bf50c5de4)

Retorna un Boolean True en caso salga todo correctamente, en caso no encontrarlo lanza un Excepcion Personalizada.

**deletedAll()**

Metodo que elimina todos los Objetos de la Base de Datos.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/b0d242c0-322b-40d2-b358-64366a3f53d4)

No retorna nada.

**findByName()**

Metodo que obtiene una lista de Objeto de la Base de Datos segun un conjunto de Caracteres pasado por parametros.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/8bb1e59f-ca76-4523-b120-60b22487f983)

Retorna una lista con todos los Funkos Obtenidos de la Base de Datos.

**backup()**

Metodo que crea un Fichero JSON llamado segun lo que se recibe como parametro.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/19534fe2-bafb-4b19-aedb-ae9a0341116e)

Retorna un Boolean True o False segun la creacion del Fichero.


### Servicios de Clase
## FunkoService.java - Interfaz que implementa los metodos a ejecutar.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/fad855bd-b7bd-4226-9550-038982b9b37a)

## FunkoServiceImpl.java - Clase que implementa la interfaz FunkoService.java

Esta implementa tambien una CACHE con una memoria de 25, es un SINGLETON y tiene tambien un Logger para la escrutira en ejecucion.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/286d06e3-e834-4d8a-86e5-ecb9ff18a93c)


## Inicializacion de la Instancia y de la CACHE en un LinkedHashMap.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/5b84ede6-dc1b-4191-aedd-3e4f3997d5a5)

**save()**

Implementacion del metodo SAVE en el Servicio.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/c60914b6-a103-45a0-8d73-b2869cf23d30)

**update()**

Implementacion del metodo UPDATE en el Servicio.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/89572ce6-e69d-4cb3-98d0-15d3a1290d5b)

**findById()**

Implementacion del metodo FINDBYID en el Servicio.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/d0baceaf-50e2-417b-aebf-7ebd6d8e556f)


**findAll()** 

Implementacion del metodo FINDALL en el Servicio.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/098fb4cd-bd4d-4b6d-8e37-893201516dc0)

**findByName()**

Implementacion del metodo FINDBYNAME en el Servico.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/09f69cf3-b278-4d8a-a775-84e44fba58a7)

**deleteById()**

Implementacion del metodo DELETEBYID en el Servicio.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/08ea4c55-548d-423d-8869-e5390977ab87)

**deleteAll()**

Implementacion del metodo DELETEALL en el Servicio.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/a9b5fe60-e9c9-430b-a468-8fc13cd96950)

**backup()**

Implementacion del metodo BACKUP en el Servicio.

![image](https://github.com/Alonso2002-jpg/DesarrolloWebEntornosServidor-PR01-Funkos-2023-2024/assets/70552761/7a524716-87e2-47c2-b434-b1b2e1f11e01)
