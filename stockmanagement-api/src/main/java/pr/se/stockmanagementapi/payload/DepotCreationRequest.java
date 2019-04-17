package pr.se.stockmanagementapi.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DepotCreationRequest {
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
