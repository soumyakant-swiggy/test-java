package com.freightfox.pdf.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(
            title = "Purchase Order System [PO]"
            , version = "1.0"
            , description = "Purchase Order System API endpoints"
            , termsOfService = "https://twinleaves.co/terms-of-service"
            , contact = @Contact(email = "info@twinleaves.co")
            , license = @License(url = "https://twinleaves.co/api-license")
        )
)
public class OpenApiSwagger {
}
