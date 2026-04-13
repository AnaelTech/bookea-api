package com.anaeltech.bookea_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/client")
@Tag(name = "Client", description = "Client related operations")
public class ClientController {
}
