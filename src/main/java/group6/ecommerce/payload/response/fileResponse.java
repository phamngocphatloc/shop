package group6.ecommerce.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class fileResponse {
    private String link;

    public fileResponse(String link){
        this.link = link;
    }
}
