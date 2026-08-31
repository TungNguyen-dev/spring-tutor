#!/bin/bash

# 1. Kiểm tra tham số arg 1 (JAR file)
if [ -z "$1" ]; then
    echo "Error: Thiếu tham số file JAR!"
    echo "Cú pháp: $0 <duong-dan-file-jar> [duong-dan-file-env]"
    exit 1
fi

JAR_FILE="$1"
# Cho phép tùy chỉnh file .env ở arg 2 (mặc định là .env nếu không truyền)
ENV_FILE="${2:-.env}"

# 2. Kiểm tra file .env và load biến môi trường
if [ -f "$ENV_FILE" ]; then
    echo "Loading environment variables from $ENV_FILE..."

    set -a
    # shellcheck disable=SC1090
    source <(grep -v '^#' "$ENV_FILE" | grep -v '^[[:space:]]*$')
    set +a
else
    echo "Warning: File $ENV_FILE không tồn tại. Tiếp tục chạy với biến mặc định."
fi

# 3. Kiểm tra file JAR có tồn tại không
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: File '$JAR_FILE' không tồn tại!"
    exit 1
fi

# 4. Chạy ứng dụng Java
echo "Starting application: $JAR_FILE"
exec java -jar "$JAR_FILE"