package it.infotechconsults.bric48.backend.rest.dto;

import java.util.List;

import it.infotechconsults.bric48.backend.enums.LogicOperator;
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
public class FiltersDTO {
    private List<String> fields;
    private List<FilterCriteriaDTO> criterias;
    private List<FilterGroupDTO> groups;
    private LogicOperator operator;
}
