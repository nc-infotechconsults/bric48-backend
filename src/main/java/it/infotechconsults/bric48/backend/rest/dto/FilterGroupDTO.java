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
public class FilterGroupDTO {
    private LogicOperator operator;
    private List<FilterGroupDTO> groups;
    private List<FilterCriteriaDTO> criterias;
}
