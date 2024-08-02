package group6.ecommerce.payload.response;

import group6.ecommerce.model.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorResponse {
    private int id;
    private String colorName;
    private String colorPicker;

    public ColorResponse(Color item) {
        this.id = item.getId();
        this.colorName = item.getColorName();
        this.colorPicker = item.getColorPicker();
    }
}
