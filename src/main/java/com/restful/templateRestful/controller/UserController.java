package com.restful.templateRestful.controller;

import com.restful.templateRestful.dto.request.UserRequestDTO;
import com.restful.templateRestful.dto.response.ResponseData;
import com.restful.templateRestful.dto.response.ResponseError;
import com.restful.templateRestful.dto.response.ResponseSuccess;
import com.restful.templateRestful.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @Operation(summary = "summary", description = "description",responses = {
            @ApiResponse(responseCode = "201", description = "User added successfully",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject (name= "ex_name", summary = "ex summary",
                value = """
                        {
                            "status": 201,
                            "message": "User added successfully",
                            "data": 1
                        }
                        """))
            )
    })
    @PostMapping
    public ResponseSuccess addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        int result = userService.addUser(userRequestDTO);
        return new ResponseSuccess(HttpStatus.CREATED, "Add user", result);
    }

    @PutMapping("/{userId}")
    public ResponseData<Integer> updateUser(@PathVariable("userId") @Min(1) int userId, @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), " Update successfully");
    }

    @PatchMapping("/{userId}")
    public ResponseError changeStatus(@PathVariable("userId") int userId, @RequestParam boolean status) {
        return new ResponseError(HttpStatus.ACCEPTED.value(), " Status didn't changed successfully");
    }

    @DeleteMapping("/{userId}")
    public ResponseSuccess deleteUser(@Min(1) @PathVariable("userId") int userId) {
        return new ResponseSuccess(HttpStatus.ACCEPTED, "User Delete");
    }

    @GetMapping
    public ResponseSuccess getUser(
            @RequestParam(required = false) int pageNo,
            @RequestParam int id,
            @RequestParam(defaultValue = "123") String email
            ) {
        return new ResponseSuccess(HttpStatus.OK, "NEW OBJECT");
    }

    @RequestMapping(method = RequestMethod.GET, headers = "apiKey=v1.1")
    public ResponseSuccess getUser_2(
            @RequestParam(required = false) int pageNo,
            @RequestParam  @Min(1) int id,
            @RequestParam(defaultValue = "123") String email
    ) {
        return new ResponseSuccess(HttpStatus.OK, "NEW OBJECT");
    }
}
