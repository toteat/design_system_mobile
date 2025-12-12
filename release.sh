#!/bin/bash

# Script para generar una release con tag
# Uso: ./release.sh <version> [mensaje]
# Ejemplo: ./release.sh 1.1.0 "Mejoras en SwitchButtonContainer"

set -e

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Función para mostrar uso
show_usage() {
    echo -e "${BLUE}Uso:${NC} ./release.sh <version> [mensaje]"
    echo -e "${BLUE}Ejemplo:${NC} ./release.sh 1.1.0 \"Mejoras en SwitchButtonContainer\""
    exit 1
}

# Verificar argumentos
if [ -z "$1" ]; then
    echo -e "${RED}Error: Debe especificar una versión${NC}"
    show_usage
fi

VERSION=$1
MESSAGE=${2:-"Release v$VERSION"}
TAG_NAME="v$VERSION"

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}  Generando Release $TAG_NAME${NC}"
echo -e "${BLUE}========================================${NC}\n"

# Verificar si hay cambios sin commitear
if [[ -n $(git status -s) ]]; then
    echo -e "${YELLOW}Hay cambios sin commitear:${NC}"
    git status -s
    echo ""
    read -p "¿Desea commitear estos cambios? (y/n) " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        read -p "Mensaje del commit: " COMMIT_MSG
        git add .
        git commit -m "$COMMIT_MSG"
        echo -e "${GREEN}✓ Cambios commiteados${NC}\n"
    else
        echo -e "${RED}✗ Cancelando release${NC}"
        exit 1
    fi
fi

# Verificar si el tag ya existe
if git rev-parse "$TAG_NAME" >/dev/null 2>&1; then
    echo -e "${RED}✗ El tag $TAG_NAME ya existe${NC}"
    read -p "¿Desea eliminarlo y recrearlo? (y/n) " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        git tag -d "$TAG_NAME"
        git push origin ":refs/tags/$TAG_NAME" 2>/dev/null || true
        echo -e "${GREEN}✓ Tag eliminado${NC}\n"
    else
        echo -e "${RED}✗ Cancelando release${NC}"
        exit 1
    fi
fi

# Crear el tag
echo -e "${YELLOW}Creando tag $TAG_NAME...${NC}"
git tag -a "$TAG_NAME" -m "$MESSAGE"
echo -e "${GREEN}✓ Tag creado localmente${NC}\n"

# Mostrar resumen
echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}  Resumen${NC}"
echo -e "${BLUE}========================================${NC}"
echo -e "Tag: ${GREEN}$TAG_NAME${NC}"
echo -e "Mensaje: ${GREEN}$MESSAGE${NC}"
echo -e "Rama actual: ${GREEN}$(git branch --show-current)${NC}"
echo -e "Último commit: ${GREEN}$(git log -1 --oneline)${NC}\n"

# Preguntar si desea pushear
read -p "¿Desea pushear los cambios y el tag ahora? (y/n) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo -e "${YELLOW}Pusheando cambios...${NC}"
    git push origin $(git branch --show-current)
    echo -e "${GREEN}✓ Cambios pusheados${NC}\n"

    echo -e "${YELLOW}Pusheando tag...${NC}"
    git push origin "$TAG_NAME"
    echo -e "${GREEN}✓ Tag pusheado${NC}\n"

    echo -e "${GREEN}========================================${NC}"
    echo -e "${GREEN}  ✓ Release completada exitosamente${NC}"
    echo -e "${GREEN}========================================${NC}"
    echo -e "Ver en GitHub: ${BLUE}https://github.com/OWNER/REPO/releases/tag/$TAG_NAME${NC}"
else
    echo -e "${YELLOW}========================================${NC}"
    echo -e "${YELLOW}  Tag creado localmente${NC}"
    echo -e "${YELLOW}========================================${NC}"
    echo -e "Para pushear manualmente ejecute:"
    echo -e "  ${BLUE}git push origin $(git branch --show-current)${NC}"
    echo -e "  ${BLUE}git push origin $TAG_NAME${NC}"
fi

