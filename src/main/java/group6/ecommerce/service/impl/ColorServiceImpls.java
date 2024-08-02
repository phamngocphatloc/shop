package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.ColorRepository;
import group6.ecommerce.model.Color;
import group6.ecommerce.payload.response.ColorResponse;
import group6.ecommerce.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor
public class ColorServiceImpls implements ColorService {
    private final ColorRepository colorRepository;
    @Override
    public Color findColorByName(String name) {
        return colorRepository.findColorByName(name);
    }

    @Override
    public List<ColorResponse> ListColorResponse() {
        ArrayList<ColorResponse> response = new ArrayList<>();
        colorRepository.findAll().stream().forEach(item -> {
            response.add(new ColorResponse(item));
        });
        return response;
    }
}
