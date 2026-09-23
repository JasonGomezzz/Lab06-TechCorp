#!/usr/bin/env bash
set -euo pipefail

if [[ "${1:-}" != "--confirmar" ]]; then
  printf 'Uso: scripts/reset-demo.sh --confirmar\n'
  printf 'Elimina el volumen MySQL de esta demostración y vuelve a crear los datos.\n'
  exit 2
fi

raiz="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$raiz"
if [[ ! -f .env ]]; then
  printf 'Falta .env. Copia .env.example y configura los valores locales.\n' >&2
  exit 1
fi

docker compose down -v
docker compose up -d --build
printf 'Base demo recreada. Flyway y DataSeeder restaurarán usuarios y documentos.\n'
