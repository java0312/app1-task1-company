package uz.pdp.app1task1company.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    @NotNull(message = "name is mandatory")
    private String name;

    @NotNull(message = "companyId is mandatory")
    private Integer companyId;

}
