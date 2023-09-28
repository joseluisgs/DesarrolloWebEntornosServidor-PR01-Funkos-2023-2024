## Aplicación

## Estructura
- El Main instancia FunkoProgram
- FunkoProgram utiliza Singleton para evitar instancias duplicadas. FunkoProgram maneja la lógica del programa.
- El paquete validators contiene las validaciones (clase FunkoValidator, la cual comprueba que el Funko sea uno válido a crear).
- El paquete utils contiene las utilidades: ApplicationProperties es una clase Singleton que sirve para leer el archivo de propiedades. Las demás son adaptadores de fechas y UUID.
- El paquete exceptions contiene las excepciones personalizadas.
- El paquete models contiene las clases POJO del programa
- El paquete repositories contiene todas las clases repositorio que interactuarán con la base de datos.
- El paquete services contiene los servicios que serán llamados por el controlador.
- El controlador llama al servicio para ejecutar las tareas que pida el usuario.