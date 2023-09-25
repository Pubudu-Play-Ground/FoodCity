package lk.ijse.dep11.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Customer {
    String id;
    String name;
    String contact;
    String fooditem;
    String status;
    int qty;

    public Customer(String id, String name, String status, String s) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
}
