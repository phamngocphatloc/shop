package group6.ecommerce.controller;

import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.ReviewRequest;
import group6.ecommerce.payload.response.HttpResponse;
import group6.ecommerce.payload.response.ReviewResponse;
import group6.ecommerce.service.ReviewSerive;
import group6.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final UserService userService;
    private final ReviewSerive reviewSerive;
    @PostMapping("add")
    public ResponseEntity<HttpResponse> ReviewProduct (@RequestBody ReviewRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users principal = (Users) authentication.getPrincipal();
        Users userLogin = userService.findById(principal.getId());
        ReviewResponse response = reviewSerive.review(request,userLogin);
        return ResponseEntity.ok(new HttpResponse(HttpStatus.OK.value(),"success",response));
    }
}
