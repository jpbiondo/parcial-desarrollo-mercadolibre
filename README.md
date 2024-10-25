## Parcial desarrollo MercadoLibre
El parcial consiste en el análisis de un ADN proveída en el endpoint `/mutant` que deberá ser mutante para poder pasar la prueba. Un ADN
es de tipo `String[]` compuesto por caracteres: A, C, T o G. El ADN será mutante cuando se encuentre más de una secuencia de cuatro de sus caracteres
válidos consecutivos de forma vertical, horizontal u oblicua:


<img src="https://github.com/user-attachments/assets/8e5fbc7b-939b-43db-9c4e-4c9647283425" width="400">

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
### POST /mutant
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
### GET /stats
Recopila las pruebas y resultados de los ADNs en la base de datos y devuelve el cociente(ratio) de mutantes entre humanos.
Si $cantHumanos = 0$ entonces el ratio devuelve $cantMutantes$.


$$
ratio = \frac{cantMutantes}{cantHumanos}
$$



