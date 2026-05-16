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
GET /products?name=myProduct1

#### Response (200 OK)
**[Response Fields]**
| 필드명 | 타입 | 설명 |
| :--- | :--- | :--- |
| `id` | Long | 상품 고유 ID |
| `name` | String | 상품명 |
| `price` | Integer | 판매 가격 |
| `stock` | Integer | 현재 재고 수량 |
| `quantity` | Integer | 구매/재고 추가 요청용 필드 |

**[Response Example]**
{
  "id": 1,
  "name": "myProduct1",
  "price": 10000,
  "stock": 100,
  "quantity": 0
}

### 상품 구매
하나 이상의 상품을 한 번에 구매

- **Method:** `POST`
- **URL:** `/products/purchase`
- **Description:** 상품 ID와 구매 수량을 포함한 객체 리스트를 전달하여 주문을 생성합니다.

#### Request 
**[Request Fields]**
| 필드명 | 타입 | 필수 여부 | 설명 |
| :--- | :--- | :--- | :--- |
| `orderProducts` | Array | Y | 주문 상품 리스트 |
| `orderProducts[].id` | Long | Y | 상품 고유 ID |
| `orderProducts[].quantity` | Integer | Y | 구매 수량 |

**[Request Example]**
{
  "orderProducts": [
    {
      "id": 1,
      "quantity": 10
    },
    {
      "id": 2,
      "quantity": 1
    }
  ]
}

#### Response (200 OK)
**[Response Fields]**
String 형식으로 구매 내역과 총 금액을 텍스트로 반환합니다.

**[Response Example]**
myProduct1 10개 구매 (100000원)
myProduct2 1개 구매 (10000원)
총 금액: 110000

---

## 관리자 (Admin)

### 상품 등록
새로운 상품 정보를 시스템에 등록

- **Method:** `POST`
- **URL:** `/admin/products`
- **Description:** 동일한 이름의 상품이 존재할 경우 예외를 발생시킵니다.

#### Request 
**[Request Fields]**
| 필드명 | 타입 | 필수 여부 | 설명 |
| :--- | :--- | :--- | :--- |
| `name` | String | Y | 상품명 |
| `price` | Integer | Y | 판매 가격 |
| `stock` | Integer | Y | 초기 재고 수량 |

**[Request Example]**
{
  "name": "myProduct2",
  "price": 10000,
  "stock": 100
}

#### Response (200 OK)
**[Response Fields]**
String 형식으로 등록 완료 메시지를 반환합니다.

**[Response Example]**
상품 등록 완료

### 재고 추가
기존 상품의 재고를 추가

- **Method:** `PATCH`
- **URL:** `/admin/products/stock`
- **Description:** 입력된 상품 ID를 식별하여 수량만큼 재고를 늘립니다.

#### Request 
**[Request Fields]**
| 필드명 | 타입 | 필수 여부 | 설명 |
| :--- | :--- | :--- | :--- |
| `id` | Long | Y | 재고를 추가할 상품 ID |
| `quantity` | Integer | Y | 추가 수량 |

**[Request Example]**
{
  "id": 1,
  "quantity": 50
}

#### Response (200 OK)
**[Response Fields]**
| 필드명 | 타입 | 설명 |
| :--- | :--- | :--- |
| `name` | String | 상품명 |
| `stock` | Integer | 추가 후 최종 재고 수량 |

**[Response Example]**
{
  "name": "myProduct1",
  "stock": 150
}

### 상품 삭제
상품 삭제, 한번에 여러 개 가능

- **Method:** `DELETE`
- **URL:** `/admin/products`
- **Description:** 입력된 ID 목록에 해당하는 상품을 삭제하고, 남은 전체 상품 목록을 반환합니다.

#### Request 
**[Request Fields]**
배열(Array) 형태로 삭제할 상품의 ID 목록을 직접 전달합니다.

**[Request Example]**
[1, 2]

#### Response (200 OK)
**[Response Fields]**
| 필드명 | 타입 | 설명 |
| :--- | :--- | :--- |
| `[]` | Array | 삭제 후 남은 상품 목록 |
| `[].name` | String | 상품명 |
| `[].stock` | Integer | 남은 재고 수량 |

**[Response Example]**
[
  {
    "name": "product3",
    "stock": 50
  },
  {
    "name": "product4",
    "stock": 20
  }
]