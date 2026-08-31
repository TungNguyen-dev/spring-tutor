#!/bin/bash

# 1. Kiểm tra tham số arg 1 (JAR file)
if [ -z "$1" ]; then
    echo "Error: Thiếu tham số file JAR!"
    echo "Cú pháp: $0 <duong-dan-file-jar> [duong-dan-file-env]"
    exit 1
fi

JAR_FILE="$1"
ENV_FILE="${2:-.env}"

# 2. Load file .env và xử lý từng dòng
if [ -f "$ENV_FILE" ]; then
    echo "=========================================="
    echo " Loading environment variables from $ENV_FILE..."
    echo "=========================================="

    # Bật tự động export tất cả các biến khai báo
    set -a

    # Đọc file .env từng dòng một, lọc bỏ \r (Windows format)
    while IFS= read -r line || [ -n "$line" ]; do
        # Xóa ký tự \r (CRLF) và khoảng trắng đầu/cuối
        clean_line=$(echo "$line" | tr -d '\r' | xargs)

        # Bỏ qua dòng trống hoặc dòng bắt đầu bằng dấu #
        if [[ -z "$clean_line" || "$clean_line" =~ ^# ]]; then
            continue
        fi

        # Export dòng hiện tại
        export "$clean_line" 2>/dev/null

        # In log debug từng biến được nạp thành công
        key="${clean_line%%=*}"
        val="${clean_line#*=}"
        echo "  - $key = $val"

    done < "$ENV_FILE"

    # Tắt tự động export
    set +a
    echo "=========================================="
else
    echo "Warning: File $ENV_FILE không tồn tại. Tiếp tục chạy với cấu hình mặc định."
fi

# 3. Kiểm tra file JAR
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: File '$JAR_FILE' không tồn tại!"
    exit 1
fi

# 4. Chạy ứng dụng Java
echo "Starting application: $JAR_FILE"
exec java -jar "$JAR_FILE"