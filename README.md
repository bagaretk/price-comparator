📦 Price Comparator API

A Spring Boot REST API that compares product prices across multiple stores using data from .csv files.

Features Implemented:
- Parse multiple price and discount .csv files
- Filter prices by product, store, category, brand, and date
- Compute effective prices including discounts
- Optimize user shopping basket for cost savings
- Show best and new discounts
- Recommend substitutes based on value per unit
- Register and check price alerts
- Return price history for products

Project Structure:

- src/
- ├── main/java/accesa/PriceComparator/
- │   ├── controller/        # REST controllers
- │   ├── model/             # DTOs and core models (Product, PriceRecord, DiscountRecord, etc.)
- │   ├── service/           # Business logic (PriceService)
- │   ├── csv/               # CsvLoader (loads price and discount data)
- ├── resources/static/      # HTML frontend & input CSV files

How to Run:

Requirements:
- Java 17+
- Maven
- IDE (Eclipse, IntelliJ) or terminal

Steps:
./mvnw spring-boot:run

Access frontend (optional, test i used for basket optimization):
http://localhost:8080/basket.html

CSV Format:

Place your .csv files in:
src/main/resources/static/

Example price file (lidl_2025-05-01.csv):
product_id;product_name;product_category;brand;package_quantity;package_unit;price;currency

Example discount file (lidl_discounts_2025-05-01.csv):
product_id;product_name;brand;package_quantity;package_unit;product_category;from_date;to_date;percentage_of_discount

API Endpoints:

Price API:
GET /api/prices?productId=&store=&category=&brand=&date=
Returns filtered price records.

Basket Optimization:
POST /api/basket/optimize
Request example:
{ "basket": ["lapte zuzu", "pâine albă"] }
Response example:
[
  { "productName": "lapte zuzu", "store": "Lidl", "price": 9.90 },
  ...
]

Discounts:
GET /api/discounts/best         - top % discounts
GET /api/discounts/new?days=2   - new discounts in last X days

Substitutes:
GET /api/substitutes?productName=lapte
Returns list of alternatives sorted by price per unit.

Price Alerts:
POST /api/alerts
Request example:
{ "productName": "lapte zuzu", "targetPrice": 8.00 }
Triggers alert if price target is met.

Tests:
- PriceControllerUnitTest — verifies price filtering without Spring context
- Other tests (BasketController, AlertController, etc.) can be added similarly.

Completed Features (Spec Coverage):
- Daily Shopping Basket Optimization: ✅
- Best Discounts: ✅
- New Discounts: ✅
- Dynamic Price History + Filters: ✅
- Product Substitutes by Unit Price: ✅
- Custom Price Alerts: ✅

Author:
Built by bagaretk
