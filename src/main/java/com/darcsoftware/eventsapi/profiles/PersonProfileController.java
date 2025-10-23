// profiles/PersonProfileController.java
package com.darcsoftware.eventsapi.profiles;

import com.darcsoftware.eventsapi.common.PageResponse;
import com.darcsoftware.eventsapi.profiles.dto.PartyWithPersonProfileResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles/people")
public class PersonProfileController {

    private final PersonProfileService service;

    public PersonProfileController(PersonProfileService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<PartyWithPersonProfileResponse> listPeople(
            @RequestParam(name = "q", required = false) String q,
            @RequestParam(name = "limit", defaultValue = "50") int limit,
            @RequestParam(name = "offset", defaultValue = "0") int offset
    ) {
        return service.listPeople(q, Math.max(1, Math.min(limit, 200)), Math.max(0, offset));
    }
}