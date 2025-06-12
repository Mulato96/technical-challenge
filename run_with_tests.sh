#!/bin/bash
set -e

echo "🧪 Ejecutando pruebas de microservicios..."


rm -rf product/target inventory/target

docker-compose -f docker-compose.yml -f docker-compose.test.yml up --build --abort-on-container-exit test-productos test-inventario

echo "✅ Pruebas exitosas. Levantando aplicación..."

docker-compose up --build -d productos-service inventario-service
