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

# Lanza scripts en distintas terminales
x-terminal-emulator -e bash -c "java $1.servidor_horoscopo.ServidorHoroscopo; exec bash" &
x-terminal-emulator -e bash -c "java $1.servidor_clima.ServidorClima; exec bash" &
x-terminal-emulator -e bash -c "java $1.servidor_central.ServidorCentral; exec bash" &

sleep 1s # El cliente debe conectarse cuando todos los servidores ya funcionen

x-terminal-emulator -e bash -c "java $1.cliente.Cliente; exec bash" &