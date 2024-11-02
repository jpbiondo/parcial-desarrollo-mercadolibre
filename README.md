## Parcial desarrollo MercadoLibre
El parcial consiste en el análisis de un ADN proveída en el endpoint `/mutant` que deberá ser mutante para poder pasar la prueba. Un ADN
es de tipo `String[]` compuesto por caracteres: A, C, T o G. El ADN será mutante cuando se encuentre más de una secuencia de cuatro de sus caracteres
válidos consecutivos de forma vertical, horizontal u oblicua:


<img src="https://github.com/user-attachments/assets/8e5fbc7b-939b-43db-9c4e-4c9647283425" width="400">



Puedes realizar las pruebas del proyecto en forma online [aquí](https://parcial-desarrollo-mercadolibre.onrender.com/).
## Ejecución del proyecto
1. Clonar el repositorio.
```sh
git clone https://github.com/jpbiondo/parcial-desarrollo-mercadolibre.git
cd parcial-desarrollo-mercadolibre
```
2. Contruir el proyecto. Para armar el proyecto, instalar las dependencias requeridas:
```sh
./gradlew build
```
3. Correr el proyecto

## API Endpoints
### POST `/mutant`
Analiza el ADN en el cuerpo de la petición y indica si es mutante o no. El formato del cuerpo de la petición es el siguiente:
```json
{
    "dna": [
        "ATGCGA",
        "AATAAA",
        "TCATGT",
        "ATCAAT",
        "CCATCA",
        "TCTCTT"
    ]
}

```
Las posibles respuestas se listan a continuación:
```json
//Es mutante - 200 OK
true
//No es mutante - 403 Forbidden
{
  "error":"Not a mutant"
}

//Formato inválido - 403 Forbidden
{
  "error":"invalid dna format"
}
```
### GET `/stats`
Recopila las pruebas y resultados de los ADNs en la base de datos y devuelve el cociente(ratio) de mutantes entre humanos.
Si $cantHumanos = 0$ entonces el ratio devuelve $cantMutantes$.


$$
ratio = \frac{cantMutantes}{cantHumanos}
$$

## Diagramas de secuencias
### Enpoint `/mutant` 
![Mutantes Endpoint](https://github.com/user-attachments/assets/64086b3e-4fe2-46ac-82b1-8a0272d147a7)
### Endpoint `/stats`
![Stats Endpoint](https://github.com/user-attachments/assets/a609b858-fd94-4663-9d6d-6c9a4c7622bd)

## Modelo de arquitectura
Actualmente la arquitectura implemente un modelo cliente-servidor. El cliente se comunica mediante los endpoints del servidor, insertando un cuerpo en la petición si se necesitase. Esta solución podría escalar cacheando las peticiones utilizando un Redis, permitiendo escalar la capacidad del servidor ya que las peticiones recurrentes se
cachearán y serán accesibles rápidamente gracias a que Redis cachea los datos en un formato llave-valor en memoria.
![image](https://github.com/user-attachments/assets/f9e6db12-f031-4388-9314-fcc698204195)



