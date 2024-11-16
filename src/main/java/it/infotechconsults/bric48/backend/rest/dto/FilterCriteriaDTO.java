package it.infotechconsults.bric48.backend.rest.dto;

import it.infotechconsults.bric48.backend.enums.QueryOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterCriteriaDTO {
    private String field;
    private QueryOperation operation;
    private Object value;
}
