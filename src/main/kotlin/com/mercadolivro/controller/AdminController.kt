package com.mercadolivro.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/admin")
class AdminController {

    @GetMapping("/report")
    fun report() = "This is a report. Only Admin can see it"

}
