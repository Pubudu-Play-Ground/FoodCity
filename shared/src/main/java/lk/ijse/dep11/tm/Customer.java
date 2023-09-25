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
    int qty;
}
