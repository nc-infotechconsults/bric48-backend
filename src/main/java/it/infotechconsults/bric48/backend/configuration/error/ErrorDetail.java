package it.infotechconsults.bric48.backend.configuration.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDetail {
    private String message;
    private String type;
}
