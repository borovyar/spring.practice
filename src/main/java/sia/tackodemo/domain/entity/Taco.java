package sia.tackodemo.domain.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.List;

@Data
public class Taco {

    @NotBlank
    @Size(min = 5, message = "Name should be at least 5 characters long")
    private String name;

    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<String> ingredients;

}
