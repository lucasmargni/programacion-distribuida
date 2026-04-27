# Programación de sistemas distribuidos

En este repositorio se desarrolla la resolución de un mismo problema utilizando distintos mecanismos en cada proyecto, indicado por el nombre de la carpeta correspondiente.

El problema consiste en 4 entidades:
- Cliente
- Servidor Central
- Servidor Clima
- Servidor Horóscopo

El cliente realiza una consulta al servidor central pidiendo una predicción del horóscopo para un signo determinado y el pronóstico del clima dada una fecha. El servidor central, utilizando el signo, consultará al servidor horóscopo y, con la fecha, al servidor clima. Las predicciones de los servidores horóscopo y clima son random.

Los servidores deben ser capaces de atender múltiples consultas a la vez, y para una misma consulta (misma fecha o mismo signo) se debe devolver el mismo resultado.

## Cómo probar

> [!IMPORTANT]
> Se debe estar posicionado en la carpeta padre del proyecto para que se pueda probar todo correctamente


```bash
# compilar los archivos
./scripts/compilar.sh sockets

# ejecutar el proyecto indicado
./scripts/ejecutar.sh sockets
```

El script para ejecutar solo manda un solo cliente a modo de prueba. Si se quiere crear mas clientes se debe ejecutar:

```bash
java sockets.cliente.Cliente
```

Todo el ejemplo de ejecución se hizo usando el proyecto de sockets. Si se quiere cambiar esto y probar otro proyecto, remplazar esta palabra con el nombre de la carpeta correspondiente