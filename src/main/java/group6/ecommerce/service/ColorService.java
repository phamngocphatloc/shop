package group6.ecommerce.service;

import group6.ecommerce.model.Color;
import group6.ecommerce.payload.response.ColorResponse;

import java.util.List;

public interface ColorService {
    Color findColorByName(String name);

    List<ColorResponse> ListColorResponse ();
}
