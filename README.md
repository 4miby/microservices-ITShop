# Micorservice Project

Đây là một dự án MICROservices bao gồm các service sau:
- **API GATEWAY**
- **PRODUCT SERVICE**
- **INVENTORY SERVICE**
- **ORDER SERVICE**
- **IDENTITY SERVICE**
- **NOTIFICATION SERVICE**

## YÊU CẦU HỆ THỐNG

- **JAVA 21**
- **DOCKER**
- **DOCKER COMPOSE**
- **MAVEN**

## HƯỚNG DẪN CÀI ĐẶT VÀ CHẠY

1. **CLONE REPOSITORY VỀ MÁY LOCAL:**

   ```bash
   git clone <repository_url>
   cd <project_folder>
   ```

2. **CHẠY DOCKER COMPOSE ĐỂ KHỞI ĐỘNG CÁC CONTAINER CẦN THIẾT:**

   ```bash
   docker-compose up -d
   ```

3. **CHẠY TỪNG SERVICE:**

   Đối với mỗi service (api-gateway, product-service, inventory-service, order-service, identity-service, notification-service), thực hiện các bước sau:

   ```bash
   cd <service_folder>
   mvn spring-boot:run
   ```

4. **TRUY CẬP API GATEWAY:**

   API Gateway sẽ chạy tại `http://localhost:8080` (hoặc port được cấu hình).

## CẤU TRÚC PROJECT

- `api-gateway`: **API GATEWAY SERVICE**
- `product-service`: **QUẢN LÝ SẢN PHẨM**
- `inventory-service`: **QUẢN LÝ KHO HÀNG**
- `order-service`: **QUẢN LÝ ĐƠN HÀNG**
- `identity-service`: **XÁC THỰC VÀ PHÂN QUYỀN**
- `notification-service`: **GỬI THÔNG BÁO**

## FRONTEND

Frontend được phát triển bằng **REACT**. Để chạy frontend:
``` bash
cd frontend
npm install
npm start
```

## CÀI ĐẶT BẰNG K8S

1. **CHẠY CÁC CONTAINER CẦN THIẾT CHO ỨNG DỤNG:**

   Chạy các container cần thiết bằng câu lệnh sau:
   ``` bash
   cd k8s
   kubectl apply -f infrastructure
   ```
   Đợi cho các container này hoàn thành chạy (trạng thái running)
   
   Kiểm tra bằng câu lệnh:
   ``` bash
   kubectl get pods
   ```

2. **CHẠY CÁC SERVICE APPLICATION:**

   ``` bash
   kubectl apply -f applications
   ```

3. **CHẠY NGINX INGRESS CONTROLLER:**

   ``` bash
   minikube addons enable ingress
   kubectl apply -f ingress
   ```
   
   Chạy lệnh để lấy ip của minikube :
   
   ``` bash
   kubectl get ingress 
   ```
   
   Thêm vào file /etc/hosts:
   
   ``` bash
   <minikube ip> itshop.k8s
   <minikube ip> api.itshop.k8s
   ```



