package com.caiodorn.codingtests.gamesys.user.rest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotBlank
    @Pattern(
            regexp = "^[a-zA-Z0-9]*$",
            message = "A valid username should be alphanumerical without spaces"
    )
    @ApiModelProperty(
            example = "JohnDoe"
    )
    private String userName;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{4,}$",
            message = "A valid password is composed of at least four characters, at least one upper case character, at least one number"
    )
    @ApiModelProperty(
            example = "vAl1d",
            position = 1
    )
    private String password;

    @NotBlank
    @Pattern(
            regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))",
            message = "Valid format: yyyy-mm-dd"
    )
    @ApiModelProperty(
            example = "1990-12-31",
            position = 2
    )
    private String dob;

    @NotBlank
    @Pattern(
            regexp = "^([0-9]{3})-([0-9]{2})-([0-9]{4})$",
            message = "Valid format: nnn-nn-nnnn, where 'n' is a number"
    )
    @ApiModelProperty(
            example = "111-22-3333",
            position = 3
    )
    private String ssn;

}
