# VoucherVault Backend

## Overview

The VoucherVault backend is a RESTful API designed to manage user authentication, products, cart operations, and voucher-related functionalities. This API is built using Spring Boot and MongoDB.

## Base URL

VoucherVault API Base URL : https://vouchervault-6do6.onrender.com

## API Endpoints

### Authentication

**POST** /api/authenticate - Login a user.

   ```sh
   {
  "username": "string",
  "password": "string"
  }
  ```

### Product Management

**POST** /api/product - Create a new product.

   ```sh
   {
  "name": "string",
  "description": "string",
  "price": 0.0,
  "category": "string",
  }
  ```

**GET** /api/product/all - Retrieve all products.

**POST** /api/product/id - Retrieve product details by ID.

   ```sh
   {
  "id": "string"
  }
  ```

**PUT** /api/product - Update product details.

```sh
   {
  "id": "string",
  "name": "string",
  "description": "string",
  "price": 0.0,
  "category": "string",
  }
  ```

**DELETE** /api/product - Delete a product.

```sh
   {
  "id": "string"
  }
  ```

**GET** /api/product/category/{category} - Retrieve products by category.

### User Management

**POST** /api/user/create-user - Create a new user.

   ```sh
   {
  "username": "string",
  "email": "string",
  "password": "string",
  "role": "string"
   }
  ```

**GET** /api/user - Get user details by username.

**GET** /api/user/vouchers - Get vouchers assigned to a user.

**POST** /api/user/cart/add - Add a product to the user cart.

   ```sh
   {
  "id": "string"
   }
  ```

**POST** /api/user/cart/remove - Remove a product from the user cart.

   ```sh
   {
  "id": "string"
   }
  ```

**GET** /api/user/cart - Retrieve products in the user cart.

**POST** /api/user/cart/apply-vouchers - Apply vouchers to the cart.

   ```sh
   {
  "voucherIds": ["string"]
   }
  ```

### Voucher Management

**POST** /api/voucher/create - Create a new voucher.

   ```sh
   {
  "code": "string",
  "category": "string",
  "discountType": "string",
  "discountValue": 0.0,
  "minimumOrderValue": 0.0,
  "validFrom": "YYYY-MM-DDTHH:mm:ss",
  "validTo": "YYYY-MM-DDTHH:mm:ss",
  "emails": ["string"]
   }
  ```

**GET** /api/voucher/all - Retrieve all vouchers.

**POST** /api/voucher/id - Retrieve voucher details by ID.

   ```sh
  {
  "id": "string"
   }
  ```

**PUT** /api/voucher - Update voucher details.

   ```sh
   {
  "id": "string",
  "code": "string",
  "category": "string",
  "discountType": "string",
  "discountValue": 0.0,
  "minimumOrderValue": 0.0,
  "validFrom": "YYYY-MM-DDTHH:mm:ss",
  "validTo": "YYYY-MM-DDTHH:mm:ss",
  "emails": ["string"]
   }
  ```

**DELETE** /api/voucher - Delete a voucher.

   ```sh
   {
  "id": "string"
   }
  ```
