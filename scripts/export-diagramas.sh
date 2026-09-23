#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
mkdir -p docs/diagramas
npx -y @mermaid-js/mermaid-cli -i docs/arquitectura.md -o docs/diagramas/arquitectura.md -a docs/diagramas
printf 'Diagramas exportados en docs/diagramas\n'
