package uz.pdp.app1task1company.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {

    @NotNull(message = "corpName is mandatory")
    private String corpName;

    @NotNull(message = "directorName is mandatory")
    private String directorName;

    @NotNull(message = "street is mandatory")
    private String street;

    @NotNull(message = "homeNumber is mandatory")
    private Integer homeNumber;

}
