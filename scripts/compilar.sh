#!/bin/bash

# Se debe pasar un parametro correspondiente a la carpeta a ejecutar
if [ $# -eq 0 ]; then
    echo "Error: se necesita pasar como parametro el nombre del proyecto a ejecutar"
    exit 1
fi

# El parametro pasado debe ser una carpeta existente
if [ ! -d $1 ]; then
    echo "Error: La carpeta '$1' no existe."
    exit 1
fi

javac $1/**/*.java