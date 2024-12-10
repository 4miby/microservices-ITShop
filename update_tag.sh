#!/bin/bash

# Lấy giá trị IMAGE_VERSION từ tham số đầu vào
IMAGE_VERSION=$1

# Thay thế giá trị tag trong file values.yaml
sed -i "s/^  tag:.*/  tag: ${IMAGE_VERSION}/" itshop-helm/values.yaml

# Hiển thị thông báo hoàn thành
echo "Updated tag in values.yaml to: ${IMAGE_VERSION}"


