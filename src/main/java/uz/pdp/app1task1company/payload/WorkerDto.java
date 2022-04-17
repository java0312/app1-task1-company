package uz.pdp.app1task1company.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {

    @NotNull(message = "fullName is mandatory")
    private String fullName;

    @NotNull(message = "phoneNumber is mandatory")
    private String phoneNumber;

    @NotNull(message = "departmentId is mandatory")
    private Integer departmentId;

    @NotNull(message = "street is mandatory")
    private String street;

    @NotNull(message = "homeNumber is mandatory")
    private Integer homeNumber;

}
