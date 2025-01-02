# VoucherVault Backend

## Overview

The VoucherVault backend is a RESTful API designed to manage user authentication, products, cart operations, and voucher-related functionalities. This API is built using Spring Boot and MongoDB.

## Base URL

VoucherVault API Base URL : https://vouchervault-6do6.onrender.com

## API Endpoints

### Authentication

**POST** /api/authenticate - Login a user.

### Product Management

POST /api/product - Create a new product.

GET /api/product/all - Retrieve all products.

POST /api/product/id - Retrieve product details by ID.

PUT /api/product - Update product details.

DELETE /api/product - Delete a product.

GET /api/product/category/{category} - Retrieve products by category.

User Management

POST /api/user/create-user - Create a new user.

GET /api/user - Get user details by username.

GET /api/user/vouchers - Get vouchers assigned to a user.

POST /api/user/cart/add - Add a product to the user cart.

POST /api/user/cart/remove - Remove a product from the user cart.

GET /api/user/cart - Retrieve products in the user cart.

POST /api/user/cart/apply-vouchers - Apply vouchers to the cart.

Voucher Management

POST /api/voucher/create - Create a new voucher.

GET /api/voucher/all - Retrieve all vouchers.

POST /api/voucher/id - Retrieve voucher details by ID.

PUT /api/voucher - Update voucher details.

DELETE /api/voucher - Delete a voucher.
