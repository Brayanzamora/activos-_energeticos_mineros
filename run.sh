#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")" && pwd)"
SRC="$ROOT/src"
OUT="$ROOT/out"
LOCAL_JDK="$ROOT/.tools/jdk"

resolve_javac() {
  if command -v javac >/dev/null 2>&1; then
    command -v javac
    return
  fi
  local nested
  nested="$(find "$LOCAL_JDK" -type f -path '*/bin/javac' 2>/dev/null | head -n 1)"
  if [[ -n "$nested" && -x "$nested" ]]; then
    echo "$nested"
    return
  fi
  echo ""
}

install_portable_jdk() {
  echo "No hay javac en el sistema. Descargando JDK 21 portable..."
  mkdir -p "$ROOT/.tools"
  local archive="$ROOT/.tools/jdk.tar.gz"
  curl -fsSL -o "$archive" \
    "https://api.adoptium.net/v3/binary/latest/21/ga/linux/x64/jdk/hotspot/normal/eclipse?project=jdk"
  rm -rf "$LOCAL_JDK"
  mkdir -p "$ROOT/.tools"
  tar -xzf "$archive" -C "$ROOT/.tools"
  local extracted
  extracted="$(find "$ROOT/.tools" -maxdepth 1 -type d -name 'jdk-21*' | head -n 1)"
  if [[ -z "$extracted" ]]; then
    echo "No se pudo extraer el JDK portable."
    exit 1
  fi
  mv "$extracted" "$LOCAL_JDK"
  rm -f "$archive"
}

JAVAC="$(resolve_javac)"
if [[ -z "$JAVAC" ]]; then
  install_portable_jdk
  JAVAC="$LOCAL_JDK/bin/javac"
fi

JAVA="$(dirname "$JAVAC")/java"

mkdir -p "$OUT"
"$JAVAC" -d "$OUT" "$SRC"/plataforma/*.java
echo "Compilación correcta. Ejecutando plataforma.Main..."
echo
"$JAVA" -cp "$OUT" plataforma.Main
