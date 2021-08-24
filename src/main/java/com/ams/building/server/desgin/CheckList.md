# Lamda Expressions: Duyệt Collection

## 1. forEach and Map

Cách thông thường khi duyệt `Map`.
```Java
Map<String, Integer> items = new HashMap<>();
    items.put("A", 10);
    items.put("B", 20);
    items.put("C", 30);
    items.put("D", 40);
    items.put("E", 50);
    items.put("F", 60);

    for (Map.Entry<String, Integer> entry : items.entrySet()) {
	System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
    }
```

Trong Java 8, bạn có thể duyệt `Map` với `forEach` + lambda expression. Trong trường hợp này, cú pháp Lambda tự suy luận kiểu dữ liệu.
```Java
Map<String, Integer> items = new HashMap<>();
    items.put("A", 10);
    items.put("B", 20);
    items.put("C", 30);
    items.put("D", 40);
    items.put("E", 50);
    items.put("F", 60);

    items.forEach((k, v) -> System.out.println("Item : " + k + " Count : " + v));
```

## 2. forEach and List

Cách thông thường khi duyệt `List`.
```Java
List<String> items = new ArrayList<>();
    items.add("A");
    items.add("B");
    items.add("C");
    items.add("D");
    items.add("E");

    for(String item : items){
	System.out.println(item);
    }
```

Trong Java 8, bạn có thể duyệt `List` với `forEach` + lambda expression or method reference.

### 2.1. Sử dụng Lambda Expression
```Java
List<String> items = new ArrayList<>();
    items.add("A");
    items.add("B");
    items.add("C");
    items.add("D");
    items.add("E");

    //lambda
    items.forEach(item -> System.out.println(item));

    //Output : A, B, C, D, E

    //or
    items.forEach(item -> {
        if("C".equals(item)){
	    System.out.println(item);
        }
    });

    //Output : C
```

### 2.2. Sử dụng tham chiếu - method reference
```java
//method reference
items.forEach(System.out::println);

//Output : A,B,C,D,E
```
References: [Method references](../../../../blob/master/Java%208/4.%20Method%20References.md)

