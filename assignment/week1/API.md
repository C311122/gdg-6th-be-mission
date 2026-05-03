# 기능 명세

## 소비자(User)

### 상품 조회
특정 상품명을 입력하여 상품 정보를 조회

- **Method:** `GET`
- **URL:** `/products`
- **Description:** 상품명(name)을 쿼리 스트링으로 전달하여 정보를 조회합니다.

#### Request 
**[Query Parameters]**
| 필드명 | 타입 | 필수 여부 | 설명 |
| :--- | :--- | :--- | :--- |
| `name` | String | Y | 검색할 상품명 |

**[Request Example]**
```text
GET /products?name=myProduct1
```

#### Response (200 OK)
**[Response Fields]**
| 필드명 | 타입 | 설명 |
| :--- | :--- | :--- |
| `productId` | Long | 상품 고유 ID |
| `name` | String | 상품명 |
| `price` | Integer | 판매 가격 |
| `quantity` | Integer | 현재 재고 수량 |

**[Response Example]**
```json
{
  "productId": 1,
  "name": "myProduct1",
  "price": 10000,
  "quantity": 1
}
```

### 상품 구매
하나 이상의 상품을 한 번에 구매

- **Method:** `POST`
- **URL:** `/orders`
- **Description:** 상품 ID와 구매 수량을 전달하여 주문을 생성

#### Request 
**[Request Fields]**
| 필드명 | 타입 | 필수 여부 | 설명 |
| :--- | :--- | :--- | :--- |
| `orderItems` | Array | Y | 주문 상품 리스트 |
| `orderItems[].productId` | Long | Y | 상품 고유 ID |
| `orderItems[].orderQuantity` | Integer | Y | 구매 수량 |

**[Request Example]**
```json
{
  "orderItems": [
    {
      "productId": 1,
      "orderQuantity": 10
    },
    {
      "productId": 2,
      "orderQuantity": 1
    }
  ]
}
```

#### Response (201 Created)
**[Response Fields]**
| 필드명 | 타입 | 설명 |
| :--- | :--- | :--- |
| `orderId` | String | 주문 번호 |
| `totalAmount` | Integer | 총 결제 금액 |
| `purchasedItems` | Array | 구매 완료된 상품 목록 |
| `purchasedItems[].name` | String | 상품명 |
| `purchasedItems[].orderQuantity` | Integer | 구매 수량 |
| `purchasedItems[].totalPrice` | Integer | 해당 상품 총 금액 |

**[Response Example]**
```json
{
  "orderId": "202605030000001",
  "totalAmount": 110000,
  "purchasedItems": [
    { "name": "myProduct1", "orderQuantity": 10, "totalPrice": 100000 },
    { "name": "myProduct2", "orderQuantity": 1, "totalPrice": 10000 }
  ]
}
```

## 관리자 (Admin)

### 상품 등록
새로운 상품 정보를 시스템에 등록

- **Method:** `POST`
- **URL:** `/products`
- **Description:** 동일한 이름의 상품이 존재할 경우 409 Conflict를 반환

#### Request 
**[Request Fields]**
| 필드명 | 타입 | 필수 여부 | 설명 |
| :--- | :--- | :--- | :--- |
| `name` | String | Y | 상품명 |
| `price` | Integer | Y | 판매 가격 |
| `quantity` | Integer | Y | 초기 재고 수량 |

**[Request Example]**
```json
{
  "name": "myProduct2",
  "price": 10000,
  "quantity": 100
}
```

#### Response (201 Created)
**[Response Fields]**
| 필드명 | 타입 | 설명 |
| :--- | :--- | :--- |
| `productId` | Long | 생성된 상품 ID |
| `name` | String | 상품명 |
| `price` | Integer | 판매 가격 |
| `quantity` | Integer | 초기 재고 수량 |

**[Response Example]**
```json
{
  "productId": 2,
  "name": "myProduct2",
  "price": 10000,
  "quantity": 100
}
```

### 재고 추가
기존 상품의 재고를 추가

- **Method:** `PATCH`
- **URL:** `/products/{productId}`
- **Description:** Path Variable로 상품을 식별, 입력된 수량만큼 재고를 늘린다

#### Request 
**[Path Variables]**
| 필드명 | 타입 | 필수 여부 | 설명 |
| :--- | :--- | :--- | :--- |
| `productId` | Long | Y | 재고를 추가할 상품 ID |

**[Request Fields]**
| 필드명 | 타입 | 필수 여부 | 설명 |
| :--- | :--- | :--- | :--- |
| `addQuantity` | Integer | Y | 추가 수량 |

**[Request Example]**
```json
{
  "addQuantity": 50
}
```

#### Response (200 OK)
**[Response Fields]**
| 필드명 | 타입 | 설명 |
| :--- | :--- | :--- |
| `name` | String | 상품명 |
| `quantity` | Integer | 추가 후 최종 재고 수량 |

**[Response Example]**
```json
{
  "name": "myProduct1",
  "quantity": 150
}
```

### 상품 삭제
상품 삭제, 한번에 여러개 가능

- **Method:** `DELETE`
- **URL:** `/products`
- **Description:** 상품을 삭제

#### Request 
**[Query Parameters]**
| 필드명 | 타입 | 필수 여부 | 설명 |
| :--- | :--- | :--- | :--- |
| `deleteIds` | Array | Y | 삭제할 상품 ID 목록 |

**[Request Example]**
```json
{
  "deleteIds": [1, 2]
}
```

#### Response (200 OK)
**[Response Fields]**
| 필드명 | 타입 | 설명 |
| :--- | :--- | :--- |
| `remainingProducts` | Array | 삭제 후 남은 상품 목록 |
| `remainingProducts[].name` | String | 상품명 |
| `remainingProducts[].quantity` | Integer | 남은 재고 수량 |

**[Response Example]**
```json
{
  "remainingProducts": [
    { "name": "product2", "quantity": 50 }
  ]
}
```