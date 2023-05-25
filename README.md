# Compilador parser recursivo decendente

## Descripción
Es un compilador creado utilizando el algoritmo parcer recursivo desendente, analizando el archivo
fuente de entrada con un analizador lexicografico, el cual genera un archivo con extensión .anl en 
el cual se escribe token,lexema y renglón de la gramatica analizada, a continuación un ejemplo del
archivo que se mensiona anteriormente.
```
if //token
if //lexema
1  //renglon
(  //token
(  //lexema
1  //renglón
num
35.12
1
igual
==
1
id
var_1
1
)
)
1
{
{
1
```
Este archivo es utilizado como entrada, para crear el parser.

## Ejecución
Para ejecutar se utiliza el archivo `globo.bat` con parametro de el nombre de un archivo fuente 
sin extensión.
1. Compilar todos los archivos  `.java `, "situarnos en el directorio donde se encuentran los archvos .java"
```
javac *.java
```
2. Ejecutar el archivo `.bat`

```
./globo.bat prog1
```