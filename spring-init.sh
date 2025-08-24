#!/usr/bin/env bash
set -euo pipefail

# spring-init.sh
# Initialize a Spring Boot project using Spring Initializr via the Spring CLI.
# Usage:
#   ./spring-init.sh <project-name> [options]
#
# Options:
#   -g <groupId>           Group ID (default: com.example)
#   -D <deps>              Comma-separated dependencies (default: web)
#   -b <build>             Build tool: maven|gradle (default: maven)
#   -J <javaVersion>       Java version (default: 21)
#   -B <bootVersion>       Spring Boot version (optional; if omitted, server default)
#   -p <packaging>         Packaging: jar|war (default: jar)
#   -l <language>          Language: java|kotlin|groovy (default: java)
#   -f                     Force overwrite if target dir exists
#   -r                     Run the app after generation (maven: ./mvnw spring-boot:run, gradle: ./gradlew bootRun)
#   -h                     Show help
#
# Examples:
#   ./spring-init.sh demo
#   ./spring-init.sh demo -g io.acme -D web,jpa,validation -b gradle -J 21 -r

usage() {
  sed -n '2,200p' "$0" | sed '/^usage()/,$d'
}

if [[ ${1:-} == "-h" || ${1:-} == "--help" || $# -eq 0 ]]; then
  usage
  exit 0
fi

if ! command -v spring >/dev/null 2>&1; then
  echo "Error: spring CLI not found. Install via Homebrew: brew tap spring-io/tap && brew install spring-boot" >&2
  exit 1
fi

PROJECT_NAME=${1:-}
shift || true
if [[ -z "$PROJECT_NAME" ]]; then
  echo "Error: project name is required." >&2
  usage
  exit 1
fi

# Defaults
GROUP_ID="com.example"
DEPS="web"
BUILD="maven"
JAVA_VERSION="21"
BOOT_VERSION=""
PACKAGING="jar"
LANGUAGE="java"
FORCE="false"
RUN_AFTER="false"

# Parse options
while getopts ":g:D:b:J:B:p:l:frh" opt; do
  case $opt in
  g) GROUP_ID="$OPTARG" ;;
  D) DEPS="$OPTARG" ;;
  b) BUILD="$OPTARG" ;;
  J) JAVA_VERSION="$OPTARG" ;;
  B) BOOT_VERSION="$OPTARG" ;;
  p) PACKAGING="$OPTARG" ;;
  l) LANGUAGE="$OPTARG" ;;
  f) FORCE="true" ;;
  r) RUN_AFTER="true" ;;
  h)
    usage
    exit 0
    ;;
  :)
    echo "Option -$OPTARG requires an argument" >&2
    exit 1
    ;;
  \?)
    echo "Unknown option: -$OPTARG" >&2
    exit 1
    ;;
  esac
done

# Validate build
case "$BUILD" in
maven | gradle) ;;
*)
  echo "Error: --build must be maven or gradle" >&2
  exit 1
  ;;
esac

# Compute package name: groupId + sanitized project name
SANITIZED_PROJ=$(echo "$PROJECT_NAME" | tr '-' '_' | tr -cd '[:alnum:]_')
PACKAGE_NAME="${GROUP_ID}.${SANITIZED_PROJ}"

TARGET_DIR="$PROJECT_NAME"
if [[ -d "$TARGET_DIR" ]]; then
  if [[ "$FORCE" != "true" ]]; then
    echo "Error: target directory '$TARGET_DIR' already exists. Use -f to overwrite." >&2
    exit 1
  else
    echo "Overwriting existing directory '$TARGET_DIR'..."
  fi
fi

# Build base spring init command
INIT_CMD=(spring init --extract
  --build "$BUILD"
  --language "$LANGUAGE"
  --java-version "$JAVA_VERSION"
  --packaging "$PACKAGING"
  -g "$GROUP_ID"
  -a "$PROJECT_NAME"
  -n "$PROJECT_NAME"
  --package-name "$PACKAGE_NAME"
  -d "$DEPS")

if [[ -n "$BOOT_VERSION" ]]; then
  INIT_CMD+=(--boot-version "$BOOT_VERSION")
fi

INIT_CMD+=("$TARGET_DIR")

# If forcing, allow overwrite of files (-f)
if [[ "$FORCE" == "true" ]]; then
  INIT_CMD=("${INIT_CMD[@]:0:1}" -f "${INIT_CMD[@]:1}")
fi

echo "> Generating project: $PROJECT_NAME"
echo "> Group:            $GROUP_ID"
echo "> Package:          $PACKAGE_NAME"
echo "> Build:            $BUILD"
echo "> Java:             $JAVA_VERSION"
echo "> Packaging:        $PACKAGING"
echo "> Dependencies:     $DEPS"
if [[ -n "$BOOT_VERSION" ]]; then echo "> Boot Version:     $BOOT_VERSION"; fi

"${INIT_CMD[@]}"

echo "> Project created at: $TARGET_DIR"

if [[ "$RUN_AFTER" == "true" ]]; then
  echo "> Starting application..."
  pushd "$TARGET_DIR" >/dev/null
  if [[ "$BUILD" == "maven" ]]; then
    ./mvnw spring-boot:run
  else
    ./gradlew bootRun
  fi
  popd >/dev/null
else
  echo "> To run:"
  if [[ "$BUILD" == "maven" ]]; then
    echo "  cd $TARGET_DIR && ./mvnw spring-boot:run"
  else
    echo "  cd $TARGET_DIR && ./gradlew bootRun"
  fi
fi
