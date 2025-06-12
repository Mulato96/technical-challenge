#!/bin/bash

# Nombres de contenedores que pueden causar conflicto
CONTAINERS=("postgres_productos" "productos-service" "inventario-service")
LOG_FILE="docker_start.log"

echo "📦 Iniciando proceso de limpieza y despliegue..." | tee "$LOG_FILE"

for container in "${CONTAINERS[@]}"; do
    if [ "$(docker ps -a -q -f name=^/${container}$)" ]; then
        echo "🧹 Eliminando contenedor conflictivo: $container" | tee -a "$LOG_FILE"
        docker rm -f "$container" >> "$LOG_FILE" 2>&1
    else
        echo "✅ No hay conflicto con: $container" | tee -a "$LOG_FILE"
    fi
done

# Preguntar si desea eliminar volúmenes huérfanos
read -p "¿Deseas eliminar volúmenes huérfanos? (s/n): " remove_vols
if [[ "$remove_vols" =~ ^[sS]$ ]]; then
    echo "🧼 Eliminando volúmenes huérfanos..." | tee -a "$LOG_FILE"
    docker volume prune -f >> "$LOG_FILE" 2>&1
fi

# Preguntar si desea eliminar imágenes dangling
read -p "¿Deseas eliminar imágenes huérfanas (dangling)? (s/n): " remove_imgs
if [[ "$remove_imgs" =~ ^[sS]$ ]]; then
    echo "🧼 Eliminando imágenes huérfanas..." | tee -a "$LOG_FILE"
    docker image prune -f >> "$LOG_FILE" 2>&1
fi

echo "🚀 Reconstruyendo y levantando contenedores..." | tee -a "$LOG_FILE"
docker-compose up --build | tee -a "$LOG_FILE"
